package client.view.account_menus.manager_view.discount_view;

import client.controller.RequestHandler;
import client.view.MenuManager;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.stage.Stage;
import org.restlet.data.Status;
import org.restlet.resource.ResourceException;
import server.model.users.Customer;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class AddDiscount extends MenuManager implements Initializable {
    public JFXTextField startField;
    public JFXTextField endField;
    public JFXTextField percentageField;
    public JFXTextField maxPriceField;
    public JFXTextField countField;
    public ListView<String> usersList;
    public JFXButton doneButton;
    public Label errorLabel;
    private ArrayList<String> customers;
    private HashMap<String, String> requestQueries;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        requestQueries = new HashMap<>();
        usersList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        customers = new ArrayList<>();
        for (String customer : Customer.getAllCustomers()) {
            usersList.getItems().add(customer);
        }
    }

    public void handleCreateDiscount() throws IOException {
        addSelectedItemsToList();
        try {
            requestQueries.clear();
            requestQueries.put("startDate", startField.getText());
            requestQueries.put("endDate", endField.getText());
            requestQueries.put("percentage", percentageField.getText());
            requestQueries.put("maxDiscount", maxPriceField.getText());
            requestQueries.put("countPerUser", countField.getText());
            RequestHandler.post("/accounts/manager_account_controller/discount/", customers, requestQueries, true, null);
            ((Stage)(doneButton.getScene().getWindow())).close();
        }
        catch (ResourceException e) {
            if (e.getStatus().equals(Status.CLIENT_ERROR_UNAUTHORIZED))
            {
                ((Stage)(doneButton.getScene().getWindow())).close();
                logout();
            }
            errorLabel.setText(RequestHandler.getClientResource().getResponseEntity().getText());
        }
    }

    private void addSelectedItemsToList() {
        customers.addAll(usersList.getSelectionModel().getSelectedItems());
    }
}
