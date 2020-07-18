package client.view.shopping_menus.product.product_view;

import client.controller.Client;
import client.controller.RequestHandler;
import client.view.MenuManager;
import client.view.account_menus.customer_view.orders_view.RateMenu;
import com.gilecode.yagson.com.google.gson.reflect.TypeToken;
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
import org.restlet.resource.ResourceException;
import server.model.Comment;
import server.model.enumerations.Status;
import server.model.users.Customer;
import server.model.users.User;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class CommentsMenuManager extends MenuManager implements Initializable{

    public VBox commentsSection;
    private HashMap<String, String> requestQueries;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        requestQueries = new HashMap<>();
        //Display previous comments
        ArrayList<Comment> comments = null;
        try {
            requestQueries.put("productID", ProductMenuManager.getProduct().getProductId());
            comments = RequestHandler.get("/shop/all_comments/", requestQueries, false,
                    new TypeToken<ArrayList<Comment>>(){}.getType());
        } catch(ResourceException e) {
            e.printStackTrace();
        }

        if(comments.isEmpty())
            commentsSection.getChildren().add(new Text("no comments to be added"));

        try {
            for(Comment comment : comments) {
                if(comment.getStatus() != Status.Confirmed)
                    continue;
                CommentItemManager.setLastComment(comment);
                Node node = FXMLLoader.load(getClass().getResource("/layouts/shopping_menus/comment_item.fxml"));
                commentsSection.getChildren().add(node);
            }
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    public void addComment(){
        if(!Client.getInstance().isLoggedIn()){
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
        if(!Client.getInstance().isLoggedIn()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Rating forbidden.");
            alert.setContentText("You must be logged in first.");
            alert.showAndWait();
            return;
        }
        requestQueries.clear();
        requestQueries.put("username", Client.getInstance().getUsername());
        User user = RequestHandler.get("/accounts/user/", requestQueries, false, User.class);
        if (!(user instanceof Customer)){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Rating forbidden.");
            alert.setContentText("You can't rate this product.");
            alert.showAndWait();
            return;
        }

        if(!((Customer)user).hasBought(ProductMenuManager.getProduct().getProductId())){
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
