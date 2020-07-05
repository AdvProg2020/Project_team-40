package view.account_menus.manager_view.discount_view;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import controller.accounts.ManagerAccountController;
import exceptions.AccountsException;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.stage.Stage;
import model.users.Customer;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddDiscount implements Initializable {
    public JFXTextField startField;
    public JFXTextField endField;
    public JFXTextField percentageField;
    public JFXTextField maxPriceField;
    public JFXTextField countField;
    public ListView<String> usersList;
    public JFXButton doneButton;
    public Label errorLabel;
    private ManagerAccountController managerAccountController;
    private ArrayList<String> customers;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        managerAccountController = ManagerAccountController.getInstance();
        usersList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        customers = new ArrayList<>();
        for (String customer : Customer.getAllCustomers()) {
            usersList.getItems().add(customer);
        }
    }

    public void handleCreateDiscount() {
        addSelectedItemsToList();
        try {
            managerAccountController.createDiscount(startField.getText(), endField.getText(), Integer.parseInt(percentageField.getText()),
                    Double.parseDouble(maxPriceField.getText()), Integer.parseInt(countField.getText()), customers);
            ((Stage)(doneButton.getScene().getWindow())).close();
        }
        catch (AccountsException e) {
            errorLabel.setText(e.getMessage());
        }
    }

    private void addSelectedItemsToList() {
        customers.addAll(usersList.getSelectionModel().getSelectedItems());
    }
}
