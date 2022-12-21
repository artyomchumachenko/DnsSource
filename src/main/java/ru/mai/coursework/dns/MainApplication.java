package ru.mai.coursework.dns;

import com.sun.tools.javac.Main;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import ru.mai.coursework.dns.controller.MainFormController;
import ru.mai.coursework.dns.entity.Product;
import ru.mai.coursework.dns.entity.User;
import ru.mai.coursework.dns.entity.UserProducts;
import ru.mai.coursework.dns.helpers.UserProductHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainApplication extends Application {
    public static Stage primaryStage;
    private Scene scene;
    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    @Override
    public void start(Stage newPrimaryStage) throws IOException {
        Font.loadFont(getClass().getResourceAsStream("ru/mai/coursework/dns/fonts/ofont.ru_Spectral.ttf"), 12);
        Font.loadFont(getClass().getResourceAsStream("ru/mai/coursework/dns/fonts/ofont.ru_Spectral.ttf"), 20);
        FXMLLoader fxmlLoader = new FXMLLoader(
                MainApplication.class.getResource("main-form.fxml"));
        Scene mainScene = new Scene(fxmlLoader.load());
        setScene(mainScene);
        newPrimaryStage.getIcons().add(new Image("ru/mai/coursework/dns/images/dns_small.png"));
        newPrimaryStage.setTitle("DNS");
        newPrimaryStage.setScene(mainScene);
        newPrimaryStage.show();
        newPrimaryStage.setResizable(false);
        primaryStage = newPrimaryStage;
    }

    @Override
    public void stop(){
        saveBasketInDb();
    }

    private void saveBasketInDb() {
        System.out.println("Stage is closing");
        User user = MainFormController.getUser();
        List<Product> bufferProducts = MainFormController.getBasketProducts();
        UserProductHelper userProductHelper = new UserProductHelper();
        for (int i = MainFormController.getLastIndexInBasket(); i < bufferProducts.size(); i++) {
            UserProducts userProduct = new UserProducts();
            userProduct.setUser(user);
            userProduct.setProduct(bufferProducts.get(i));
            userProductHelper.save(userProduct);
        }
    }

    public static void main(String[] args) {
        launch();
    }
}