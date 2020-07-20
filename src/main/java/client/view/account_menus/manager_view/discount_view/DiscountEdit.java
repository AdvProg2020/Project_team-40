package client.view.account_menus.manager_view.discount_view;

import client.controller.RequestHandler;
import client.view.ChangeListener;
import client.view.MenuManager;
import client.view.ValidInput;
import com.gilecode.yagson.YaGson;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import exceptions.InvalidInputException;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.restlet.data.Status;
import org.restlet.resource.ResourceException;
import server.model.DiscountCode;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class DiscountEdit extends MenuManager implements Initializable {
    public JFXButton cancelButton;
    public JFXButton doneButton;
    public JFXTextField startDateField;
    public JFXTextField endDateField;
    public JFXTextField percentageField;
    public JFXTextField maxPriceField;
    public JFXTextField countField;
    public Label errorLabel;
    private String oldStart;
    private String oldEnd;
    private String oldPercentage;
    private String oldMax;
    private String oldCount;

    private DiscountCode discountCode;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addTextFieldsListeners();
    }
    private void addTextFieldsListeners() {
        percentageField.textProperty().addListener(new ChangeListener(2, percentageField));
        countField.textProperty().addListener(new ChangeListener(1, countField));
        maxPriceField.textProperty().addListener(new ChangeListener(20, maxPriceField));
    }

    public void setDiscountCode(DiscountCode discountCode) {
        this.discountCode = discountCode;
    }

    public void setOldValues(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                startDateField.setText(discountCode.getStartDate().toString());
                endDateField.setText(discountCode.getEndDate().toString());
                percentageField.setText(Integer.toString(discountCode.getPercentage()));
                maxPriceField.setText(Double.toString(discountCode.getMaxAmount()));
                countField.setText(Integer.toString(discountCode.getCountPerUser()));
            }
        });

        oldStart = discountCode.getStartDate().toString();
        oldEnd = discountCode.getEndDate().toString();
        oldPercentage = Integer.toString(discountCode.getPercentage());
        oldCount = Integer.toString(discountCode.getCountPerUser());
        oldMax = Double.toString(discountCode.getMaxAmount());
    }


    public void handleEditDiscount() {
        HashMap<String, String> toEdit = new HashMap<>();
        if (!startDateField.getText().equals(oldStart))
            toEdit.put("Start date", startDateField.getText());
        if (!endDateField.getText().equals(oldEnd))
            toEdit.put("End date", endDateField.getText());
        if (!percentageField.getText().equals(oldPercentage))
            toEdit.put("Percentage", percentageField.getText());
        if (!countField.getText().equals(oldCount))
            toEdit.put("Count per user", countField.getText());
        if (!maxPriceField.getText().equals(oldMax))
            toEdit.put("Maximum amount", maxPriceField.getText());

        try {
            validateInputs();
            HashMap<String, String> queries = new HashMap<>();
            queries.put("code", discountCode.getCode());

            YaGson mapper = new YaGson();
            String entity = mapper.toJson(toEdit, HashMap.class);
            RequestHandler.put("/accounts/manager_account_controller/discount/", entity, queries, true, null);
            ((Stage)cancelButton.getScene().getWindow()).close();
        } catch (ResourceException e) {
            if (e.getStatus().equals(Status.CLIENT_ERROR_UNAUTHORIZED))
            {
                ((Stage)cancelButton.getScene().getWindow()).close();
                logout();
            }
            else {
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
        } catch (InvalidInputException e) {
            errorLabel.setText(e.getMessage());
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

    public void handleCancel() {
        ((Stage)cancelButton.getScene().getWindow()).close();
    }

}
