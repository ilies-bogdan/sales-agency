package persistence.orm;

import domain.SalesAgent;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import persistence.RepositoryException;
import persistence.SalesAgentRepository;

import java.util.List;

public class SalesAgentORMRepository implements SalesAgentRepository {
    private final SessionFactory sessionFactory;

    public SalesAgentORMRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public SalesAgent add(SalesAgent salesAgent) {
        return null;
    }

    @Override
    public SalesAgent findByID(Integer integer) throws RepositoryException {
        return null;
    }

    @Override
    public void delete(SalesAgent salesAgent) {

    }

    @Override
    public void update(Integer integer, SalesAgent salesAgent) {

    }

    @Override
    public List<SalesAgent> getAll() {
        return null;
    }

    @Override
    public SalesAgent findByUsername(String username) throws RepositoryException {
        try(Session session = sessionFactory.openSession()) {
            Transaction tran = null;
            try {
                tran = session.beginTransaction();
                String query = "from SalesAgent s where s.username = ?1";
                return session.createQuery(query, SalesAgent.class)
                        .setParameter(1, username)
                        .setMaxResults(1)
                        .uniqueResult();
            } catch (Exception e) {
                if (tran != null) {
                    tran.rollback();
                }
            }
        }
        throw new RepositoryException("Sales agent not found!");
    }
}
