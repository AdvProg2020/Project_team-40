package view.shopping_menus.product.product_view;

import javafx.fxml.Initializable;
import model.Comment;

import java.net.URL;
import java.util.ResourceBundle;

public class CommentItemManager implements Initializable{

    private static Comment lastComment;
    private Comment comment;

    public CommentItemManager(){
        comment = lastComment;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

    }

    public static void setLastComment(Comment lastComment){
        CommentItemManager.lastComment = lastComment;
    }
}
