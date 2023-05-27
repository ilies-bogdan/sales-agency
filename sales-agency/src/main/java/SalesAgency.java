import controller.LoginController;
import controller.PopupMessage;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import persistence.database.*;
import persistence.orm.*;
import service.SalesService;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class SalesAgency extends Application {
    private SalesService salesSrv;
    private SessionFactory sessionFactory;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Properties properties = new Properties();
        try {
            properties.load(new FileReader("db.config"));
        } catch (IOException e) {
            PopupMessage.showErrorMessage("Can not find database config file: " + e.getMessage());
        }

        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        try {
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception e) {
            System.err.println("Session creation error: " + e);
            StandardServiceRegistryBuilder.destroy(registry);
        }

        this.salesSrv = new SalesService(
                new SalesAgentORMRepository(sessionFactory),
                new ManagerOrmRepository(sessionFactory),
                new ProductORMRepository(sessionFactory),
                new OrderORMRepository(sessionFactory),
                new OrderItemORMRepository(sessionFactory)
        );
        initializeLoginView(primaryStage);
    }

    private void initializeLoginView(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("views/login-view.fxml"));
        Scene primaryScene = new Scene(fxmlLoader.load());
        primaryStage.setResizable(false);
        primaryStage.setTitle("Login");
        primaryStage.setScene(primaryScene);
        LoginController loginController = fxmlLoader.getController();
        loginController.setService(salesSrv);
        primaryStage.show();
    }
}
