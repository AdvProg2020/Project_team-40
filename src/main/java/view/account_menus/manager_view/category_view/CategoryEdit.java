package view.account_menus.manager_view.category_view;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import controller.accounts.ManagerAccountController;
import exceptions.AccountsException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.stage.Stage;
import model.Category;
import model.DiscountCode;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class CategoryEdit implements Initializable {
    public JFXButton doneButton;
    public JFXButton cancelButton;
    public Label errorLabel;
    public JFXTextField nameField;
    public ListView<String> propertiesListView;

    private String oldName;
    private ArrayList<String> oldProperties;
    private Category category;
    private ManagerAccountController managerAccountController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        managerAccountController = ManagerAccountController.getInstance();
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


    public void handleEditCategory(ActionEvent event) {
        HashMap<String, String> toEdit = new HashMap<>();
        if (!nameField.getText().equals(oldName))
            toEdit.put("name", nameField.getText());
        for (String property : propertiesListView.getItems()) {
            String oldProperty = oldProperties.get(propertiesListView.getItems().indexOf(property));
            if (!oldProperty.equals(property))
                toEdit.put(oldProperty, property);
        }
        try {
            managerAccountController.editCategory(oldName, toEdit);
            ((Stage)cancelButton.getScene().getWindow()).close();
        } catch (AccountsException e) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    errorLabel.setText(e.getMessage());
                }
            });
        }

    }



    public void handleCancel(ActionEvent event) {
        ((Stage)cancelButton.getScene().getWindow()).close();
    }

}
