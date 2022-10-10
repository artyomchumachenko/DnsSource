package ru.mai.coursework.dns.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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

    List productButtonsList = new LinkedList<>();
    List imageList = new LinkedList<>();
    List priceList = new LinkedList<>();

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
    private VBox productsVBox;

    @FXML
    private TextField searchingField;

    @FXML
    private ImageView searchingImage;

    @FXML
    private ImageView leftImage;

    @FXML
    private ImageView rightImage;

    @FXML
    private TextField numberOfPage;

    @FXML
    private VBox imagesVBox;

    @FXML
    private VBox priceVBox;

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
        productButtonsList = productsVBox.getChildren().stream().toList();
        leftImage.setDisable(true);
        leftImage.setVisible(false);
        imageList = imagesVBox.getChildren().stream().toList();
        priceList = priceVBox.getChildren().stream().toList();
        ProductViewController.initStartProducts(productButtonsList);
    }

    private void imagesHandlers() {
        searchingImage.setOnMouseClicked(event -> {
            searchingField.setText("Я НАЖИМАЮ НА SEARCH IMAGE");
        });
    }

    private void pagingProductHandlers() {
        rightImage.setOnMouseClicked(event -> {
            ProductViewController.loadNextPageProducts(
                    productButtonsList,
                    imageList,
                    priceList,
                    rightImage,
                    leftImage,
                    numberOfPage
            );
        });
        leftImage.setOnMouseClicked(event -> {
            ProductViewController.loadPrevPageProducts(
                    productButtonsList,
                    imageList,
                    priceList,
                    rightImage,
                    leftImage,
                    numberOfPage
            );
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
