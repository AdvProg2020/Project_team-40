package Main;

import exceptions.DataException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Loader;
import view.GUI;

import java.io.File;

public class Main {
    private static final String PATH = "src/main/resources";

    public static void main(String[] args) {
        initializeLoading();
        GUI.initialize();
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

}