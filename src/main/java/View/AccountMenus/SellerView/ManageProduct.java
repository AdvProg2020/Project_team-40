package View.AccountMenus.SellerView;

import Controller.Accounts.SellerAccountController;
import View.ConsoleCommand;
import View.Menu;
import exceptions.AccountsException;
import model.Product;

import java.util.ArrayList;
import java.util.HashMap;

public class ManageProduct extends Menu {
    SellerAccountController sellerAccountController;
    Product product;

    public ManageProduct(Menu parentMenu, Product product) {
        super("Manage Product Menu", parentMenu);
        HashMap<Integer, Menu> submenus = new HashMap<>();
        submenus.put(1, getViewProduct());
        submenus.put(2, getViewBuyers());
        submenus.put(3, getEditProduct());
        submenus.put(4, getAddProductsQuantity());
        submenus.put(5, getReduceProductsQuantity());
        this.setSubMenus(submenus);
        sellerAccountController = SellerAccountController.getInstance();
        this.product = product;
    }

    private Menu getViewProduct() {
        return new Menu("Product's Information", this) {
            @Override
            public void show() {
                System.out.println(product);
            }

            @Override
            public void execute() {
                parentMenu.show();
                parentMenu.execute();
            }
        };
    }

    private Menu getViewBuyers() {
        return new Menu("Product's Buyers", this) {
            @Override
            public void show() {
                ArrayList<String> buyers = product.getAllBuyers();
                for(String buyer: buyers) {
                    System.out.println(buyer);
                }
            }

            @Override
            public void execute() {
                parentMenu.show();
                parentMenu.execute();
            }
        };
    }

    private Menu getEditProduct() {
        return new Menu("Edit Product", this) {
            String[] fields = {"name", "company", "price", "count", "category", "status"};

            @Override
            public void show() {
                System.out.println("Choose the field you want to edit:");
                for(int i = 1; i <= 6; i++) {
                    System.out.println(i + ". "  + fields[i - 1]);
                }
            }

            @Override
            public void execute() {
                int field = getNumberOfNextMenu(6);
                String newField = null;
                if(field == 1 || field == 2 || field == 5) {
                    System.out.println("Enter new information:");
                    newField = scanner.nextLine();
                } else if(field == 3){
                    System.out.println("Enter new price:");
                    newField = getValidInput(ConsoleCommand.DOUBLE, "Enter a valid number.");
                } else if(field == 4) {
                    System.out.println("Enter new count:");
                    newField = getValidInput(ConsoleCommand.INTEGER, "Enter a valid number");
                } else
                    newField = getNewStatus();
                try {
                    sellerAccountController.editProduct(product.getProductId(), fields[field - 1], newField);
                } catch (AccountsException e) {
                    System.out.println(e.getMessage());
                }
                parentMenu.show();
                parentMenu.execute();
            }

            private String getNewStatus() {
                System.out.println("Enter the new Status:\n" +
                        "1. Creating\n" +
                        "2. Editing\n" +
                        "3. confirmed");
                int status = getNumberOfNextMenu(3);
                if(status == 1) {
                    return "creating";
                } else if(status == 2) {
                    return"editing";
                } else {
                    return "confirmed";
                }
            }
        };
    }

    private Menu getAddProductsQuantity() {
        return null;
    }

    private Menu getReduceProductsQuantity() {
        return null;
    }
}
