package view.shopping_menus.product.product_view;

import controller.menus.ProductController;
import exceptions.MenuException;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.Comment;
import view.MenuManager;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CommentsMenuManager extends MenuManager implements Initializable{

    public VBox commentsSection;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        //Display previous comments
        ArrayList<Comment> comments = null;
        try {
             comments = ProductController.getInstance().getComments(ProductMenuManager.getProduct().getProductId());
        } catch(MenuException e) {
            e.printStackTrace();
        }

        if(comments.isEmpty())
            commentsSection.getChildren().add(new Text("no comments to be added"));

        try {
            for(Comment comment : comments) {
                CommentItemManager.setLastComment(comment);
                Node node = (Node) FXMLLoader.load(getClass().getResource("/layouts/shopping_menus/comment_item.fxml"));
                commentsSection.getChildren().add(node);
            }
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    public void addComment(){
        //TODO
    }

    public void rate(){
        //TODO
    }
}
