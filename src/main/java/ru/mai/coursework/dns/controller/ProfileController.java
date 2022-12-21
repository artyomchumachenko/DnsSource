package ru.mai.coursework.dns.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import ru.mai.coursework.dns.MainApplication;
import ru.mai.coursework.dns.entity.Product;
import ru.mai.coursework.dns.entity.User;
import ru.mai.coursework.dns.entity.bridge.UserProducts;
import ru.mai.coursework.dns.helpers.bridge.UserProductHelper;

import java.util.List;

public class ProfileController {

    @FXML
    private TextField loginTextField;

    @FXML
    private AnchorPane mainAnchorPane;

    @FXML
    private TextField phoneTextField;

    @FXML
    private Button exit;

    @FXML
    void initialize() {
        loadInfoAboutUser();
    }

    private void loadInfoAboutUser() {
        loginTextField.setText("Логин: " + MainFormController.getUser().getLogin());
        if (MainFormController.getUser().getPhone() != null) {
            phoneTextField.setText("Номер: " + MainFormController.getUser().getPhone());
        } else {
            phoneTextField.setText("Номер: не указан");
        }
    }

    @FXML
    private void exitFromProfile() {
        String exit = MainFormController.showConfirmationAlertBeforeExitFromProfile();
        if (exit.equals("OK")) {
            saveBasketInDb();
            MainFormController.authState.setValue(false);
            MainFormController.clearBasketProducts();
            MainFormController.profileStage.close();
        }
    }

    private void saveBasketInDb() {
        System.out.println("Stage is closing");
        User user = MainFormController.getUser();
        List<Product> bufferProducts = MainFormController.getBasketProducts();
        UserProductHelper userProductHelper = new UserProductHelper();
        for (int i = MainFormController.getLastIndexInBasket(); i < bufferProducts.size(); i++) {
            UserProducts userProduct = new UserProducts();
            userProduct.setUser(user);
            userProduct.setProduct(bufferProducts.get(i));
            userProductHelper.save(userProduct);
        }
    }
}
