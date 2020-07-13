package client.view.account_menus.manager_view.requests_view;

import client.controller.RequestHandler;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.restlet.resource.ResourceException;
import server.model.requests.AddOff;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class AddOffMenu extends RequestMenu implements Initializable {
    public Label offIdLabel;
    public Label startDateLabel;
    public Label endDateLabel;
    public Label sellerLabel;
    public Label percentageLabel;
    public JFXButton acceptButton;
    public JFXButton declineButton;
    private AddOff addOff;
    private HashMap<String, String> requestQueries;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        requestQueries = new HashMap<>();
    }

    public void setOffIdLabel(String offId) {
        offIdLabel.setText(offId);
    }

    public void setStartDateLabel(String startDate) {
        startDateLabel.setText(startDate);
    }

    public void setSellerLabel(String seller) {
        sellerLabel.setText(seller);
    }

    public void setEndDateLabel(String endDate) {
        endDateLabel.setText(endDate);
    }

    public void setPercentageLabel(String percentage) {
        percentageLabel.setText(percentage);
    }

    public void setAddOff(AddOff addProduct) {
        this.addOff = addProduct;
    }

    public void handleAcceptRequest(ActionEvent event) {
        try {
            requestQueries.clear();
            RequestHandler.put("/accounts/manager_account_controller/accept_request/", addOff.getRequestId(), requestQueries, true, null);
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
            RequestHandler.put("/accounts/manager_account_controller/decline_request/", addOff.getRequestId(), requestQueries, true, null);
            ((Stage)((Node)event.getSource()).getScene().getWindow()).close();
        } catch (ResourceException e) {
            try {
                System.err.println(RequestHandler.getClientResource().getResponseEntity().getText());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
