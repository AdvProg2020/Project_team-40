package client.view.account_menus.customer_view.orders_view;

import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import server.model.log.Log;

public class OrderItem {
    public JFXButton viewLogButton;

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

    public void handleViewLog() {
        HBox item = (HBox)(viewLogButton.getParent());
        String logId = ((Label) item.getChildren().get(0)).getText();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/layouts/customer_menus/customer_orders_menus/order.fxml"));
        try {
            Log log = Log.getLogById(logId);
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
