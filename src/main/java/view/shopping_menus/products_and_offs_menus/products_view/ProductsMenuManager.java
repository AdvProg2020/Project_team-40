package view.shopping_menus.products_and_offs_menus.products_view;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXScrollPane;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.VBox;
import view.MenuManager;

import java.net.URL;
import java.util.ResourceBundle;

public class ProductsMenuManager extends MenuManager implements Initializable{
    public ChoiceBox sortChoiceBox;
    public VBox productsVBox;
    public JFXScrollPane productsScrollPane;
    public JFXButton showMoreButton;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
    }
}
