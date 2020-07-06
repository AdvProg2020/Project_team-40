package view.account_menus.manager_view.category_view;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import server.controller.accounts.ManagerAccountController;
import exceptions.AccountsException;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import server.model.Category;
import server.model.enumerations.PropertyType;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class AddCategory implements Initializable {
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
    ManagerAccountController managerAccountController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        managerAccountController = ManagerAccountController.getInstance();
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
            managerAccountController.createCategory(nameField.getText(), parent, properties);
            ((Stage)(doneButton.getScene().getWindow())).close();
        }
        catch (AccountsException e) {
            errorLabel.setText(e.getMessage());
        }
    }
}
