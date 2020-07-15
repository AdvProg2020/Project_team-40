package client.view.account_menus.manager_view.requests_view;

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
import javafx.stage.Modality;
import javafx.stage.Stage;
import server.model.Comment;
import server.model.Off;
import server.model.Product;
import server.model.requests.*;
import server.model.users.Seller;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class RequestItem extends MenuManager implements Initializable {
    public JFXButton viewRequestButton;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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
                else if (type.equalsIgnoreCase("Remove Product"))
                    setRemoveProductLabel((RemoveProductMenu) requestMenu, request);
                else if(type.equalsIgnoreCase("Add Comment"))
                    setAddCommentLabels((AddCommentMenu) requestMenu, request);

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
                requestMenu.setCreditLabel(Double.toString(seller.getCreditInWallet()));
                requestMenu.setEmailLabel(seller.getEmail());
                requestMenu.setPhoneLabel(seller.getPhoneNo());
            }

            private void setRemoveProductLabel(RemoveProductMenu removeProductMenu, Request request){
                Product product = ((RemoveProduct)request).getProduct();
                removeProductMenu.setRemoveProduct((RemoveProduct) request);
                removeProductMenu.setProductIdLabel(product.getProductId());
                removeProductMenu.setNameLabel(product.getName());
                removeProductMenu.setCategoryLabel(product.getCategory());
                removeProductMenu.setCompanyLabel(product.getCompany());
                removeProductMenu.setBasePriceLabel(Double.toString(product.getBasePrice()));
                removeProductMenu.setSellerLabel(product.getSellerUsername());
            }

            private void setEditOffLabels(EditOffMenu requestMenu, Request request) {
                EditOff editOffRequest = (EditOff) request;
                Off off = editOffRequest.getOff();
                requestMenu.setEditOff((EditOff)request);
                requestMenu.setOldLabel(editOffRequest.getField());
                requestMenu.setNewLabel(editOffRequest.getNewField());
            }

            private void setEditProductLabels(EditProductMenu requestMenu, Request request) {
                EditProduct editProductRequest = (EditProduct)request;
                Product product = editProductRequest.getProduct();
                requestMenu.setEditProduct((EditProduct)request);
                requestMenu.setOldLabel(editProductRequest.getField());
                requestMenu.setNewLabel(editProductRequest.getNewField());

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

            private void setAddCommentLabels(AddCommentMenu requestMenu, Request request) {
                AddComment addCommentRequest = (AddComment)request;
                Comment comment = addCommentRequest.getComment();
                requestMenu.setAddComment(addCommentRequest);
                requestMenu.setUserNameLabel(comment.getUsername());
                requestMenu.setLastUpdateLabel(comment.getLastUpdate().toString());
                requestMenu.setStatusLabel(comment.getIsBoughtStatus());
                requestMenu.setTitleLabel(comment.getTitle());
                requestMenu.setContentLabel(comment.getContent());
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
        if (type.equalsIgnoreCase("Add Comment"))
            return "add_comment";
        return null;
    }

    public void handleViewRequest() {
        HBox item = (HBox)(viewRequestButton.getParent());
        String requestId =((Label)item.getChildren().get(0)).getText();
        String type =((Label)item.getChildren().get(1)).getText();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/layouts/manager_menus/manager_request_menus/" + getFxmlNameByType(type) + ".fxml"));
        try {
            HashMap<String, String> queries = new HashMap<>();
            queries.put("requestID", requestId);
            Request request = (Request) RequestHandler.get("/accounts/manager_account_controller/request/", queries, true, Request.class);
            AnchorPane pane = loader.load();
            RequestMenu requestMenu = loader.getController();
            setLabelsContent(requestMenu, type, request);
            Stage userWindow = new Stage();
            int width = requestMenu instanceof AddCommentMenu ? 600 : 520;
            userWindow.setScene(new Scene(pane, width, 600));
            userWindow.initModality(Modality.APPLICATION_MODAL);
            userWindow.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
