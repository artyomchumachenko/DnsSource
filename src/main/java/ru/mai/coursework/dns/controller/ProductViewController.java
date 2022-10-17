package ru.mai.coursework.dns.controller;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import ru.mai.coursework.dns.entity.Product;
import ru.mai.coursework.dns.entity.ProductCategory;
import ru.mai.coursework.dns.entity.ProductCh;
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
    private static int pageNumber;
    /**
     * Amount of pButtons
     */
    private static final String REGEX_BUTTON_ID = "\\D";
    private static final int NUM_OF_BUTTON = 5;
    private static final int ONE = 1;
    private static final int TWO = 2;

    private static List<Product> productList = new ProductHelper().productListAll(NUM_OF_BUTTON * ONE);

    /**
     * Method which initializes current pButtons, imgButtons, priceButtons
     */
    public static void printProducts(List<Button> productButtons,
                                     List<Button> priceButtons,
                                     ImageView rightImage, ImageView leftImage,
                                     int startProductIndex,
                                     TextField page) {
        pageNumber = ONE;
        if (productList.isEmpty()) {
            productList = new ProductHelper().productListAll(NUM_OF_BUTTON * pageNumber); // If the list is empty we fill it with all products
        }
        enableArrow(rightImage);
        disableArrow(leftImage);
        currentProductButtonIndex = 0;
        currentProductIndexToLoad = startProductIndex;
        nextPageProductIndex = NUM_OF_BUTTON;
        int lastIndexOfProducts = productList.size() - ONE;
        page.setText(String.valueOf(pageNumber));

        while (currentProductIndexToLoad < nextPageProductIndex) {
            if (currentProductIndexToLoad > lastIndexOfProducts) {
                while (currentProductButtonIndex < NUM_OF_BUTTON) {
                    // Disable not used buttons
                    changeStateButtons(currentProductButtonIndex, productButtons, priceButtons, true);
                    // If we received that on initialize first product page
                    // currentProductIndexToLoad > lastIndexOfProducts
                    // arrow can't flip pages
                    disableArrow(rightImage);
                    disableArrow(leftImage);
                    currentProductButtonIndex++;
                }
                break;
            } else {
                changeStateButtons(currentProductButtonIndex, productButtons, priceButtons, false);
                productButtons.get(currentProductButtonIndex).setText(productList.get(currentProductIndexToLoad).getProductName());
                currentProductButtonIndex++;
                currentProductIndexToLoad++;
            }
        }
        nextPageProductIndex += NUM_OF_BUTTON;
    }

    public static void resetProductList() {
        productList = new LinkedList<>();
    }

    /**
     * Print searching results
     */
    public static void printSearchingResults(List<Button> productButtons,
                                             String name,
                                             List<Button> priceList,
                                             ImageView rightImage, ImageView leftImage,
                                             TextField page) {
        productList = new ProductHelper().productListByName(name);
        int startProductIndex = 0;
        printProducts(productButtons, priceList, rightImage, leftImage, startProductIndex, page);
    }

    private static boolean isSelectedCategoryFound = true;
    public static void printCategoryFilterResults(List<Button> productButtons,
                                                  List<Button> priceList,
                                                  ImageView rightImage, ImageView leftImage,
                                                  TextField page) {
        List<Product> bufferProductList = productList;
        productList = FilterFieldController.filterProductsListResult(NUM_OF_BUTTON * pageNumber);
        if (productList.isEmpty()) {
            isSelectedCategoryFound = false;
            productList = bufferProductList;
            System.out.println("pl is empty");
        }
        int startProductIndex = 0;
        printProducts(productButtons, priceList, rightImage, leftImage, startProductIndex, page);
    }

    /**
     * Print prev product page
     */
    public static void loadPrevPageProducts(List<Button> productButtons,
                                            List<Button> priceButtons,
                                            ImageView rightImage, ImageView leftImage,
                                            TextField page) {
        currentProductButtonIndex = 0;
        pageNumber--;
        currentProductIndexToLoad = NUM_OF_BUTTON * (pageNumber - ONE);
        nextPageProductIndex = NUM_OF_BUTTON * pageNumber;

        while (currentProductIndexToLoad < nextPageProductIndex) {
            changeStateButtons(currentProductButtonIndex, productButtons, priceButtons, false);
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
                                            List<Button> priceButtons,
                                            ImageView rightImage, ImageView leftImage,
                                            TextField page,
                                            ComboBox<String> cmbBox) {
        currentProductButtonIndex = 0;
        pageNumber++;
        if (cmbBox.getValue() != null && !cmbBox.getValue().equals("Все товары") && isSelectedCategoryFound) {
            productList = FilterFieldController.filterProductsListResult(NUM_OF_BUTTON * pageNumber);
        } else {
            productList = new ProductHelper().productListAll(NUM_OF_BUTTON * pageNumber);
        }
        int lastIndexOfProducts = productList.size() - ONE;

        while (currentProductIndexToLoad < nextPageProductIndex) {
            if (currentProductIndexToLoad > lastIndexOfProducts) {
                while (currentProductButtonIndex < NUM_OF_BUTTON) {
                    changeStateButtons(currentProductButtonIndex, productButtons, priceButtons, true);
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
                                           List<Button> price,
                                           boolean disable) {
        button.get(buttonIndex).setDisable(disable);
        button.get(buttonIndex).setVisible(!disable);
        price.get(buttonIndex).setDisable(disable);
        price.get(buttonIndex).setVisible(!disable);
    }
}
