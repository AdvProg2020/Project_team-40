package client.view.account_menus.manager_view.requests_view;

import client.controller.RequestHandler;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.restlet.resource.ResourceException;
import server.model.requests.SellingPermission;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class SellingPermissionMenu extends RequestMenu implements Initializable {
    public Label usernameLabel;
    public Label nameLabel;
    public Label emailLabel;
    public Label phoneLabel;
    public Label companyLabel;
    public Label creditLabel;
    public JFXButton acceptButton;
    public JFXButton declineButton;
    private SellingPermission sellingPermission;
    private HashMap<String, String> requestQueries;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        requestQueries = new HashMap<>();
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
            requestQueries.clear();
            RequestHandler.put("/accounts/manager_account_controller/accept_request/", sellingPermission.getRequestId(), requestQueries, true, null);
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
            RequestHandler.put("/accounts/manager_account_controller/decline_request/", sellingPermission.getRequestId(), requestQueries, true, null);
            ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
        } catch (ResourceException e) {
            try {
                System.err.println(RequestHandler.getClientResource().getResponseEntity().getText());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
