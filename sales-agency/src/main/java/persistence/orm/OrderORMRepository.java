package persistence.orm;

import domain.Order;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import persistence.OrderRepository;
import persistence.RepositoryException;

import java.util.List;

public class OrderORMRepository implements OrderRepository {
    private final SessionFactory sessionFactory;

    public OrderORMRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Order add(Order order) {
        try(Session session = sessionFactory.openSession()) {
            Transaction tran = null;
            try {
                tran = session.beginTransaction();
                Integer id = (Integer) session.save(order);
                order.setID(id);
                tran.commit();
                return order;
            } catch (Exception e) {
                if (tran != null) {
                    tran.rollback();
                }
            }
        }
        return null;
    }

    @Override
    public Order findByID(Integer integer) throws RepositoryException {
        return null;
    }

    @Override
    public void delete(Order order) {

    }

    @Override
    public void update(Integer integer, Order order) {

    }

    @Override
    public List<Order> getAll() {
        return null;
    }
}
