package view.main_menu;

import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import view.MenuManager;

import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuManager extends MenuManager implements Initializable {
    public Pane InnerPane;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        mainInnerPane = InnerPane;
    }

    //TODO: WRITE SOMETHING IN THE MIDDLE OF THE FU.... SCENE! IT'S TOO EMPTY!
}
