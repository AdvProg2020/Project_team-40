package client.view.account_menus.manager_view.discount_view;

import client.controller.RequestHandler;
import client.view.ChangeListener;
import client.view.MenuManager;
import client.view.ValidInput;
import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.com.google.gson.reflect.TypeToken;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import exceptions.InvalidInputException;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.stage.Stage;
import org.restlet.data.Status;
import org.restlet.resource.ResourceException;

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
        addTextFieldsListeners();
        requestQueries = new HashMap<>();
        usersList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        customers = new ArrayList<>();
        ArrayList<String> allCustomers = RequestHandler.get("/accounts/customer_account_controller/all_customers/", requestQueries, true, ArrayList.class);
        assert allCustomers != null;
        for (String customer : allCustomers) {
            usersList.getItems().add(customer);
        }
    }

    private void addTextFieldsListeners() {
        percentageField.textProperty().addListener(new ChangeListener(2, percentageField));
        countField.textProperty().addListener(new ChangeListener(1, countField));
        maxPriceField.textProperty().addListener(new ChangeListener(20, maxPriceField));
    }

    public void handleCreateDiscount() throws IOException {
        addSelectedItemsToList();
        try {
            validateInputs();
            requestQueries.clear();
            requestQueries.put("startDate", startField.getText());
            requestQueries.put("endDate", endField.getText());
            requestQueries.put("percentage", percentageField.getText());
            requestQueries.put("maxDiscount", maxPriceField.getText());
            requestQueries.put("countPerUser", countField.getText());

            YaGson mapper = new YaGson();
            String entity = mapper.toJson(customers, new TypeToken<ArrayList<String>>(){}.getType());
            RequestHandler.post("/accounts/manager_account_controller/discount/", entity, requestQueries, true, null);
            ((Stage)(doneButton.getScene().getWindow())).close();
        }
        catch (InvalidInputException e) {
            errorLabel.setText(e.getMessage());
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

    private void validateInputs() throws InvalidInputException {
        if (ValidInput.INTEGER.isInputInvalid(percentageField.getText()))
            throw new InvalidInputException("Invalid percentage input.");
        if (ValidInput.DOUBLE.isInputInvalid(maxPriceField.getText()))
            throw new InvalidInputException("Invalid maximum price input");
        if (ValidInput.INTEGER.isInputInvalid(countField.getText()))
            throw new InvalidInputException("Invalid count per user input.");
    }

    private void addSelectedItemsToList() {
        customers.addAll(usersList.getSelectionModel().getSelectedItems());
    }
}
