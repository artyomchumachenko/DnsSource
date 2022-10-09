package ru.mai.coursework.dns.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

public class MainFormController implements Initializable {
    public static List<Label> labelList = new LinkedList<>();
    public static List<ImageView> imageLeftList = new LinkedList<>();
    public static List<ImageView> imageRightList = new LinkedList<>();
    @FXML
    private Label gridLabel1;

    @FXML
    private Label gridLabel2;

    @FXML
    private Label gridLabel3;

    @FXML
    private Label gridLabel4;

    @FXML
    private Label gridLabel5;

    @FXML
    private Label gridLabel6;

    @FXML
    private Label gridLabelName;

    @FXML
    private GridPane gridPaneMain;

    @FXML
    private ImageView imageView11;

    @FXML
    private ImageView imageView12;

    @FXML
    private ImageView imageView13;

    @FXML
    private ImageView imageView14;

    @FXML
    private ImageView imageView15;

    @FXML
    private ImageView imageView16;

    @FXML
    private ImageView imageView21;

    @FXML
    private ImageView imageView22;

    @FXML
    private ImageView imageView23;

    @FXML
    private ImageView imageView24;

    @FXML
    private ImageView imageView25;

    @FXML
    private ImageView imageView26;

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

    private List<ImageView> getLeftImages() {
        imageLeftList.add(imageView11);
        imageLeftList.add(imageView12);
        imageLeftList.add(imageView13);
        imageLeftList.add(imageView14);
        imageLeftList.add(imageView15);
        imageLeftList.add(imageView16);
        return imageLeftList;
    }

    private List<ImageView> getRightImages() {
        imageRightList.add(imageView21);
        imageRightList.add(imageView22);
        imageRightList.add(imageView23);
        imageRightList.add(imageView24);
        imageRightList.add(imageView25);
        imageRightList.add(imageView26);
        return imageRightList;
    }

    // Как отключить умный импорт
    // Как отключить прилепание кнопок в JavaFX

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ProductViewController.loadButtonHandler(loadButton, getLabels());
        try {
            ProductViewController.importImages(getLeftImages());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
