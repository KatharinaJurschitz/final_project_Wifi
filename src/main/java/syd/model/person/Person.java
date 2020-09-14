package syd.model.person;

import javax.persistence.*;
import java.io.Serializable;

@MappedSuperclass
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected long id;
    protected String name;
    protected String address;
    protected String telephone;

    public Person(String pName, String pAddress, String pTelephone) throws Exception{
        if (pName.isEmpty()){
            throw new Exception("Please fill all mandatory fields.");
        }
        this.name = pName;
        this.address = pAddress;
        this.telephone = pTelephone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    protected Person() {} // JPA verlangt für Entity einen Leer-Cstr

    @Override
    public String toString() {
        return "Person{ id=" + id + ", name='" + name + '\'' + ", address='" + address + '\'' +
                ", telephone='" + telephone + '\'' + '}';
    }

//    public String personInfo(){
//        return "Name: " + this.name + "\nAddress: " + this.address + "\nTelephone: " + this.telephone;
//    }

    //    public Person(String pName) throws Exception{
//        if (pName.isEmpty()){
//            throw new Exception("Please fill all mandatory fields.");
//        }
//        this.name = pName;
//    }
}
