package View.MainMenuView;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuManager implements Initializable{

    public Pane innerPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
    }

    public void goToProductsMenu(){
        innerPane.getChildren().clear();
        try {
            innerPane.getChildren().add(FXMLLoader.load(getClass().getResource("inner-menu.fxml")));
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void goToOffsMenu(){

    }

    public void goToAccountsMenu(){

    }

    public void exit(){

    }
}
