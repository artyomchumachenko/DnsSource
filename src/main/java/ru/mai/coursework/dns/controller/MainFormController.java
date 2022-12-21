package ru.mai.coursework.dns.controller;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.mai.coursework.dns.MainApplication;
import ru.mai.coursework.dns.entity.Product;
import ru.mai.coursework.dns.loaders.ImageLoader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

public class MainFormController implements Initializable {

    /**
     * Состояние окна авторизации
     */
    public static Stage authStage = null;
    /**
     * Состояние окна регистрации
     */
    public static Stage regStage = null;
    /**
     * Состояние окна характеристик товара
     */
    public static Stage showChs = null;
    /**
     * Состояние окна корзины
     */
    public static Stage basketStage = null;

    public static Stage profileStage = null;

    public static BooleanProperty authState = new SimpleBooleanProperty(false);

    private static List<Product> basketProducts = new ArrayList<>(); // ?????????

    private static String userLogin = "";
    private static String userPhoneNumber = "";

    public static String getUserLogin() {
        return userLogin;
    }

    public static void setUserLogin(String userLogin) {
        MainFormController.userLogin = userLogin;
    }

    public static String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public static void setUserPhoneNumber(String userPhoneNumber) {
        MainFormController.userPhoneNumber = userPhoneNumber;
    }

    /**
     * List, содержащий кнопки "Товаров" и "Цен"
     */
    List productButtonsList = new LinkedList<>();
    List priceList = new LinkedList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initAllImages();
        initStartPage();
        pagingProductHandlers();
        searchingHandlers();
        startCategoriesComboBox();
        checkAuthState();
    }

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
    private ListView<ComboBox<String>> chsListView;

    @FXML
    private Button signAccButton;

    @FXML
    private Button regAccButton;

    @FXML
    private HBox regHBox;

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

    private void checkAuthState() {
        authState.addListener((observable, oldValue, newValue) -> {
            // Only if completed
            if (newValue) {
                signAccButton.setVisible(false);
                signAccButton.setDisable(true);
                regAccButton.setVisible(false);

                regAccButton.setText("Profile (" + userLogin + ")");
                regAccButton.setOnMouseClicked(event -> {
                    System.out.println("Something");
                    showProfile();
                });
                regAccButton.setVisible(true);
            }
        });
    }

    public static void showAlertWithoutHeaderText(String title, String contentText) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);

        // Header Text: null
        alert.setHeaderText(null);
        alert.setContentText(contentText);

        alert.showAndWait();
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

    public static void addProductToBasket(Product product) {
        basketProducts.add(product);
    }

    public static List<Product> getBasketProducts() {
        return basketProducts;
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
                chsListView
        );
    }

    /**
     * Сброс всех применённых фильтров
     */
    private void resetAllFilters() {
        FilterFieldController.resetFilters();
        chsListView.getItems().clear();
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

    @FXML
    void showAuthorizationWindow(MouseEvent event) {
        FXMLLoader loader = new FXMLLoader(
                MainApplication.class.getResource("auth.fxml"));
        Stage authorizationWindow = new Stage();
        try {
            authorizationWindow.setScene(new Scene(loader.load()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        authorizationWindow.getIcons().add(new Image("ru/mai/coursework/dns/images/sign_in.png"));
        authorizationWindow.setTitle("Вход");
        authorizationWindow.initModality(Modality.WINDOW_MODAL);
        authorizationWindow.initOwner(MainApplication.primaryStage);
        authorizationWindow.show();
        authorizationWindow.setResizable(false);

        authStage = authorizationWindow;
    }

    @FXML
    void showRegistrationWindow(MouseEvent event) {
        FXMLLoader loader = new FXMLLoader(
                MainApplication.class.getResource("regist.fxml"));
        Stage authorizationWindow = new Stage();
        try {
            authorizationWindow.setScene(new Scene(loader.load()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        authorizationWindow.getIcons().add(new Image("ru/mai/coursework/dns/images/icon-256x256.png"));
        authorizationWindow.setTitle("Регистрация");
        authorizationWindow.initModality(Modality.WINDOW_MODAL);
        authorizationWindow.initOwner(MainApplication.primaryStage);
        authorizationWindow.show();
        authorizationWindow.setResizable(false);

        regStage = authorizationWindow;
    }

    @FXML
    void showProductCharacteristics(MouseEvent event) {
        clickProductButtonHandler(event);
        FXMLLoader loader = new FXMLLoader(
                MainApplication.class.getResource("show-chs.fxml"));
        Stage authorizationWindow = new Stage();
        try {
            authorizationWindow.setScene(new Scene(loader.load()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        authorizationWindow.getIcons().add(new Image("ru/mai/coursework/dns/images/more-details-icon-3.jpg"));
        authorizationWindow.setTitle("Характеристики");
        authorizationWindow.initModality(Modality.WINDOW_MODAL);
        authorizationWindow.initOwner(MainApplication.primaryStage);
        authorizationWindow.show();
        authorizationWindow.setResizable(false);

        showChs = authorizationWindow;
    }

    @FXML
    void showBasket(MouseEvent event) {
        FXMLLoader loader = new FXMLLoader(
                MainApplication.class.getResource("basket.fxml"));
        Stage authorizationWindow = new Stage();
        try {
            authorizationWindow.setScene(new Scene(loader.load()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        authorizationWindow.getIcons().add(new Image("ru/mai/coursework/dns/images/shopping_basket.png"));
        authorizationWindow.setTitle("Корзина");
        authorizationWindow.initModality(Modality.WINDOW_MODAL);
        authorizationWindow.initOwner(MainApplication.primaryStage);
        authorizationWindow.show();
        authorizationWindow.setResizable(false);

        basketStage = authorizationWindow;
    }

    void showProfile() {
        FXMLLoader loader = new FXMLLoader(
                MainApplication.class.getResource("profile.fxml"));
        Stage profileWindow = new Stage();
        try {
            profileWindow.setScene(new Scene(loader.load()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        profileWindow.getIcons().add(new Image("ru/mai/coursework/dns/images/more-details-icon-12.jpg"));
        profileWindow.setTitle("Профиль");
        profileWindow.initModality(Modality.WINDOW_MODAL);
        profileWindow.initOwner(MainApplication.primaryStage);
        profileWindow.show();
        profileWindow.setResizable(false);

        profileStage = profileWindow;
    }
}