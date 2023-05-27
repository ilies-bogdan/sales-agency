package domain;

import javax.persistence.Table;

@javax.persistence.Entity
@Table(name = "Managers")
public class Manager extends User {
    public Manager() {
        super("", "");
    }

    public Manager(String username, String password) {
        super(username, password);
    }

    public Manager(int id, String username, String password) {
        super(id, username, password);
    }
}
