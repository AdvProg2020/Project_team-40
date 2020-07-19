package client.view.account_menus.manager_view.category_view;

import client.controller.Client;
import client.controller.RequestHandler;
import client.view.MenuManager;
import com.gilecode.yagson.com.google.gson.reflect.TypeToken;
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
import org.restlet.data.Status;
import org.restlet.resource.ResourceException;
import server.model.Category;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Set;

public class CategoryMenuManager extends MenuManager implements Initializable {
    public JFXButton addCategory;
    public JFXButton refreshButton;
    public VBox vBoxItems;
    public Label mainLabel;
    public AnchorPane mainPane;
    private HashMap<String, String> requestQueries;
    private String clientRole;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        requestQueries = new HashMap<>();
        clientRole = Client.getInstance().getRole();
        if(clientRole.equals("Seller")) {
            initializeForSeller();
            loadCategories("Seller");
        }
        else
            loadCategories("Manager");
    }

    private void initializeForSeller() {
        mainLabel.setText("Categories");
        mainPane.getChildren().remove(addCategory);
        mainPane.getChildren().remove(refreshButton);
    }

    private void loadCategories(String role) {
        vBoxItems.getChildren().clear();
        requestQueries.clear();
        try {
            Set<String> allCategories = getProperCategories(role);
        for (String categoryName : allCategories) {
            try {
                requestQueries.clear();
                requestQueries.put("name", categoryName);
                Category category = RequestHandler.get("/accounts/manager_account_controller/category/", requestQueries, false, Category.class);
                AnchorPane item = (AnchorPane) FXMLLoader.load(getClass().
                        getResource("/layouts/manager_menus/manager_category_menus/category_item.fxml"));
                HBox hBox = (HBox) item.getChildren().get(0);
                setLabelsContent(category, hBox);
                vBoxItems.getChildren().add(item);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        }
        catch (ResourceException e){
            if (e.getStatus().equals(Status.CLIENT_ERROR_UNAUTHORIZED))
                logout();
        }
    }

    private Set<String> getProperCategories(String role) {
        Set<String> allCategories = null;
        try {

            if (role.equals("Manager"))
                allCategories = RequestHandler.get("/accounts/manager_account_controller/all_categories/", requestQueries, true, Set.class);
            else if (role.equals("Seller")) {
                HashMap<String, Category> sellerCats = RequestHandler.get("/accounts/seller_account_controller/all_categories/",
                        requestQueries, true, new TypeToken<HashMap<String, Category>>() {
                        }.getType());
                assert sellerCats != null;
                allCategories = sellerCats.keySet();
            }
        }catch (ResourceException e){
            if (e.getStatus().equals(Status.CLIENT_ERROR_UNAUTHORIZED)) {
                logout();
            }
        }
        return allCategories;
    }

    private void setLabelsContent(Category category, HBox hBox) {
        Label nameLabel =(Label) hBox.getChildren().get(0);
        Label parentCategoryLabel = (Label) hBox.getChildren().get(1);
        nameLabel.setText(category.getName());
        parentCategoryLabel.setText(category.getParentCategoryName());
    }

    public void handleAddCategory() {
        Stage stage = new Stage();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/layouts/manager_menus/manager_category_menus/add_category.fxml"));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root, 1200, 600));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    loadCategories("Manager");
                }
            });
        }
    }

    public void handleRefresh() {
        loadCategories(clientRole);
    }
}
