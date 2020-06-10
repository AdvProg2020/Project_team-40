package view.account_menus.seller_view.sellers_products_view;

import controller.accounts.SellerAccountController;
import view.account_menus.seller_view.manage_products_view.ManageProduct;
import view.ConsoleCommand;
import view.Menu;
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
                int productNumber = 1;
                for(String productId: productsIds) {
                    try {
                        System.out.println(productNumber + ". " +
                                sellerAccountController.getProductDetails(productId).toString());
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
                } catch (AccountsException e) {
                    System.out.println(e.getMessage());
                    parentMenu.show();
                    parentMenu.execute();
                }
            }
        };
    }

    public Menu getAddProduct() {
        return new Menu("Add New Product", this) {
            @Override
            public void show() {
                System.out.println("Enter product's properties:");
            }

            @Override
            public void execute() {
                System.out.println("Name: ");
                String productName = getValidInput(ConsoleCommand.DEFAULT, "");
                System.out.println("Producer company:");
                String company = getValidInput(ConsoleCommand.DEFAULT, "");
                System.out.println("Price:");
                double price = Double.parseDouble(getValidInput(ConsoleCommand.DOUBLE,
                        "Enter a valid number."));
                System.out.println("Quantity of Product:");
                int count = Integer.parseInt(getValidInput(ConsoleCommand.INTEGER, "Enter a valid number."));
                System.out.println("Enter a category:");
                String category = getCategory();
                System.out.println("Description:");
                String description = getValidInput(ConsoleCommand.DEFAULT, "");
                try {
                    Product product  = sellerAccountController
                            .createNewProduct(productName, company, price, count, category, description);
                    addProperties(product);
                    System.out.println("Product will be added after Manager's acceptances");
                } catch (AccountsException e) {
                    System.out.println(e.getMessage());
                }
                this.parentMenu.show();
                this.parentMenu.execute();
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
                this.parentMenu.show();
                this.parentMenu.execute();
            }
        };
    }

    //These methods are used in getAddProduct in this class and getEditProduct
    // in ManageProduct class to handle the adding of a new category:

    public String getCategory() {
        HashMap<String, Category> categories = sellerAccountController.getAllCategories();
        ArrayList<String> orderedCategories = new ArrayList<>();
        int categoryNumber = 1;
        for(String name: categories.keySet()) {
            System.out.println(categoryNumber + ". " + name);
            orderedCategories.add(name);
            categoryNumber++;
        }
        categoryNumber = getNumberOfNextMenu(orderedCategories.size());
        return orderedCategories.get(categoryNumber - 1);
    }

    private void addProperties(Product product) {
        ArrayList<String> categoryProperties = sellerAccountController.
                getCategoryProperties(product.getCategory());
        for(String property: categoryProperties) {
            String propertyOfProduct = getProperty(property);
            if(ConsoleCommand.DOUBLE.getStringMatcher(propertyOfProduct).matches()) {
                sellerAccountController.setProductsProperties(property,
                        Double.parseDouble(propertyOfProduct), product);
            } else {
                sellerAccountController.setProductsProperties(property, propertyOfProduct, product);
            }
        }
    }

    private String getProperty(String property) {
        System.out.println("Enter the value of this property: " + property);
        return getValidInput(ConsoleCommand.DEFAULT, "");
    }
}
