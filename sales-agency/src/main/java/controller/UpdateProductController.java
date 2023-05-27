package controller;

import domain.Product;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import service.SalesService;

public class UpdateProductController {
    private SalesService salesSrv;
    private Product product;
    @FXML
    private TextField textFieldName;
    @FXML
    private TextField textFieldPrice;
    @FXML
    private TextField textFieldQuantity;

    public void setService(SalesService service) {
        this.salesSrv = service;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void handleUpdateRequest() {
        String newName = textFieldName.getText();
        if (newName.isEmpty()) {
            newName = product.getName();
        }

        float newPrice = 0;
        if (textFieldPrice.getText().isEmpty()) {
            newPrice = product.getPrice();
        } else {
            try {
                newPrice = Float.parseFloat(textFieldPrice.getText());
            } catch (NumberFormatException e) {
                PopupMessage.showErrorMessage("Invalid product data!");
                return;
            }
        }

        int newQuantity = 0;
        if (textFieldQuantity.getText().isEmpty()) {
            newQuantity = product.getQuantity();
        } else {
            try {
                newQuantity = Integer.parseInt(textFieldQuantity.getText());
            } catch (NumberFormatException e) {
                PopupMessage.showErrorMessage("Invalid product data!");
                return;
            }
        }

        salesSrv.updateProduct(product.getID(), newName, newPrice, newQuantity);
        textFieldName.setText("");
        textFieldPrice.setText("");
        textFieldQuantity.setText("");
        PopupMessage.showInformationMessage("Product updated!");
    }
}
