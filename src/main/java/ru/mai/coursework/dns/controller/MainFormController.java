package ru.mai.coursework.dns.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import ru.mai.coursework.dns.loaders.ImageLoader;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

public class MainFormController implements Initializable {

    @FXML
    private Button pButton1;

    @FXML
    private Button pButton2;

    @FXML
    private Button pButton3;

    @FXML
    private Button pButton4;

    @FXML
    private Button pButton5;

    @FXML
    private ContextMenu pButtonCntxt1;

    @FXML
    private ContextMenu pButtonCntxt2;

    @FXML
    private ContextMenu pButtonCntxt3;

    @FXML
    private ContextMenu pButtonCntxt4;

    @FXML
    private ContextMenu pButtonCntxt5;

    @FXML
    private ImageView pImage1;

    @FXML
    private ImageView pImage2;

    @FXML
    private ImageView pImage3;

    @FXML
    private ImageView pImage4;

    @FXML
    private ImageView pImage5;

    @FXML
    private Label pLabel;

    @FXML
    private Button priceButton1;

    @FXML
    private Button priceButton2;

    @FXML
    private Button priceButton3;

    @FXML
    private Button priceButton4;

    @FXML
    private Button priceButton5;

    @FXML
    private VBox productsVBox;

    @FXML
    private Button regAccButton;

    @FXML
    private Label searchLabel;

    @FXML
    private TextField searchingField;

    @FXML
    private ImageView searchingImage;

    @FXML
    private Button signAccButton;

    @FXML
    private ImageView leftImage;

    @FXML
    private ImageView rightImage;

    private void initAllImages() {
        try {
            ImageLoader.loaderDnsImage(pImage1);
            ImageLoader.loaderDnsImage(pImage2);
            ImageLoader.loaderDnsImage(pImage3);
            ImageLoader.loaderDnsImage(pImage4);
            ImageLoader.loaderDnsImage(pImage5);
            ImageLoader.loaderSearchingImage(searchingImage);
            ImageLoader.loaderLeftImage(leftImage);
            ImageLoader.loaderRightImage(rightImage);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    List<Button> buttons = new LinkedList<>();

    private void initFirstProducts() {
        buttons.add(pButton1);
        buttons.add(pButton2);
        buttons.add(pButton3);
        buttons.add(pButton4);
        buttons.add(pButton5);
        ProductViewController.initStartProducts(buttons);
    }

    private void imagesHandlers() {
        searchingImage.setOnMouseClicked(event -> {
            searchingField.setText("Я НАЖИМАЮ НА SEARCH IMAGE");
        });
        leftImage.setOnMouseClicked(event -> {
            searchingField.setText("Я НАЖИМАЮ НА LEFT IMAGE");
        });
        rightImage.setOnMouseClicked(event -> {
            searchingField.setText("Я НАЖИМАЮ НА RIGHT IMAGE");
        });
        rightImage.setOnMouseClicked(event -> {
            ProductViewController.loadNextProducts(buttons);
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initAllImages();
        initFirstProducts();
        imagesHandlers();
    }
}
