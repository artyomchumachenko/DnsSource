package ru.mai.coursework.dns.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
        initScrollPaneForFilters();
    }

    /**
     * List, содержащий кнопки "Товаров" и "Цен"
     */
    List productButtonsList = new LinkedList<>();
    List priceList = new LinkedList<>();

    @FXML
    private AnchorPane mainAnchorPane;

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
    private HBox currProductPropsHBox;

    @FXML
    private VBox filterVBox;

    /**
     * Инициализация всех ImageView приложения
     */
    private void initAllImages() {
        try {
            ImageLoader.loaderSearchingImage(searchingImage);
            ImageLoader.loaderLeftImage(leftImage);
            ImageLoader.loaderRightImage(rightImage);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Инициализация Scroll Pane'a для Combo Box'a с фильтрами
     */
    private void initScrollPaneForFilters() {
        ScrollPane scroll = new ScrollPane();
        scroll.setContent(filterVBox);
        scroll.setLayoutX(17);
        scroll.setLayoutY(169);
        scroll.setPrefWidth(268);
        mainAnchorPane.getChildren().add(scroll);
    }

    /**
     * Слушатель нажатия на стрелки для листания страниц с товарами
     */
    private void pagingProductHandlers() {
        rightImage.setOnMouseClicked(event -> ProductViewController.loadNextPageProducts(
                productButtonsList,
                priceList,
                rightImage,
                leftImage,
                numberOfPage,
                categoryComboBox,
                searchingField
        ));
        leftImage.setOnMouseClicked(event -> ProductViewController.loadPrevPageProducts(
                productButtonsList,
                priceList,
                rightImage,
                leftImage,
                numberOfPage
        ));
    }

    /**
     * Слушатель нажатия на кнопку поиска товара по названию
     */
    private void searchingHandlers() {
        searchingImage.setOnMouseClicked(event -> {
            resetAllFilters();
            String name = searchingField.getText();
            ProductViewController.printSearchingResults(
                    productButtonsList,
                    name,
                    priceList,
                    rightImage, leftImage,
                    numberOfPage);
        });
    }

    /**
     * @return Список кнопок для товара
     */
    private List getProductButtonsList() {
        return productsVBox.getChildren().stream().toList();
    }

    /**
     * @return Список кнопок (правее кнопок товаров)
     */
    private List getPriceButtonsList() {
        return priceVBox.getChildren().stream().toList();
    }

    /**
     * Инициализация стартового состояния ImageViews
     */
    private void startImageStates() {
        leftImage.setDisable(true);
        leftImage.setVisible(false);
    }

    /**
     * Загрузка стартовой страницы с товарами
     */
    private void initStartPage() {
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

    /**
     * Проверка на выбор "самой низшей" категории из Combo Box'a
     * для подгрузки товаров из базы данных
     */
    private void finalCategorySelectedChecker() {
        FilterFieldController.lastCategoryChecker(
                categoryComboBox,
                acceptFilters,
                productButtonsList,
                priceList,
                rightImage, leftImage,
                numberOfPage,
                currProductPropsHBox,
                filterVBox
        );
    }

    /**
     * Сброс всех применённых фильтров
     */
    private void resetAllFilters() {
        FilterFieldController.resetFilters();
        filterVBox.getChildren().remove(1, filterVBox.getChildren().size());
        currProductPropsHBox.getChildren().remove(1, currProductPropsHBox.getChildren().size());
        startCategoriesComboBox();
        categoryComboBox.setMouseTransparent(false);
        initStartPage();
    }

    /**
     * Загрузка стартового ComboBox'a с категориями
     */
    private void startCategoriesComboBox() {
        FilterFieldController.startComboBoxCategories(categoryComboBox);
    }

    /**
     * Слушатель кликов на кнопки товаров
     */
    @FXML
    public void clickProductButtonHandler(MouseEvent event) {
        ProductViewController.clickProductButtonHandler(event);
    }

    /**
     * Слушатель событий в categoryComboBox'e
     */
    @FXML
    public void categoryBoxListener(ActionEvent event) {
        if (categoryComboBox.getValue() != null && !categoryComboBox.getValue().equals("Все товары")) {
            FilterFieldController.reloadCategoryBox(categoryComboBox);
        }
        finalCategorySelectedChecker();
    }

    /**
     * Сброс всех фильтров
     */
    @FXML
    public void cancelFilters() {
        searchingField.setText("");
        resetAllFilters();
    }
}
