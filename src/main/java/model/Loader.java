package model;

import exceptions.DataException;
import model.log.Log;
import model.users.User;

import java.io.IOException;

public class Loader {
    private static Loader loader;

    static {
        loader = new Loader();
    }

    private Loader(){}

    public void loadData() throws DataException {
        Category.loadData();
        Product.loadData();
        DiscountCode.loadData();
        User.loadData();
        Log.loadData();
        Off.loadData();
    }

    public void saveData() throws DataException {
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
