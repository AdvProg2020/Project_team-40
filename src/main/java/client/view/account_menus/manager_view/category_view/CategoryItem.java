package client.view.account_menus.manager_view.category_view;

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
import server.model.Category;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Set;

public class CategoryItem extends MenuManager implements Initializable {
    public JFXButton deleteCategoryButton;
    public JFXButton viewCategoryButton;
    public JFXButton editCategoryButton;
    public HBox buttons;
    private HashMap<String, String> requestQueries;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        requestQueries = new HashMap<>();
        if(Client.getInstance().getRole().equals("Seller")) {
            buttons.getChildren().remove(deleteCategoryButton);
            buttons.getChildren().remove(editCategoryButton);
        }
    }

    private void loadCategories(VBox vBoxItems) {
        vBoxItems.getChildren().clear();
        requestQueries.clear();
        Set<String> allCategories = RequestHandler.get("/accounts/manager_account_controller/all_categories/", requestQueries, true, Set.class);
        assert allCategories != null;
        for (String categoryName : allCategories) {
            try {
                Category category = Category.getCategoryByName(categoryName);
                AnchorPane item = (AnchorPane) FXMLLoader.load(getClass().getResource("/layouts/manager_menus/manager_category_menus/category_item.fxml"));
                HBox hBox = (HBox) item.getChildren().get(0);
                setLabelsContent(category, hBox);
                vBoxItems.getChildren().add(item);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private void setLabelsContent(Category category, HBox hBox) {
        Label nameLabel =((Label) hBox.getChildren().get(0));
        Label parentCategoryLabel =((Label) hBox.getChildren().get(1));
        nameLabel.setText(category.getName());
        parentCategoryLabel.setText(category.getParentCategoryName());
    }

    private void setLabelsContent(CategoryView categoryView, Category category) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                categoryView.setCategory(category);
                categoryView.setNameLabel(category.getName());
                categoryView.setParentCategoryLabel(category.getParentCategoryName());
                categoryView.setProductsList(category.getProductIDs());
                categoryView.setSubCategoriesList(category.getSubCategoriesNames());
            }
        });
    }

    public void handleDeleteCategory() {
        HBox item = (HBox)((deleteCategoryButton.getParent()).getParent());
        VBox items =(VBox)(item.getParent()).getParent();
        String name =((Label)item.getChildren().get(0)).getText();
        try {
            requestQueries.clear();
            requestQueries.put("name", name);
            RequestHandler.delete("/accounts/manager_account_controller/category/", requestQueries, true, String.class);
        } catch (ResourceException e) {
            if (e.getStatus().equals(Status.CLIENT_ERROR_UNAUTHORIZED))
                logout();
        }
        Platform.runLater(() -> loadCategories(items));
    }

    public void handleViewCategory() {
        HBox item = (HBox) viewCategoryButton.getParent().getParent();
        String name =((Label)item.getChildren().get(0)).getText();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/layouts/manager_menus/manager_category_menus/category.fxml"));
        try {
            Category category = Category.getCategoryByName(name);
            AnchorPane pane = loader.load();
            CategoryView categoryView = loader.getController();
            setLabelsContent(categoryView, category);
            Stage userWindow = new Stage();
            userWindow.setScene(new Scene(pane, 1300, 550));
            userWindow.initModality(Modality.APPLICATION_MODAL);
            userWindow.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleEditCategory() {
        HBox item = (HBox) ((editCategoryButton.getParent()).getParent());
        String name = ((Label) item.getChildren().get(0)).getText();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/layouts/manager_menus/manager_category_menus/edit_category.fxml"));
        try {
            Category category = Category.getCategoryByName(name);
            AnchorPane pane = loader.load();
            CategoryEdit editCategoryController = loader.getController();
            editCategoryController.setCategory(category);
            editCategoryController.setOldValues();
            Stage userWindow = new Stage();
            userWindow.setScene(new Scene(pane, 800, 600));
            userWindow.initModality(Modality.APPLICATION_MODAL);
            userWindow.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
