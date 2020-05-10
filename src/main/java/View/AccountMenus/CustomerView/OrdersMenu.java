package View.AccountMenus.CustomerView;

import Controller.Accounts.CustomerAccountController;
import View.ConsoleCommand;
import View.Menu;
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
    }

    public Menu getViewOrders() {
        return new Menu("Orders", this) {
            @Override
            public void show() {
                HashMap<String, Log> orders = CustomerAccountController.getInstance().getOrders();
                for(Map.Entry<String, Log> order: orders.entrySet()) {
                    System.out.println(order.getValue());
                }
            }

            @Override
            public void execute() {
                parentMenu.show();
                parentMenu.execute();
            }
        };
    }

    public Menu getShowOrder(){
        return new Menu("Order", this) {
            //TODO: HASN'T BEEN TESTED
            @Override
            public void show() {
                ArrayList<Log> orders = new ArrayList<>(CustomerAccountController.getInstance().getOrders().values());
                for(int i = 1; i <= orders.size(); i++) {
                    System.out.println(i + ": " + orders.get(i - 1).getDate());
                }
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

    public Menu getRateProduct(){
        return new Menu("Rating Menu", this) {
            @Override
            public void show() {

            }

            @Override
            public void execute() {
                super.execute();
            }
        };
    }
}
