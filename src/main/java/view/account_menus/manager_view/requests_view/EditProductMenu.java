package view.account_menus.manager_view.requests_view;

import com.jfoenix.controls.JFXButton;
import controller.accounts.ManagerAccountController;
import exceptions.AccountsException;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.requests.AddProduct;
import model.requests.EditProduct;

import java.net.URL;
import java.util.ResourceBundle;

public class EditProductMenu extends RequestMenu implements Initializable {

    public JFXButton acceptButton;
    public JFXButton declineButton;
    public Label oldLabel;
    public Label newLabel;
    private ManagerAccountController managerAccountController;
    private EditProduct editProduct;

    public static class Product implements Initializable{

        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {

        }
    }

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

        //TODO: go to digest menu

    }
}
