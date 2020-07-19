package client.view.account_menus.manager_view.requests_view;

import client.controller.RequestHandler;
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
import server.model.Off;
import server.model.requests.EditOff;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class EditOffMenu extends RequestMenu implements Initializable{

    public JFXButton acceptButton;
    public JFXButton declineButton;
    public Label oldLabel;
    public Label newLabel;
    private EditOff editOff;
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

    public void setEditOff(EditOff editOff) {
        this.editOff = editOff;
    }

    public void handleAcceptRequest(ActionEvent event) {
        try {
            requestQueries.clear();
            RequestHandler.put("/accounts/manager_account_controller/accept_request/", editOff.getRequestId(), requestQueries, true, null);
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
            RequestHandler.put("/accounts/manager_account_controller/decline_request/", editOff.getRequestId(), requestQueries, true, null);
            ((Stage)((Node)event.getSource()).getScene().getWindow()).close();
        } catch (ResourceException e) {
            try {
                System.err.println(RequestHandler.getClientResource().getResponseEntity().getText());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void handleViewOff() {
        try{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/layouts/manager_menus/manager_request_menus/view_off.fxml"));
        Pane pane = loader.load();
        OffMenu offMenu = loader.getController();
        Off off = editOff.getOff();
        offMenu.setOff(off);
        offMenu.setOffIdLabel(off.getId());
        offMenu.setSellerLabel(off.getSellerUsername());
        offMenu.setStartDateLabel(off.getStartDate());
        offMenu.setEndDateLabel(off.getEndDate());
        offMenu.setProductIdsListView();
            Stage stage = new Stage();
            stage.setScene(new Scene(pane, 600, 600));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
