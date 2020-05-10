package View.AccountMenus.CustomerView;

import Controller.Accounts.CustomerAccountController;
import View.Menu;
import exceptions.AccountsException;
import model.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CartMenu extends Menu {
    public CartMenu(Menu parentMenu) {
        super("Cart Menu", parentMenu);
        HashMap<Integer, Menu> submenus = new HashMap<>();
        submenus.put(1, getShowChosenProducts());
        submenus.put(2, getShowChosenProduct());
        submenus.put(3, getIncreaseChosenProduct());
        submenus.put(4, getDecreaseChosenProduct());
        submenus.put(5, getShowTotalPrice());
        submenus.put(6, getPurchase());
        this.setSubMenus(submenus);
    }

    public Menu getShowChosenProducts(){
        return new Menu("Cart", this) {

            @Override
            public void show() {
                System.out.println("Cart:");
                HashMap<Product, Integer> cart = CustomerAccountController.getInstance().getCart();
                int productNumber = 1;
                for(Map.Entry<Product, Integer> entry: cart.entrySet()) {
                    System.out.println(productNumber + ". " + entry.getKey().getName() + "\n" +
                            "Seller: "+ entry.getKey().getSeller().getUsername() + "\n" +
                            "Quantity:" + entry.getValue());
                    productNumber++;
                }
            }

            @Override
            public void execute() {
                parentMenu.show();
                parentMenu.execute();
            }
        };
    }

    public Menu getShowChosenProduct(){
        return new Menu("Product's Details", this) {
            ArrayList<Product> products;
            @Override
            public void show() {
                products = getProductsInOrder();
            }

            @Override
            public void execute() {
                if (products.isEmpty()) {
                    System.out.println("You haven't chosen any product.");
                } else {
                    int chosenProductNumber = getNumberOfNextMenu(products.size());
                    Product product = products.get(chosenProductNumber - 1);
                    System.out.println(product);
                    System.out.println(CustomerAccountController.getInstance().getCart().get(product));
                }
                parentMenu.show();
                parentMenu.execute();
            }
        };
    }

    public Menu getIncreaseChosenProduct(){
        return new Menu("Increase Product's Quantity", this) {
            ArrayList<Product> products;
            @Override
            public void show() {
                products = getProductsInOrder();
            }

            @Override
            public void execute() {
                if (products.isEmpty()) {
                    System.out.println("You haven't chosen any product.");
                } else {
                    int chosenProductNumber = getNumberOfNextMenu(products.size());
                    Product product = products.get(chosenProductNumber - 1);
                    try {
                        CustomerAccountController.getInstance().increaseChosenProductsQuantity(product.getProductId());
                        System.out.println("Product quantity increased successfully.");
                    } catch (AccountsException e) {
                        System.out.println(e.getMessage());
                    }
                }
                parentMenu.show();
                parentMenu.execute();
            }
        };
    }

    public Menu getDecreaseChosenProduct(){
        return null;
    }

    public Menu getShowTotalPrice(){
        return null;
    }

    public Menu getPurchase(){
        return null;
    }

    public Menu getReceiveInfo(){
        return null;
    }

    public Menu getEnterDiscountCode(){
        return null;
    }

    public Menu getPayment(){
        return null;
    }

    public ArrayList<Product> getProductsInOrder() {
        System.out.println("Cart:");
        ArrayList<Product> products = new ArrayList<Product>(CustomerAccountController.getInstance().getCart().keySet());
        int productNumber = 1;
        for(Product product: products) {
            System.out.println(productNumber + ". " + product.getName() + "\n" +
                    "Seller: " + product.getSeller().getUsername());
            productNumber++;
        }
        if(!products.isEmpty())
            System.out.println("Choose Product:");
        return products;
    }
}
