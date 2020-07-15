package client.view.account_menus.manager_view.discount_view;

import client.controller.Client;
import client.controller.RequestHandler;
import client.view.MenuManager;
import com.jfoenix.controls.JFXButton;
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
import org.restlet.data.Status;
import org.restlet.resource.ResourceException;
import server.model.DiscountCode;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class DiscountItem extends MenuManager implements Initializable {
    public JFXButton deleteDiscountButton;
    public JFXButton viewDiscountButton;
    public JFXButton editDiscountButton;
    public HBox controlButtons;
    private HashMap<String, String> requestQueries;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        requestQueries = new HashMap<>();
        if(Client.getInstance().getRole().equals("Customer")) {
            controlButtons.getChildren().remove(deleteDiscountButton);
            controlButtons.getChildren().remove(editDiscountButton);
        }
    }

    private void loadDiscounts(VBox vBoxItems) {
        vBoxItems.getChildren().clear();
        requestQueries.clear();
        try {
            ArrayList<DiscountCode> allDiscounts = (ArrayList<DiscountCode>) RequestHandler.get("/accounts/manager_account_controller/all_discounts/",
                    requestQueries, true, ArrayList.class);
            assert allDiscounts != null;
            for (DiscountCode discountCode : allDiscounts) {
                AnchorPane item = (AnchorPane) FXMLLoader.load(getClass().getResource("/layouts/manager_menus/manager_discount_menus/discount_item.fxml"));
                HBox hBox = (HBox) item.getChildren().get(0);
                setLabelsContent(discountCode, hBox);
                vBoxItems.getChildren().add(item);
            }
        }
            catch (ResourceException e) {
                if (e.getStatus().equals(Status.CLIENT_ERROR_UNAUTHORIZED))
                    logout();
                e.printStackTrace();
            } catch (IOException e) {
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

    private void setLabelsContent(DiscountView discountView, LinkedHashMap<?, ?> discountCode) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                discountView.setCodeLabel((String) discountCode.get("code"));
                Date start = new Date((Long) discountCode.get("startDate"));
                Date end = new Date((Long)discountCode.get("endDate"));
                discountView.setStartLabel(start.toString());
                discountView.setEndLabel(end.toString());
                discountView.setCountLabel(String.valueOf(discountCode.get("countPerUser")));
                discountView.setMaxLabel(String.valueOf(discountCode.get("maxAmount")));
                discountView.setPercentageLabel(String.valueOf((discountCode.get("percentage"))));
                discountView.setStatusLabel((String) discountCode.get("status"));
                discountView.setUsersList((ArrayList<String>) discountCode.get("includedCostumers"));
            }
        });
    }

    public void handleDeleteDiscount() {
        HBox item = (HBox)((deleteDiscountButton.getParent()).getParent());
        VBox items =(VBox)(item.getParent()).getParent();
        String code =((Label)item.getChildren().get(0)).getText();
        try {
            requestQueries.clear();
            requestQueries.put("code", code);
            RequestHandler.delete("/accounts/manager_account_controller/discount/", requestQueries, true, null);
        } catch (ResourceException e) {
            if (e.getStatus().equals(Status.CLIENT_ERROR_UNAUTHORIZED))
                logout();
            else
                e.printStackTrace();
        }
        Platform.runLater(() -> loadDiscounts(items));

    }

    public void handleViewDiscount(){
        HBox item = (HBox)((viewDiscountButton.getParent()).getParent());
        String code =((Label)item.getChildren().get(0)).getText();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/layouts/manager_menus/manager_discount_menus/discount.fxml"));
        try {
            requestQueries.clear();;
            requestQueries.put("code", code);
            ArrayList<?> discount = (ArrayList<?>) RequestHandler.get("/accounts/manager_account_controller/discount/", requestQueries, true, ArrayList.class);
            AnchorPane pane = loader.load();
            DiscountView discountView = loader.getController();
            assert discount != null;
            setLabelsContent(discountView, (LinkedHashMap<?, ?>) discount.get(0));
            Stage userWindow = new Stage();
            userWindow.setScene(new Scene(pane, 900, 550));
            userWindow.initModality(Modality.APPLICATION_MODAL);
            userWindow.showAndWait();
        } catch (ResourceException e) {
            if (e.getStatus().equals(Status.CLIENT_ERROR_UNAUTHORIZED))
                logout();
            else
                e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void handleEditDiscount() {
        HBox item = (HBox)((editDiscountButton.getParent()).getParent());
        String code =((Label)item.getChildren().get(0)).getText();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/layouts/manager_menus/manager_discount_menus/edit_discount.fxml"));
        try {
            requestQueries.clear();;
            requestQueries.put("code", code);
            DiscountCode discount = (DiscountCode) RequestHandler.get("/accounts/manager_account_controller/discount/", requestQueries, true, DiscountCode.class);
            AnchorPane pane = loader.load();
            DiscountEdit editDiscountController = loader.getController();
            editDiscountController.setDiscountCode(discount);
            editDiscountController.setOldValues();
            Stage userWindow = new Stage();
            userWindow.setScene(new Scene(pane, 520, 600));
            userWindow.initModality(Modality.APPLICATION_MODAL);
            userWindow.showAndWait();
        } catch (ResourceException e) {
            if (e.getStatus().equals(Status.CLIENT_ERROR_UNAUTHORIZED))
                logout();
            else
                e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
