package ru.mai.coursework.dns.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class ProfileController {

    @FXML
    private TextField loginTextField;

    @FXML
    private AnchorPane mainAnchorPane;

    @FXML
    private TextField phoneTextField;

    @FXML
    private Button exit;

    @FXML
    void initialize() {
        loadInfoAboutUser();
    }

    private void loadInfoAboutUser() {
        loginTextField.setText("Логин: " + MainFormController.getUserLogin());
        if (MainFormController.getUserPhoneNumber() != null) {
            phoneTextField.setText("Номер: " + MainFormController.getUserPhoneNumber());
        } else {
            phoneTextField.setText("Номер: не указан");
        }
    }

    @FXML
    private void exitFromProfile() {
        String exit = MainFormController.showConfirmationAlertBeforeExitFromProfile();
        if (exit.equals("OK")) {
            MainFormController.authState.setValue(false);
            MainFormController.clearBasketProducts();
            MainFormController.profileStage.close();
        }
    }
}
