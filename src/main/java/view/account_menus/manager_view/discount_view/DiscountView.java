package view.account_menus.manager_view.discount_view;

import com.jfoenix.controls.JFXButton;
import controller.accounts.ManagerAccountController;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.DiscountCode;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

public class DiscountView implements Initializable {
    public Label codeLabel;
    public Label statusLabel;
    public Label startLabel;
    public Label endLabel;
    public Label percentageLabel;
    public Label countLabel;
    public Label maxLabel;
    public JFXButton backButton;
    public AnchorPane pane;
    public ListView<String> usersList;
    private ManagerAccountController managerAccountController;
    private DiscountCode discountCode;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        managerAccountController = ManagerAccountController.getInstance();

    }

    public void setCodeLabel(String code) {
        codeLabel.setText(code);
    }

    public void setStatusLabel(String status) {
        statusLabel.setText(status);
    }

    public void setStartLabel(String start) {
        startLabel.setText(start);
    }

    public void setEndLabel(String end) {
        endLabel.setText(end);
    }

    public void setPercentageLabel(String percentage) {
        percentageLabel.setText(percentage);
    }

    public void setCountLabel(String countPerUser) {
        countLabel.setText(countPerUser);
    }

    public void setMaxLabel(String maxPrice) {
        maxLabel.setText(maxPrice);
    }

    public void setUsersList(Set<String> users) {
        for (String user : users) {
            usersList.getItems().add(user);
        }
    }

    public void setDiscountCode(DiscountCode discountCode) {
        this.discountCode = discountCode;
    }

    public void handleCloseWindow(ActionEvent event) {
        ((Stage)((Node)event.getSource()).getScene().getWindow()).close();
    }

}
