package model;

import exceptions.DataException;
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

    private Loader() throws IOException {}

    public void loadData() throws IOException, DataException {
        //TODO:HANDLE IO EXCEPTION
        //TODO: WHAT IS DATA EXCEPTION AND HOW TO HANDLE IT?
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
