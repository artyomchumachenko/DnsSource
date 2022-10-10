package ru.mai.coursework.dns.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import ru.mai.coursework.dns.loaders.ImageLoader;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

public class MainFormController implements Initializable {

    List<Button> productButtonsList = new LinkedList<>();
    List<ImageView> imageList = new LinkedList<>();
    List<Button> priceList = new LinkedList<>();

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

    @FXML
    private TextField numberOfPage;

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

    private void initFirstProducts() {
        productButtonsList.add(pButton1);
        productButtonsList.add(pButton2);
        productButtonsList.add(pButton3);
        productButtonsList.add(pButton4);
        productButtonsList.add(pButton5);

        imageList.add(pImage1);
        imageList.add(pImage2);
        imageList.add(pImage3);
        imageList.add(pImage4);
        imageList.add(pImage5);

        priceList.add(priceButton1);
        priceList.add(priceButton2);
        priceList.add(priceButton3);
        priceList.add(priceButton4);
        priceList.add(priceButton5);
        ProductViewController.initStartProducts(productButtonsList);
    }

    private void imagesHandlers() {
        searchingImage.setOnMouseClicked(event -> {
            searchingField.setText("Я НАЖИМАЮ НА SEARCH IMAGE");
        });
        leftImage.setOnMouseClicked(event -> {
            searchingField.setText("Я НАЖИМАЮ НА LEFT IMAGE");
        });
    }

    private void pagingProductHandlers() {
        rightImage.setOnMouseClicked(event -> {
            ProductViewController.loadNextProducts(productButtonsList, imageList, priceList,
                                                    rightImage);
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initAllImages();
        initFirstProducts();
        pagingProductHandlers();

        imagesHandlers();
    }
}
