package client.view.account_menus.manager_view;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class FinancialResourcesManager {
    public Label usernameLabel;
    public Label passwordLabel;
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
}
