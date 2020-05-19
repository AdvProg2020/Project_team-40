package View.ShopingMenus.Product;

import Controller.Menus.ProductController;
import View.Menu;
import exceptions.MenuException;

import java.util.*;

public class ProductMenu extends Menu {
    protected ProductController productController;
    protected String productID;

    public ProductMenu(Menu parentMenu, String productID) {
        super("Product Menu", parentMenu);
        this.productID = productID;
        productController = ProductController.getInstance();
        HashMap<Integer, Menu> submenus = new HashMap<>();
        submenus.put(1, new ProductsDigestMenu(this, productID));
        submenus.put(2, getShowAttributes());
        submenus.put(3, getCompare());
        submenus.put(4, new CommentMenu(this, productID));
        submenus.put(5, getShowRatings());
        setSubMenus(submenus);
    }

    public Menu getShowAttributes() {
        return new Menu("Show attributes", this){
            @Override
            public void show(){
                HashMap<String, String> allAttributes = new HashMap<>();
                try {
                    allAttributes = productController.getProductAttributes(productID);
                }catch(MenuException e){
                    e.getMessage();
                }

                for(Map.Entry<String, String> entry : allAttributes.entrySet()) {
                    System.out.println(entry.getKey() + " " + entry.getValue());
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

    public Menu getCompare() {
        return new Menu("Show compare", this){
            @Override
            public void show(){
                String secondProductID = scanner.nextLine();

                try {
                    productController.digestProduct(secondProductID);
                }catch(MenuException e){
                    e.getMessage();
                };

                HashMap<String, String> firstAttributes = new HashMap<>();
                HashMap<String, String> secondAttributes = new HashMap<>();
                try {
                    firstAttributes = productController.getProductAttributes(productID);
                    secondAttributes = productController.getProductAttributes(secondProductID);
                }catch(MenuException e){
                    e.getMessage();
                };

                //List of fields without their values
                ArrayList<String> allAttributeFaces = new ArrayList<>();

                try{
                    allAttributeFaces.addAll(productController.getProductAttributesFace(productID));
                    allAttributeFaces.addAll(productController.getProductAttributesFace(secondProductID));
                }catch(MenuException e){
                    e.getMessage();
                };

                //This part is for removing duplicated fields
                Set<String> set = new HashSet<>(allAttributeFaces);
                allAttributeFaces.clear();
                allAttributeFaces.addAll(set);

                System.out.println("Field || First product || Second product");
                for(String face : allAttributeFaces) {
                    System.out.print(face);
                    System.out.print(" || ");
                    if(firstAttributes.containsKey(face)){
                        System.out.print(firstAttributes.get(face));
                    }
                    System.out.print(" || ");
                    if(secondAttributes.containsKey(face)){
                        System.out.print(secondAttributes.get(face));
                    }
                    System.out.println("");
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

    public Menu getShowRatings() {
        return new Menu("Product's Rates", this) {
            @Override
            public void show() {
                System.out.println("Product's average score: " + productController.getProductsRatings(productID));
            }

            @Override
            public void execute() {
                parentMenu.show();
                parentMenu.execute();
            }
        };
    }
}
