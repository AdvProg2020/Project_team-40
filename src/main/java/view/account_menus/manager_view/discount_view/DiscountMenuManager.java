package view.account_menus.manager_view.discount_view;

import com.jfoenix.controls.JFXButton;
import controller.accounts.AccountController;
import controller.accounts.CustomerAccountController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.DiscountCode;
import model.users.Customer;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class DiscountMenuManager implements Initializable {
    public VBox vBoxItems;
    public JFXButton addDiscount;
    public JFXButton refreshButton;
    public AnchorPane mainPane;
    public Label title;
    private controller.accounts.ManagerAccountController managerAccountController;
    private AccountController accountController;
    private CustomerAccountController customerAccountController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        managerAccountController = controller.accounts.ManagerAccountController.getInstance();
        accountController = AccountController.getInstance();
        if(accountController.getThisUser() instanceof Customer) {
            mainPane.getChildren().remove(addDiscount);
            mainPane.getChildren().remove(refreshButton);
            title.setText("My Discount Codes");
            loadCustomersDiscounts();
        } else {
            loadAllDiscounts();
        }
    }

    private void loadCustomersDiscounts() {
        customerAccountController = CustomerAccountController.getInstance();
        HashMap<String, DiscountCode> discountCodes = customerAccountController.getDiscountCodes();
        for(DiscountCode discountCode : discountCodes.values()) {
            addDiscountToList(discountCode);
        }
    }

    public void loadAllDiscounts() {
        for (DiscountCode discountCode : managerAccountController.getAllDiscountCodes()) {
            addDiscountToList(discountCode);
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

    public void refresh(MouseEvent mouseEvent) {
        vBoxItems.getChildren().clear();
        loadAllDiscounts();
    }
}
