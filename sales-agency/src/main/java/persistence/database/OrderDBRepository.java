package persistence.database;

import controller.PopupMessage;
import domain.Order;
import persistence.OrderRepository;
import utils.Constants;
import utils.JdbcUtils;

import java.sql.*;
import java.util.List;
import java.util.Properties;

public class OrderDBRepository implements OrderRepository {
    private final JdbcUtils dbUtils;

    public OrderDBRepository(Properties properties) {
        this.dbUtils = new JdbcUtils(properties);
    }

    @Override
    public Order add(Order order) {
        Connection connection = dbUtils.getConnection();
        try(PreparedStatement statement = connection.prepareStatement("INSERT INTO Orders (time_placed, status, agent_id) VALUES (?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, order.getTimePlaced().format(Constants.DATE_TIME_FORMATTER));
            statement.setString(2, order.getStatus().toString());
            statement.setInt(3, order.getAgentID());
            statement.executeUpdate();

            try(ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    order.setID(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            PopupMessage.showErrorMessage("DB error " + e);
        }
        return order;
    }

    @Override
    public Order findByID(Integer integer) { return null; }

    @Override
    public void delete(Order order) { }

    @Override
    public void update(Integer integer, Order order) { }

    @Override
    public List<Order> getAll() { return null; }
}
