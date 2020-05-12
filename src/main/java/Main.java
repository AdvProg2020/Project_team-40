import View.MainMenu;
import View.Menu;
import exceptions.DataException;
import model.Loader;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        run();
    }

    private static void run() {
        try {
            Loader.getLoader().loadData();
        } catch (DataException e) {
            System.out.println(e.getMessage());
        }
        MainMenu mainMenu = new MainMenu();
        Menu.setScanner(new Scanner(System.in));
        mainMenu.show();
        mainMenu.execute();
    }
}
