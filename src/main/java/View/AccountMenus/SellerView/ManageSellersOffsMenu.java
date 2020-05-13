package View.AccountMenus.SellerView;

import Controller.Accounts.SellerAccountController;
import View.Menu;

public class ManageSellersOffsMenu extends Menu {
    SellerAccountController sellerAccountController;

    public ManageSellersOffsMenu(Menu parentMenu) {
        super("Manage Seller's Offs Menu", parentMenu);
        sellerAccountController = SellerAccountController.getInstance();
    }

    private void getViewOff(){

    }

    private void getEditOff(){

    }

    private void getAddOff(){

    }
}
