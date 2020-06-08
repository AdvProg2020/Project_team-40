package View.AccountMenus.SellerView.ManageProductView;

import Controller.Accounts.SellerAccountController;
import View.AccountMenus.SellerView.SellersProductView.ManageSellersProductsMenu;
import View.ConsoleCommand;
import View.Menu;
import exceptions.AccountsException;
import model.Product;

import javax.security.auth.login.AccountException;
import java.util.ArrayList;
import java.util.HashMap;

public class ManageProduct extends Menu {
    SellerAccountController sellerAccountController;
    String productId;

    public ManageProduct(Menu parentMenu, String productId) {
        super("Manage Product Menu", parentMenu);
        HashMap<Integer, Menu> submenus = new HashMap<>();
        submenus.put(1, getViewProduct());
        submenus.put(2, getViewBuyers());
        submenus.put(3, getEditProduct());
        submenus.put(4, getAddProductsQuantity());
        submenus.put(5, getReduceProductsQuantity());
        this.setSubMenus(submenus);
        sellerAccountController = SellerAccountController.getInstance();
        this.productId = productId;
    }

    private Menu getViewProduct() {
        return new Menu("Product's Information", this) {
            @Override
            public void show() {
                System.out.println(Product.getProductById(productId));
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
                ArrayList<String> buyers = Product.getProductById(productId).getAllBuyers();
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
            HashMap<String, String> extraStringProperties;
            HashMap<String, Double> extraValueProperties;

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
                if(field == 1 || field == 2) {
                    System.out.println("Enter new information:");
                    newField = getValidInput(ConsoleCommand.DEFAULT, "");
                } else if(field == 3){
                    System.out.println("Enter new price:");
                    newField = getValidInput(ConsoleCommand.DOUBLE, "Enter a valid number.");
                } else if(field == 4) {
                    System.out.println("Enter new count:");
                    newField = getValidInput(ConsoleCommand.INTEGER, "Enter a valid number");
                } else if(field == 5) {
                    newField = getNewCategory();
                    getProperties(newField);
                } else
                    newField = getNewStatus();
                try {
                    sellerAccountController.editProduct(productId, fields[field - 1], newField,
                            extraValueProperties, extraStringProperties);
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

            private String getNewCategory() {
                return new ManageSellersProductsMenu(this).getCategory();
            }

            private void getProperties(String categoryName) {
                extraStringProperties = new HashMap<>();
                extraValueProperties = new HashMap<>();
                ArrayList<String> categoryProperties = sellerAccountController.getCategoryProperties(categoryName);
                for(String property: categoryProperties) {
                    System.out.println("Type of property: " + property + "\nEnter value:");
                    String value = getValidInput(ConsoleCommand.DEFAULT, "");
                    if(ConsoleCommand.DOUBLE.getStringMatcher(value).matches()) {
                        extraValueProperties.put(property, Double.parseDouble(value));
                    } else {
                        extraStringProperties.put(property, value);
                    }
                }
            }
        };
    }

    private Menu getAddProductsQuantity() {
        return new Menu("Add Product's Quantity", this) {
            @Override
            public void show() {
                System.out.println("Quantity: " + Product.getProductById(productId).getCount());
                System.out.println("Enter quantity of new products:");
            }

            @Override
            public void execute() {
                int addedQuantity = Integer.parseInt(getValidInput(ConsoleCommand.INTEGER,
                        "Enter a valid number."));
                sellerAccountController.increaseProductsCount(addedQuantity, productId);
                System.out.println("Product's count increased.");
                parentMenu.show();
                parentMenu.execute();
            }
        };
    }

    private Menu getReduceProductsQuantity() {
        return new Menu("Reduce Product's Quantity", this) {
            @Override
            public void show() {
                System.out.println("Quantity: " + Product.getProductById(productId).getCount());
                System.out.println("Enter number of Products tou want to remove:");
            }

            @Override
            public void execute() {
                int removedQuantity = Integer.parseInt(getValidInput(ConsoleCommand.INTEGER,
                        "Enter a valid number."));
                try {
                    sellerAccountController.decreaseProductCount(removedQuantity, productId);
                    System.out.println("Product's count decreased.");
                } catch (AccountException e) {
                    System.out.println(e.getMessage());
                }
                parentMenu.show();
                parentMenu.execute();
            }
        };
    }
}
