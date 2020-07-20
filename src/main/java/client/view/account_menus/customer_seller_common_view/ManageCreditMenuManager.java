package client.view.account_menus.customer_seller_common_view;

import client.view.MenuManager;
import javafx.scene.input.MouseEvent;

public class ManageCreditMenuManager extends MenuManager {
    private static boolean isToWallet;

    public void moveCredit() {
    }

    public static void setIsToWallet(boolean isToWallet) {
        ManageCreditMenuManager.isToWallet = isToWallet;
    }
}
