package View.AccountMenus.SellerView;

import Controller.Accounts.SellerAccountController;
import View.ConsoleCommand;
import View.Menu;
import View.ShopingMenus.ProductsAndOffsMenus.ProductsMenu;
import exceptions.AccountsException;
import model.Category;
import model.Product;

import java.util.ArrayList;
import java.util.HashMap;

public class ManageSellersProductsMenu extends Menu {
    SellerAccountController sellerAccountController;

    public ManageSellersProductsMenu(Menu parentMenu) {
        super("Manage Seller's Products Menu", parentMenu);
        HashMap<Integer, Menu> submenus = new HashMap<>();
        submenus.put(1, getViewProducts());
        submenus.put(2, manageProduct());
        submenus.put(3, getAddProduct());
        submenus.put(4, getRemoveProduct());
        this.setSubMenus(submenus);
        sellerAccountController = SellerAccountController.getInstance();
    }

    private Menu getViewProducts() {
        return new Menu("Products", this) {
            @Override
            public void show() {
                ArrayList<String> productsIds = sellerAccountController.getSellerProductIDs();
                int productNumber = 0;
                for(String productId: productsIds) {
                    try {
                        System.out.println(productNumber + ". " +
                                sellerAccountController.getProductDetails(productId).getName() +
                                "ID: " + productId);
                    } catch (AccountsException e) {
                        System.out.println(e.getMessage());
                    }
                    productNumber++;
                }
            }

            @Override
            public void execute() {
                parentMenu.show();
                parentMenu.execute();
            }
        };
    }

    private Menu manageProduct() {
        return new Menu("Manage Product Menu", this) {
            @Override
            public void show() {
                getViewProducts().show();
                System.out.println("Enter product's number:");
            }

            @Override
            public void execute() {
                ArrayList<String> productsIds = sellerAccountController.getSellerProductIDs();
                int productNumber = getNumberOfNextMenu(productsIds.size());
                try {
                    Product product = sellerAccountController.getProductDetails(productsIds.get(productNumber - 1));
                    ManageProduct manageProduct = new ManageProduct(parentMenu, product.getProductId());
                    manageProduct.show();
                    manageProduct.execute();
                    //TODO: TEST THAT DOES IT RETURN TO THIS METHOD OR TO THE RIGHT MENU
                } catch (AccountsException e) {
                    System.out.println(e.getMessage());
                    parentMenu.show();
                    parentMenu.execute();
                }
            }
        };
    }

    private Menu getAddProduct(){
        return new Menu("Add New Product", this) {
            @Override
            public void show() {
                System.out.println("Enter product's properties:\n" +
                        "Name:");
            }

            @Override
            public void execute() {
                String productName = scanner.nextLine();
                System.out.println("Producer company:");
                String company = scanner.nextLine();
                System.out.println("Price:");
                double price = Double.parseDouble(getValidInput(ConsoleCommand.DOUBLE,
                        "Enter a valid number."));
                System.out.println("Quantity of Product:");
                int count = Integer.parseInt(getValidInput(ConsoleCommand.INTEGER, "Enter a valid number."));
                String category = getCategory();
                System.out.println("Description:");
                String description = scanner.nextLine();
                try {
                    sellerAccountController.createNewProduct(name, company, price, count, category, description);
                    System.out.println("Product will be added after Manager's acceptances");
                } catch (AccountsException e) {
                    System.out.println(e.getMessage());
                }
            }

            private String getCategory() {
                //TODO: PUT AN OPTION FOR USER TO GO BACK
                HashMap<String, Category> categories = sellerAccountController.getAllCategories();
                ArrayList<String> orderedCategories = new ArrayList<>();
                int categoryNumber = 0;
                for(String name: categories.keySet()) {
                    System.out.println(categoryNumber + ". " + name);
                    orderedCategories.add(name);
                    categoryNumber++;
                }
                categoryNumber = getNumberOfNextMenu(orderedCategories.size());
                return orderedCategories.get(categoryNumber - 1);
            }
        };
    }

    private Menu getRemoveProduct() {
        return new Menu("Remove Product", this) {
            @Override
            public void show() {
                getViewProducts().show();
                System.out.println("Choose the product you want to remove:");
            }

            @Override
            public void execute() {
                ArrayList<String> productsIds = sellerAccountController.getSellerProductIDs();
                int productNumber = getNumberOfNextMenu(productsIds.size());
                try {
                    sellerAccountController.removeProductFromSeller(productsIds.get(productNumber - 1));
                    System.out.println("Product will be removed after Manger's acceptance.");
                } catch (AccountsException e) {
                    System.out.println(e.getMessage());
                }
            }
        };
    }
}
