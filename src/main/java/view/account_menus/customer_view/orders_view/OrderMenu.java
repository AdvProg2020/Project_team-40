package view.account_menus.customer_view.orders_view;

import com.jfoenix.controls.JFXButton;
import controller.accounts.CustomerAccountController;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.log.Log;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Set;

public class OrderMenu implements Initializable {
    public JFXButton backButton;
    public AnchorPane pane;
    public Label logIdLabel;
    public Label dateLabel;
    public Label costLabel;
    public Label discountLabel;
    public Label statusLabel;
    public Label addressLabel;
    public ListView<String> productsList;
    private CustomerAccountController customerAccountController;
    private Log log;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerAccountController = CustomerAccountController.getInstance();
    }

    public void setLogIdLabel(String logId) {
        logIdLabel.setText(logId);
    }

    public void setDateLabel(String date) {
        dateLabel.setText(date);
    }

    public void setCostLabel(String cost) {
        costLabel.setText(cost);
    }

    public void setDiscountLabel(String costBeforeDiscount) {
        discountLabel.setText(costBeforeDiscount);
    }

    public void setStatusLabel(String status) {
        statusLabel.setText(status);
    }

    public void setAddressLabel(String address){
        addressLabel.setText(address);
    }

    public void setLog(Log log) {
        this.log = log;
    }

    public void setProductsList(Set<String> products) {
        for (String product : products) {
            productsList.getItems().add(product);
        }
    }

    public void handleCloseWindow(ActionEvent event) {
        ((Stage)((Node)event.getSource()).getScene().getWindow()).close();
    }

}
