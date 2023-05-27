package persistence.database;

import controller.PopupMessage;
import domain.Address;
import domain.Manager;
import domain.SalesAgent;
import persistence.ManagerRepository;
import persistence.RepositoryException;
import utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

public class ManagerDBRepository implements ManagerRepository {
    private final JdbcUtils dbUtils;

    public ManagerDBRepository(Properties properties) {
        this.dbUtils = new JdbcUtils(properties);
    }

    private Manager extractManager(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String username = resultSet.getString("username");
        String password = resultSet.getString("password");
        return new Manager(id, username, password);
    }

    @Override
    public Manager findByUsername(String username) throws RepositoryException {
        Connection connection = dbUtils.getConnection();
        try(PreparedStatement statement = connection.prepareStatement("SELECT * FROM Managers WHERE username=?")) {
            statement.setString(1, username);
            try(ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return extractManager(resultSet);
                }
            }
        } catch (SQLException e) {
            PopupMessage.showErrorMessage("DB error " + e);
        }
        throw new RepositoryException("Manager not found!");
    }

    @Override
    public Manager add(Manager manager) { return null; }

    @Override
    public Manager findByID(Integer integer) throws RepositoryException { return null; }

    @Override
    public void delete(Manager manager) { }

    @Override
    public void update(Integer integer, Manager manager) { }

    @Override
    public List<Manager> getAll() { return null; }
}
