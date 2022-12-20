package ru.mai.coursework.dns.controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class ProfileController {

    @FXML
    private TextField loginTextField;

    @FXML
    private AnchorPane mainAnchorPane;

    @FXML
    private TextField phoneTextField;

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
}
