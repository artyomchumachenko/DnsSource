package ru.mai.coursework.dns.controller;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import org.hibernate.Session;
import ru.mai.coursework.dns.HibernateUtil;
import ru.mai.coursework.dns.entity.Product;
import ru.mai.coursework.dns.helpers.ProductHelper;

import java.util.List;

public class ProductViewController extends MainFormController {
    private static int nextPageProductIndex;
    private static int currentProductIndexToLoad;
    private static int currentProductButtonIndex;
    private static int lastIndexOfProducts;
    private static final int NUM_OF_BUTTON = 5;
    private static final int ONE = 1;

    public static List<Product> takeProductList() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Product> productList = new ProductHelper().getProductList();
        return productList;
    }

    public static void initStartProducts(List<Button> productButtons) {
        List<Product> productList = takeProductList();
        currentProductButtonIndex = 0;
        lastIndexOfProducts = productList.size() - ONE;

        if (lastIndexOfProducts >= NUM_OF_BUTTON) {
            nextPageProductIndex = NUM_OF_BUTTON;
        }
        for (int i = currentProductIndexToLoad; i < nextPageProductIndex; i++) {
            productButtons.get(currentProductButtonIndex).setText(productList.get(currentProductIndexToLoad).getProductName());
            currentProductButtonIndex++;
            currentProductIndexToLoad++;
        }
        nextPageProductIndex += NUM_OF_BUTTON;
    }

    public static void loadNextProducts(List<Button> productButtons,
                                        List<ImageView> imageButtons,
                                        List<Button> priceButtons,
                                        ImageView rightImage) {
        List<Product> productList = takeProductList();
        currentProductButtonIndex = 0;

        for (int i = currentProductIndexToLoad; i < nextPageProductIndex; i++) {
            if (i > lastIndexOfProducts) {
                currentProductIndexToLoad = 0;
                nextPageProductIndex = 0;
                for (int currButton = currentProductButtonIndex; currButton < NUM_OF_BUTTON; currButton++) {
                    disableMissingButton(currButton, productButtons, imageButtons, priceButtons, rightImage);
                }
                break;
            }
            productButtons.get(currentProductButtonIndex).setText(productList.get(currentProductIndexToLoad).getProductName());
            currentProductButtonIndex++;
            currentProductIndexToLoad++;
        }
        nextPageProductIndex += NUM_OF_BUTTON;
    }

    private static void disableMissingButton(int buttonIndex,
                                             List<Button> pButton,
                                             List<ImageView> imageButtons,
                                             List<Button> priceButtons,
                                             ImageView rightImage) {
        pButton.get(buttonIndex).setDisable(true);
        pButton.get(buttonIndex).setVisible(false);
        imageButtons.get(buttonIndex).setDisable(true);
        imageButtons.get(buttonIndex).setVisible(false);
        priceButtons.get(buttonIndex).setDisable(true);
        priceButtons.get(buttonIndex).setVisible(false);
        rightImage.setDisable(true);
        rightImage.setVisible(false);
    }
}
