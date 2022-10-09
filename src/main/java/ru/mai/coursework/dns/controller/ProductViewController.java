package ru.mai.coursework.dns.controller;

import javafx.scene.control.Button;
import org.hibernate.Session;
import ru.mai.coursework.dns.HibernateUtil;
import ru.mai.coursework.dns.entity.Product;
import ru.mai.coursework.dns.helpers.ProductHelper;

import java.util.List;

public class ProductViewController extends MainFormController {
    private static int lastProductIndexToLoad;
    private static int startProductIndexToLoad = 0;
    private static int currentButtonIndex = 0;
    private static int amountProductsIndex = 0;

//    public static List<Product> takeProductList() {
//        Session session = HibernateUtil.getSessionFactory().openSession();
//        List<Product> productList = new ProductHelper().getProductList();
//        return productList;
//    }

    public static void initStartProducts(List<Button> buttons) {
        currentButtonIndex = 0;
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Product> productList = new ProductHelper().getProductList();
        amountProductsIndex = productList.size() - 1;
        if (amountProductsIndex >= 4) {
            lastProductIndexToLoad = 5;
        }
        for (int i = startProductIndexToLoad; i < lastProductIndexToLoad; i++) {
            buttons.get(currentButtonIndex).setText(productList.get(startProductIndexToLoad).getProductName());
            currentButtonIndex++;
            startProductIndexToLoad++;
        }
        lastProductIndexToLoad += 5;
    }

    public static void loadNextProducts(List<Button> buttons) {
        currentButtonIndex = 0;
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Product> productList = new ProductHelper().getProductList();
        int addIndexes = -1;
        if (lastProductIndexToLoad > amountProductsIndex) {
            addIndexes = lastProductIndexToLoad - amountProductsIndex;
        }
        for (int i = startProductIndexToLoad; i < lastProductIndexToLoad; i++) {
            if (i == amountProductsIndex) {
                startProductIndexToLoad = 0;
                lastProductIndexToLoad = 0;
                for (int j = 0; i < addIndexes; i++) {
                    buttons.get(currentButtonIndex).setText(productList.get(j).getProductName());
                    currentButtonIndex++;
                }
                break;
            }
            buttons.get(currentButtonIndex).setText(productList.get(startProductIndexToLoad).getProductName());
            currentButtonIndex++;
            startProductIndexToLoad++;
        }
        lastProductIndexToLoad += 5;
    }
}
