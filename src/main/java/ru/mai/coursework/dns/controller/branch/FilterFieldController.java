package ru.mai.coursework.dns.controller.branch;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import ru.mai.coursework.dns.entity.*;
import ru.mai.coursework.dns.entity.bridge.CategoryCh;
import ru.mai.coursework.dns.entity.bridge.ChValues;
import ru.mai.coursework.dns.entity.bridge.ProductCategory;
import ru.mai.coursework.dns.entity.bridge.ProductCh;
import ru.mai.coursework.dns.helpers.*;
import ru.mai.coursework.dns.helpers.bridge.CategoryChHelper;
import ru.mai.coursework.dns.helpers.bridge.ChValuesHelper;

import java.util.LinkedList;
import java.util.List;

public class FilterFieldController {
    private static int currentCategoryId;
    private static List<ComboBox<String>> valueCharacteristicsList = new LinkedList<>();
    private static List<Characteristics> characteristicsList = new LinkedList<>();
    private static int amountCharacteristicsInFilter = 0;
    private static List<String> valueList = new LinkedList<>();

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
     * Список товаров, соответствующих выбранным фильтрам
     * @return List(Product)
     */
    public static List<Product> productListByProductChs(int amount) {
        Categories currentCategory = new CategoryHelper().categoryById(currentCategoryId);
        List<ProductCh> productChList = new ProductHelper().productChListByFilters(
                currentCategory, amount,
                amountCharacteristicsInFilter,
                characteristicsList,
                valueList
        );
        List<Product> productList = new LinkedList<>();
        for (ProductCh product : productChList) {
            productList.add(product.getProduct());
            System.out.println(product.getProduct().getProductName());
        }
        return productList;
    }

    /**
     * Проверка на выбор "низшей" категории из categoryComboBox
     */
    public static void lastCategoryChecker(
            ComboBox<String> categoryComboBox,
            Button acceptFilters,
            List<Button> productButtonsList,
            List<Button> priceList,
            ImageView rightImage, ImageView leftImage,
            TextField numberOfPage,
            HBox currProductPropsHBox,
            ListView<ComboBox<String>> chsListView
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

            // Добавление комбо боксов с выбором каждой из характеристик категории
            addAllChsValueComboBoxes(chsListView);
        }
    }

    /**
     * Добавление новых Combo Box'ов
     * содержащих значения для характеристик
     * определённой категории товаров
     */
    private static void addAllChsValueComboBoxes(ListView<ComboBox<String>> chsListView) {
        int currCategoryId = currentCategoryId;
        Categories currCategory = new CategoryHelper().categoryById(currCategoryId);
        List<Characteristics> categoryChsList = new CategoryChHelper().chsListByCategory(currCategory);
        ObservableList<String> observableChsList = FXCollections.observableArrayList();
        for (Characteristics characteristics : categoryChsList) {
            observableChsList.add(characteristics.getChName());
        }
        for (String chName : observableChsList) {
            Characteristics ch = new CharacteristicHelper().characteristicByName(chName);
            characteristicsList.add(ch);
            CategoryCh currCategoryCh = new CategoryChHelper().categoryChByCategoryAndCharacteristic(
                    currCategory,
                    ch
            );
            List<ChValues> chValues = new ChValuesHelper().valuesListByCategoryCh(currCategoryCh);

            ComboBox<String> chsValueComboBox = new ComboBox<>();
            ObservableList<String> observableValuesList = FXCollections.observableArrayList();
            for (ChValues value : chValues) {
                observableValuesList.add(value.getValue());
            }
            chsValueComboBox.setItems(observableValuesList);
            chsValueComboBox.setPromptText(chName);
            chsValueComboBox.setPrefSize(268, 28);
            int valuesListSize = 0;
            if (valueCharacteristicsList != null) {
                valuesListSize = valueCharacteristicsList.size();
            }
            chsValueComboBox.setId("valuesComboBox" + valuesListSize);
            chsValueComboBox.setOnAction(event1 -> {
                System.out.println("You clicked on " + chsValueComboBox.getSelectionModel().getSelectedItem());
                amountCharacteristicsInFilter++;
                valueList.add(chsValueComboBox.getValue());
                characteristicsList.add(ch);
            });
            chsValueComboBox.setFocusTraversable(false);
            valueCharacteristicsList.add(chsValueComboBox);
            chsListView.getItems().add(chsValueComboBox);
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
        acceptFilters.setOnAction(event1 -> {
            if (amountCharacteristicsInFilter != 0) {
                ProductViewController.printFullFilterResults(
                        productButtonsList,
                        priceList,
                        rightImage, leftImage,
                        numberOfPage
                );
            } else {
                ProductViewController.printCategoryFilterResults(
                        productButtonsList,
                        priceList,
                        rightImage, leftImage,
                        numberOfPage
                );
            }
        });
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
        testButton.setFont(Font.font(12));
        testButton.setAlignment(Pos.CENTER_LEFT);
        testButton.setMouseTransparent(true);
        testButton.setFocusTraversable(false);
        testButton.setMaxSize(120, 33);
        currProductPropsHBox.getChildren().add(testButton);
    }

    /**
     * Сбросить значения всех статических элементов,
     * относящихся к фильтрам
     */
    public static void resetFilters() {
        currentCategoryId = 0;
        amountCharacteristicsInFilter = 0;
        valueCharacteristicsList = new LinkedList<>();
        characteristicsList = new LinkedList<>();
        valueList = new LinkedList<>();
    }
}
