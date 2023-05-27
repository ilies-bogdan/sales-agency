package persistence.orm;

import domain.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import persistence.ProductRepository;
import persistence.RepositoryException;

import java.util.List;

public class ProductORMRepository implements ProductRepository {
    private final SessionFactory sessionFactory;

    public ProductORMRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Product findByName(String name) throws RepositoryException {
        try(Session session = sessionFactory.openSession()) {
            Transaction tran = null;
            try {
                tran = session.beginTransaction();
                String query = "from Product p where p.name = ?1";
                return session.createQuery(query, Product.class)
                        .setParameter(1, name)
                        .setMaxResults(1)
                        .uniqueResult();
            } catch (Exception e) {
                if (tran != null) {
                    tran.rollback();
                }
            }
        }
        throw new RepositoryException("Product not found!");
    }

    @Override
    public Product add(Product product) {
        try(Session session = sessionFactory.openSession()) {
            Transaction tran = null;
            try {
                tran = session.beginTransaction();
                Integer id = (Integer) session.save(product);
                product.setID(id);
                tran.commit();
                return product;
            } catch (Exception e) {
                if (tran != null) {
                    tran.rollback();
                }
            }
        }
        return null;
    }

    @Override
    public Product findByID(Integer id) throws RepositoryException {
        try(Session session = sessionFactory.openSession()) {
            return session.load(Product.class, id);
        }
    }

    @Override
    public void delete(Product product) {

    }

    @Override
    public void update(Integer id, Product product) {
        try(Session session = sessionFactory.openSession()) {
            Transaction tran = null;
            try {
                tran = session.beginTransaction();
                Product p = session.load(Product.class, id);
                p.setName(product.getName());
                p.setPrice(product.getPrice());
                p.setQuantity(product.getQuantity());
                tran.commit();
            } catch (Exception e) {
                if (tran != null) {
                    tran.rollback();
                }
            }
        }
    }

    @Override
    public List<Product> getAll() {
        try(Session session = sessionFactory.openSession()) {
            Transaction tran = null;
            try {
                tran = session.beginTransaction();
                return session.createQuery("from Product p", Product.class).list();
            } catch (Exception e) {
                if (tran != null) {
                    tran.rollback();
                }
            }
        }
        return null;
    }
}
