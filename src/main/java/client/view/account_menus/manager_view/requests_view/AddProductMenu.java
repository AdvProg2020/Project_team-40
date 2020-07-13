package client.view.account_menus.manager_view.requests_view;

import client.controller.RequestHandler;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.restlet.resource.ResourceException;
import server.model.requests.AddProduct;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class AddProductMenu extends RequestMenu implements Initializable {


    public Label productIdLabel;
    public Label nameLabel;
    public Label categoryLabel;
    public Label sellerLabel;
    public Label companyLabel;
    public Label basePriceLabel;
    public JFXButton acceptButton;
    public JFXButton declineButton;
    private AddProduct addProduct;
    private HashMap<String, String> requestQueries;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        requestQueries = new HashMap<>();
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

    public void setAddProduct(AddProduct addProduct) {
        this.addProduct = addProduct;
    }

    public void handleAcceptRequest(ActionEvent event) {
        try {
            requestQueries.clear();
            RequestHandler.put("/accounts/manager_account_controller/accept_request/", addProduct.getRequestId(), requestQueries, true, null);
            ((Stage)((Node)event.getSource()).getScene().getWindow()).close();
        } catch (ResourceException e) {
            try {
                System.err.println(RequestHandler.getClientResource().getResponseEntity().getText());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void handleDeclineRequest(ActionEvent event) {
        try {
            requestQueries.clear();
            RequestHandler.put("/accounts/manager_account_controller/decline_request/", addProduct.getRequestId(), requestQueries, true, null);
            ((Stage)((Node)event.getSource()).getScene().getWindow()).close();
        } catch (ResourceException e) {
            try {
                System.err.println(RequestHandler.getClientResource().getResponseEntity().getText());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
