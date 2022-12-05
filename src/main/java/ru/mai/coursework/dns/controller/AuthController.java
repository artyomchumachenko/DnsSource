package ru.mai.coursework.dns.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

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
}
