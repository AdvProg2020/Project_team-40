package View.MainMenuView;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuManager implements Initializable{

    public Pane innerPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
    }

    public void setInnerPane(Parent root){
        innerPane.getChildren().clear();
        innerPane.getChildren().add(root);
    }

    public void goToProductsMenu(){
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("products-menu.fxml"));
        }catch(IOException e){
            e.printStackTrace();
        }
        setInnerPane(root);
    }

    public void goToOffsMenu(){
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("offs-menu.fxml"));
        }catch(IOException e){
            e.printStackTrace();
        }
        setInnerPane(root);
    }

    public void goToAccountsMenu(){
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("accounts-menu.fxml"));
        }catch(IOException e){
            e.printStackTrace();
        }
        setInnerPane(root);
    }

    public void exit(){

    }
}
