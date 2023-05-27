package domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@javax.persistence.Entity
@Table(name = "Agents")
public class SalesAgent extends User {
    private Address address;

    public SalesAgent() {
        super("", "");
        this.address = null;
    }

    public SalesAgent(String username, String password, Address address) {
        super(username, password);
        this.address = address;
    }

    public SalesAgent(int id, String username, String password, Address address) {
        super(id, username, password);
        this.address = address;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id", nullable = false)
    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
