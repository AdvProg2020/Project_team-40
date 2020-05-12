package View.AccountMenus.CustomerView;

import Controller.Accounts.CustomerAccountController;
import View.Menu;
import exceptions.AccountsException;
import exceptions.StopPurchaseException;
import model.Product;
import model.log.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CartMenu extends Menu {
    public CartMenu(Menu parentMenu) {
        super("Cart Menu", parentMenu);
        HashMap<Integer, Menu> submenus = new HashMap<>();
        submenus.put(1, getShowChosenProducts());
        submenus.put(2, getShowChosenProduct());
        submenus.put(3, getIncreaseChosenProduct());
        submenus.put(4, getDecreaseChosenProduct());
        submenus.put(5, getShowTotalPrice());
        submenus.put(6, getPurchase());
        this.setSubMenus(submenus);
    }

    public Menu getShowChosenProducts(){
        return new Menu("Cart", this) {

            @Override
            public void show() {
                System.out.println("Cart:");
                HashMap<Product, Integer> cart = CustomerAccountController.getInstance().getCart();
                int productNumber = 1;
                for(Map.Entry<Product, Integer> entry: cart.entrySet()) {
                    System.out.println(productNumber + ". " + entry.getKey().getName() + "\n" +
                            "Seller: "+ entry.getKey().getSeller().getUsername() + "\n" +
                            "Quantity:" + entry.getValue());
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

    public Menu getShowChosenProduct(){
        return new Menu("Product's Details", this) {
            ArrayList<Product> products;
            @Override
            public void show() {
                products = getProductsInOrder();
            }

            @Override
            public void execute() {
                if (products.isEmpty()) {
                    System.out.println("You haven't chosen any product.");
                } else {
                    int chosenProductNumber = getNumberOfNextMenu(products.size());
                    Product product = products.get(chosenProductNumber - 1);
                    System.out.println(product);
                    System.out.println(CustomerAccountController.getInstance().getCart().get(product));
                }
                parentMenu.show();
                parentMenu.execute();
            }
        };
    }

    public Menu getIncreaseChosenProduct(){
        return new Menu("Increase Product's Quantity", this) {
            ArrayList<Product> products;
            @Override
            public void show() {
                products = getProductsInOrder();
            }

            @Override
            public void execute() {
                if (products.isEmpty()) {
                    System.out.println("You haven't chosen any product.");
                } else {
                    int chosenProductNumber = getNumberOfNextMenu(products.size());
                    Product product = products.get(chosenProductNumber - 1);
                    try {
                        CustomerAccountController.getInstance().increaseChosenProductsQuantity(product.getProductId());
                        System.out.println("Product quantity increased successfully.");
                    } catch (AccountsException e) {
                        System.out.println(e.getMessage());
                    }
                }
                parentMenu.show();
                parentMenu.execute();
            }
        };
    }

    public Menu getDecreaseChosenProduct(){
        return new Menu("Decrease Product's Quantity", this) {
            ArrayList<Product> products;

            @Override
            public void show() {
                products = getProductsInOrder();
            }

            @Override
            public void execute() {
                if (products.isEmpty()) {
                    System.out.println("You haven't chosen any product.");
                } else {
                    int chosenProductNumber = getNumberOfNextMenu(products.size());
                    Product product = products.get(chosenProductNumber - 1);
                    try {
                        CustomerAccountController.getInstance().decreaseChosenProductQuantity(product.getProductId());
                    } catch (AccountsException e) {
                        System.out.println(e.getMessage());
                    }
                }
                parentMenu.show();
                parentMenu.execute();
            }
        };
    }

    public Menu getShowTotalPrice(){
        return new Menu("See Cart's Total Price",this) {
            @Override
            public void show() {
                System.out.println("Total price of cart:" +
                        CustomerAccountController.getInstance().getCartTotalPrice());
            }

            @Override
            public void execute() {
                parentMenu.show();
                parentMenu.execute();
            }
        };
    }

    public Menu getPurchase(){
        return new Menu("Purchase", this) {
            @Override
            public void show() {
                System.out.println("Cart:");
                HashMap<Product, Integer> products = CustomerAccountController.getInstance().getCart();
                System.out.printf("%30s%30s%30s", "Product", "Quantity", "Price");
                for(Map.Entry<Product, Integer> entry: products.entrySet()) {
                    System.out.printf("%30s%30s%30s", entry.getKey().getName(), entry.getValue(),
                            entry.getKey().getPrice());
                }
                System.out.println("Enter back if you want to stop the process.");
            }

            @Override
            public void execute() {
                try {
                    getUsersAddress();
                    enterDiscountCode();
                    makePayment();
                } catch (StopPurchaseException e) {
                    System.out.println(e.getMessage());
                    CustomerAccountController.getInstance().getReceiverInfo(null);
                }
                parentMenu.show();
                parentMenu.execute();
            }
        };
    }

    public void getUsersAddress() throws StopPurchaseException {
        System.out.println("Enter your home address:");
        String address = scanner.nextLine();
        if(address.equalsIgnoreCase("back")) {
            throw new StopPurchaseException("Process has been stopped.");
        }
        CustomerAccountController.getInstance().getReceiverInfo(address);
    }

    public void enterDiscountCode() throws StopPurchaseException {
        System.out.println("Do you have any discount code?\n" +
                "1. Yes\n" +
                "2. No\n");
        int hasDiscountCode = getNumberOfNextMenu(2);
        if(hasDiscountCode == 1) {
            System.out.println("Enter discount code:");
            String discountCode = scanner.nextLine();
            if (discountCode.equalsIgnoreCase("back")) {
                throw new StopPurchaseException("Process has been stopped.");
            }
            try {
                CustomerAccountController.getInstance().enterDiscountCode(discountCode);
            } catch (AccountsException e) {
                System.out.println("Invalid discount code!");
                enterDiscountCode();
            }
        } else {
            CustomerAccountController.getInstance().setPriceWithoutDiscount();
        }
    }

    public void makePayment() {
        System.out.println("Do you want to finalize payment?\n" +
                "1. Yes\n" +
                "2. No");
        int doesFinalize = getNumberOfNextMenu(2);
        if(doesFinalize == 1) {
            try {
                Log log = CustomerAccountController.getInstance().makePayment();
                System.out.println("Purchase log:\n" + log);
            } catch (AccountsException e) {
                System.out.println(e.getMessage());
            }
        } else {
            CustomerAccountController.getInstance().resetPurchaseVariables();
        }
    }

    public ArrayList<Product> getProductsInOrder() {
        System.out.println("Cart:");
        ArrayList<Product> products = new ArrayList<Product>(CustomerAccountController.getInstance().getCart().keySet());
        int productNumber = 1;
        for(Product product: products) {
            System.out.println(productNumber + ". " + product.getName() + "\n" +
                    "Seller: " + product.getSeller().getUsername());
            productNumber++;
        }
        if(!products.isEmpty())
            System.out.println("Choose Product:");
        return products;
    }
}
