package persistence.database;

import controller.PopupMessage;
import domain.OrderItem;
import persistence.OrderItemRepository;
import utils.Constants;
import utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;
import java.util.Set;

public class OrderItemDBRepository implements OrderItemRepository {
    private final JdbcUtils dbUtils;

    public OrderItemDBRepository(Properties properties) {
        this.dbUtils = new JdbcUtils(properties);
    }

    @Override
    public OrderItem add(OrderItem orderItem) {
        Connection connection = dbUtils.getConnection();
        try(PreparedStatement statement = connection.prepareStatement("INSERT INTO OrderItems (order_id, product_id, quantity) VALUES (?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, orderItem.getOrderID());
            statement.setInt(2, orderItem.getProductID());
            statement.setInt(3, orderItem.getQuantity());
            statement.executeUpdate();
        } catch (SQLException e) {
            PopupMessage.showErrorMessage("DB error " + e);
        }
        return null;
    }

    @Override
    public OrderItem findByID(Set<Integer> integers) { return null; }

    @Override
    public void delete(OrderItem orderItem) { }

    @Override
    public void update(Set<Integer> integers, OrderItem orderItem) { }

    @Override
    public List<OrderItem> getAll() { return null; }
}
