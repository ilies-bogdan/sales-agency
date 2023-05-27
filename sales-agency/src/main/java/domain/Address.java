package domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@javax.persistence.Entity
@Table(name = "Addresses")
public class Address implements Entity<Integer> {
    private int id;
    private String city;
    private String street;
    private int number;
    @OneToMany(mappedBy = "address")
    private List<SalesAgent> salesAgents = new ArrayList<>();

    public Address() {
        this.id = 0;
        this.city = "";
        this.street = "";
        this.number = 0;
    }

    public Address(String city, String street, int number) {
        this.city = city;
        this.street = street;
        this.number = number;
    }

    public Address(int id, String city, String street, int number) {
        this.id = id;
        this.city = city;
        this.street = street;
        this.number = number;
    }

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Override
    public Integer getID() {
        return id;
    }

    @Override
    public void setID(Integer id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
