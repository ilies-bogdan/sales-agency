package controller;

import domain.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import service.SalesService;
import utils.observer.Observer;

import java.io.IOException;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ManagerController implements Observer {
    private SalesService salesSrv;
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
    private TextField textFieldSearch;

    public void setService(SalesService service) {
        this.salesSrv = service;
        salesSrv.addObserver(this);
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

        textFieldSearch.textProperty().addListener(o -> handleFilter());
    }

    private void handleFilter() {
        Predicate<Product> byName = product -> product.getName().toLowerCase().startsWith(textFieldSearch.getText().toLowerCase());
        modelProducts.setAll(salesSrv.getAllProducts().stream().filter(byName).collect(Collectors.toList()));
    }

    public void handleUpdateRequest() throws IOException {
        if (tableViewProducts.getSelectionModel().isEmpty()) {
            PopupMessage.showErrorMessage("No product selected!");
            return;
        }

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("views/update-product-view.fxml"));
        Stage updateStage = new Stage();
        updateStage.setResizable(false);
        updateStage.setTitle("Update Product");
        updateStage.setScene(new Scene(fxmlLoader.load()));
        UpdateProductController updateProductController = fxmlLoader.getController();
        updateProductController.setService(salesSrv);
        updateProductController.setProduct(tableViewProducts.getSelectionModel().getSelectedItem());
        updateStage.show();
    }

    public void handleAddRequest() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("views/add-product-view.fxml"));
        Stage addStage = new Stage();
        addStage.setResizable(false);
        addStage.setTitle("Add Product");
        addStage.setScene(new Scene(fxmlLoader.load()));
        AddProductController addProductController = fxmlLoader.getController();
        addProductController.setService(salesSrv);
        addStage.show();
    }
}
