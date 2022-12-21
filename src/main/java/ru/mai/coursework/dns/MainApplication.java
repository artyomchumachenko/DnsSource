package ru.mai.coursework.dns;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.text.Font;

import java.io.IOException;
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

    public static void main(String[] args) {
        launch();
    }
}