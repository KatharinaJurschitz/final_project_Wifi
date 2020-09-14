package syd.model.guest;

import syd.model.daycareentry.DaycareEntry;
import syd.model.person.owner.Owner;
import javax.persistence.*;
import java.util.Set;

@Entity
public class Guest {
    protected String name;
    protected String type;
    protected String careoption;
    @OneToMany
    private Set<DaycareEntry> daycareEntries;            //für Verknüpfung mit anderen Tabellen
    @ManyToOne
    private Owner owner;                                 //für Verknüpfung mit anderen Tabellen
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    protected long id;

    protected Guest() {} // JPA verlangt für Entity einen Leer-Cstr

    @Override
    public String toString() {
        return "[ID " + this.id + "] Name: " + this.name + ", Type: " + this.type + ", Care Option: " + this.careoption;
    }

    public Guest(String pName, String pType, String pCareoption) throws Exception {
        if (pName.isEmpty() || pType.isEmpty()){
            throw new Exception("Please fill all mandatory fields.");
        }
        this.name = pName;
        this.type = pType;
        this.careoption = pCareoption;
    }

//    public String guestInfo(){
//        return ("Name: " + this.name + "\nType: " + this.type);
//    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getCareoption() {
        return careoption;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCareoption(String careoption) {
        this.careoption = careoption;
    }

    public long getId() {
        return id;
    }
}
