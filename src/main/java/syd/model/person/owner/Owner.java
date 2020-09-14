package syd.model.person.owner;

import syd.model.daycareentry.DaycareEntry;
import syd.model.guest.Guest;
import syd.model.person.Person;
import javax.persistence.*;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

@Entity
public class Owner extends Person {
    @OneToMany
    private Set <DaycareEntry> daycareEntries;
    @OneToMany(fetch = FetchType.EAGER)
    private Set <Guest> guests;
    @Column(unique=true)
    private String username;

    public void addGuest(Guest pGuest){
        guests.add(pGuest);
    }

    public Set<Guest> getGuests() {
        return guests;
    }

    public void removeGuestById (long pId){
        guests.removeIf(guest -> {return guest.getId()== pId;});
//        guests.removeIf(new Predicate<Guest>() {
//            @Override
//            public boolean test(Guest guest) {
//                return guest.getId()==pId;
//            }
//        });
    }

    public Guest getGuest(String pGuestName, String pGuestType){
         Optional <Guest> guest = getGuests().stream().filter(new Predicate<Guest>() {
             @Override
             public boolean test(Guest guest) {
                 return guest.getType().equals(pGuestType)&&guest.getName().equals(pGuestName);
             }
         }).findFirst();
         if (guest.isPresent()){
             return guest.get();
         } else {
             return null;
         }
    }

//    public Owner(String pUsername, String pName) throws Exception{
//        super(pName);
//        this.username = pUsername;
//    }

    public Owner(String pUsername, String pName, String pAddress, String pTelephone) throws Exception{
        super(pName, pAddress, pTelephone);
        this.username = pUsername;
    }

    @Override
    public String toString() {
        return "[ID " + this.id + "] Name: " + this.name + ", Address: " + this.address + ", Telephone: " + this.telephone;
    }

    public Owner (){} // JPA verlangt für Entity einen Leer-Cstr
}