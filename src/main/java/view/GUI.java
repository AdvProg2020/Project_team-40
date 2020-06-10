package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.main_menu.MainMenu;
import view.main_menu.MainMenuManager;

public class GUI extends Application {

    public GUI(){

    }

    public static void setMenu(){

    }

    public enum Menu{
        ACCOUNTS_MENU("/layouts/accounts-menu.fxml"),
        PRODUCTS_MENU("/layouts/products-menu.fxml"),
        MAIN_MENU("/layouts/main-menu.fxml"),
        OFFS_MENU("/layouts/offs_menu.fxml");
        Parent root;
        String file;
        boolean isPreLoaded;

        Menu(Parent root){
            this.root = root;
            isPreLoaded = true;
        }

        Menu(String file){
            this.file = file;
            isPreLoaded = false;
        }

        public String getFile(){
            return file;
        }

        public boolean isPreLoaded(){
            return isPreLoaded;
        }

        public Parent getRoot(){
            return root;
        }
    }

    public static void initialize(){
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/layouts/main.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 1000, 800));
        primaryStage.show();
    }
}