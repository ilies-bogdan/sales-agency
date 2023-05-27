package controller;

import domain.Product;
import domain.SalesAgent;
import domain.dto.OrderItemDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import service.SalesService;
import service.ServiceException;
import utils.observer.Observer;

import java.util.function.Predicate;
import java.util.stream.Collectors;

public class SalesAgentController implements Observer {
    private SalesService salesSrv;
    private SalesAgent agent;
    private ObservableList<Product> modelProducts = FXCollections.observableArrayList();
    @FXML
    private TableView<Product> tableViewProducts;
    @FXML
    private TableColumn<Product, String> tableColumnProduct;
    @FXML
    private TableColumn<Product, Float> tableColumnPrice;
    @FXML
    private TableColumn<Product, Integer> tableColumnAvailableQuantity;
    @FXML
    private ListView<OrderItemDTO> listViewOrder;
    @FXML
    private Spinner<Integer> spinnerRequestedQuantity;
    @FXML
    private TextField textFieldSearch;

    public void setService(SalesService service) {
        this.salesSrv = service;
        salesSrv.addObserver(this);
    }

    public void setAgent(SalesAgent agent) {
        this.agent = agent;
        initializeView();
    }

    @Override
    public void update() {
        initializeView();
    }

    private void initializeView() {
        modelProducts.setAll(salesSrv.getAllProducts());
    }

    @FXML
    public void initialize() {
        tableColumnProduct.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableColumnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        tableColumnAvailableQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        tableViewProducts.setItems(modelProducts);
        tableViewProducts.setPlaceholder(new Label("No products found!"));

        spinnerRequestedQuantity.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10000));

        textFieldSearch.textProperty().addListener(o -> handleFilter());
    }

    private void handleFilter() {
        Predicate<Product> byName = product -> product.getName().toLowerCase().startsWith(textFieldSearch.getText().toLowerCase());
        modelProducts.setAll(salesSrv.getAllProducts().stream().filter(byName).collect(Collectors.toList()));
    }

    public void handleAddToOrderRequest() {
        if (tableViewProducts.getSelectionModel().isEmpty()) {
            PopupMessage.showErrorMessage("No product selected!");
            return;
        }
        int reqQuant = 0;
        try {
            reqQuant = Integer.parseInt(spinnerRequestedQuantity.getEditor().textProperty().get());
        } catch (NumberFormatException e) {
            PopupMessage.showErrorMessage("Invalid required quantity value!");
            return;
        }
        if (reqQuant <= 0) {
            PopupMessage.showErrorMessage("Invalid required quantity value!");
            return;
        }
        Product selected =  tableViewProducts.getSelectionModel().getSelectedItem();
        if(reqQuant > selected.getQuantity()) {
            PopupMessage.showErrorMessage("Insufficient quantity!");
            return;
        }
        OrderItemDTO dto = new OrderItemDTO(selected.getName(), reqQuant);
        if (listViewOrder.getItems().contains(dto)) {
            listViewOrder.getItems().set(listViewOrder.getItems().indexOf(dto), dto);
        } else {
            listViewOrder.getItems().add(new OrderItemDTO(selected.getName(), reqQuant));
        }
        spinnerRequestedQuantity.getEditor().setText("1");
    }

    public void handleOrderRequest() {
        try {
            salesSrv.placeOrder(agent, listViewOrder.getItems());
            listViewOrder.getItems().clear();
            PopupMessage.showInformationMessage("Order placed!");
        } catch (ServiceException e) {
            PopupMessage.showErrorMessage(e.getMessage());
        }
    }
}
