package view.register_login_view;

import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import view.MenuManager;

public class RegisterManager extends MenuManager {
    public GridPane infoPane;
    public RadioButton customerButton;
    public RadioButton sellerButton;
    public RadioButton managerButton;
    public Label creditLabel;
    public Label companyLabel;
    public TextField lastName;
    public TextField companyField;
    public TextField creditField;

    public void register() {

    }

    public void clickCustomer() {
        creditLabel.setText("Credit:");
        companyLabel.setText("");
        createCredit();
        removeCompany();
    }

    public void clickSeller() {
        companyLabel.setText("Company:");
        creditLabel.setText("Credit:");
        createCredit();
        if (companyField == null) {
            companyField = new TextField();
            infoPane.add(companyField, 1, 11);
        }
    }

    public void clickManager() {
        companyLabel.setText("");
        creditLabel.setText("");
        removeCompany();
        removeCredit();
    }

    private void removeCredit() {
        if(creditField != null) {
            infoPane.getChildren().remove(creditField);
            creditField = null;
        }
    }

    private void removeCompany() {
        if(companyField != null) {
            infoPane.getChildren().remove(companyField);
            companyField = null;
        }
    }

    private void createCredit() {
        if (creditField == null) {
            creditField = new TextField();
            infoPane.add(creditField, 1, 10);
        }
    }
}
