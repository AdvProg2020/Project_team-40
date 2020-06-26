package view.account_menus.customer_view.orders_view;

import controller.accounts.CustomerAccountController;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.log.Log;

import java.net.URL;
import java.util.ResourceBundle;

public class OrdersMenuManager implements Initializable {
    public VBox vBoxItems;
    private CustomerAccountController customerAccountController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerAccountController = CustomerAccountController.getInstance();
        loadLogs();
    }

    private void loadLogs() {
        for (String logId : customerAccountController.getOrders().keySet()) {
            try {
                Log log = customerAccountController.getOrder(logId);
                AnchorPane item = FXMLLoader.load(getClass().getResource("/layouts/customer_menus/customer_orders_menus/order_item.fxml"));
                HBox hBox = (HBox) item.getChildren().get(0);
                setLabelsContent(log, hBox);
                vBoxItems.getChildren().add(item);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void setLabelsContent(Log log, HBox hBox) {
        Label logIdLabel =((Label) hBox.getChildren().get(0));
        Label dateLabel =((Label) hBox.getChildren().get(1));
        Label costLabel =((Label) hBox.getChildren().get(2));
        Label discountLabel =((Label) hBox.getChildren().get(3));

        logIdLabel.setText(log.getId());
        dateLabel.setText(log.getDate().toString());
        costLabel.setText(Double.toString(log.getCost()));
        discountLabel.setText(Double.toString(log.getCostWithoutDiscount()));
    }
}