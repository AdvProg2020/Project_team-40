package client.view.account_menus.manager_view.requests_view;

import client.controller.RequestHandler;
import client.view.shopping_menus.product.product_view.ProductMenuManager;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.restlet.resource.ResourceException;
import server.model.requests.EditProduct;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class EditProductMenu extends RequestMenu implements Initializable {

    public JFXButton acceptButton;
    public JFXButton declineButton;
    public Label oldLabel;
    public Label newLabel;
    private EditProduct editProduct;
    private HashMap<String, String> requestQueries;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        requestQueries = new HashMap<>();
    }

    public void setNewLabel(String newField) {
        newLabel.setText(newField);
    }

    public void setOldLabel(String oldField) {
        oldLabel.setText(oldField);
    }

    public void setEditProduct(EditProduct editProduct) {
        this.editProduct = editProduct;
    }

    public void handleAcceptRequest(ActionEvent event) {
        try {
            requestQueries.clear();
            RequestHandler.put("/accounts/manager_account_controller/accept_request/", editProduct.getRequestId(), requestQueries, true, null);
            ((Stage)((Node)event.getSource()).getScene().getWindow()).close();
        } catch (ResourceException e) {
            try {
                System.err.println(RequestHandler.getClientResource().getResponseEntity().getText());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void handleDeclineRequest(ActionEvent event) {
        try {
            requestQueries.clear();
            RequestHandler.put("/accounts/manager_account_controller/decline_request/", editProduct.getRequestId(), requestQueries, true, null);
            ((Stage)((Node)event.getSource()).getScene().getWindow()).close();
        } catch (ResourceException e) {
            try {
                System.err.println(RequestHandler.getClientResource().getResponseEntity().getText());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void handleViewProduct() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/layouts/shopping_menus/product_menu.fxml"));
        ProductMenuManager.setProduct(editProduct.getProduct());
        try {
            Pane pane = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(pane, 1200, 600));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
