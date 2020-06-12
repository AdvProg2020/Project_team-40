package view.register_login_view;

import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import view.MenuManager;

public class RegisterManager extends MenuManager {
    public RadioButton customerButton;
    public RadioButton sellerButton;
    public RadioButton managerButton;
    public Label creditLabel;
    public Label companyLabel;

    public void register() {

    }

    public void clickCustomer() {
        creditLabel.setText("Credit:");
        companyLabel.setText("");
    }

    public void clickSeller() {
        companyLabel.setText("Company:");
        creditLabel.setText("Credit:");
    }

    public void clickManager() {
        companyLabel.setText("");
        creditLabel.setText("");
    }
}
