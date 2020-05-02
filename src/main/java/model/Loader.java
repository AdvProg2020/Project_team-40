package model;

import model.log.Log;
import model.users.User;

import java.io.IOException;

public class Loader {
    private static Loader loader;

    static {
        try {
            loader = new Loader();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Loader() throws IOException {
        this.loadData();
    }

    public void loadData() throws IOException {
        //TODO:HANDLE IO EXCEPTION
        Category.loadData();
        Product.loadData();
        DiscountCode.loadData();
        User.loadData();
        Log.loadData();
        Off.loadData();
    }

    public void saveData() {
        Category.saveData();
        Product.saveData();
        DiscountCode.saveData();
        User.saveData();
        Log.saveData();
        Off.saveData();
    }

    public static Loader getLoader() {
        return loader;
    }
}
