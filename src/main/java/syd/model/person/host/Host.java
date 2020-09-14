package syd.model.person.host;

import syd.model.daycareentry.DaycareEntry;
import syd.model.person.Person;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.Set;
import java.util.function.Predicate;

@Entity
public class Host extends Person {
    private double pricePerDay;
    private int numberOfCatsAllowed;
    private int numberOfDogsAllowed;
    private int numberOfRabbitsAllowed;
    private int numberOfGuineaPigsAllowed;
    private int numberOfLizardAllowed;
    private int numberOfRatMouseAllowed;
    private int numberOfSnakeAllowed;
    private int numberOfBirdAllowed;
    @OneToMany (fetch = FetchType.EAGER)
    private Set<DaycareEntry> daycareEntries;

    public Host(String pName, double pPricePerDay, String pAddress,
                String pTelephone) throws Exception{
        super(pName, pAddress, pTelephone);
        this.pricePerDay = pPricePerDay;
    }

    @Override
    public String toString() {
        return "[ID " + this.id + "] Name: " + this.name + ", Address: " +
                this.address + ", Telephone:" + this.telephone +
                ", Price per Day: " + this.pricePerDay;
    }

    public String shortInfo(){
        return this.name + ", Price per Day: " + this.pricePerDay;
    }

    public int getFreeCapacity(LocalDate pFromDate, LocalDate pToDate, String pGuestType){
        int checkCapacity;
        switch (pGuestType){
            case "cat": checkCapacity = this.getNumberOfCatsAllowed();
            break;
            case "dog": checkCapacity = this.getNumberOfDogsAllowed();
            break;
            case "bird": checkCapacity = this.getNumberOfBirdAllowed();
            break;
            case "guinea pig": checkCapacity = this.getNumberOfGuineaPigsAllowed();
            break;
            case "rabbit": checkCapacity = this.getNumberOfRabbitsAllowed();
            break;
            case "lizard": checkCapacity = this.getNumberOfLizardAllowed();
            break;
            case "rat/mouse": checkCapacity = this.getNumberOfRatMouseAllowed();
            break;
            case "snake": checkCapacity = this.getNumberOfSnakeAllowed();
            break;
            default: checkCapacity = 0;
        }
        checkCapacity = (int) (checkCapacity - this.daycareEntries.stream().
                filter(new Predicate<DaycareEntry>() {
                    @Override
                    public boolean test(DaycareEntry daycareEntry) {
                        return daycareEntry.getGuest().getType().
                                equals(pGuestType)&&daycareEntry.getFromDate().
                                compareTo(pToDate)<=0&&daycareEntry.
                                getToDate().
                                compareTo(pFromDate)>=0;
                    }
                }).count());
        return checkCapacity;
    }

    public int getNumberOfCatsAllowed() {
        return numberOfCatsAllowed;
    }

    public int getNumberOfDogsAllowed() {
        return numberOfDogsAllowed;
    }

    public int getNumberOfRabbitsAllowed() {
        return numberOfRabbitsAllowed;
    }

    public int getNumberOfGuineaPigsAllowed() {
        return numberOfGuineaPigsAllowed;
    }

    public int getNumberOfLizardAllowed() {
        return numberOfLizardAllowed;
    }

    public int getNumberOfRatMouseAllowed() {
        return numberOfRatMouseAllowed;
    }

    public double getPricePerDay() {
        return pricePerDay;
    }

    public int getNumberOfSnakeAllowed() {
        return numberOfSnakeAllowed;
    }

    public int getNumberOfBirdAllowed() {
        return numberOfBirdAllowed;
    }

    public Host(){} // JPA verlangt für Entity einen Leer-Cstr

    //    public Host(String pName, double pPricePerDay) throws Exception{
//        super(pName);
//        this.pricePerDay = pPricePerDay;
//    }
}
