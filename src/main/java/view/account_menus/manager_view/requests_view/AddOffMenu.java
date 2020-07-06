package view.account_menus.manager_view.requests_view;

import com.jfoenix.controls.JFXButton;
import server.controller.accounts.ManagerAccountController;
import exceptions.AccountsException;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import server.model.requests.AddOff;
import java.net.URL;
import java.util.ResourceBundle;

public class AddOffMenu extends RequestMenu implements Initializable {
    public Label offIdLabel;
    public Label startDateLabel;
    public Label endDateLabel;
    public Label sellerLabel;
    public Label percentageLabel;
    public JFXButton acceptButton;
    public JFXButton declineButton;
    private ManagerAccountController managerAccountController;
    private AddOff addOff;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        managerAccountController = ManagerAccountController.getInstance();
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
            managerAccountController.acceptRequest(addOff.getRequestId());
            ((Stage)((Node)event.getSource()).getScene().getWindow()).close();
        } catch (AccountsException e) {
            System.err.println(e.getMessage());
        }
    }

    public void handleDeclineRequest(ActionEvent event) {
        try {
            managerAccountController.declineRequest(addOff.getRequestId());
            ((Stage)((Node)event.getSource()).getScene().getWindow()).close();
        } catch (AccountsException e) {
            System.err.println(e.getMessage());
        }
    }
}
