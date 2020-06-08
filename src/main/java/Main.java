import View.MainMenu;
import View.Menu;
import exceptions.DataException;

import model.Loader;

import java.io.File;
import java.util.Scanner;

public class Main{
    private static final String PATH = "src/main/resources";

    public static void main(String[] args) {
        initializeLoading();
        System.out.println("This works! java 1.8");
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
            Menu.setScanner(new Scanner(System.in));
            MainMenu mainMenu = new MainMenu();
            mainMenu.show();
            mainMenu.execute();
        } catch (DataException e) {
            System.err.println(e.getMessage());
        }
    }
}

















//    private static void run() {
//        MainMenu mainMenu = new MainMenu();
//        Menu.setScanner(new Scanner(System.in));
//        mainMenu.show();
//        mainMenu.execute();
//    }