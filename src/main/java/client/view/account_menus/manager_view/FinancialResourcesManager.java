package client.view.account_menus.manager_view;

import client.controller.RequestHandler;
import com.gilecode.yagson.com.google.gson.reflect.TypeToken;
import com.jfoenix.controls.JFXTextField;
import exceptions.InvalidInputException;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import org.restlet.resource.ResourceException;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class FinancialResourcesManager implements Initializable {
    public Label usernameLabel;
    public Label accountIDLabel;
    public Label errorLabel;

    public Button minCreditButton;
    public Button wageButton;
    public Button saveMinCredit;
    public Button saveWage;
    public JFXTextField minCreditTextField;
    public JFXTextField wageTextField;


    public GridPane gridPane;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        HashMap<String, String> information = RequestHandler.get("/accounts/customer_account_controller/bank_account/",
                new HashMap<>(), true, new TypeToken<HashMap<String, String>>(){}.getType());
        usernameLabel.setText(information.get("bank username"));
        accountIDLabel.setText(information.get("bank id"));
    }


    public void handleReadMinCredit(ActionEvent event) {
        minCreditTextField.setDisable(false);
        saveMinCredit.setDisable(false);
    }

    public void handleReadWage(ActionEvent event) {
        wageTextField.setDisable(false);
        saveWage.setDisable(false);
    }

    public void handleSaveMinCredit(ActionEvent event) {
        String credit = minCreditTextField.getText();
        try {
            double amount = Double.parseDouble(credit);
            if (amount < 0)
                throw new InvalidInputException("The amount is less than 0!");
            RequestHandler.put("/accounts/manager_account_controller/min_wallet_credit/", credit, new HashMap<>(), true, null);
            minCreditTextField.setDisable(true);
            saveMinCredit.setDisable(true);
        } catch (NumberFormatException e) {
            errorLabel.setText("Wrong input format");
        } catch (InvalidInputException e) {
            errorLabel.setText(e.getMessage());
        }
        catch (ResourceException e){
            try {
                errorLabel.setText(RequestHandler.getClientResource().getResponseEntity().getText());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void handleSaveWage(ActionEvent event) {
        String wagePercentage = wageTextField.getText();
        try {
            int wage = Integer.parseInt(wagePercentage);
            if (wage < 0 || wage > 100)
                throw new InvalidInputException("Enter a number between 0 and 100");
            RequestHandler.put("/accounts/manager_account_controller/wage/", wagePercentage, new HashMap<>(), true, null);
            wageTextField.setDisable(true);
            saveWage.setDisable(true);
        }catch (NumberFormatException e){
            errorLabel.setText("Wrong input format");
        } catch (InvalidInputException e) {
            errorLabel.setText(e.getMessage());
        }
        catch (ResourceException e){
            try {
                errorLabel.setText(RequestHandler.getClientResource().getResponseEntity().getText());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }
}
