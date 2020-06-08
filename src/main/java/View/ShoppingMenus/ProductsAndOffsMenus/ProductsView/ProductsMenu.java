package View.ShoppingMenus.ProductsAndOffsMenus.ProductsView;

import Controller.Menus.AllProductsController;
import Controller.Menus.OffMenuController;
import View.Menu;
import View.ShopingMenus.ProductsAndOffsMenus.SortsMenu;
import View.ShoppingMenus.Product.ProductView.ProductMenu;
import View.ShoppingMenus.ProductsAndOffsMenus.FiltersView.FiltersMenu;
import exceptions.AccountsException;
import exceptions.MenuException;
import model.Product;

import java.util.ArrayList;
import java.util.HashMap;

public class ProductsMenu extends Menu {
    private AllProductsController allProductsController;
    private OffMenuController offMenuController;

    public ProductsMenu(Menu parentMenu) {
        super("Products Menu", parentMenu);
        allProductsController = AllProductsController.getInstance();
        offMenuController = OffMenuController.getInstance();
        HashMap<Integer, Menu> submenus = new HashMap<>();
        submenus.put(1, getViewCategories());
        submenus.put(2, getViewSubcategories());
        submenus.put(3, getShowProducts());
        submenus.put(4, getShowProduct());
        submenus.put(5, new SortsMenu<>(this, allProductsController));
        submenus.put(6, new FiltersMenu<>(this, allProductsController));
        setSubMenus(submenus);
    }

    public Menu getViewCategories() {
        return new Menu("Show categories", this){
            @Override
            public void show(){
                int index = 0;
                for(String category : allProductsController.getAllCategories()) {
                    index++;
                    System.out.println(index + ". " + category);
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

    public Menu getViewSubcategories(){
        return new Menu("Show subcategories", this){
            @Override
            public void show(){
                System.out.println("Enter parent category name :");
                String parentName = scanner.nextLine();

                ArrayList<String> allSubCategories = new ArrayList<>();
                try {
                    allSubCategories = allProductsController.getAllSubCategories(parentName);
                }catch(AccountsException e){
                    e.getMessage();
                }
                int index = 0;
                for(String subCategory : allSubCategories) {
                    index++;
                    System.out.println(index + ". "  + subCategory);
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

    public Menu getShowProducts(){
        return new Menu("Show all products", this){
            @Override
            public void show(){
                int index = 0;
                for(Product product : allProductsController.getAllProducts()) {
                    index++;
                    System.out.println(index + ". " + product);
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

    public Menu getShowProduct(){
        return new Menu("Show product", this) {
            @Override
            public void show() {
                System.out.println("Enter product id :");
            }

            @Override
            public void execute() {
                String productID = scanner.nextLine();
                try {
                    offMenuController.getProduct(productID);
                } catch(MenuException e) {
                    System.out.println(e.getMessage());
                    this.parentMenu.show();
                    this.parentMenu.execute();
                }
                ProductMenu productMenu = new ProductMenu(this.parentMenu, productID);
                productMenu.show();
                productMenu.execute();
            }
        };
    }
}
