package persistence.database;

import controller.PopupMessage;
import domain.Product;
import persistence.ProductRepository;
import persistence.RepositoryException;
import utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ProductDBRepository implements ProductRepository {
    private final JdbcUtils dbUtils;

    public ProductDBRepository(Properties properties) {
        this.dbUtils = new JdbcUtils(properties);
    }

    private Product extractProduct(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        float price = resultSet.getFloat("price");
        int quantity = resultSet.getInt("quantity");
        return new Product(id, name, price, quantity);
    }

    @Override
    public Product add(Product product) { return null; }

    @Override
    public Product findByID(Integer id) throws RepositoryException {
        Connection connection = dbUtils.getConnection();
        try(PreparedStatement statement = connection.prepareStatement("SELECT * FROM Products WHERE id=?")) {
            statement.setInt(1, id);
            try(ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return extractProduct(resultSet);
                }
            }
        } catch (SQLException e) {
            PopupMessage.showErrorMessage("DB error " + e);
        }
        throw new RepositoryException("Product not found!");
    }

    @Override
    public void delete(Product product) { }

    @Override
    public void update(Integer id, Product product) {
        Connection connection = dbUtils.getConnection();
        try(PreparedStatement statement = connection.prepareStatement("UPDATE Products SET name=?, price=?, quantity=? WHERE id=?")) {
            statement.setString(1, product.getName());
            statement.setFloat(2, product.getPrice());
            statement.setInt(3, product.getQuantity());
            statement.setInt(4, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            PopupMessage.showErrorMessage("DB error " + e);
        }
    }

    @Override
    public List<Product> getAll() {
        Connection connection = dbUtils.getConnection();
        List<Product> products = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement("SELECT * FROM Products")) {
            try(ResultSet resultSet = statement.executeQuery()) {
                while(resultSet.next()) {
                    products.add(extractProduct(resultSet));
                }
            }
        } catch (SQLException e) {
            PopupMessage.showErrorMessage("DB error " + e);
        }
        return products;
    }

    @Override
    public Product findByName(String name) throws RepositoryException {
        Connection connection = dbUtils.getConnection();
        try(PreparedStatement statement = connection.prepareStatement("SELECT * FROM Products WHERE name=?")) {
            statement.setString(1, name);
            try(ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return extractProduct(resultSet);
                }
            }
        } catch (SQLException e) {
            PopupMessage.showErrorMessage("DB error " + e);
        }
        throw new RepositoryException("Product not found!");
    }
}
