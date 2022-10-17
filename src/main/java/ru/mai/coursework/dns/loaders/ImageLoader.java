package ru.mai.coursework.dns.loaders;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ru.mai.coursework.dns.controller.MainFormController;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ImageLoader extends MainFormController {

    private static final String pathToSearchingImage = "src/main/resources/ru/mai/coursework/dns/images/searching.png";
    private static final String pathToLeftImage = "src/main/resources/ru/mai/coursework/dns/images/left.png";
    private static final String pathToRightImage = "src/main/resources/ru/mai/coursework/dns/images/right.png";

    public static void loaderSearchingImage(ImageView imageView) throws FileNotFoundException {
        Image searchingImage = new Image(new FileInputStream(pathToSearchingImage));
        imageView.setImage(searchingImage);
    }

    public static void loaderLeftImage(ImageView imageView) throws FileNotFoundException {
        Image leftImage = new Image(new FileInputStream(pathToLeftImage));
        imageView.setImage(leftImage);
    }

    public static void loaderRightImage(ImageView imageView) throws FileNotFoundException {
        Image rightImage = new Image(new FileInputStream(pathToRightImage));
        imageView.setImage(rightImage);
    }
}
