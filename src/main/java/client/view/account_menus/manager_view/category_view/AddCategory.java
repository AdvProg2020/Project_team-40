package client.view.account_menus.manager_view.category_view;

import client.controller.RequestHandler;
import client.view.MenuManager;
import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.com.google.gson.reflect.TypeToken;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import org.restlet.data.Status;
import org.restlet.resource.ResourceException;
import server.model.Category;
import server.model.enumerations.PropertyType;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class AddCategory extends MenuManager implements Initializable {
    public JFXTextField nameField;
    public JFXTextField propertyField;
    public JFXButton doneButton;
    public ListView<String> categoriesList;
    public ListView<String> propertiesList;
    public Label errorLabel;
    public ChoiceBox<String> propertyTypeChoiceBox;
    public JFXButton addPropertyButton;
    private String parent;
    private HashMap<String, PropertyType> properties;
    private HashMap<String, String> requestQueries;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        requestQueries = new HashMap<>();
        propertyTypeChoiceBox.getItems().add("Range");
        propertyTypeChoiceBox.getItems().add("String");
        propertyTypeChoiceBox.setValue("String");
        properties = new HashMap<>();
        for (String category : Category.getAllCategories().keySet()) {
            categoriesList.getItems().add(category);
        }
    }



    private void addSelectedItemsToList() {
        if (categoriesList.getSelectionModel().getSelectedItems().size() == 1)
            parent = categoriesList.getSelectionModel().getSelectedItems().get(0);
    }

    public void handleAddProperty() {
        String propertyTypeString = propertyTypeChoiceBox.getValue();
        PropertyType propertyType = propertyTypeString.equalsIgnoreCase("String") ? PropertyType.STRING : PropertyType.RANGE;
        properties.put(propertyField.getText(), propertyType);
        Platform.runLater(() -> propertiesList.getItems().add(propertyField.getText()));
    }

    public void handleCreateCategory() {
        addSelectedItemsToList();
        try {
            if (nameField.getText().equals("")){
                ((Stage)(nameField.getScene().getWindow())).close();
                return;
            }
            requestQueries.clear();
            requestQueries.put("name", nameField.getText());
            requestQueries.put("parentName",parent);

            YaGson mapper = new YaGson();
            String entity = mapper.toJson(properties, new TypeToken<HashMap<String, PropertyType>>(){}.getType());
            RequestHandler.post("/accounts/manager_account_controller/category/", entity, requestQueries, true, null);
            ((Stage)(doneButton.getScene().getWindow())).close();
        }
        catch (ResourceException e) {
            if (e.getStatus().equals(Status.CLIENT_ERROR_UNAUTHORIZED)){
                ((Stage)(doneButton.getScene().getWindow())).close();
                logout();
            }
            errorLabel.setText(e.getMessage());
        }
    }
}
