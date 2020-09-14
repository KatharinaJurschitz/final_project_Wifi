package syd.model.daycare;

import syd.model.daycareentry.DaycareEntry;
import syd.model.daycareentry.DaycareEntryRepository;
import syd.model.guest.Guest;
import syd.model.guest.GuestRepository;
import syd.model.person.host.Host;
import syd.model.person.host.HostRepository;
import syd.model.person.owner.Owner;
import syd.model.person.owner.OwnerRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.function.Consumer;
import static java.time.temporal.ChronoUnit.DAYS;

public class Daycare {
    //private ArrayList <DaycareEntry> entries = new ArrayList<DaycareEntry>();
    private DaycareEntryRepository daycareEntryRepository;
    private GuestRepository guestRepository;
    private OwnerRepository ownerRepository;
    private HostRepository hostRepository;

    public Daycare(DaycareEntryRepository daycareEntryRepository, GuestRepository guestRepository, OwnerRepository ownerRepository, HostRepository hostRepository){
       this.daycareEntryRepository = daycareEntryRepository;
       this.guestRepository = guestRepository;
       this.ownerRepository = ownerRepository;
       this.hostRepository = hostRepository;
    }

    public double getPriceForBooking(LocalDate pFrom, LocalDate pTo, Host pHost) throws Exception{
        checkDate(pFrom,pTo);
        long amountDays = (DAYS.between(pFrom,pTo))+1;
        double price = amountDays * pHost.getPricePerDay();
        return price;
    }

    public String getConfirmMsg(Long pId){
        DaycareEntry entry = daycareEntryRepository.findByid(pId);
        if (entry==null){
            return "There is no entry with this Registration Number.";
        }
        return "Hello " + entry.getOwner().getName() + ". Thank you for your reservation. " + entry.getHost().getName() +
                " is looking forward to caring for your " + entry.getGuest().getType() + " " + entry.getGuest().getName() + " from " +
                DaycareEntry.dateToString(entry.getFromDate()) + " to " + DaycareEntry.dateToString(entry.getToDate()) +
                ". Your reservation No is: " + entry.getId();
    }

    public void checkDate(LocalDate pFrom, LocalDate pTo) throws Exception{
        if (pTo.compareTo(pFrom)<0){
            throw new Exception("From-Date must be smaller than To-Date.");
        }
    }

    public void checkMandatory(String pField)throws Exception{
        if (pField.isEmpty()){
            throw new Exception("Please fill all mandatory fields.");
        }
    }

    public DaycareEntry bookHosting(Guest pGuest, Owner pOwner, LocalDate pFrom, LocalDate pTo, Host pHost) throws Exception{
        DaycareEntry entry = new DaycareEntry();
        checkMandatory(pOwner.getName());
        checkMandatory(pGuest.getName());
        checkMandatory(pFrom.toString());
        checkMandatory(pTo.toString());
        checkDate(pFrom,pTo);
        entry.setDaycareEntry(pOwner, pGuest, pFrom, pTo, pHost);
        //entries.add(entry);
        daycareEntryRepository.save(entry); // speichern, damit ID generiert wird
        entry.setRegistrationNo(Long.toString(entry.getId()));
        entry.setStatus("reservation booked");
        daycareEntryRepository.save(entry); // nochmal speichern, damit RegNo und Status mit abgespeichert werden
        return entry;
    }

    public String bringIn(Long pId){
        DaycareEntry entry = daycareEntryRepository.findByid(pId);
        if (entry==null){
            return "Error: This Registration No does not exist.";
        }
        switch (entry.getStatus()) {
            case "reservation booked":
                Owner ownerVar = entry.getOwner();
                Guest guestVar = entry.getGuest();
                Host hostVar = entry.getHost();
                LocalDate fromVar = entry.getFromDate();
                LocalDate toVar = entry.getToDate();
                if (entry.getFromDate().compareTo(LocalDate.now()) < 0) {
                    entry.setStatus("brought in");
                    daycareEntryRepository.save(entry);
                    return "Hello " + ownerVar.getName() + ". You're bringing in " + guestVar.getName() +
                            " too late. According to your reservation you will be charged the price of " +
                            "the full duration. Thanks for your understanding. The status was " +
                            "set to \'brought in\'. This reservation is valid until " + DaycareEntry.dateToString(toVar) + ".";
                } else if (entry.getFromDate().equals(LocalDate.now())) {
                    entry.setStatus("brought in");
                    daycareEntryRepository.save(entry);
                    return ("Hello " + ownerVar.getName() + ". Thanks for bringing in " +
                            guestVar.getName() + ". The status was set to \'brought in\'. This reservation is valid until "
                            + DaycareEntry.dateToString(toVar) + ". ");
                } else {
                    return "Sorry. You can't bring in " + guestVar.getName() + " yet. Please bring them in on " +
                            fromVar + ". Thank you.";
                }
            case "brought in":
            case "picked up":
                return "This guest has already been brought in.";
            case "cancelled":
                return "This reservation was cancelled and thus the guest can't be brought in.";
            default:
                return "Error: Please check the status of this reservation.";
        }
    }

