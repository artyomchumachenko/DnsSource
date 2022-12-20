package ru.mai.coursework.dns.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import ru.mai.coursework.dns.entity.Product;

public class BasketController {

    @FXML
    private ListView<String> productsInBasket;

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
}
