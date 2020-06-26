package view;

import exceptions.DataException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Loader;

public class GUI extends Application {
    public static void initialize(){
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/layouts/main.fxml"));
        primaryStage.setTitle("Project team 40");
        primaryStage.setScene(new Scene(root, 1100, 700));
        primaryStage.show();
        primaryStage.setOnCloseRequest(windowEvent -> {
            try {
                Loader.getLoader().saveData();
            } catch (DataException e) {
                e.printStackTrace();
            }
            System.exit(1);
        });
    }
}