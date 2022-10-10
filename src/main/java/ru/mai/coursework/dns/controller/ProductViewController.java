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
    /**
     * Next product index after product in pButton5
     */
    private static int nextPageProductIndex;
    /**
     * Current product index which adding to pButton
     */
    private static int currentProductIndexToLoad;
    /**
     * Current pButton which will be filling with product
     */
    private static int currentProductButtonIndex;
    /**
     * Product index which lie last in productList
     */
    private static int lastIndexOfProducts;
    private static int pageNumber;
    /**
     * Amount of pButtons
     */
    private static final int NUM_OF_BUTTON = 5;
    private static final int ONE = 1;
    private static final int TWO = 2;

    private static List<Product> productList = initProductList();

    /**
     * Method which return all products from table @products
     *
     * @return List
     */
    public static List<Product> initProductList() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        return new ProductHelper().getProductList();
    }

    /**
     * Method which return all products with equal "name" from table @products
     *
     * @param name String for searches corresponding product
     * @return List
     */
    public static List<Product> initSearchProductList(String name) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        productList = new ProductHelper().getProductListByName(name);
        return productList;
    }

    /**
     * Method which initializes current pButtons, imgButtons, priceButtons
     */
    public static void initStartProducts(List<Button> productButtons,
                                         List<ImageView> imageButtons,
                                         List<Button> priceButtons,
                                         ImageView rightImage, ImageView leftImage,
                                         TextField searchField) {
        if (productList.isEmpty()) {
            searchField.setText("");
            productList = initProductList(); // If the list is empty we fill it with all products
        }
        enableArrow(rightImage);
        disableArrow(leftImage);
        currentProductButtonIndex = 0;
        currentProductIndexToLoad = 0;
        nextPageProductIndex = NUM_OF_BUTTON;
        lastIndexOfProducts = productList.size() - ONE;
        pageNumber = ONE;

        while (currentProductIndexToLoad < nextPageProductIndex) {
            if (currentProductIndexToLoad > lastIndexOfProducts) {
                while (currentProductButtonIndex < NUM_OF_BUTTON) {
                    // Disable not used buttons
                    changeStateButtons(currentProductButtonIndex, productButtons, imageButtons, priceButtons, true);
                    // If we received that on initialize first product page
                    // currentProductIndexToLoad > lastIndexOfProducts
                    // arrow can't flip pages
                    disableArrow(rightImage);
                    disableArrow(leftImage);
                    currentProductButtonIndex++;
                }
                break;
            } else {
                changeStateButtons(currentProductButtonIndex, productButtons, imageButtons, priceButtons, false);
                productButtons.get(currentProductButtonIndex).setText(productList.get(currentProductIndexToLoad).getProductName());
                currentProductButtonIndex++;
                currentProductIndexToLoad++;
            }
        }
        nextPageProductIndex += NUM_OF_BUTTON;
    }

    /**
     * Print searching results
     */
    public static void printSearchingResults(List<Button> productButtons,
                                             String name,
                                             List<ImageView> imageList,
                                             List<Button> priceList,
                                             ImageView rightImage, ImageView leftImage,
                                             TextField searchField) {
        productList = initSearchProductList(name);
        initStartProducts(productButtons, imageList, priceList, rightImage, leftImage, searchField);
    }

    /**
     * Print prev product page
     */
    public static void loadPrevPageProducts(List<Button> productButtons,
                                            List<ImageView> imageButtons,
                                            List<Button> priceButtons,
                                            ImageView rightImage, ImageView leftImage,
                                            TextField page) {
        currentProductButtonIndex = 0;
        pageNumber--;
        currentProductIndexToLoad = NUM_OF_BUTTON * pageNumber - NUM_OF_BUTTON;
        nextPageProductIndex = NUM_OF_BUTTON * pageNumber;

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
        page.setText(String.valueOf(pageNumber));
        enableArrow(rightImage);
    }

    public static void loadNextPageProducts(List<Button> productButtons,
                                            List<ImageView> imageButtons,
                                            List<Button> priceButtons,
                                            ImageView rightImage, ImageView leftImage,
                                            TextField page) {
        currentProductButtonIndex = 0;

        while (currentProductIndexToLoad < nextPageProductIndex) {
            if (currentProductIndexToLoad > lastIndexOfProducts) {
                while (currentProductButtonIndex < NUM_OF_BUTTON) {
                    changeStateButtons(currentProductButtonIndex, productButtons, imageButtons, priceButtons, true);
                    disableArrow(rightImage);
                    currentProductButtonIndex++;
                }
                break;
            } else {
                productButtons.get(currentProductButtonIndex).setText(productList.get(currentProductIndexToLoad).getProductName());
                currentProductButtonIndex++;
                currentProductIndexToLoad++;
            }
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
