import exceptions.DataException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Loader;

import java.io.File;

public class Main extends Application{
    private static final String PATH = "src/main/resources";
    private static Stage stage;

    public static void main(String[] args) {
        initializeLoading();
        launch(args);
    }

    private static void resourcesInitialization() throws DataException {
        File resourcesDirectory = new File(PATH);
        if (!resourcesDirectory.exists())
            if (!resourcesDirectory.mkdir())
                throw new DataException("System loading failed.");
    }

    private static void initializeLoading(){
        try {
            resourcesInitialization();
        } catch (DataException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
        try {
            Loader.getLoader().loadData();
        } catch (DataException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        stage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("layouts/main-menu.fxml"));
        stage.setTitle("My App");
        stage.setScene(new Scene(root, 800, 800));
        stage.show();
    }

}

















//    private static void run() {
//        MainMenu mainMenu = new MainMenu();
//        Menu.setScanner(new Scanner(System.in));
//        mainMenu.show();
//        mainMenu.execute();
//    }