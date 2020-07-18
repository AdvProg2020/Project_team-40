package client.view.shopping_menus.product.product_view;

import client.controller.Client;
import client.controller.RequestHandler;
import client.view.MenuManager;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.restlet.resource.ResourceException;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class AddCommentMenuManager extends MenuManager implements Initializable{

    public TextField titleField;
    public TextArea contentField;

    private String productId;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

    }

    public void submit(){

        if(titleField.getText().isEmpty() || contentField.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Comment.");
            alert.setContentText("Please complete all the fills.");
            alert.showAndWait();
            return;
        }

        try {
            HashMap<String, String> queries = new HashMap<>();
            queries.put("username", Client.getInstance().getUsername());
            //TODO: check if the user must have logged in/check post argument
            queries.put("title", titleField.getText());
            queries.put("content", contentField.getText());
            RequestHandler.put("/shop/comment/", productId, queries, false, null);
            ((Stage) titleField.getScene().getWindow()).close();
        } catch(ResourceException e) {
            try {
                System.err.println(RequestHandler.getClientResource().getResponseEntity().getText());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void setProductId(String productId){
        this.productId = productId;
    }

}
