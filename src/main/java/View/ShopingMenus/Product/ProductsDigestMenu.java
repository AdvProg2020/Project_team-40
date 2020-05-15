package View.ShopingMenus.Product;

import Controller.Menus.ProductController;
import View.Menu;
import exceptions.MenuException;

import java.util.ArrayList;
import java.util.HashMap;

public class ProductsDigestMenu extends Menu {
    private ProductController productController;
    private String productID;

    public ProductsDigestMenu(Menu parentMenu, String productID) {
        super("Product's Digest Menu", parentMenu);
        productController = ProductController.getInstance();
        this.productID = productID;
        HashMap<Integer, Menu> submenus = new HashMap<>();
        submenus.put(1, getAddToCart());
        submenus.put(2, getSelectSeller());
        setSubMenus(submenus);
    }

    public Menu getAddToCart(){
        return new Menu("Show add to cart", this){
            @Override
            public void show(){
                try{
                    productController.addProductToCart(productID, 1);
                }catch(MenuException e){
                    System.out.print(e.getMessage());
                }
                System.out.println("Product successfully added to cart");
                System.out.println("Enter anything to return");
            }

            @Override
            public void execute(){
                scanner.nextLine();
                this.parentMenu.show();
                this.parentMenu.execute();
            }
        };
    }

    public Menu getSelectSeller(){
        return new Menu("Show select sellers", this){
            @Override
            public void show(){
                ArrayList<String> sellers = new ArrayList<>();
                try {
                    sellers = productController.getSellersForProduct(productController.digestProduct(productID).getName());
                }catch(MenuException e){
                    System.out.println(e.getMessage());
                }
                for(String seller : sellers) {
                    System.out.println(seller);
                }
                System.out.println("Enter the name of a seller from above :");
            }

            @Override
            public void execute(){
                String seller = scanner.nextLine();
                try {
                    productController.sellectSellerForProduct(productController.digestProduct(productID).getName(), seller);
                }catch(MenuException e){
                    System.out.print(e.getMessage());
                }
                this.parentMenu.show();
                this.parentMenu.execute();
            }
        };
    }
}
