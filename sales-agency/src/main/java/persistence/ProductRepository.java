package persistence;

import domain.Product;

public interface ProductRepository extends Repository<Product, Integer> {
    Product findByName(String name) throws RepositoryException;
}
