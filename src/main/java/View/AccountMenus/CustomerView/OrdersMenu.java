package View.AccountMenus.CustomerView;

import Controller.Accounts.CustomerAccountController;
import View.Menu;
import model.log.Log;

import java.util.HashMap;
import java.util.Map;

public class OrdersMenu extends Menu {
    CustomerAccountController customerAccountController;

    public OrdersMenu(Menu parentMenu) {
        super("Orders Menu", parentMenu);

    //    submenus.put(6, getViewOrders());
    }

    public Menu getViewOrders(){
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

    public void getShowOrder(){

    }

    public void getRateProduct(){

    }
}
