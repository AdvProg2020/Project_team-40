package view.account_menus.manager_view.requests_view;

import com.jfoenix.controls.JFXButton;
import controller.accounts.ManagerAccountController;
import exceptions.AccountsException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.requests.AddProduct;
import model.requests.EditProduct;
import view.shopping_menus.product.product_view.ProductMenuManager;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EditProductMenu extends RequestMenu implements Initializable {

    public JFXButton acceptButton;
    public JFXButton declineButton;
    public Label oldLabel;
    public Label newLabel;
    private ManagerAccountController managerAccountController;
    private EditProduct editProduct;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        managerAccountController = ManagerAccountController.getInstance();
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
            managerAccountController.acceptRequest(editProduct.getRequestId());
            ((Stage)((Node)event.getSource()).getScene().getWindow()).close();
        } catch (AccountsException e) {
            System.err.println(e.getMessage());
        }
    }

    public void handleDeclineRequest(ActionEvent event) {
        try {
            managerAccountController.declineRequest(editProduct.getRequestId());
            ((Stage)((Node)event.getSource()).getScene().getWindow()).close();
        } catch (AccountsException e) {
            System.err.println(e.getMessage());
        }
    }

    public void handleViewProduct(ActionEvent event) {
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
