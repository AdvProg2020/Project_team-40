package client.view.account_menus.customer_view.orders_view;

import client.controller.Client;
import client.controller.RequestHandler;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import server.model.log.Log;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
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
    public Label transitionLabel;
    public JFXComboBox<String> transitionComboBox;
    private Log log;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        transitionComboBox.getItems().addAll("Delivered", "Not Delivered");
        transitionComboBox.setOnAction(e -> {

            HashMap<String, String> requestQueries = new HashMap<>();
            requestQueries.put("logId", log.getId());

            if(transitionComboBox.getValue().equals("Delivered"))
                RequestHandler.post("/accounts/manager_account_controller/manager_sales_history/", null, requestQueries, true, null);
            if(transitionComboBox.getValue().equals("Not Delivered"))
                RequestHandler.delete("/accounts/manager_account_controller/manager_sales_history/", requestQueries, true, null);
            statusLabel.setText(transitionComboBox.getValue());

        });

        if(!Client.getInstance().getRole().equals("Manager"))
            disableManagerOnly();
        productsList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton() == MouseButton.PRIMARY && mouseEvent.getClickCount() == 2){
                    handleRateProduct(productsList.getSelectionModel().getSelectedItems().get(0));
                }
            }
        });
    }

    private void handleRateProduct(String productId) {
        Stage stage = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/layouts/customer_menus/customer_orders_menus/rate_menu.fxml"));
            AnchorPane pane = loader.load();
            RateMenu rateMenu = loader.getController();
            rateMenu.setProductId(productId);
            stage.setScene(new Scene(pane, 500, 350));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public void setAddressLabel(String address){
        addressLabel.setText(address);
    }

    public void setStatusLabel(String status){
        statusLabel.setText(status);
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

    public void disableManagerOnly(){
        pane.getChildren().remove(transitionComboBox);
        pane.getChildren().remove(transitionLabel);
    }
}
