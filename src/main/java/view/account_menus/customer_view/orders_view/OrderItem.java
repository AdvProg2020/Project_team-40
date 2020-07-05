package view.account_menus.customer_view.orders_view;

import com.jfoenix.controls.JFXButton;
import controller.accounts.CustomerAccountController;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.log.Log;

import java.net.URL;
import java.util.ResourceBundle;

public class OrderItem implements Initializable {
    public JFXButton viewLogButton;
    private CustomerAccountController customerAccountController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerAccountController = CustomerAccountController.getInstance();

    }

    private void loadLogs(VBox vBoxItems) {
        vBoxItems.getChildren().clear();
        for (String logId : customerAccountController.getOrders().keySet()) {
            try {
                Log log = customerAccountController.getOrder(logId);
                AnchorPane item = FXMLLoader.load(getClass().getResource("/layouts/customer_menus/customer_orders_menu/order_item.fxml"));
                HBox hBox = (HBox) item.getChildren().get(0);
                setLabelsContent(log, hBox);
                vBoxItems.getChildren().add(item);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void setLabelsContent(OrderMenu orderMenu, Log log) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                orderMenu.setLog(log);
                orderMenu.setLogIdLabel(log.getId());
                orderMenu.setDateLabel(log.getDate().toString());
                orderMenu.setCostLabel(Double.toString(log.getCost()));
                orderMenu.setDiscountLabel(Double.toString(log.getCostWithoutDiscount()));
                orderMenu.setAddressLabel(log.getAddress());
                orderMenu.setProductsList(log.getProductsId().keySet());
            }
        });
    }

    private void setLabelsContent(Log log, HBox hBox) {
        Label logIdLabel = ((Label) hBox.getChildren().get(0));
        Label dateLabel = ((Label) hBox.getChildren().get(1));
        Label costLabel = ((Label) hBox.getChildren().get(2));
        Label discountLabel = ((Label) hBox.getChildren().get(3));

        logIdLabel.setText(log.getId());
        dateLabel.setText(log.getDate().toString());
        costLabel.setText(Double.toString(log.getCost()));
        discountLabel.setText(Double.toString(log.getCostWithoutDiscount()));
    }


    public void handleViewLog() {
        HBox item = (HBox)(viewLogButton.getParent());
        String logId = ((Label) item.getChildren().get(0)).getText();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/layouts/customer_menus/customer_orders_menus/order.fxml"));
        try {
            Log log = customerAccountController.getOrder(logId);
            AnchorPane pane = loader.load();
            OrderMenu orderMenu = loader.getController();
            setLabelsContent(orderMenu, log);
            Stage userWindow = new Stage();
            userWindow.setScene(new Scene(pane, 800, 600));
            userWindow.initModality(Modality.APPLICATION_MODAL);
            userWindow.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
