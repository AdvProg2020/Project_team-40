package view.account_menus.customer_view.orders_view;

import controller.accounts.AccountController;
import controller.accounts.CustomerAccountController;
import controller.accounts.SellerAccountController;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.log.Log;
import model.users.Customer;

import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;

public class OrdersMenuManager implements Initializable {
    public VBox vBoxItems;
    public HBox topHBox;
    public Label titleLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AccountController accountController = AccountController.getInstance();
        if (accountController.getThisUser() instanceof Customer) {
            loadLogs(CustomerAccountController.getInstance().getOrders().values());
        } else {
            titleLabel.setText("Sales History");
            loadLogs(SellerAccountController.getInstance().getSalesHistory());
        }
    }

    private void loadLogs(Collection<Log> values) {
        for (Log log : values) {
            try {
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
        System.out.println(log.getId());
        dateLabel.setText(log.getDate().toString());
        System.out.println(log.getDate().toString());
        costLabel.setText(Double.toString(log.getCost()));
        System.out.println(log.getCost());
        discountLabel.setText(Double.toString(log.getCostWithoutDiscount()));
        System.out.println(log.getCostWithoutDiscount());
    }
}