package domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Basic;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@javax.persistence.Entity
@Table(name = "Orders")
public class Order implements Entity<Integer> {
    private int id;
    private LocalDateTime timePlaced;
    private OrderStatus status;
    private int agentID;

    public Order() {
        this.id = 0;
        this.timePlaced = null;
        this.status = null;
        this.agentID = 0;
    }

    public Order(LocalDateTime timePlaced, OrderStatus status, int agentID) {
        this.timePlaced = timePlaced;
        this.status = status;
        this.agentID = agentID;
    }

    public Order(int id, LocalDateTime timePlaced, OrderStatus status, int agentID) {
        this.id = id;
        this.timePlaced = timePlaced;
        this.status = status;
        this.agentID = agentID;
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

    public LocalDateTime getTimePlaced() {
        return timePlaced;
    }

    public void setTimePlaced(LocalDateTime timePlaced) {
        this.timePlaced = timePlaced;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public int getAgentID() {
        return agentID;
    }

    public void setAgentID(int agentID) {
        this.agentID = agentID;
    }
}
