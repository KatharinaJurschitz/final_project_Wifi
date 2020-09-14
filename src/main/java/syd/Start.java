package syd;

import org.springframework.boot.builder.SpringApplicationBuilder;
import syd.model.daycareentry.DaycareEntry;
import syd.model.daycareentry.DaycareEntryRepository;
import syd.model.guest.Guest;
import syd.model.guest.GuestRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import syd.model.person.host.Host;
import syd.model.person.host.HostRepository;
import syd.model.person.owner.Owner;
import syd.model.person.owner.OwnerRepository;
import syd.view.Rahmen;

import java.time.LocalDate;

import static java.lang.String.valueOf;

@SpringBootApplication(scanBasePackages={"syd.model.guest", "syd.model.person"})
public class Start {
    public static void main(String[] args) {
        new SpringApplicationBuilder(Start.class).headless(false).run(args);
    }

    @Bean
    public CommandLineRunner test (GuestRepository guestRepository, OwnerRepository ownerRepository, HostRepository hostRepository, DaycareEntryRepository daycareEntryRepository){
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                new Rahmen(guestRepository, ownerRepository, hostRepository, daycareEntryRepository);
//                guestRepository.save(new Guest("stinki","cat"));
//                guestRepository.save(new Guest("jamsie","cat"));
//                guestRepository.save(new Guest("miez","cat"));
//                guestRepository.save(new Guest("doggo","dog"));
//                guestRepository.save(new Guest("wuffwuff","dog"));

//                guestRepository.findByType("cat").forEach(guest -> log.info(guest.toString()));

//                ownerRepository.save(new Owner("Godric"));
//                ownerRepository.save(new Owner("Rowena"));
//                ownerRepository.save(new Owner("Salazar"));
//                ownerRepository.save(new Owner("Helga"));

//                ownerRepository.findByName("Rowena").forEach(owner -> log.info(owner.toString()));

//                hostRepository.save(new Host("Minerva",20));
//                hostRepository.save(new Host("Filius",15));
//                hostRepository.save(new Host("Severus",15));
//                hostRepository.save(new Host("Pomera",10));

//                DaycareEntry entry = new DaycareEntry();
////                entry.setDaycareEntry(ownerRepository.findByid(26L),
////                        guestRepository.findByid(2L),
////                        LocalDate.of(2020, 9, 1),
////                        LocalDate.of(2020,9,3),
////                        hostRepository.findByid(41L));
////                daycareEntryRepository.save(entry);

//                daycareEntryRepository.findAll().forEach(daycareEntry -> log.info(daycareEntry.getInfo()));

//                Host hostVariable = hostRepository.findByName("Pomera").get(0);
//                hostVariable.setName("Pomona");
//                hostRepository.save(hostVariable);
//                hostRepository.findAll().forEach(host -> log.info(host.toString()));
//                guestRepository.findByType("cat").forEach(guest -> ownerVariable.addGuest(guest));
//                ownerRepository.save(ownerVariable);
            }
        };
    }
}
