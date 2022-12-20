package ru.mai.coursework.dns.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import ru.mai.coursework.dns.entity.User;
import ru.mai.coursework.dns.helpers.UserHelper;

public class RegistController {

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
    private PasswordField passwordTextField1;

    @FXML
    private Button tryToEnterButton;

    @FXML
    private TextField phoneTextField;

    @FXML
    void initialize() {
        registrationButtonListener();
    }

    private void registrationButtonListener() {
        tryToEnterButton.setOnMouseClicked(event -> {
            String login = loginTextField.getText();
            if (!login.equals("")) {
                UserHelper userHelper = new UserHelper();
                if (!userHelper.isUserByLoginExist(login)) {
                    System.out.println("Логин подходит");
                    String pass = passwordTextField.getText();
                    String pass1 = passwordTextField1.getText();
                    if (!pass.equals("") && !pass1.equals("") && pass.equals(pass1)) {
                        System.out.println("Добавить нового пользователя в бд");
                        User newUser = new User();
                        newUser.setLogin(login);
                        newUser.setPassword(pass);
                        if (!phoneTextField.getText().equals("")) newUser.setPhone(phoneTextField.getText());
                        userHelper.save(newUser);
                        MainFormController.regStage.close();
                        MainFormController.showAlertWithoutHeaderText(
                                "Регистрация",
                                "Регистрация выполнена успешно!"
                        );
                    }
                } else MainFormController.showAlertWithoutHeaderText(
                        "Регистрация", "Пользователь с таким логином уже существует!"
                );
            } else MainFormController.showAlertWithoutHeaderText(
                    "Регистрация", "Введите логин!"
            );
        });
    }
}
