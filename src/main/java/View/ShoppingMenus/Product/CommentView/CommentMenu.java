package View.ShoppingMenus.Product.CommentView;

import Controller.Menus.ProductController;
import View.Menu;
import exceptions.MenuException;
import model.Comment;
import model.enumerations.Status;

import java.util.ArrayList;
import java.util.HashMap;

public class CommentMenu extends Menu {
    private ProductController productController;
    private String productID;

    public CommentMenu(Menu parentMenu, String productID) {
        super("Comment Menu", parentMenu);
        productController = ProductController.getInstance();
        this.productID = productID;
        HashMap<Integer, Menu> submenus = new HashMap<>();
        submenus.put(1, addComment());
        setSubMenus(submenus);
    }

    @Override
    public void show(){
        ArrayList<Comment> comments = new ArrayList<>();
        try {
            comments = productController.getComments(productID);
            System.out.println("1");
        }catch(MenuException e){
            System.out.println(e.getMessage());
        }

        System.out.println("2");

        for(Comment comment : comments) {
            if(comment.getStatus() == Status.Confirmed){
                System.out.println(comment);
            }
        }

        super.show();
    }

    public Menu addComment(){

        return new Menu("Add comment", this){
            @Override
            public void show(){
                System.out.println("Title :");
                String title = scanner.nextLine();
                System.out.println("Content :");
                String content = scanner.nextLine();
                try {
                    productController.addComment(productID, title, content);
                }catch(MenuException e){
                    System.out.println(e.getMessage());
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
}
