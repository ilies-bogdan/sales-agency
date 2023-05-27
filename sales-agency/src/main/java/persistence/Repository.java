package persistence;

import domain.Entity;

import java.util.List;

public interface Repository<E extends Entity<ID>, ID> {
    E add(E e);
    E findByID(ID id) throws RepositoryException;
    void delete(E e);
    void update(ID id, E e);
    List<E> getAll();
}
