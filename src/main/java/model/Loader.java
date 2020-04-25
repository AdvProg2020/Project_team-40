package model;

import model.users.User;

import java.io.IOException;

public class Loader {
    private static Loader loader = new Loader();

    private Loader(){
        this.loadData();
    }

    public void loadData() {
        Category.loadData();
        Product.loadData();
//        DiscountCode.loadData();
  //      User.loadData();
    }

    public void saveData(){
        Category.saveData();
        Product.saveData();
        DiscountCode.saveData();
        User.saveData();
    }

    public static Loader getLoader() {
        return loader;
    }
}
