package ru.mai.coursework.dns.controller;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import org.hibernate.Session;
import ru.mai.coursework.dns.HibernateUtil;
import ru.mai.coursework.dns.ProductHelper;
import ru.mai.coursework.dns.entity.Product;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

public class ProductViewController extends MainFormController {

    private static int currentPage = 0;
    private static int indexProductInList = 0;
    private static final String pathToDnsImage = "C:\\Java Projects\\DNS\\src\\main\\java\\ru\\mai\\coursework\\dns\\images\\dns..jpg";

    public static void loadButtonHandler(Button loadButton, List<Label> labels) {
        loadButton.setStyle("-fx-border-color: #ff0000; -fx-border-width: thick;");
        loadButton.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            loadButton.setText(String.valueOf(currentPage + 1));
            Session session = HibernateUtil.getSessionFactory().openSession();
            List<Product> productList = new ProductHelper().getProductList();
            int index = 0;
            int maxIndexProduct = productList.size();
            currentPage++;
            for (int i = 0; i < 6; i++) {
                if (index == 6) {
                    index = -1;
                }
                if (indexProductInList == maxIndexProduct) {
                    indexProductInList = 0;
                    currentPage = 0;
                    break;
                }
                labels.get(index).setText(productList.get(indexProductInList).getProductName());
                index++;
                indexProductInList++;
            }
        });
    }

    public static void importImages(List<ImageView> leftList) throws FileNotFoundException {
        Image image = new Image(new FileInputStream("C:\\Java Projects\\DNS\\src\\main\\java\\ru\\mai\\coursework\\dns\\images\\dns..jpg"));
        for (ImageView img : leftList) {
            img.setImage(image);
            img.setStyle("-fx-border-color: black");
        }
    }
}
