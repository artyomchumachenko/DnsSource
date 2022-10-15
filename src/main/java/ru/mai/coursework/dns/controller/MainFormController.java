package ru.mai.coursework.dns.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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

    /**
     * Select buttons (need to write handlers for product buttons and other)
     */
    @FXML
    public void selectButton(MouseEvent event) {
        Object eventSource = event.getSource();
        if (eventSource instanceof Button) {
            Button clickButton = (Button) eventSource;
            ProductViewController.clickOnProductButton(clickButton);
        }
    }

    private void initAllImages() {
        try {
            for (Object img : imageList) {
                ImageLoader.loaderDnsImage((ImageView) img);
            }
            ImageLoader.loaderSearchingImage(searchingImage);
            ImageLoader.loaderLeftImage(leftImage);
            ImageLoader.loaderRightImage(rightImage);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void initFirstProducts() {
        productButtonsList = productsVBox.getChildren().stream().toList();
        imageList = imagesVBox.getChildren().stream().toList();
        priceList = priceVBox.getChildren().stream().toList();
        leftImage.setDisable(true);
        leftImage.setVisible(false);
        int startProductIndex = 0;
        ProductViewController.printProducts(productButtonsList, imageList, priceList,
                                                rightImage, leftImage, searchingField,
                                                startProductIndex);
    }

    private void pagingProductHandlers() {
        rightImage.setOnMouseClicked(event -> ProductViewController.loadNextPageProducts(
                productButtonsList,
                imageList,
                priceList,
                rightImage,
                leftImage,
                numberOfPage
        ));
        leftImage.setOnMouseClicked(event -> ProductViewController.loadPrevPageProducts(
                productButtonsList,
                imageList,
                priceList,
                rightImage,
                leftImage,
                numberOfPage
        ));
    }

    private void searchingHandlers() {
        searchingImage.setOnMouseClicked(event -> {
            String name = searchingField.getText();
            numberOfPage.setText(String.valueOf(1));
            ProductViewController.printSearchingResults(
                    productButtonsList,
                    name,
                    imageList,
                    priceList,
                    rightImage, leftImage,
                    searchingField);
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initAllImages();
        initFirstProducts();
        pagingProductHandlers();
        searchingHandlers();
    }
}
