package syd.model.daycareentry;

import syd.model.guest.Guest;
import syd.model.person.host.Host;
import syd.model.person.owner.Owner;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

@Entity
public class DaycareEntry {
    @ManyToOne
    private Owner owner;
    @ManyToOne
    private Guest guest;
    @ManyToOne
    private Host host;
    private LocalDate fromDate;
    private LocalDate toDate;
    private String status;
    //private static int currentNoGuests = 0;
    private String registrationNo;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    public void setDaycareEntry (Owner pOwner, Guest pGuest, LocalDate pFromDate, LocalDate pToDate, Host pHost) {
        this.owner = pOwner;
        this.guest = pGuest;
        this.fromDate = pFromDate;
        this.toDate = pToDate;
        this.host = pHost;
        //this.currentNoGuests++;
    }

    public DaycareEntry (){}

    public String getInfo(){
        return "RegNo: " + this.registrationNo + "\n    Guest: " + this.guest.getName() + " [" +
                this.guest.getType() + "]\n    Owner: " + this.owner.getName() + "\n    Host: " + this.host.shortInfo() +
                "\n    Dates: " + dateToString(fromDate) + " - " + dateToString(toDate)  + "\n    Status: " + this.status + "\n";
    }

//    public String getShortInfo(){
//        return "RegNo: " + this.registrationNo + " | Guest: " + this.guest.getName() + " | Owner: " + this.owner.getName();
//    }

    public static String dateToString(LocalDate pDate){
        return pDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG));
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setRegistrationNo(String registrationNo) {
        this.registrationNo = registrationNo;
    }

//    public void setGuest(Guest guest) {
//        this.guest = guest;
//    }

    public Owner getOwner() {
        return owner;
    }

    public Guest getGuest() {
        return guest;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public String getStatus() {
        return status;
    }

    public String getRegistrationNo() {
        return registrationNo;
    }

//    public static int getCurrentNoGuests() {
//        return currentNoGuests;
//    }

    public Host getHost() {
        return host;
    }

    public long getId() {
        return id;
    }
}
