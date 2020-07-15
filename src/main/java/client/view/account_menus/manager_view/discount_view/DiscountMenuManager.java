package client.view.account_menus.manager_view.discount_view;

import client.controller.Client;
import client.controller.RequestHandler;
import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import server.model.DiscountCode;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class DiscountMenuManager implements Initializable {
    public VBox vBoxItems;
    public JFXButton addDiscount;
    public JFXButton refreshButton;
    public AnchorPane mainPane;
    public Label title;
    private HashMap<String, String> requestQueries;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        requestQueries = new HashMap<>();
        if(Client.getInstance().getRole().equals("Customer")) {
            mainPane.getChildren().remove(addDiscount);
            mainPane.getChildren().remove(refreshButton);
            title.setText("My Discount Codes");
            loadCustomersDiscounts();
        } else {
            loadAllDiscounts();
        }
    }

    private void loadCustomersDiscounts() {
        requestQueries.clear();
        HashMap<String, DiscountCode> discountCodes = (HashMap)RequestHandler.get("/accounts/customer_account_controller/all_discounts/", requestQueries, true, HashMap.class);
        assert discountCodes != null;
        for(DiscountCode discountCode : discountCodes.values()) {
            addDiscountToList(discountCode);
        }
    }

    public void loadAllDiscounts() {
        requestQueries.clear();
        ArrayList<?> discountCodes = (ArrayList<?>)RequestHandler.get("/accounts/manager_account_controller/all_discounts/", requestQueries, true, ArrayList.class);
        assert discountCodes != null;
        for (Object obj : discountCodes) {
            LinkedHashMap<?,?> discountCode = (LinkedHashMap<?, ?>) obj;
            addDiscountToList(discountCode);
        }
    }

    private void addDiscountToList(LinkedHashMap<?, ?> discountCode){
        try {
            AnchorPane item = (AnchorPane) FXMLLoader.load(getClass().getResource("/layouts/manager_menus/manager_discount_menus/discount_item.fxml"));
            HBox hBox = (HBox) item.getChildren().get(0);
            setLabelsContent(discountCode, hBox);
            vBoxItems.getChildren().add(item);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addDiscountToList(DiscountCode discountCode){
        try {
            AnchorPane item = (AnchorPane) FXMLLoader.load(getClass().getResource("/layouts/manager_menus/manager_discount_menus/discount_item.fxml"));
            HBox hBox = (HBox) item.getChildren().get(0);
            setLabelsContent(discountCode, hBox);
            vBoxItems.getChildren().add(item);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void setLabelsContent(DiscountCode discountCode, HBox hBox) {
        Label codeLabel =((Label) hBox.getChildren().get(0));
        Label startLabel =((Label) hBox.getChildren().get(1));
        Label endLabel =((Label) hBox.getChildren().get(2));
        Label percentageLabel =((Label) hBox.getChildren().get(3));
        codeLabel.setText(discountCode.getCode());
        startLabel.setText(discountCode.getStartDate().toString());
        endLabel.setText(discountCode.getEndDate().toString());
        percentageLabel.setText(Integer.toString(discountCode.getPercentage()));
    }

    private void setLabelsContent(LinkedHashMap<?, ?> discountCode, HBox hBox) {
        Label codeLabel =((Label) hBox.getChildren().get(0));
        Label startLabel =((Label) hBox.getChildren().get(1));
        Label endLabel =((Label) hBox.getChildren().get(2));
        Label percentageLabel =((Label) hBox.getChildren().get(3));
        codeLabel.setText((String) discountCode.get("code"));
        Date start = new Date((Long) discountCode.get("startDate"));
        Date end = new Date((Long)discountCode.get("endDate"));
        startLabel.setText(start.toString());
        endLabel.setText(end.toString());
        percentageLabel.setText(String.valueOf((discountCode.get("percentage"))));
    }

    public void handleAddDiscount() {
        Stage stage = new Stage();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/layouts/manager_menus/manager_discount_menus/add_discount.fxml"));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root, 800, 600));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    loadAllDiscounts();
                }
            });
        }

    }

    public void refresh() {
        vBoxItems.getChildren().clear();
        loadAllDiscounts();
    }
}
