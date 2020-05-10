package View.AccountMenus.CustomerView;

import Controller.Accounts.CustomerAccountController;
import View.Menu;
import model.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CartMenu extends Menu {
    CustomerAccountController customerAccountController;

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
            private ArrayList<Product> products;

            public ArrayList<Product> getProducts() {
                return products;
            }

            @Override
            public void show() {
                System.out.println("Cart:");
                HashMap<Product, Integer> cart = CustomerAccountController.getInstance().getCart();
                ArrayList<Product> products = new ArrayList<>();
                int number = 1;
                for(Map.Entry<Product, Integer> entry: cart.entrySet()) {
                    System.out.println(number + ": " + entry.getKey().getName() + "\n" +
                            "Quantity:" + entry.getValue());
                    products.add(entry.getKey());
                    number++;
                }
                this.products = products;
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
            @Override
            public void show() {
                //TODO: THE ORDER PROBLEM...
            }

            @Override
            public void execute() {
                parentMenu.show();
                parentMenu.execute();
            }
        };
    }

    public Menu getIncreaseChosenProduct(){
        return null;
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
}
