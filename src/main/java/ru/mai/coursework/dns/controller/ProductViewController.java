package ru.mai.coursework.dns.controller;

import com.sun.tools.javac.Main;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import ru.mai.coursework.dns.entity.Product;
import ru.mai.coursework.dns.entity.ProductCategory;
import ru.mai.coursework.dns.entity.ProductCh;
import ru.mai.coursework.dns.helpers.ProductHelper;

import java.util.LinkedList;
import java.util.List;

public class ProductViewController {
    /**
     * Индекс первого продукта для загрузки на следующую страницу
     */
    private static int nextPageProductIndex;
    /**
     * Текущий индекс продукта для загрузки из базы данных
     */
    private static int currentProductIndexToLoad;
    /**
     * Текущий индекс кнопки товара для загрузки информации из базы данных
     */
    private static int currentProductButtonIndex;
    private static int pageNumber;
    /**
     * Регулярка для выделения индекса из названия кнопки
     * Пример: pButton4 -> id = 4; нажата кнопка №5
     */
    private static final String REGEX_BUTTON_ID = "\\D";
    /**
     * Количество кнопок товаров на главной странице
     */
    private static final int NUM_OF_BUTTON = 5;
    private static final int ONE = 1;
    private static final int TWO = 2;
    /**
     * Низшая категория товара найдена
     */
    private static boolean isSelectedCategoryFound = false;
    /**
     * Загрузка первых 5 товаров из базы данных в список товаров
     */
    private static List<Product> productList = new ProductHelper().productListAll(NUM_OF_BUTTON * ONE);

    private static Product chosenProduct = null;

