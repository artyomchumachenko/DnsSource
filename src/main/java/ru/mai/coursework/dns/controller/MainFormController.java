package ru.mai.coursework.dns.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import org.hibernate.Session;
import ru.mai.coursework.dns.HibernateUtil;
import ru.mai.coursework.dns.ProductHelper;
import ru.mai.coursework.dns.entity.Product;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

public class MainFormController implements Initializable {
    @FXML
    public Label gridLabel1;

    @FXML
    public Label gridLabel2;

    @FXML
    public Label gridLabel3;

    @FXML
    public Label gridLabel4;

    @FXML
    public Label gridLabel5;

    @FXML
    public Label gridLabel6;

    @FXML
    private GridPane gridMain;

    @FXML
    private Label gridLabelName;

    @FXML
    private Button loadButton;

    public List<Label> takeLabels() {
        List<Label> labels = new LinkedList<>();
        labels.add(gridLabel1);
        labels.add(gridLabel2);
        labels.add(gridLabel3);
        labels.add(gridLabel4);
        labels.add(gridLabel5);
        labels.add(gridLabel6);
        return labels;
    }

    static int indexProduct = 0;
    static int press = 0;
    private void loadButtonHandler() {
        loadButton.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            loadButton.setText(String.valueOf(press + 1));
            Session session = HibernateUtil.getSessionFactory().openSession();
            List<Product> productList = new ProductHelper().getProductList();
            int index = 0;
            int maxIndexProduct = productList.size();
            List<Label> labels = takeLabels();
            press++;
            for (int i = 0; i < 6; i++) {
                if (index == 6) {
                    index = -1;
                }
                if (indexProduct == maxIndexProduct) {
                    indexProduct = 0;
                    press = 0;
                    break;
                }
                labels.get(index).setText(productList.get(indexProduct).getProductName());
                index++;
                indexProduct++;
            }
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadButtonHandler();
    }
}
