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
    CustomerAccountController customerAccountController;

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
        customerAccountController = CustomerAccountController.getInstance();
    }

    private Menu getShowChosenProducts(){
        return new Menu("Cart", this) {
            @Override
            public void show() {
                System.out.println("Cart:");
                HashMap<Product, Integer> cart = customerAccountController.getCart();
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

    private Menu getShowChosenProduct(){
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
                    System.out.println(customerAccountController.getCart().get(product));
                }
                parentMenu.show();
                parentMenu.execute();
            }
        };
    }

    private Menu getIncreaseChosenProduct(){
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
                        customerAccountController.increaseChosenProductsQuantity(product.getProductId());
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

    private Menu getDecreaseChosenProduct(){
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
                        customerAccountController.decreaseChosenProductQuantity(product.getProductId());
                    } catch (AccountsException e) {
                        System.out.println(e.getMessage());
                    }
                }
                parentMenu.show();
                parentMenu.execute();
            }
        };
    }

    private Menu getShowTotalPrice(){
        return new Menu("See Cart's Total Price",this) {
            @Override
            public void show() {
                System.out.println("Total price of cart:" +
                        customerAccountController.getCartTotalPrice());
            }

            @Override
            public void execute() {
                parentMenu.show();
                parentMenu.execute();
            }
        };
    }

    private Menu getPurchase(){
        return new Menu("Purchase", this) {
            @Override
            public void show() {
                System.out.println("Cart:");
                HashMap<Product, Integer> products = customerAccountController.getCart();
                for(Map.Entry<Product, Integer> entry: products.entrySet()) {
                    System.out.printf("product: %s\n\tQuantity: %s\n\tPrice: %s\n",
                            entry.getKey().getName(), entry.getValue(), entry.getKey().getPrice());
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
                    customerAccountController.getReceiverInfo(null);
                }
                parentMenu.show();
                parentMenu.execute();
            }
        };
    }

    private void getUsersAddress() throws StopPurchaseException {
        System.out.println("Enter your home address:");
        String address = scanner.nextLine();
        if(address.equalsIgnoreCase("back")) {
            throw new StopPurchaseException("Process has been stopped.");
        }
        customerAccountController.getReceiverInfo(address);
    }

    private void enterDiscountCode() throws StopPurchaseException {
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
                customerAccountController.enterDiscountCode(discountCode);
            } catch (AccountsException e) {
                System.out.println("Invalid discount code!");
                enterDiscountCode();
            }
        } else {
            customerAccountController.setPriceWithoutDiscount();
        }
    }

    private void makePayment() {
        System.out.println("Do you want to finalize payment?\n" +
                "1. Yes\n" +
                "2. No");
        int doesFinalize = getNumberOfNextMenu(2);
        if(doesFinalize == 1) {
            try {
                Log log = customerAccountController.makePayment();
                System.out.println("Purchase log:\n" + log);
            } catch (AccountsException e) {
                System.out.println(e.getMessage());
            }
        } else {
            customerAccountController.resetPurchaseVariables();
        }
    }

    private ArrayList<Product> getProductsInOrder() {
        System.out.println("Cart:");
        ArrayList<Product> products = new ArrayList<Product>(customerAccountController.getCart().keySet());
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
