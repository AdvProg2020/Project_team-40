package view.shopping_menus.product.product_digest_view;

import controller.menus.ProductController;
import view.Menu;
import exceptions.MenuException;
import model.Product;

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

    @Override
    public void show(){

        Product product;
        try {
            product = productController.digestProduct(productID);
            System.out.println("Product description : " + product);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

        super.show();
    }

    public Menu getAddToCart(){
        return new Menu("Add to cart", this){
            @Override
            public void show(){
                try{
                    productController.addProductToCart(productID, 1);
                    System.out.println("Product successfully added to cart");
                }catch(MenuException e){
                    System.out.print(e.getMessage());
                }
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
        return new Menu("Select seller", this){
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
                    productController.selectSellerForProduct(productController.digestProduct(productID).getName(), seller);
                }catch(MenuException e){
                    System.out.print(e.getMessage());
                }
                this.parentMenu.show();
                this.parentMenu.execute();
            }
        };
    }
}
