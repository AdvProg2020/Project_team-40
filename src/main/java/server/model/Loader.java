package server.model;

import exceptions.DataException;
import server.model.log.Log;
import server.model.requests.Request;
import server.model.users.User;


public class Loader {
    private static Loader loader;

    static {
        loader = new Loader();
    }

    private Loader(){}

    public void loadData() throws DataException {
        User.loadData();
        Request.loadData();
        Category.loadData();
        Product.loadData();
        DiscountCode.loadData();
        Log.loadData();
        Off.loadData();
        Comment.loadData();
        Score.loadData();
        Cart.loadData();
    }

    public void saveData() throws DataException {
        Request.saveData();
        Category.saveData();
        Comment.saveData();
        Score.saveData();
        Product.saveData();
        DiscountCode.saveData();
        User.saveData();
        Log.saveData();
        Off.saveData();
        Cart.saveData();
    }

    public static Loader getLoader() {
        return loader;
    }
}
