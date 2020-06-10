package view.shopping_menus.products_and_offs_menus.offs_view;

import controller.menus.OffMenuController;
import view.Menu;
import view.shopping_menus.products_and_offs_menus.sorts_view.SortsMenu;
import view.shopping_menus.product.product_view.ProductMenu;
import view.shopping_menus.products_and_offs_menus.filter_view.FiltersMenu;
import exceptions.MenuException;
import model.Product;

import java.util.HashMap;

public class OffsMenu extends Menu{
    private OffMenuController offMenuController;

    public OffsMenu(Menu parentMenu){
        super("Offs Menu", parentMenu);
        offMenuController = OffMenuController.getInstance();
        HashMap<Integer, Menu> submenus = new HashMap<>();
        submenus.put(1, getShowProductsWithOff());
        submenus.put(2, getShowProduct());
        submenus.put(3, new SortsMenu<>(this, offMenuController));
        submenus.put(4, new FiltersMenu<>(this, offMenuController));
        setSubMenus(submenus);

    }

    public Menu getShowProductsWithOff(){
        return new Menu("Show products with off", this){
            @Override
            public void show(){
                for(Product product : offMenuController.getProductsWithOff()) {
                    System.out.println(product);
                    System.out.println("-------------------------------------");
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

