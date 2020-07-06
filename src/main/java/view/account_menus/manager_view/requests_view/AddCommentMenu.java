package view.account_menus.manager_view.requests_view;

import com.jfoenix.controls.JFXButton;
import server.controller.accounts.ManagerAccountController;
import exceptions.AccountsException;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import server.model.requests.AddComment;

import java.net.URL;
import java.util.ResourceBundle;

public class AddCommentMenu extends RequestMenu implements Initializable {
    public Label userNameLabel;
    public Label statusLabel;
    public Label lastUpdateLabel;
    public Label titleLabel;
    public Label contentLabel;
    public JFXButton acceptButton;
    public JFXButton declineButton;

    private ManagerAccountController managerAccountController;
    private AddComment addComment;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        managerAccountController = ManagerAccountController.getInstance();
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
            managerAccountController.acceptRequest(addComment.getRequestId());
            ((Stage)((Node)event.getSource()).getScene().getWindow()).close();
        } catch (AccountsException e) {
            System.err.println(e.getMessage());
        }
    }

    public void handleDeclineRequest(ActionEvent event) {
        try {
            managerAccountController.declineRequest(addComment.getRequestId());
            ((Stage)((Node)event.getSource()).getScene().getWindow()).close();
        } catch (AccountsException e) {
            System.err.println(e.getMessage());
        }
    }

}
