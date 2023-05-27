package persistence;

import domain.Manager;

public interface ManagerRepository extends Repository<Manager, Integer> {
    Manager findByUsername(String username) throws RepositoryException;
}
