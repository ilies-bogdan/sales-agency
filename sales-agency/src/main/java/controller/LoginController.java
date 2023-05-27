package controller;

import domain.Manager;
import domain.SalesAgent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import persistence.RepositoryException;
import service.SalesService;
import service.ServiceException;

import java.io.IOException;

public class LoginController {
    private SalesService salesSrv;
    @FXML
    private TextField textFieldUsername;
    @FXML
    private PasswordField passwordField;

    public void setService(SalesService service) {
        this.salesSrv = service;
    }

    public void handleLoginSalesAgentRequest() {
        String username = textFieldUsername.getText();
        String password = passwordField.getText();

        try {
            SalesAgent agent = salesSrv.loginSalesAgent(username, password);
            passwordField.getScene().getWindow().hide();
            initializeAgentView(agent);
        } catch (ServiceException | RepositoryException | IOException e) {
            PopupMessage.showErrorMessage("Invalid credentials!");
        }
    }

    private void initializeAgentView(SalesAgent agent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("views/agent-view.fxml"));
        Stage agentStage = new Stage();
        agentStage.setResizable(false);
        agentStage.setTitle("Sales Agent");
        agentStage.setScene(new Scene(fxmlLoader.load()));
        SalesAgentController agentController = fxmlLoader.getController();
        agentController.setService(salesSrv);
        agentController.setAgent(agent);
        agentStage.show();
    }

    public void handleLoginManagerRequest() {
        String username = textFieldUsername.getText();
        String password = passwordField.getText();

        try {
            Manager manager = salesSrv.loginManager(username, password);
            passwordField.getScene().getWindow().hide();
            initializeManagerView(manager);
        } catch (ServiceException | RepositoryException | IOException e) {
            PopupMessage.showErrorMessage("Invalid credentials!");
        }
    }

    private void initializeManagerView(Manager manager) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("views/manager-view.fxml"));
        Stage managerStage = new Stage();
        managerStage.setResizable(false);
        managerStage.setTitle("Manager");
        managerStage.setScene(new Scene(fxmlLoader.load()));
        ManagerController managerController = fxmlLoader.getController();
        managerController.setService(salesSrv);
        managerStage.show();
    }
}