    public String pickUp(Long pId){
        DaycareEntry entry = daycareEntryRepository.findByid(pId);
        if (entry==null){
            return "Error: This Registration No does not exist.";
        }
        switch (entry.getStatus()) {
            case "reservation booked":
                return "This guest can't be picked up because they haven't been brought in yet.";
            case "picked up":
                return "This guest has already been picked up.";
            case "cancelled":
                return "This reservation was cancelled and thus the guest can't be picked up.";
            case "brought in":
                Owner ownerVar = entry.getOwner();
                Guest guestVar = entry.getGuest();
                Host hostVar = entry.getHost();
                LocalDate fromVar = entry.getFromDate();
                LocalDate toVar = entry.getToDate();
                entry.setStatus("picked up");
                daycareEntryRepository.save(entry);
                if (entry.getToDate().compareTo(LocalDate.now()) > 0) {
                    return "Hello " + ownerVar.getName() + ". You're picking up " + guestVar.getName() +
                            " too early. According to your reservation you will be charged the price of " +
                            "the full duration. Thanks for your understanding. The status was set to \'picked up\'.";
                } else if (entry.getToDate().equals(LocalDate.now())) {
                    return ("Hello " + ownerVar.getName() + ". Your " + guestVar.getType() + " " + guestVar.getName()
                            + " was here from " + DaycareEntry.dateToString(fromVar) + " to " + DaycareEntry.dateToString(toVar) +
                            ". Thanks for your trust. The status was set to \'picked up\'. ");
                } else {
                    return "Hello " + ownerVar.getName() + ". You're picking up " + guestVar.getName() +
                            " too late. According to your reservation you will be charged the additional days. Thanks " +
                            "for your understanding. The status was set to \'picked up\'.";
                }
            default:
                return "Error: Please check the status of this reservation.";
        }
    }

    public String cancelRes(Long pId){
        DaycareEntry entry = daycareEntryRepository.findByid(pId);
        if (entry==null){
            return "Error: This Registration No does not exist.";
        }
        if (entry.getStatus().equals("reservation booked") && entry.getFromDate().compareTo(LocalDate.now())<0){
            return "It is too late to cancel this reservation now. Your pet is expected at the agreed host's place.";
        } else if (entry.getStatus().equals("brought in") || entry.getStatus().equals("picked up")){
            return "It is too late to cancel this reservation now. You already brought in your pet.";
        } else {
            entry.setStatus("cancelled");
            daycareEntryRepository.save(entry);
            return "Your reservation has been cancelled.";
        }
    }

