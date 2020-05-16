package View.AccountMenus.CustomerView;

import Controller.Accounts.CustomerAccountController;
import View.ConsoleCommand;
import View.Menu;
import model.Product;
import model.log.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OrdersMenu extends Menu {
    CustomerAccountController customerAccountController;

    public OrdersMenu(Menu parentMenu) {
        super("Orders Menu", parentMenu);
        HashMap<Integer, Menu> submenus = new HashMap<>();
        submenus.put(1, getViewOrders());
        submenus.put(2, getShowOrder());
        submenus.put(3, getRateProduct());
        this.setSubMenus(submenus);
        customerAccountController = CustomerAccountController.getInstance();
    }

    private Menu getViewOrders() {
        return new Menu("Orders", this) {
            @Override
            public void show() {
                System.out.println("Orders:");
                HashMap<String, Log> orders = customerAccountController.getOrders();
                for(Map.Entry<String, Log> order: orders.entrySet()) {
                    System.out.println("Date: " + order.getValue().getDate());
                    System.out.println("Cost: " + order.getValue().getCost());
                }
            }

            @Override
            public void execute() {
                parentMenu.show();
                parentMenu.execute();
            }
        };
    }

    private Menu getShowOrder() {
        return new Menu("Order's Details", this) {
            //TODO: HASN'T BEEN TESTED
            @Override
            public void show() {
                ArrayList<Log> orders = new ArrayList<>(customerAccountController.getOrders().values());
                for(int i = 1; i <= orders.size(); i++) {
                    System.out.println(i + ": " + orders.get(i - 1).getDate() + "\n" + orders.get(i - 1).getCost());
                }
                System.out.println("Choose one of the logs:");
                int numOfOrder = getNumberOfNextMenu(orders.size());
                System.out.println(orders.get(numOfOrder - 1));
            }

            @Override
            public void execute() {
                parentMenu.show();
                parentMenu.execute();
            }
        };
    }

    private Menu getRateProduct() {
        return new Menu("Rating Menu", this) {
            String productId;

            @Override
            public void show() {
                ArrayList<Log> orders = new ArrayList<>(customerAccountController.getOrders().values());
                for(int i = 1; i <= orders.size(); i++) {
                    System.out.println(i + ": " + orders.get(i - 1).getDate() + "\n" + orders.get(i - 1).getCost());
                }
                System.out.println("Choose one of the logs:");
                int numOfOrder = getNumberOfNextMenu(orders.size());
                Log log = orders.get(numOfOrder - 1);
                System.out.println("Choose one of the products to rate.");
                int productsNumber = 1;
                ArrayList<String> productIds= new ArrayList<>(log.getProductsId().keySet());
                for(String productId: productIds) {
                    System.out.println(productsNumber + ". " + Product.getProductById(productId));
                    productsNumber++;
                }
                int choseProductNumber = getNumberOfNextMenu(productIds.size());
                productId = productIds.get(choseProductNumber - 1);
                System.out.println("Product: " + Product.getProductById(productId).getName() +
                        "\nEnter A number from  1 to 5:");
            }

            @Override
            public void execute() {
                try {
                    customerAccountController.rateProduct(productId, getNumberOfNextMenu(5));
                }catch(Exception e){
                    System.out.println(e.getMessage());
                };

                parentMenu.show();
                parentMenu.execute();
            }
        };
    }
    //TODO: RATE METHOD NEEDS A TEST!
}