    /**
     * Вывод продуктов из productList в pButtons
     */
    public static void printProducts(
            List<Button> productButtons,
            List<Button> priceButtons,
            ImageView rightImage, ImageView leftImage,
            int startProductIndex,
            TextField page
    ) {
        pageNumber = ONE;
        if (productList.isEmpty()) {
            // If the list is empty we fill it with all products
            productList = new ProductHelper().productListAll(NUM_OF_BUTTON * pageNumber);
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

    /**
     * Очистка productList
     */
    public static void resetProductList() {
        isSelectedCategoryFound = false;
        productList = new LinkedList<>();
    }

    /**
     * Вывод продуктов, полученных в результате поиска по названию
     */
    public static void printSearchingResults(
            List<Button> productButtons,
            String name,
            List<Button> priceList,
            ImageView rightImage, ImageView leftImage,
            TextField page
    ) {
        // Заполнение productList из базы данных по названию продукта
        productList = new ProductHelper().productListByName(name, NUM_OF_BUTTON * pageNumber);
        if (productList.size() != 0) {
            int startProductIndex = 0;
            printProducts(productButtons, priceList, rightImage, leftImage, startProductIndex, page);
        } else MainFormController.showAlertWithoutHeaderText(
                "Предупреждение!",
                "Товаров с таким названием не существует"
        );
    }

    /**
     * Вывод продуктов, соответствующих определённой категории, выбранной в categoryComboBox
     */
    public static void printCategoryFilterResults(
            List<Button> productButtons,
            List<Button> priceList,
            ImageView rightImage, ImageView leftImage,
            TextField page
    ) {
        // Сохраняем копию текущего productList на случай, если товаров, выбранной категории, не будет найдено
        List<Product> bufferProductList = productList;
        // Заполнение productList из базы данных по выбранной категории
        productList = FilterFieldController.productListByCategory(NUM_OF_BUTTON * pageNumber);
        if (productList.isEmpty()) {
            productList = bufferProductList;
            System.out.println("pl is empty");
        } else {
            isSelectedCategoryFound = true;
        }
        int startProductIndex = 0;
        printProducts(productButtons, priceList, rightImage, leftImage, startProductIndex, page);
    }

    public static void printFullFilterResults(
            List<Button> productButtons,
            List<Button> priceList,
            ImageView rightImage, ImageView leftImage,
            TextField page
    ) {
        List<Product> bufferProductList = productList;
        productList = FilterFieldController.productListByProductChs(
                NUM_OF_BUTTON * pageNumber
        );
        if (productList.isEmpty()) {
            productList = bufferProductList;
            System.out.println("ТОВАРОВ НЕ НАЙДЕНО");
            MainFormController.showAlertWithoutHeaderText(
                    "Предупреждение!",
                    "Товаров с такими фильтрами не найдено"
            );
        } else {
            isSelectedCategoryFound = false;
        }
        int startProductIndex = 0;
        printProducts(productButtons, priceList, rightImage, leftImage, startProductIndex, page);
    }

    /**
     * Вывод предыдущей старницы товаров
     */
    public static void loadPrevPageProducts(
            List<Button> productButtons,
            List<Button> priceButtons,
            ImageView rightImage, ImageView leftImage,
            TextField page
    ) {
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

    /**
     * Загрузка следующей страницы с товарами
     */
    public static void loadNextPageProducts(
            List<Button> productButtons,
            List<Button> priceButtons,
            ImageView rightImage, ImageView leftImage,
            TextField page,
            ComboBox<String> cmbBox,
            TextField textField
    ) {
        currentProductButtonIndex = 0;
        pageNumber++;

        if (cmbBox.getValue() != null && !cmbBox.getValue().equals("Все товары") && isSelectedCategoryFound) {
            productList = FilterFieldController.productListByCategory(NUM_OF_BUTTON * pageNumber);
        } else {
            productList = new ProductHelper().productListAll(NUM_OF_BUTTON * pageNumber);
        }
        if (!textField.getText().equals("")) {
            productList = new ProductHelper().productListByName(textField.getText(), NUM_OF_BUTTON * pageNumber);
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

    /**
     * Слушатель кликов на кнопки товаров
     */
    public static void clickProductButtonHandler(MouseEvent event) {
        System.out.println("Select button successfully");
        Object eventSource = event.getSource();
        if (eventSource instanceof Button clickButton) {
            clickOnProductButton(clickButton);
        }
    }

    /**
     * Вывод информации о товаре, на который кликнул пользователь
     */
    public static void clickOnProductButton(Button productButton) {
        String buttonId = productButton.getId();
        buttonId = buttonId.replaceAll(REGEX_BUTTON_ID, "");
        // Индекс продукта в productList'e на который нажал пользователь
        int clickProductId = Integer.parseInt(buttonId) + NUM_OF_BUTTON * (pageNumber - ONE);
        Product clickProduct = productList.get(clickProductId);
        setChosenProduct(clickProduct);

        System.out.println("Product name: " + clickProduct.getProductName());
        MainFormController.addProductToBasket(clickProduct);
        for (ProductCategory cat : clickProduct.getProductCategories()) {
            System.out.println("Product category = " + cat.getCategory().getCategoryName());
            System.out.println("Product upperCategoryId = " + cat.getCategory().getUpCategoryId());
        }
        for (ProductCh ch : clickProduct.getProductChs()) {
            System.out.println("Product chName = " + ch.getCharacteristic().getChName());
            System.out.println("Product chValue = " + ch.getChValue());
        }
    }

    public static Product getChosenProduct() {
        return chosenProduct;
    }

    public static void setChosenProduct(Product chosenProduct) {
        ProductViewController.chosenProduct = chosenProduct;
    }

    /**
     * Изменение состояния видимости кнопок товаров
     */
    private static void changeStateButtons(
            int buttonIndex,
            List<Button> button,
            List<Button> price,
            boolean disable
    ) {
        button.get(buttonIndex).setDisable(disable);
        button.get(buttonIndex).setVisible(!disable);
        price.get(buttonIndex).setDisable(disable);
        price.get(buttonIndex).setVisible(!disable);
    }
}
