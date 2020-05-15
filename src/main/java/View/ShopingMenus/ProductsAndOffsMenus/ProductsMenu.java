package View.ShopingMenus.ProductsAndOffsMenus;

import Controller.Menus.AllProductsController;
import View.Menu;
import exceptions.AccountsException;
import model.Product;

import java.util.ArrayList;
import java.util.HashMap;

public class ProductsMenu extends Menu {
    AllProductsController allProductsController;

    public ProductsMenu(Menu parentMenu) {
        super("Products Menu", parentMenu);
        allProductsController = AllProductsController.getInstance();
        HashMap<Integer, Menu> submenus = new HashMap<>();
        submenus.put(1, getViewCategories());
        submenus.put(2, getViewSubcategories());
        submenus.put(3, getShowProducts());
        submenus.put(4, getShowProduct());
        submenus.put(5, new SortsMenu<>(this, allProductsController));
        submenus.put(6, new FiltersMenu<>(this, allProductsController));
        setSubMenus(submenus);
    }

    public Menu getViewCategories(){
        return new Menu("Show categories", this){
            @Override
            public void show(){
                for(String category : allProductsController.getAllCategories()) {
                    System.out.println(category);
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
                for(String subCategory : allSubCategories) {
                    System.out.println(subCategory);
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
                for(Product product : allProductsController.getAllProducts()) {
                    System.out.println(product.getName());
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
        //TODO:Implement
        return null;
    }
}
