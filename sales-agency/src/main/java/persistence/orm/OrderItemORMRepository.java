package persistence.orm;

import domain.OrderItem;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import persistence.OrderItemRepository;
import persistence.RepositoryException;

import java.util.List;
import java.util.Set;

public class OrderItemORMRepository implements OrderItemRepository {
    private final SessionFactory sessionFactory;

    public OrderItemORMRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public OrderItem add(OrderItem orderItem) {
        try(Session session = sessionFactory.openSession()) {
            Transaction tran = null;
            try {
                tran = session.beginTransaction();
                Integer id = (Integer) session.save(orderItem);
                orderItem.setID(id);
                tran.commit();
                return orderItem;
            } catch (Exception e) {
                if (tran != null) {
                    tran.rollback();
                }
            }
        }
        return null;
    }

    @Override
    public OrderItem findByID(Set<Integer> integers) throws RepositoryException {
        return null;
    }

    @Override
    public void delete(OrderItem orderItem) {

    }

    @Override
    public void update(Set<Integer> integers, OrderItem orderItem) {

    }

    @Override
    public List<OrderItem> getAll() {
        return null;
    }
}
