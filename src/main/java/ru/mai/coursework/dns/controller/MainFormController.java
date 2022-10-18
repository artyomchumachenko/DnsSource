package ru.mai.coursework.dns.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import ru.mai.coursework.dns.entity.Categories;
import ru.mai.coursework.dns.entity.CategoryCh;
import ru.mai.coursework.dns.entity.Characteristics;
import ru.mai.coursework.dns.entity.VariantsCategoryCh;
import ru.mai.coursework.dns.helpers.CategoryHelper;
import ru.mai.coursework.dns.helpers.CharacteristicHelper;
import ru.mai.coursework.dns.loaders.ImageLoader;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

public class MainFormController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initAllImages();
        initStartPage();
        pagingProductHandlers();
        searchingHandlers();
        startCategoriesComboBox();
    }

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
    private ComboBox<String> chComboBox;

    @FXML
    private ChoiceBox<String> choiceBox;

    @FXML
    private HBox currProductPropsHBox;

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

    private List getProductButtonsList() {
        return productsVBox.getChildren().stream().toList();
    }

    private List getPriceButtonsList() {
        return priceVBox.getChildren().stream().toList();
    }

    private void startImageStates() {
        leftImage.setDisable(true);
        leftImage.setVisible(false);
        choiceBox.setDisable(true);
        choiceBox.setVisible(false);
        chComboBox.setDisable(true);
        chComboBox.setVisible(false);
    }

    private void initStartPage() {
        System.out.println("Init all products successfully");
        ProductViewController.resetProductList();
        productButtonsList = getProductButtonsList();
        priceList = getPriceButtonsList();
        startImageStates();
        int startProductIndex = 0;
        ProductViewController.printProducts(
                productButtonsList,
                priceList,
                rightImage, leftImage,
                startProductIndex,
                numberOfPage
        );
    }

    private void startCategoriesComboBox() {
        FilterFieldController.startComboBoxCategories(categoryComboBox);
    }

    @FXML
    public void clickProductButtonHandler(MouseEvent event) {
        ProductViewController.clickProductButtonHandler(event);
    }

    @FXML
    public void categoryBoxListener(ActionEvent event) {
        if (categoryComboBox.getValue() != null && !categoryComboBox.getValue().equals("Все товары")) {
            FilterFieldController.reloadCategoryBox(categoryComboBox);
        }
        lastCategoryCheck();
    }

    private void lastCategoryCheck() {
        if (categoryComboBox.getItems().size() == 1) {
            categoryComboBox.setMouseTransparent(true);
            acceptFilters.setOnAction(event1 -> {
                acceptFilters();
            });
            acceptFilters.fire();

            // Take choosing category
            Categories category = new CategoryHelper().categoryById(
                    new CategoryHelper().idByCategoryName(categoryComboBox.getValue())
            );

            // Characteristic list for this category
            List<CategoryCh> chList = new CategoryHelper().chListByCategory(category);
            List<String> chNameList = new LinkedList<>();
            for (CategoryCh ch : chList) {
                chNameList.add(ch.getCharacteristic().getChName());
            }
            ObservableList<String> items = FXCollections.observableList(chNameList);
            chComboBox.setVisible(true);
            chComboBox.setDisable(false);
            chComboBox.setItems(items);

//            List<VariantsCategoryCh> variantsList = new CharacteristicHelper().variantsListByCharacteristic();
        }
    }

    @FXML
    public void selectedCharacteristic(ActionEvent event) {
        if (chComboBox.getValue() != null) {
            String item = chComboBox.getSelectionModel().getSelectedItem();
            System.out.println("Item " + item);
            Categories cat = new CategoryHelper().categoryByName(categoryComboBox.getValue());
            Characteristics ch = new CharacteristicHelper().characteristicByName(item);
            System.out.println(ch.getChId());
            System.out.println(ch.getChName());
            CategoryCh catCh = new CharacteristicHelper().categoryChByParams(
                    cat,
                    ch
            );
            List<VariantsCategoryCh> var = catCh.getVariantsCategoryChs();
            for (VariantsCategoryCh v : var) {
                System.out.println(v.getVariantValue());
            }
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

        /**
         * test function
         */
        testHBoxElements(categoryComboBox.getValue());
    }

    @FXML
    public void cancelFilters() {
        System.out.println("Click to cancel filters");
        startCategoriesComboBox();
        categoryComboBox.setMouseTransparent(false);
        initStartPage();

        /**
         * test function
         */
        currProductPropsHBox.getChildren().remove(1, currProductPropsHBox.getChildren().size());
    }

    /**
     * test function
     */
    private void testHBoxElements(String name) {
        Button testButton = new Button(name);
        testButton.setFont(Font.font(14));
        testButton.setAlignment(Pos.CENTER_LEFT);
        testButton.setMaxSize(120, 33);
        currProductPropsHBox.getChildren().add(testButton);
    }
}
