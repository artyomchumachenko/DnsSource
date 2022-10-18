package ru.mai.coursework.dns.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import ru.mai.coursework.dns.entity.Categories;
import ru.mai.coursework.dns.entity.Product;
import ru.mai.coursework.dns.entity.ProductCategory;
import ru.mai.coursework.dns.helpers.CategoryHelper;
import ru.mai.coursework.dns.helpers.ProductHelper;

import java.util.LinkedList;
import java.util.List;

public class FilterFieldController {
    private static int currentCategoryId;

    public static void reloadCategoryBox(ComboBox<String> categoryComboBox) {
        clickOnCategory(categoryComboBox);
    }

    private static void clickOnCategory(ComboBox<String> categoryComboBox) {
        String currentCategoryName = categoryComboBox.getValue();
        currentCategoryId = new CategoryHelper().idByCategoryName(currentCategoryName);
        List<String> newCategoryList = new CategoryHelper().categoryNameListById(currentCategoryId);
        ObservableList<String> obsList = FXCollections.observableList(newCategoryList);
        categoryComboBox.setItems(obsList);
    }

    public static void startComboBoxCategories(ComboBox<String> categoryComboBox) {
        ObservableList<String> categoryList = FXCollections.observableArrayList("Все товары");
        List<String> nameList = new CategoryHelper().categoryNameListById(0); // *nameCategoryListById
        categoryList.addAll(nameList);
        categoryComboBox.setItems(categoryList);
        categoryComboBox.setValue(categoryList.get(0));
    }

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
}
