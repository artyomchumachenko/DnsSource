package ru.mai.coursework.dns;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.hibernate.Session;
import ru.mai.coursework.dns.entity.Product;

import java.io.IOException;
import java.util.List;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Main Window");
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Product> productList = new ProductHelper().getProductList();
        for (Product prd : productList) {
            System.out.println("Product - " + prd.getProductName());
        }
        System.out.println("Привет бро");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}