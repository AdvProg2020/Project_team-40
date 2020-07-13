package client.view.account_menus.manager_view.requests_view;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import server.model.Off;

import java.net.URL;
import java.util.ResourceBundle;

public class OffMenu implements Initializable {
    public Label offIdLabel;
    public Label startDateLabel;
    public Label endDateLabel;
    public Label sellerLabel;
    public Label percentageLabel;
    public ListView<String> productIdsListView;
    private Off off;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setOffIdLabel(String offId) {
        offIdLabel.setText(offId);
    }

    public void setStartDateLabel(String startDate) {
        startDateLabel.setText(startDate);
    }

    public void setEndDateLabel(String endDate) {
        startDateLabel.setText(endDate);
    }

    public void setProductIdsListView() {
        productIdsListView.getItems().addAll(off.getProductIDs());
    }

    public void setSellerLabel(String seller) {
        sellerLabel.setText(seller);
    }

    public void setOff(Off off) {
        this.off = off;
    }

    public void handleCloseWindow(ActionEvent event) {
        ((Stage)((Node)event.getSource()).getScene().getWindow()).close();
    }
}
