package view.account_menus.manager_view.requests_view;

import com.jfoenix.controls.JFXButton;
import controller.accounts.ManagerAccountController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Off;
import model.Product;
import model.requests.AddOff;
import model.requests.AddProduct;
import model.requests.Request;
import model.requests.SellingPermission;
import model.users.Seller;
import view.MenuManager;

import java.net.URL;
import java.util.ResourceBundle;

public class RequestItem extends MenuManager implements Initializable {
    public JFXButton viewRequestButton;
    ManagerAccountController managerAccountController;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        managerAccountController = ManagerAccountController.getInstance();
    }

    private void setLabelsContent(RequestMenu requestMenu, String type,Request request) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if (type.equalsIgnoreCase("Add Product"))
                    setAddProductLabels((AddProductMenu)requestMenu, request);
                else if (type.equalsIgnoreCase("Add Off"))
                    setAddOffLabels((AddOffMenu)requestMenu, request);
                else if (type.equalsIgnoreCase("Edit Product"))
                    setEditProductLabels((EditProductMenu) requestMenu, request);
                else if (type.equalsIgnoreCase("Edit Off"))
                    setEditOffLabels((EditOffMenu)requestMenu, request);
                else if (type.equalsIgnoreCase("Selling Permission"))
                    setSellingPermissionLabels((SellingPermissionMenu) requestMenu, request);


            }
            private void setAddProductLabels(AddProductMenu addProductMenu, Request request){
                Product product = ((AddProduct)request).getProduct();
                addProductMenu.setAddProduct((AddProduct) request);
                addProductMenu.setProductIdLabel(product.getProductId());
                addProductMenu.setNameLabel(product.getName());
                addProductMenu.setCategoryLabel(product.getCategory());
                addProductMenu.setCompanyLabel(product.getCompany());
                addProductMenu.setBasePriceLabel(Double.toString(product.getBasePrice()));
                addProductMenu.setSellerLabel(product.getSellerUsername());

            }

            private void setSellingPermissionLabels(SellingPermissionMenu requestMenu, Request request) {
                Seller seller = (Seller) Seller.getUserByUsername(((SellingPermission)request).getSellerUsername());
                requestMenu.setSellingPermission((SellingPermission)request);
                requestMenu.setUsernameLabel(seller.getUsername());
                requestMenu.setNameLabel(seller.getFirstName());
                requestMenu.setCompanyLabel(seller.getCompanyInfo());
                requestMenu.setCreditLabel(Double.toString(seller.getCredit()));
                requestMenu.setEmailLabel(seller.getEmail());
                requestMenu.setPhoneLabel(seller.getPhoneNo());
            }

            private void setEditOffLabels(EditOffMenu requestMenu, Request request) {
            }

            private void setEditProductLabels(EditProductMenu requestMenu, Request request) {
            }

            private void setAddOffLabels(AddOffMenu requestMenu, Request request) {
                Off off = ((AddOff)request).getOff();
                requestMenu.setAddOff((AddOff)request);
                requestMenu.setOffIdLabel(off.getId());
                requestMenu.setStartDateLabel(off.getStartDate());
                requestMenu.setEndDateLabel(off.getEndDate());
                requestMenu.setSellerLabel(off.getSeller().getUsername());
                requestMenu.setPercentageLabel(Double.toString(off.getDiscountPercentage()));
            }
        });
    }




    private String getFxmlNameByType(String type){
        if (type.equalsIgnoreCase("Add Product"))
            return "add_product";
        if (type.equalsIgnoreCase("Add Off"))
            return "add_off";
        if (type.equalsIgnoreCase("Edit Product"))
            return "edit_product";
        if (type.equalsIgnoreCase("Edit Off"))
            return "edit_off";
        if (type.equalsIgnoreCase("Selling Permission"))
            return "selling_permission";
        if (type.equalsIgnoreCase("Remove Product"))
            return "remove_product";
        return null;
    }

    public void handleViewRequest(ActionEvent event) {
        HBox item = (HBox)(viewRequestButton.getParent());
        String requestId =((Label)item.getChildren().get(0)).getText();
        String type =((Label)item.getChildren().get(1)).getText();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/layouts/request_menus/" + getFxmlNameByType(type) + ".fxml"));
        try {
            Request request = managerAccountController.getRequest(requestId);
            AnchorPane pane = loader.load();
            RequestMenu requestMenu = loader.getController();
            setLabelsContent(requestMenu, type, request);
            Stage userWindow = new Stage();
            userWindow.setScene(new Scene(pane, 520, 600));
            userWindow.initModality(Modality.APPLICATION_MODAL);
            userWindow.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
