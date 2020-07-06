package view.shopping_menus.product.product_view;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import server.model.Comment;

import java.net.URL;
import java.util.ResourceBundle;

public class CommentItemManager implements Initializable{

    private static Comment lastComment;
    private Comment comment;

    public ImageView imageView;
    public Text userText, titleText, contentText, statusText, dateText;


    public CommentItemManager(){
        comment = lastComment;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        //TODO : image

        statusText.setText(comment.getStatus().toString());
        dateText.setText(comment.getLastUpdate().toString());
        userText.setText(comment.getUsername());
        titleText.setText(comment.getTitle());
        contentText.setText(comment.getContent());
    }

    public static void setLastComment(Comment lastComment){
        CommentItemManager.lastComment = lastComment;
    }
}
