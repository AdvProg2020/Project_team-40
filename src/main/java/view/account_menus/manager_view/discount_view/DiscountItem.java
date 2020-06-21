package view.account_menus.manager_view.discount_view;

import com.jfoenix.controls.JFXButton;
import controller.accounts.ManagerAccountController;
import exceptions.AccountsException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.DiscountCode;

import java.net.URL;
import java.util.ResourceBundle;

public class DiscountItem implements Initializable {
    public JFXButton deleteDiscountButton;
    public JFXButton viewDiscountButton;
    public JFXButton editDiscountButton;
    private ManagerAccountController managerAccountController;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        managerAccountController = ManagerAccountController.getInstance();
    }
    private void loadDiscounts(VBox vBoxItems) {
        vBoxItems.getChildren().clear();
        for (DiscountCode discountCode : managerAccountController.getAllDiscountCodes()) {
            try {
                AnchorPane item = (AnchorPane) FXMLLoader.load(getClass().getResource("/layouts/manager_discount_menus/discount_item.fxml"));
                HBox hBox = (HBox) item.getChildren().get(0);
                setLabelsContent(discountCode, hBox);
                vBoxItems.getChildren().add(item);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
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

    private void setLabelsContent(DiscountView discountView, DiscountCode discountCode) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                discountView.setDiscountCode(discountCode);
                discountView.setCodeLabel(discountCode.getCode());
                discountView.setStartLabel(discountCode.getStartDate().toString());
                discountView.setEndLabel(discountCode.getEndDate().toString());
                discountView.setCountLabel(Integer.toString(discountCode.getCountPerUser()));
                discountView.setMaxLabel(Double.toString(discountCode.getMaxAmount()));
                discountView.setPercentageLabel(Integer.toString(discountCode.getPercentage()));
                discountView.setStatusLabel(discountCode.getStatus());
                discountView.setUsersList(discountCode.getIncludedCostumers());
            }
        });
    }

    public void handleDeleteDiscount(ActionEvent event) {
        HBox item = (HBox)((deleteDiscountButton.getParent()).getParent());
        VBox items =(VBox)(item.getParent()).getParent();
        String code =((Label)item.getChildren().get(0)).getText();
        try {
            managerAccountController.removeDiscount(code);
        } catch (AccountsException e) {
            System.err.println(e.getMessage());
        }
        Platform.runLater(() -> loadDiscounts(items));

    }

    public void handleViewDiscount(ActionEvent event){
        HBox item = (HBox)((viewDiscountButton.getParent()).getParent());
        String code =((Label)item.getChildren().get(0)).getText();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/layouts/manager_discount_menus/discount.fxml"));
        try {
            DiscountCode discount = managerAccountController.getDiscount(code);
            AnchorPane pane = loader.load();
            DiscountView discountView = loader.getController();
            setLabelsContent(discountView, discount);
            Stage userWindow = new Stage();
            userWindow.setScene(new Scene(pane, 900, 550));
            userWindow.initModality(Modality.APPLICATION_MODAL);
            userWindow.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void handleEditDiscount(ActionEvent event) {
        HBox item = (HBox)((editDiscountButton.getParent()).getParent());
        String code =((Label)item.getChildren().get(0)).getText();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/layouts/manager_discount_menus/edit_discount.fxml"));
        try {
            DiscountCode discount = managerAccountController.getDiscount(code);
            AnchorPane pane = loader.load();
            DiscountEdit editDiscountController = loader.getController();
            editDiscountController.setDiscountCode(discount);
            editDiscountController.setOldValues();
            Stage userWindow = new Stage();
            userWindow.setScene(new Scene(pane, 520, 600));
            userWindow.initModality(Modality.APPLICATION_MODAL);
            userWindow.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
