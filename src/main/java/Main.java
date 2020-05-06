import View.MainMenu;
import View.Menu;
import model.Loader;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        run();
    }

    private static void run() throws IOException {
        //TODO: FIX SAVE AND LOAD
//        Loader.getLoader().loadData();
        MainMenu mainMenu = new MainMenu();
        Menu.setScanner(new Scanner(System.in));
        mainMenu.show();
        mainMenu.execute();
    }
}
