package view.shopping_menus.product.product_view;

import controller.menus.ProductController;
import exceptions.MenuException;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import view.MenuManager;

import java.net.URL;
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
            ProductController.getInstance().addComment(productId, titleField.getText(), contentField.getText());
            ((Stage) titleField.getScene().getWindow()).close();
        } catch(MenuException e) {
        }
    }

    public void setProductId(String productId){
        this.productId = productId;
    }

}
