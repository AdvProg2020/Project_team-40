package view.account_menus.manager_view.manage_products_view;

import controller.accounts.ManagerAccountController;
import view.Menu;
import exceptions.AccountsException;

import java.util.HashMap;

public class ManageProductsMenu extends Menu {
    ManagerAccountController managerAccountController;

    public ManageProductsMenu(Menu parentMenu) {
        super("Manage Products Menu", parentMenu);
        managerAccountController = ManagerAccountController.getInstance();
        HashMap<Integer, Menu> submenus = new HashMap<>();
        submenus.put(1, getRemoveProduct());
        setSubMenus(submenus);
    }

    public Menu getRemoveProduct(){
        return new Menu("Remove product", this) {
            @Override
            public void show() {
                System.out.println("Enter a productId or back to return: ");
            }

            @Override
            public void execute() {
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("Back")) {
                    this.parentMenu.show();
                    this.parentMenu.execute();
                } else {
                    try {
                        managerAccountController.removeProduct(input);
                    } catch (AccountsException e) {
                        System.out.println(e.getMessage());
                    }
                }
                this.parentMenu.show();
                this.parentMenu.execute();
            }
        };
    }
}
