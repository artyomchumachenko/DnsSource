package ru.mai.coursework.dns.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import ru.mai.coursework.dns.entity.*;
import ru.mai.coursework.dns.helpers.CategoryChHelper;
import ru.mai.coursework.dns.helpers.CategoryHelper;
import ru.mai.coursework.dns.helpers.ProductHelper;

import java.util.LinkedList;
import java.util.List;

public class FilterFieldController {
    private static int currentCategoryId;
    private static List<ComboBox<String>> characteristicsList = new LinkedList<>();
    private static List<ComboBox<String>> valueCharacteristicsList = new LinkedList<>();

    /**
     * Обновление categoryComboBox при выборе новой категории
     */
    public static void reloadCategoryBox(ComboBox<String> categoryComboBox) {
        clickOnCategory(categoryComboBox);
    }

    /**
     * Загрузка новых категорий для categoryComboBox после клика на "более низшую" категорию товара
     */
    private static void clickOnCategory(ComboBox<String> categoryComboBox) {
        String currentCategoryName = categoryComboBox.getValue();
        currentCategoryId = new CategoryHelper().idByCategoryName(currentCategoryName);
        List<Categories> newCategoryList = new CategoryHelper().categoryNameListByUpperId(currentCategoryId);
        ObservableList<String> observableList = FXCollections.observableArrayList(currentCategoryName);
        // Заполнение списка новых категорий для categoryComboBox
        for (Categories category : newCategoryList) {
            String categoryName = category.getCategoryName();
            observableList.add(categoryName);
        }
        categoryComboBox.setItems(observableList);
    }

    /**
     * Загрузка стартовых "самых высших" категорий для товаров
     */
    public static void startComboBoxCategories(ComboBox<String> categoryComboBox) {
        ObservableList<String> categoryList = FXCollections.observableArrayList("Все товары");
        List<Categories> newCategoryList = new CategoryHelper().categoryNameListByUpperId(0);
        for (Categories category : newCategoryList) {
            String categoryName = category.getCategoryName();
            categoryList.add(categoryName);
        }
        categoryComboBox.setItems(categoryList);
        categoryComboBox.setValue(categoryList.get(0));
    }

    /**
     * @param amount Количество товаров, которое нужно взять из базы данных (стандартно = 5)
     * @return Список товаров, соответствующих выбранной "низшей" категории
     */
    public static List<Product> productListByCategory(int amount) {
        Categories currentCategory = new CategoryHelper().categoryById(currentCategoryId);
        List<ProductCategory> productListByCategory = new ProductHelper().productListByCategory(
                currentCategory,
                amount
        );
        List<Product> productList = new LinkedList<>();
        for (ProductCategory product : productListByCategory) {
            productList.add(product.getProduct());
            System.out.println(product.getProduct().getProductName());
        }
        return productList;
    }

    /**
     * Проверка на выбор "низшей" категории из categoryComboBox
     */
    public static boolean lastCategoryChecker(
            ComboBox<String> categoryComboBox,
            Button acceptFilters,
            List<Button> productButtonsList,
            List<Button> priceList,
            ImageView rightImage, ImageView leftImage,
            TextField numberOfPage,
            HBox currProductPropsHBox,
            VBox filterVBox
    ) {
        // Условие выбора "низшей" категории - она остаётся одна в categoryComboBox
        if (categoryComboBox.getItems().size() == 1) {
            acceptCategoryFilter(
                    categoryComboBox,
                    acceptFilters,
                    productButtonsList,
                    priceList,
                    rightImage, leftImage,
                    numberOfPage
            );

            // Добавление элементов из применённых фильтров в HBox над товарами
            testHBoxElements(
                    categoryComboBox.getValue(),
                    currProductPropsHBox
            );

            ComboBox<String> chsComboBox = new ComboBox<>();
            int currCategoryId = currentCategoryId;

            Categories currCategory = new CategoryHelper().categoryById(currCategoryId);
            List<Characteristics> categoryChsList = new CategoryChHelper().methodName(currCategory);
            ObservableList<String> observableList = FXCollections.observableArrayList("Characteristics");
            for (Characteristics characteristics : categoryChsList) {
                observableList.add(characteristics.getChName());
            }
            chsComboBox.setItems(observableList);
            chsComboBox.setValue(observableList.get(0));
            chsComboBox.setPrefSize(181, 28);
            int chsListSize = 0;
            if (characteristicsList != null) {
                chsListSize = characteristicsList.size();
            }
            chsComboBox.setId("chsComboBox" + chsListSize);
            chsComboBox.setOnAction(event -> {
                System.out.println("AAA\t" + chsComboBox.getSelectionModel().getSelectedItem());
            });
            characteristicsList.add(chsComboBox);
            if (characteristicsList != null) {
                filterVBox.getChildren().add(characteristicsList.get(0));
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * Имитация клика на применение фильтров для подгрузки товаров, выбранной "низшей" категории
     */
    private static void acceptCategoryFilter(
            ComboBox<String> categoryComboBox,
            Button acceptFilters,
            List<Button> productButtonsList,
            List<Button> priceList,
            ImageView rightImage, ImageView leftImage,
            TextField numberOfPage
    ) {
        categoryComboBox.setMouseTransparent(true);
        acceptFilters.setOnAction(event1 -> ProductViewController.printCategoryFilterResults(
                productButtonsList,
                priceList,
                rightImage, leftImage,
                numberOfPage
        ));
        acceptFilters.fire();
    }

    /**
     * Добавление итемов фильтров в HBox над товарами
     */
    private static void testHBoxElements(
            String name,
            HBox currProductPropsHBox
    ) {
        Button testButton = new Button(name);
        testButton.setFont(Font.font(14));
        testButton.setAlignment(Pos.CENTER_LEFT);
        testButton.setMaxSize(120, 33);
        currProductPropsHBox.getChildren().add(testButton);
    }

    public static void resetFilters() {
        currentCategoryId = 0;
        characteristicsList = new LinkedList<>();
        valueCharacteristicsList = new LinkedList<>();
    }
}
