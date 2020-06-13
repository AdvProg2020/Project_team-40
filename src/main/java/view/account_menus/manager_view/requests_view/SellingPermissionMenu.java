package view.account_menus.manager_view.requests_view;

import controller.accounts.ManagerAccountController;
import exceptions.AccountsException;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.requests.AddProduct;
import model.requests.SellingPermission;

import java.net.URL;
import java.util.ResourceBundle;

public class SellingPermissionMenu extends RequestMenu implements Initializable {
    public Label usernameLabel;
    public Label nameLabel;
    public Label emailLabel;
    public Label phoneLabel;
    public Label companyLabel;
    public Label creditLabel;
    private ManagerAccountController managerAccountController;
    private SellingPermission sellingPermission;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        managerAccountController = ManagerAccountController.getInstance();
    }

    public void setUsernameLabel(String username) {
        usernameLabel.setText(username);
    }

    public void setNameLabel(String name) {
        nameLabel.setText(name);
    }

    public void setEmailLabel(String email) {
        emailLabel.setText(email);
    }

    public void setPhoneLabel(String phone) {
        phoneLabel.setText(phone);
    }

    public void setCompanyLabel(String company) {
        companyLabel.setText(company);
    }

    public void setCreditLabel(String credit) {
        creditLabel.setText(credit);
    }

    public void setSellingPermission(SellingPermission sellingPermission) {
        this.sellingPermission = sellingPermission;
    }

    public void handleAcceptRequest(ActionEvent event) {
        try {
            managerAccountController.acceptRequest(sellingPermission.getRequestId());
            ((Stage)((Node)event.getSource()).getScene().getWindow()).close();
        } catch (AccountsException e) {
            System.err.println(e.getMessage());
        }
    }

    public void handleDeclineRequest(ActionEvent event) {
        try {
            managerAccountController.declineRequest(sellingPermission.getRequestId());
            ((Stage)((Node)event.getSource()).getScene().getWindow()).close();
        } catch (AccountsException e) {
            System.err.println(e.getMessage());
        }
    }
}