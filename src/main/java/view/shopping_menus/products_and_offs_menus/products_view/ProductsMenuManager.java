package view.shopping_menus.products_and_offs_menus.products_view;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXScrollPane;
import controller.menus.AllProductsController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import model.Product;
import view.MenuManager;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProductsMenuManager extends MenuManager implements Initializable{
    public ChoiceBox sortChoiceBox;
    public VBox productsBox;
    public JFXButton showMoreButton;
    public Label loading;

    private static int indexOfLastUser;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        showMoreItems();
        showMoreButton.setOnAction(event -> showMoreItems());
    }

    public void showMoreItems(){
        loading.setText("loading...");

        try {
            Thread.sleep(200);
        }catch(Exception e){

        }

        Node[] nodes = new Node[20];
        for(int i = 0; i < 20; ++i){
            Product product = null;

            try {
                product = AllProductsController.getInstance().getAllProducts().get(indexOfLastUser);
            }catch(IndexOutOfBoundsException e){
                break;
            }

            try {
                nodes[i] = (Node) FXMLLoader.load(getClass().getResource("/layouts/item.fxml"));
                productsBox.getChildren().add(nodes[i]);
            }catch(IOException e){
                e.printStackTrace();
            }

            indexOfLastUser++;
        }
        loading.setText("");
    }

    public static int getIndexOfLastUser(){
        return indexOfLastUser;
    }
}
