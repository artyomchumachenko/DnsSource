package ru.mai.coursework.dns.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

public class MainFormController implements Initializable {
    public static List<Label> labelList = new LinkedList<>();
    @FXML
    public Label gridLabel1;

    @FXML
    public Label gridLabel2;

    @FXML
    public Label gridLabel3;

    @FXML
    public Label gridLabel4;

    @FXML
    public Label gridLabel5;

    @FXML
    public Label gridLabel6;

    @FXML
    private GridPane gridMain;

    @FXML
    private Label gridLabelName;

    @FXML
    private Button loadButton;

    private List<Label> getLabels() {
        labelList.add(gridLabel1);
        labelList.add(gridLabel2);
        labelList.add(gridLabel3);
        labelList.add(gridLabel4);
        labelList.add(gridLabel5);
        labelList.add(gridLabel6);
        return labelList;
    }

    // Как отключить умный импорт
    // Как отключить прилепание кнопок в JavaFX

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ProductViewController.loadButtonHandler(loadButton, getLabels());
    }
}
