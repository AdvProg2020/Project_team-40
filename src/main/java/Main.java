import View.MainMenu;
import View.Menu;
import exceptions.DataException;
import model.Loader;
import model.users.Seller;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    private static final String PATH = "src/main/resources";

    public static void main(String[] args) {
        run();
    }

    private static void resourcesInitialization() throws DataException {
        File resourcesDirectory = new File(PATH);
        if (!resourcesDirectory.exists())
            if (!resourcesDirectory.mkdir())
                throw new DataException("System loading failed.");
    }

    private static void run() {
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
        MainMenu mainMenu = new MainMenu();
        Menu.setScanner(new Scanner(System.in));
        mainMenu.show();
        mainMenu.execute();
    }
}
