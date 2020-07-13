package client.view.account_menus.manager_view.category_view;

import client.controller.RequestHandler;
import client.view.MenuManager;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.stage.Stage;
import org.restlet.data.Status;
import org.restlet.resource.ResourceException;
import server.model.Category;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class CategoryEdit extends MenuManager implements Initializable {
    public JFXButton doneButton;
    public JFXButton cancelButton;
    public Label errorLabel;
    public JFXTextField nameField;
    public ListView<String> propertiesListView;

    private String oldName;
    private ArrayList<String> oldProperties;
    private Category category;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        oldProperties = new ArrayList<>();
        propertiesListView.setEditable(true);
        propertiesListView.setCellFactory(TextFieldListCell.forListView());
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setOldValues(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                nameField.setText(category.getName());
                for (String s : category.getExtraProperties().keySet()) {
                    if (!s.equals(""))
                        propertiesListView.getItems().add(s);
                }
            }
        });
        oldName = category.getName();
        oldProperties.addAll(category.getExtraProperties().keySet());
    }

    public void handleEditCategory() {
        HashMap<String, String> toEdit = new HashMap<>();
        if (!nameField.getText().equals(oldName))
            toEdit.put("name", nameField.getText());
        for (String property : propertiesListView.getItems()) {
            String oldProperty = oldProperties.get(propertiesListView.getItems().indexOf(property));
            if (!oldProperty.equals(property))
                toEdit.put(oldProperty, property);
        }
        try {
            HashMap<String, String> queries = new HashMap<>();
            queries.put("name", oldName);
            RequestHandler.put("/accounts/manager_account_controller/category/", toEdit, queries, true, null);
            ((Stage)cancelButton.getScene().getWindow()).close();
        } catch (ResourceException e) {
            if (e.getStatus().equals(Status.CLIENT_ERROR_UNAUTHORIZED)){
                ((Stage)cancelButton.getScene().getWindow()).close();
                logout();
            }
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        errorLabel.setText(RequestHandler.getClientResource().getResponseEntity().getText());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            });
        }

    }

    public void handleCancel() {
        ((Stage)cancelButton.getScene().getWindow()).close();
    }
}
