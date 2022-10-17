package ru.mai.coursework.dns.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import ru.mai.coursework.dns.entity.Categories;
import ru.mai.coursework.dns.entity.CategoryCh;
import ru.mai.coursework.dns.entity.Characteristics;
import ru.mai.coursework.dns.helpers.CategoryHelper;
import ru.mai.coursework.dns.loaders.ImageLoader;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

public class MainFormController implements Initializable {

    List productButtonsList = new LinkedList<>();
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
    private VBox priceVBox;

    @FXML
    private ComboBox<String> categoryComboBox;

    @FXML
    private Button acceptFilters;

    @FXML
    private Button cancelFilters;

    @FXML
    private ComboBox<String> chComboBox;

    @FXML
    private ChoiceBox<String> choiceBox;

    private void initAllImages() {
        try {
            System.out.println("Init all images successfully");
            ImageLoader.loaderSearchingImage(searchingImage);
            ImageLoader.loaderLeftImage(leftImage);
            ImageLoader.loaderRightImage(rightImage);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void pagingProductHandlers() {
        System.out.println("Paging operation successfully");
        rightImage.setOnMouseClicked(event -> ProductViewController.loadNextPageProducts(
                productButtonsList,
                priceList,
                rightImage,
                leftImage,
                numberOfPage,
                categoryComboBox
        ));
        leftImage.setOnMouseClicked(event -> ProductViewController.loadPrevPageProducts(
                productButtonsList,
                priceList,
                rightImage,
                leftImage,
                numberOfPage
        ));
    }

    private void searchingHandlers() {
        System.out.println("Searching operation successfully");
        searchingImage.setOnMouseClicked(event -> {
            String name = searchingField.getText();
            ProductViewController.printSearchingResults(
                    productButtonsList,
                    name,
                    priceList,
                    rightImage, leftImage,
                    numberOfPage);
        });
    }

    @FXML
    public void selectButton(MouseEvent event) {
        System.out.println("Select button successfully");
        Object eventSource = event.getSource();
        if (eventSource instanceof Button) {
            Button clickButton = (Button) eventSource;
            ProductViewController.clickOnProductButton(clickButton);
        }
    }

    private void initFirstProducts() {
        System.out.println("Init all products successfully");
        ProductViewController.resetProductList();
        productButtonsList = productsVBox.getChildren().stream().toList();
        priceList = priceVBox.getChildren().stream().toList();
        leftImage.setDisable(true);
        leftImage.setVisible(false);
        int startProductIndex = 0;
        ProductViewController.printProducts(
                productButtonsList,
                priceList,
                rightImage, leftImage,
                startProductIndex,
                numberOfPage
        );

        choiceBox.setDisable(true);
        choiceBox.setVisible(false);
        chComboBox.setDisable(true);
        chComboBox.setVisible(false);
    }

    private void startCategoriesFilterField() {
        ObservableList<String> categoryList = FXCollections.observableArrayList("Все товары");
        List<String> nameList = new CategoryHelper().categoryNameListById(0);
        categoryList.addAll(nameList);
        categoryComboBox.setItems(categoryList);
        categoryComboBox.setValue(categoryList.get(0));
    }

    @FXML
    public void categoryBoxListener(ActionEvent event) {
        if (categoryComboBox.getValue() != null && !categoryComboBox.getValue().equals("Все товары")) {
            FilterFieldController.reloadCategoryBox(categoryComboBox);
        }
        if (categoryComboBox.getItems().size() == 1) {
            categoryComboBox.setMouseTransparent(true);
            acceptFilters.setOnAction(event1 -> {
                acceptFilters();
            });
            acceptFilters.fire();

//            chComboBox
            Categories category = new CategoryHelper().categoryById(
                    new CategoryHelper().idByCategoryName(categoryComboBox.getValue())
            );
            List<CategoryCh> chList = new CategoryHelper().chListByCategory(category);
            List<String> chNameList = new LinkedList<>();
            for (CategoryCh ch : chList) {
                chNameList.add(ch.getCharacteristic().getChName());
            }
            ObservableList<String> items = FXCollections.observableList(chNameList);
            chComboBox.setVisible(true);
            chComboBox.setDisable(false);
            chComboBox.setItems(items);
        }
    }

    @FXML
    public void acceptFilters() {
        System.out.println("Click to accept filters");
        ProductViewController.printCategoryFilterResults(
                productButtonsList,
                priceList,
                rightImage, leftImage,
                numberOfPage
        );
    }

    @FXML
    public void cancelFilters() {
        System.out.println("Click to cancel filters");
        startCategoriesFilterField();
        categoryComboBox.setMouseTransparent(false);
        initFirstProducts();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initAllImages();
        initFirstProducts();
        pagingProductHandlers();
        searchingHandlers();

        startCategoriesFilterField();
    }
}
