package service;

import domain.*;
import domain.dto.OrderItemDTO;
import persistence.*;
import utils.observer.Observable;
import utils.observer.Observer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SalesService implements Observable {
    private SalesAgentRepository agentRepo;
    private ManagerRepository managerRepo;
    private ProductRepository productRepo;
    private OrderRepository orderRepo;
    private OrderItemRepository orderItemRepo;
    private List<Observer> observers;

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyAllObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }

    public SalesService(SalesAgentRepository agentRepo, ManagerRepository managerRepo, ProductRepository productRepo, OrderRepository orderRepo, OrderItemRepository orderItemRepo) {
        this.agentRepo = agentRepo;
        this.managerRepo = managerRepo;
        this.productRepo = productRepo;
        this.orderRepo = orderRepo;
        this.orderItemRepo = orderItemRepo;
        this.observers = new ArrayList<>();
    }

    public SalesAgent loginSalesAgent(String username, String password) throws RepositoryException, ServiceException {
        SalesAgent agent = agentRepo.findByUsername(username);
        if (!agent.getPassword().equals(password)) {
            throw new ServiceException("Invalid credentials!");
        }
        return agent;
    }

    public Manager loginManager(String username, String password) throws RepositoryException, ServiceException {
        Manager manager = managerRepo.findByUsername(username);
        if (!manager.getPassword().equals(password)) {
            throw new ServiceException("Invalid credentials!");
        }
        return manager;
    }

    public List<Product> getAllProducts() {
        return productRepo.getAll();
    }

    public void placeOrder(SalesAgent agent, List<OrderItemDTO> orderItems) throws ServiceException {
        Order placedOrder = orderRepo.add(new Order(LocalDateTime.now(), OrderStatus.PLACED, agent.getID()));
        for (OrderItemDTO dto : orderItems) {
            try {
                Product product = productRepo.findByName(dto.getProductName());
                orderItemRepo.add(new OrderItem(placedOrder.getID(), product.getID(), dto.getRequestedQuantity()));
                productRepo.update(product.getID(), new Product(product.getName(), product.getPrice(), product.getQuantity() - dto.getRequestedQuantity()));
                notifyAllObservers();
            } catch (RepositoryException e) {
                throw new ServiceException("Product not found!");
            }
        }
    }

    public void updateProduct(int productId, String newName, float newPrice, int newQuantity) {
        productRepo.update(productId, new Product(newName, newPrice, newQuantity));
        notifyAllObservers();
    }

    public void addProduct(String name, float price, int quantity) {
        productRepo.add(new Product(name, price, quantity));
        notifyAllObservers();
    }
}
