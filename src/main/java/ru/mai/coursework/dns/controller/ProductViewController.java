package ru.mai.coursework.dns.controller;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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
    private static int pageNumber;
    private static final int NUM_OF_BUTTON = 5;
    private static final int ONE = 1;
    private static final int TWO = 2;

    public static List<Product> takeProductList() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Product> productList = new ProductHelper().getProductList();
        return productList;
    }

    public static void initStartProducts(List<Button> productButtons) {
        List<Product> productList = takeProductList();
        currentProductButtonIndex = 0;
        lastIndexOfProducts = productList.size() - ONE;
        pageNumber = ONE;

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

    public static void loadPrevPageProducts(List<Button> productButtons,
                                            List<ImageView> imageButtons,
                                            List<Button> priceButtons,
                                            ImageView rightImage,
                                            ImageView leftImage,
                                            TextField page) {
        List<Product> productList = takeProductList();
        currentProductButtonIndex = 0;
        currentProductIndexToLoad -= NUM_OF_BUTTON * TWO;
        nextPageProductIndex -= NUM_OF_BUTTON * TWO;

        while (currentProductIndexToLoad < nextPageProductIndex) {
            changeStateButtons(currentProductButtonIndex, productButtons, imageButtons, priceButtons, false);
            productButtons.get(currentProductButtonIndex).setText(productList.get(currentProductIndexToLoad).getProductName());
            currentProductButtonIndex++;
            currentProductIndexToLoad++;
        }
        if ((currentProductIndexToLoad - NUM_OF_BUTTON * TWO) < 0) {
            currentProductIndexToLoad = NUM_OF_BUTTON;
            nextPageProductIndex = NUM_OF_BUTTON;
            disableArrow(leftImage);
        }
        nextPageProductIndex += NUM_OF_BUTTON;
        pageNumber--;
        enableArrow(rightImage);
        page.setText(String.valueOf(pageNumber));
    }

    public static void loadNextPageProducts(List<Button> productButtons,
                                            List<ImageView> imageButtons,
                                            List<Button> priceButtons,
                                            ImageView rightImage,
                                            ImageView leftImage,
                                            TextField page) {
        List<Product> productList = takeProductList();
        currentProductButtonIndex = 0;

        while (currentProductIndexToLoad < nextPageProductIndex) {
            if (currentProductIndexToLoad > lastIndexOfProducts) {
                while (currentProductButtonIndex < NUM_OF_BUTTON) {
                    changeStateButtons(currentProductButtonIndex, productButtons, imageButtons, priceButtons, true);
                    disableArrow(rightImage);
                    currentProductButtonIndex++;
                }
            } else {
                productButtons.get(currentProductButtonIndex).setText(productList.get(currentProductIndexToLoad).getProductName());
            }
            currentProductButtonIndex++;
            currentProductIndexToLoad++;
        }
        nextPageProductIndex += NUM_OF_BUTTON;
        pageNumber++;
        page.setText(String.valueOf(pageNumber));
        enableArrow(leftImage);
    }

    private static void enableArrow(ImageView arrow) {
        arrow.setDisable(false);
        arrow.setVisible(true);
    }

    private static void disableArrow(ImageView arrow) {
        arrow.setDisable(true);
        arrow.setVisible(false);
    }

    private static void changeStateButtons(int buttonIndex,
                                           List<Button> button,
                                           List<ImageView> image,
                                           List<Button> price,
                                           boolean disable) {
        button.get(buttonIndex).setDisable(disable);
        button.get(buttonIndex).setVisible(!disable);
        image.get(buttonIndex).setDisable(disable);
        image.get(buttonIndex).setVisible(!disable);
        price.get(buttonIndex).setDisable(disable);
        price.get(buttonIndex).setVisible(!disable);
    }
}