    String output = null;
    public String currentDate(){
        DaycareEntry entry = null;
        output = "These are all the open reservations as per today: \n";
        daycareEntryRepository.findAll().forEach(new Consumer<DaycareEntry>() {
            @Override
            public void accept(DaycareEntry entry) {
                output = output + entry.getRegistrationNo() + ": " + entry.getGuest().getName() + " by " +
                        entry.getOwner().getName() + " (" + entry.getFromDate() + " to " + entry.getToDate()
                        + ") - Status: " + entry.getStatus() +"\n";
            }
        });
        output = output + "\nThese reservations start today: \n";
        daycareEntryRepository.findAll().forEach(new Consumer<DaycareEntry>() {
            @Override
            public void accept(DaycareEntry entry) {
                if (entry.getFromDate().equals(LocalDate.now())) {
                    output = output + entry.getRegistrationNo() + ": " + entry.getGuest().getName() + " by " +
                            entry.getOwner().getName() + " (" + entry.getFromDate() + " to " + entry.getToDate()
                            + ") - Status: " + entry.getStatus() + "\n";
                }
            }
        });
        output = output + "\nThese reservations end today: \n";
        daycareEntryRepository.findAll().forEach(new Consumer<DaycareEntry>() {
            @Override
            public void accept(DaycareEntry entry) {
                if (entry.getToDate().equals(LocalDate.now())) {
                    output = output + entry.getRegistrationNo() + ": " + entry.getGuest().getName() + " by " +
                            entry.getOwner().getName() + " (" + entry.getFromDate() + " to " + entry.getToDate()
                            + ") - Status: " + entry.getStatus() + "\n";
                }
            }
        });
        output = output + "\nThese are the late bring-ins: \n";
        daycareEntryRepository.findAll().forEach(new Consumer<DaycareEntry>() {
            @Override
            public void accept(DaycareEntry entry) {
                if (entry.getFromDate().compareTo(LocalDate.now())<0) {
                    if (entry.getStatus().equals("reservation booked")) {
                        output = output + entry.getRegistrationNo() + ": " + entry.getGuest().getName() + " by " +
                                entry.getOwner().getName() + " (" + entry.getFromDate() + " to " + entry.getToDate()
                                + ") - Status: " + entry.getStatus() + "\n";
                    }
                }
            }
        });
        output = output + "\nThese are the late pick-ups: \n";
        daycareEntryRepository.findAll().forEach(new Consumer<DaycareEntry>() {
            @Override
            public void accept(DaycareEntry entry) {
                if (entry.getToDate().compareTo(LocalDate.now())<0) {
                    if (entry.getStatus().equals("brought in")) {
                        output = output + entry.getRegistrationNo() + ": " + entry.getGuest().getName() + " by " +
                                entry.getOwner().getName() + " (" + entry.getFromDate() + " to " + entry.getToDate()
                                + ") - Status: " + entry.getStatus() + "\n";
                    }
                }
            }
        });
        return output;
    }

    String searchGuestOutput = null;
    public String searchByGuest(String pGuestname){
        searchGuestOutput = "List of entries with Guest name " + pGuestname + ":\n\n";
        guestRepository.findByName(pGuestname).forEach(new Consumer<Guest>() {
            @Override
            public void accept(Guest guest) {
                daycareEntryRepository.findByGuest(guest).forEach(new Consumer<DaycareEntry>() {
                    @Override
                    public void accept(DaycareEntry daycareEntry) {
                        searchGuestOutput = searchGuestOutput + daycareEntry.getInfo() + "\n";
                    }
                });
            }
        });
        return searchGuestOutput;
    }

    String searchOwnerOutput = null;
    public String searchByOwner(String pUsername){
        searchOwnerOutput = "List of entries with Owner Username " + pUsername + ":\n\n";
        Owner owner = ownerRepository.findByUsername(pUsername);
        daycareEntryRepository.findByOwner(owner).forEach(new Consumer<DaycareEntry>() {
            @Override
            public void accept(DaycareEntry daycareEntry) {
                searchOwnerOutput = searchOwnerOutput + daycareEntry.getInfo() + "\n";
            }
        });
        return searchOwnerOutput;
    }

    String searchHostOutput = null;
    public String searchByHost(String pHostname){
        searchHostOutput = "List of entries with Host name " + pHostname + ":\n\n";
        List<Host> hosts = hostRepository.findByName(pHostname);
        Host host = hosts.get(0);
        daycareEntryRepository.findByHost(host).forEach(new Consumer<DaycareEntry>() {
            @Override
            public void accept(DaycareEntry daycareEntry) {
                searchHostOutput = searchHostOutput + daycareEntry.getInfo() + "\n";
            }
        });
        return searchHostOutput;
    }

    String searchStatusOutput = null;
    public String searchByStatus(String pStatus){
        searchStatusOutput = "List of entries with Status " + pStatus + ":\n\n";
        daycareEntryRepository.findByStatus(pStatus).forEach(new Consumer<DaycareEntry>() {
            @Override
            public void accept(DaycareEntry daycareEntry) {
                searchStatusOutput = searchStatusOutput + daycareEntry.getInfo() + "\n";
            }
        });
        return searchStatusOutput;
    }

    public String searchByRegNo(Long pId){
        DaycareEntry entry = daycareEntryRepository.findByid(pId);
        if (entry==null){
            return "Error: This Registration No does not exist.";
        }
        return entry.getInfo();
    }
}
