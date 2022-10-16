package ru.mai.coursework.dns.controller;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import ru.mai.coursework.dns.entity.Categories;
import ru.mai.coursework.dns.entity.Product;
import ru.mai.coursework.dns.entity.ProductCategory;
import ru.mai.coursework.dns.entity.ProductCh;
import ru.mai.coursework.dns.helpers.CategoryHelper;
import ru.mai.coursework.dns.helpers.ProductCategoryHelper;
import ru.mai.coursework.dns.helpers.ProductChHelper;
import ru.mai.coursework.dns.helpers.ProductHelper;

import java.util.LinkedList;
import java.util.List;

public class ProductViewController {
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
    private static final String REGEX_BUTTON_ID = "\\D";
    private static final int NUM_OF_BUTTON = 5;
    private static final int ONE = 1;
    private static final int TWO = 2;

    private static List<Product> productList = new ProductHelper().productListAll();

    /**
     * Method which initializes current pButtons, imgButtons, priceButtons
     */
    public static void printProducts(List<Button> productButtons,
                                     List<ImageView> imageButtons,
                                     List<Button> priceButtons,
                                     ImageView rightImage, ImageView leftImage,
                                     TextField searchField,
                                     int startProductIndex) {
        if (productList.isEmpty()) {
            searchField.setText("");
            productList = new ProductHelper().productListAll(); // If the list is empty we fill it with all products
        }
        enableArrow(rightImage);
        disableArrow(leftImage);
        currentProductButtonIndex = 0;
        currentProductIndexToLoad = startProductIndex;
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
        productList = new ProductHelper().productListByName(name);
        int startProductIndex = 0;
        printProducts(productButtons, imageList, priceList, rightImage, leftImage, searchField, startProductIndex);
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
        currentProductIndexToLoad = NUM_OF_BUTTON * (pageNumber - ONE);
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

    public static void clickOnProductButton(Button productButton) {
        String buttonId = productButton.getId();
        buttonId = buttonId.replaceAll(REGEX_BUTTON_ID, "");
        int clickProductId = Integer.parseInt(buttonId) + NUM_OF_BUTTON * (pageNumber - ONE);
        Product clickProduct = productList.get(clickProductId);

        System.out.println("Product name: " + clickProduct.getProductName());
        for (ProductCategory cat : clickProduct.getProductCategories()) {
            System.out.println("Product category = " + cat.getCategory().getCategoryName());
            System.out.println("Product upperCategoryId = " + cat.getCategory().getUpCategoryId());
        }
        for (ProductCh ch : clickProduct.getProductChs()) {
            System.out.println("Product chName = " + ch.getCharacteristic().getChName());
            System.out.println("Product chValue = " + ch.getChValue());
        }
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
