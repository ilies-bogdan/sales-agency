package persistence;

import domain.OrderItem;

import java.util.Set;

public interface OrderItemRepository extends Repository<OrderItem, Set<Integer>> { }
