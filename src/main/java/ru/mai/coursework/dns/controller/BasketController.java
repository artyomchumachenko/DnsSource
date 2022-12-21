package ru.mai.coursework.dns.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import ru.mai.coursework.dns.entity.Product;
import ru.mai.coursework.dns.entity.User;
import ru.mai.coursework.dns.helpers.bridge.UserProductHelper;

public class BasketController {

    @FXML
    private ListView<String> productsInBasket;

    @FXML
    private Button delete;

    @FXML
    void initialize() {
        showProductFromBasket();
    }

    private void showProductFromBasket() {
        if (MainFormController.getBasketProducts() != null) {
            for (Product pr : MainFormController.getBasketProducts()) {
                productsInBasket.getItems().add(pr.getProductName());
            }
        }
    }

    @FXML
    private void clickToProduct() {
        int productIndex = productsInBasket.getSelectionModel().getSelectedIndex();
        User authUser = MainFormController.getUser();
        Product selectedProduct = MainFormController.getBasketProducts().get(productIndex);

        UserProductHelper userProductHelper = new UserProductHelper();
        userProductHelper.deleteByUserAndProduct(authUser, selectedProduct);
        productsInBasket.getItems().remove(productIndex);
        MainFormController.deleteProductByIndex(productIndex);
    }
}
