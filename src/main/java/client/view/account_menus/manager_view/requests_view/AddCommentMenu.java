package client.view.account_menus.manager_view.requests_view;

import client.controller.RequestHandler;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.restlet.resource.ResourceException;
import server.model.requests.AddComment;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class AddCommentMenu extends RequestMenu implements Initializable {
    public Label userNameLabel;
    public Label statusLabel;
    public Label lastUpdateLabel;
    public Label titleLabel;
    public Label contentLabel;
    public JFXButton acceptButton;
    public JFXButton declineButton;
    private AddComment addComment;
    private HashMap<String, String> requestQueries;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        requestQueries = new HashMap<>();
    }

    public void setUserNameLabel(String username) {
        userNameLabel.setText(username);
    }

    public void setStatusLabel(String status) {
        statusLabel.setText(status);
    }

    public void setLastUpdateLabel(String lastUpdate) {
        lastUpdateLabel.setText(lastUpdate);
    }

    public void setTitleLabel(String title) {
        titleLabel.setText(title);
    }

    public void setContentLabel(String content) {
        contentLabel.setText(content);
    }


    public void setAddComment(AddComment addComment) {
        this.addComment = addComment;
    }

    public void handleAcceptRequest(ActionEvent event) {
        try {
            requestQueries.clear();
            RequestHandler.put("/accounts/manager_account_controller/accept_request/", addComment.getRequestId(), requestQueries, true, null);
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
            RequestHandler.put("/accounts/manager_account_controller/decline_request/", addComment.getRequestId(), requestQueries, true, null);
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
