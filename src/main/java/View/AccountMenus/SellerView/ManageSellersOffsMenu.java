package View.AccountMenus.SellerView;

import Controller.Accounts.SellerAccountController;
import View.ConsoleCommand;
import View.Menu;
import exceptions.AccountsException;
import model.Off;
import model.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class ManageSellersOffsMenu extends Menu {
    SellerAccountController sellerAccountController;

    public ManageSellersOffsMenu(Menu parentMenu) {
        super("Manage Seller's Offs Menu", parentMenu);
        HashMap<Integer, Menu> submenus = new HashMap<>();
        submenus.put(1, getViewOffs());
        submenus.put(2, getViewOff());
        submenus.put(3, getAddOff());
        submenus.put(4, getEditOff());
        sellerAccountController = SellerAccountController.getInstance();
    }

    private Menu getViewOffs() {
        return new Menu("List of Offs", this) {
            @Override
            public void show() {
                System.out.println("Offs:");
                HashMap<String, Off> offs = sellerAccountController.getAllOffs();
                int offNumber = 1;
                for(Off off: offs.values()) {
                    System.out.println(offNumber + ". " + off.getId());
                }
            }

            @Override
            public void execute() {
                parentMenu.show();
                parentMenu.execute();
            }
        };
    }

    private Menu getViewOff() {
        return new Menu("Off Information", this) {
            ArrayList<Off> offs;

            @Override
            public void show() {
                offs = showAndGetListOfOffs();
            }

            @Override
            public void execute() {
                int chosenOffNumber = getNumberOfNextMenu(offs.size());
                System.out.println(offs.get(chosenOffNumber - 1));
                parentMenu.show();
                parentMenu.execute();
            }
        };
    }

    private Menu getEditOff(){
        return new Menu("Edit Off", this) {
            ArrayList<Off> offs;

            @Override
            public void show() {
                offs = showAndGetListOfOffs();
            }

            @Override
            public void execute() {
                int chosenOffNumber = getNumberOfNextMenu(offs.size());
                Off off = offs.get(chosenOffNumber - 1);
                System.out.println(off);
                editOff(off);
                parentMenu.show();
                parentMenu.execute();
            }

            private void editOff(Off off) {
                //TODO:
            }
        };
    }

    private Menu getAddOff(){
        return new Menu("Add Off", this) {
            ArrayList<String> productsIds;
            @Override
            public void show() {
                productsIds = sellerAccountController.getSellerProductIDs();
                System.out.println("your Products:");
                int productNumber = 1;
                for(String productId: productsIds) {
                    try {
                        System.out.println(productNumber + ". " +
                                        sellerAccountController.getProductDetails(productId).getName() +
                                "\n" + productId);
                    } catch (AccountsException e) {
                        System.out.println(e.getMessage());
                    }
                    productNumber++;
                }
                System.out.println("Enter the number of the products you want to include in your off," +
                        " Enter end to finish:");
            }

            @Override
            public void execute() {
                ArrayList<Product> productsInOff = new ArrayList<Product>();
                String input = scanner.nextLine();
                while(!(input.equalsIgnoreCase("end"))) {
                    if(ConsoleCommand.INTEGER.getStringMatcher(input.trim()).matches()) {
                        int chosenProductNumber = Integer.parseInt(input);
                        try {
                            productsInOff.add(sellerAccountController.
                                    getProductDetails(productsIds.get(chosenProductNumber - 1)));
                        } catch (AccountsException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                }
                //TODO:
                parentMenu.show();
                parentMenu.execute();
            }
        };
    }

    private ArrayList<Off> showAndGetListOfOffs() {
        ArrayList<Off> offs = new ArrayList<>(sellerAccountController.getAllOffs().values());
        int offNumber = 1;
        for(Off off: offs) {
            System.out.println(offNumber + ". " + off.getId());
            offNumber++;
        }
        System.out.println("Choose one of the offs.");
        return offs;
    }
}
