package view.shopping_menus.product.product_view;

import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import model.Comment;

import java.net.URL;
import java.util.ResourceBundle;

public class CommentItemManager implements Initializable{

    private static Comment lastComment;
    private Comment comment;

    public ImageView imageView;
    public Text usernameText, titleText, contentText;


    public CommentItemManager(){
        comment = lastComment;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        //TODO : image

        usernameText.setText(comment.getUsername());
        titleText.setText(comment.getTitle());
        contentText.setText(comment.getContent());
    }

    public static void setLastComment(Comment lastComment){
        CommentItemManager.lastComment = lastComment;
    }
}
