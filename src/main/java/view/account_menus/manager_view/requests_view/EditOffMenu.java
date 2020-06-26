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
import model.Off;
import model.requests.EditOff;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EditOffMenu extends RequestMenu implements Initializable{

    public JFXButton acceptButton;
    public JFXButton declineButton;
    public Label oldLabel;
    public Label newLabel;
    private ManagerAccountController managerAccountController;
    private EditOff editOff;

    public static class Product implements Initializable {

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

    public void setEditOff(EditOff editOff) {
        this.editOff = editOff;
    }

    public void handleAcceptRequest(ActionEvent event) {
        try {
            managerAccountController.acceptRequest(editOff.getRequestId());
            ((Stage)((Node)event.getSource()).getScene().getWindow()).close();
        } catch (AccountsException e) {
            System.err.println(e.getMessage());
        }
    }

    public void handleDeclineRequest(ActionEvent event) {
        try {
            managerAccountController.declineRequest(editOff.getRequestId());
            ((Stage)((Node)event.getSource()).getScene().getWindow()).close();
        } catch (AccountsException e) {
            System.err.println(e.getMessage());
        }
    }

    public void handleViewOff(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/layouts/manager_menus/manager_request_menus/view_off.fxml"));
        OffMenu offMenu = loader.getController();
        Off off = editOff.getOff();
        offMenu.setOff(off);
        offMenu.setOffIdLabel(off.getId());
        offMenu.setSellerLabel(off.getSeller().getUsername());
        offMenu.setStartDateLabel(off.getStartDate());
        offMenu.setEndDateLabel(off.getEndDate());
        offMenu.setProductIdsListView();
        try {
            Pane pane = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(pane, 600, 600));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
