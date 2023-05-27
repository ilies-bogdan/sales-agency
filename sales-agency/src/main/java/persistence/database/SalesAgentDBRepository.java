package persistence.database;

import controller.PopupMessage;
import domain.Address;
import domain.SalesAgent;
import persistence.SalesAgentRepository;
import persistence.RepositoryException;
import utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

public class SalesAgentDBRepository implements SalesAgentRepository {
    private final JdbcUtils dbUtils;

    public SalesAgentDBRepository(Properties properties) {
        this.dbUtils = new JdbcUtils(properties);
    }

    private SalesAgent extractAgent(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("agent_id");
        String username = resultSet.getString("username");
        String password = resultSet.getString("password");
        int addressID = resultSet.getInt("address_id");
        String city = resultSet.getString("city");
        String street = resultSet.getString("street");
        int number = resultSet.getInt("number");
        return new SalesAgent(id, username, password, new Address(addressID, city, street, number));
    }

    @Override
    public SalesAgent findByUsername(String username) throws RepositoryException {
        Connection connection = dbUtils.getConnection();
        try(PreparedStatement statement = connection.prepareStatement("SELECT Agents.id AS 'agent_id', username, password, A.id AS 'address_id', city, street, number " +
                "FROM Agents INNER JOIN Addresses A on A.id = Agents.address_id WHERE username=?")) {
            statement.setString(1, username);
            try(ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return extractAgent(resultSet);
                }
            }
        } catch (SQLException e) {
            PopupMessage.showErrorMessage("DB error " + e);
        }
        throw new RepositoryException("Agent not found!");
    }

    @Override
    public SalesAgent add(SalesAgent agent) { return null; }

    @Override
    public SalesAgent findByID(Integer integer) { return null; }

    @Override
    public void delete(SalesAgent agent) { }

    @Override
    public void update(Integer integer, SalesAgent agent) { }

    @Override
    public List<SalesAgent> getAll() { return null; }
}
