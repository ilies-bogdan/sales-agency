package persistence;

import domain.SalesAgent;

public interface SalesAgentRepository extends Repository<SalesAgent, Integer> {
    SalesAgent findByUsername(String username) throws RepositoryException;
}
