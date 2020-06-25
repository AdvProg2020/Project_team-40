package view.account_menus.customer_view.cart_view;

import model.log.Log;
import view.account_menus.AllAccountsManager;

public class LogMenuManager extends AllAccountsManager {
    private static Log log;

    public static void setLog(Log log) {
        LogMenuManager.log = log;
    }
}
