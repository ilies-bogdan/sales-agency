package persistence.orm;

import domain.Manager;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import persistence.ManagerRepository;
import persistence.RepositoryException;

import java.util.List;

public class ManagerOrmRepository implements ManagerRepository {
    private final SessionFactory sessionFactory;

    public ManagerOrmRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Manager findByUsername(String username) throws RepositoryException {
        try(Session session = sessionFactory.openSession()) {
            Transaction tran = null;
            try {
                tran = session.beginTransaction();
                String query = "from Manager m where m.username = ?1";
                return session.createQuery(query, Manager.class)
                        .setParameter(1, username)
                        .setMaxResults(1)
                        .uniqueResult();
            } catch (Exception e) {
                if (tran != null) {
                    tran.rollback();
                }
            }
        }
        throw new RepositoryException("Manager not found!");
    }

    @Override
    public Manager add(Manager manager) {
        return null;
    }

    @Override
    public Manager findByID(Integer integer) throws RepositoryException {
        return null;
    }

    @Override
    public void delete(Manager manager) {

    }

    @Override
    public void update(Integer integer, Manager manager) {

    }

    @Override
    public List<Manager> getAll() {
        return null;
    }
}
