package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import service.SalesService;

public class AddProductController {
    private SalesService salesSrv;
    @FXML
    private TextField textFieldName;
    @FXML
    private TextField textFieldPrice;
    @FXML
    private TextField textFieldQuantity;

    public void setService(SalesService service) {
        this.salesSrv = service;
    }

    public void handleAddRequest() {
        String name = textFieldName.getText();
        if (name.trim().isEmpty()) {
            PopupMessage.showErrorMessage("Invalid product data!");
            return;
        }

        float price = 0;
        try {
            price = Float.parseFloat(textFieldPrice.getText());
        } catch (NumberFormatException e) {
            PopupMessage.showErrorMessage("Invalid product data!");
            return;
        }

        int quantity = 0;
        try {
            quantity = Integer.parseInt(textFieldQuantity.getText());
        } catch (NumberFormatException e) {
            PopupMessage.showErrorMessage("Invalid product data!");
            return;
        }

        salesSrv.addProduct(name, price, quantity);
        textFieldName.setText("");
        textFieldPrice.setText("");
        textFieldQuantity.setText("");
        PopupMessage.showInformationMessage("Product added!");
    }
}
