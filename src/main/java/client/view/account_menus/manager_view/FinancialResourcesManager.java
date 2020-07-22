package client.view.account_menus.manager_view;

import client.controller.RequestHandler;
import com.gilecode.yagson.com.google.gson.reflect.TypeToken;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class FinancialResourcesManager implements Initializable {
    public Label usernameLabel;
    public Label accountIDLabel;
    public Label balanceLabel;
    public Label minCreditLabel;
    public Label wageLabel;

    public Label minCreditError;
    public Label wageError;

    public Button minCreditButton;
    public Button wageButton;

    private TextField minCreditField;
    private TextField wageField;

    public GridPane gridPane;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        HashMap<String, String> information = RequestHandler.get("/accounts/customer_account_controller/bank_account/",
                new HashMap<>(), true, new TypeToken<HashMap<String, String>>(){}.getType());

    }
}
