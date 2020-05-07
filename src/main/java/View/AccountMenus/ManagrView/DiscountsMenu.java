package View.AccountMenus.ManagrView;

import Controller.Accounts.ManagerAccountController;
import View.Menu;
import exceptions.AccountsException;
import model.DiscountCode;

import java.util.ArrayList;
import java.util.HashMap;

public class DiscountsMenu extends Menu {
    ManagerAccountController managerAccountController;

    public DiscountsMenu(Menu parentMenu) {
        super("Manage Discounts Menu", parentMenu);
        managerAccountController = ManagerAccountController.getInstance();
        HashMap<Integer, Menu> submenus = new HashMap<>();
        submenus.put(1, getCreateDiscount());
        submenus.put(2, getViewDiscount());
        submenus.put(3, getEditDiscount());
        submenus.put(4, getRemoveDiscount());
        setSubMenus(submenus);
    }

    @Override
    public void show() {
        for (DiscountCode discountCode : managerAccountController.getAllDiscountCodes()) {
            System.out.println(discountCode);
            System.out.println("--------------------------------------------------");
        }
        System.out.println("=====================================================");
        super.show();
    }

    public Menu getCreateDiscount(){
        return new Menu("Create discount code", this) {
            @Override
            public void show() {}

            @Override
            public void execute() {
                System.out.println("Enter starting date in this format: dd-MM-yyyy HH:mm");
                String start = scanner.nextLine();
                System.out.println("Enter ending date in this format: dd-MM-yyyy HH:mm");
                String end = scanner.nextLine();
                System.out.println("Enter discount percentage: ");
                int percentage = Integer.parseInt(scanner.nextLine());
                System.out.println("Enter maximum discount amount for a product: ");
                double max = Double.parseDouble(scanner.nextLine());
                System.out.println("Enter number of codes per user: ");
                int count = Integer.parseInt(scanner.nextLine());
                System.out.println("Enter included usernames. Enter OK to end.");
                ArrayList <String> allUsernames = new ArrayList<>();
                String username;
                while (!(username = scanner.nextLine()).equalsIgnoreCase("Ok")){
                    allUsernames.add(username);
                }
                try {
                    managerAccountController.createDiscount(start, end, percentage, max, count, allUsernames);
                } catch (AccountsException e) {
                    System.out.println(e.getMessage());
                }
                this.parentMenu.show();
                this.parentMenu.execute();
            }
        };
    }

    public Menu getViewDiscount(){
        return null;
    }

    public Menu getEditDiscount(){
        return null;
    }

    public Menu getRemoveDiscount(){
        return null;
    }
}
