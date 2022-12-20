package ru.mai.coursework.dns;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

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
        FXMLLoader fxmlLoader = new FXMLLoader(
                MainApplication.class.getResource("main-form.fxml"));
        Scene mainScene = new Scene(fxmlLoader.load());
        setScene(mainScene);
//        newPrimaryStage.getIcons().add(new Image("images/railway_icon.png"));
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