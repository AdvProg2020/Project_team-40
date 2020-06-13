package view.account_menus.manager_view.requests_view;

import com.jfoenix.controls.JFXButton;
import controller.accounts.ManagerAccountController;
import exceptions.AccountsException;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.requests.AddProduct;
import model.requests.RemoveProduct;
import view.MenuManager;

import java.net.URL;
import java.util.ResourceBundle;

public class RemoveProductMenu extends RequestMenu implements Initializable {
    public Label productIdLabel;
    public Label nameLabel;
    public Label categoryLabel;
    public Label sellerLabel;
    public Label companyLabel;
    public Label basePriceLabel;
    public JFXButton acceptButton;
    public JFXButton declineButton;
    private ManagerAccountController managerAccountController;
    private RemoveProduct removeProduct;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        managerAccountController = ManagerAccountController.getInstance();
    }

    public void setProductIdLabel(String productId) {
        productIdLabel.setText(productId);
    }

    public void setNameLabel(String name) {
        nameLabel.setText(name);
    }

    public void setCategoryLabel(String category) {
        categoryLabel.setText(category);
    }

    public void setSellerLabel(String seller) {
        sellerLabel.setText(seller);
    }

    public void setCompanyLabel(String company) {
        companyLabel.setText(company);
    }

    public void setBasePriceLabel(String basePrice) {
        basePriceLabel.setText(basePrice);
    }

    public void setRemoveProduct(RemoveProduct removeProduct) {
        this.removeProduct = removeProduct;
    }

    public void handleAcceptRequest(ActionEvent event) {
        try {
            managerAccountController.acceptRequest(removeProduct.getRequestId());
            ((Stage)((Node)event.getSource()).getScene().getWindow()).close();
        } catch (AccountsException e) {
            System.err.println(e.getMessage());
        }
    }

    public void handleDeclineRequest(ActionEvent event) {
        try {
            managerAccountController.declineRequest(removeProduct.getRequestId());
            ((Stage)((Node)event.getSource()).getScene().getWindow()).close();
        } catch (AccountsException e) {
            System.err.println(e.getMessage());
        }
    }

}
