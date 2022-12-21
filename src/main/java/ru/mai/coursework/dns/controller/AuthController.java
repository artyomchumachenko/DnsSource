package ru.mai.coursework.dns.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import ru.mai.coursework.dns.entity.User;
import ru.mai.coursework.dns.helpers.UserHelper;

import java.net.URL;
import java.util.ResourceBundle;

public class AuthController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField loginTextField;

    @FXML
    private AnchorPane mainAnchorPane;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private Button tryToEnterButton;

    @FXML
    void initialize() {

    }

    @FXML
    private void tryToAuth() {
        User user = null;
        String login = loginTextField.getText();
        String password = passwordTextField.getText();

        if (!login.equals("") && !password.equals("")) {
            UserHelper userHelper = new UserHelper();
            user = userHelper.getUsersByLoginAndPassword(login, password);
        }
        if (user != null) {
            MainFormController.setUser(user);
            MainFormController.authStage.close();
            MainFormController.authState.setValue(true);
            MainFormController.showAlertWithoutHeaderText(
                    "Вход",
                    "Вход выполнен успешно, " + user.getLogin() + ", приятной работы!"
            );
            MainFormController.setUser(user);
            System.out.println("Load user basket");
            MainFormController.loadBasketAfterUserAuth();
        } else {
            System.out.println("Неверный логин или пароль");
            MainFormController.showAlertWithoutHeaderText(
                    "Вход",
                    "Неправильный логин или пароль"
            );
        }
    }
}
