package view.shopping_menus.product.product_view;

import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import controller.menus.ProductController;
import exceptions.MenuException;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Comment;
import model.enumerations.Status;
import model.users.Customer;
import model.users.User;
import view.MenuManager;
import view.account_menus.customer_view.orders_view.RateMenu;

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
                if(comment.getStatus() != Status.Confirmed)
                    continue;
                CommentItemManager.setLastComment(comment);
                Node node = (Node) FXMLLoader.load(getClass().getResource("/layouts/shopping_menus/comment_item.fxml"));
                commentsSection.getChildren().add(node);
            }
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    public void addComment(){
        if(User.getLoggedInUser() == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Commenting forbidden.");
            alert.setContentText("You must be logged in first.");
            alert.showAndWait();
            return;
        }

        Stage stage = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/layouts/shopping_menus/add_comment_menu.fxml"));
            AnchorPane pane = loader.load();
            AddCommentMenuManager addCommentMenuManager = loader.getController();
            addCommentMenuManager.setProductId(ProductMenuManager.getProduct().getProductId());
            stage.setScene(new Scene(pane, 500, 350));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void rate(){
        if(User.getLoggedInUser() == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Rating forbidden.");
            alert.setContentText("You must be logged in first.");
            alert.showAndWait();
            return;
        }

        if(!((Customer)User.getLoggedInUser()).hasBought(ProductMenuManager.getProduct().getProductId())){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Rating forbidden.");
            alert.setContentText("You must buy this product first to rate it.");
            alert.showAndWait();
            return;
        }

        Stage stage = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/layouts/customer_menus/customer_orders_menus/rate_menu.fxml"));
            AnchorPane pane = loader.load();
            RateMenu rateMenu = loader.getController();
            rateMenu.setProductId(ProductMenuManager.getProduct().getProductId());
            stage.setScene(new Scene(pane, 500, 350));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
