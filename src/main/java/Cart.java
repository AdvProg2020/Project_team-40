import model.Product;
import model.users.Costumer;

import java.util.ArrayList;

public class Cart {
    private ArrayList<Product> products;
    private Cart cart;

    private Cart(){
        this.products = new ArrayList<>();
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void addProduct(Product product){}

    public void removeProduct(Product product){}

    public void addProductsToUserCart(Costumer costumer){}

    public Cart getCart() {
        return cart;
    }
}
