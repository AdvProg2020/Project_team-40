package view.register_login_view;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import model.users.User;
import view.MenuManager;

import java.net.URL;
import java.util.ResourceBundle;

public class RegisterManager extends MenuManager implements Initializable {
    private final String fillError = "This Field is required!";
    public GridPane infoPane;
    public RadioButton customerButton;
    public RadioButton sellerButton;
    public RadioButton managerButton;
    public Label creditLabel;
    public Label companyLabel;
    public Label usernameError;
    public Label passwordError;
    public Label firstNameError;
    public Label lastNameError;
    public Label emailError;
    public Label phoneNumberError;
    public Label roleError;
    public Label creditError;
    public Label companyError;
    public TextField companyField;
    public TextField creditField;
    public TextField username;
    public PasswordField password;
    public TextField firstName;
    public TextField lastName;
    public TextField email;
    public TextField phoneNumber;

    public void register() {
        try {
            findBlankInput();
            if(customerButton.isPressed()) {
                registerCustomer();
            } else if(sellerButton.isPressed()) {
                registerSeller();
            } else if(managerButton.isPressed()) {
                registerManager();
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private void findBlankInput() throws Exception {
        boolean isBlank = false;
        isBlank = isFieldBlank(isBlank, username, usernameError);
        isBlank = isFieldBlank(isBlank, password, passwordError);
        isBlank = isFieldBlank(isBlank, firstName, firstNameError);
        isBlank = isFieldBlank(isBlank, lastName, lastNameError);
        isBlank = isFieldBlank(isBlank, phoneNumber, phoneNumberError);
        isBlank = isFieldBlank(isBlank, email, emailError);
        if(customerButton.isSelected()) {
            isBlank = isFieldBlank(isBlank, creditField, creditError);
        } else if(sellerButton.isSelected()) {
            isBlank = isFieldBlank(isBlank, creditField, creditError);
            isBlank = isFieldBlank(isBlank, companyField, companyError);
        } else if(managerButton == null) {
            isBlank = true;
            roleError.setText("You must choose one option!");
        } else if(!managerButton.isSelected()) {
            isBlank = true;
            roleError.setText("You must choose one option!");
        }
        if(isBlank)
            throw new Exception("Blank field");
    }

    private boolean isFieldBlank(boolean isBlank, TextField field, Label error) {
        if(field.getText().isBlank()) {
            error.setText(fillError);
            isBlank = true;
        } else {
            error.setText("");
        }
        return isBlank;
    }

    private void registerManager() {

    }

    private void registerSeller() {

    }

    private void registerCustomer() {
  //      CustomerAccountController.getInstance().createCustomerAccount(username.getText(), password.getText(),
    //            firstName.getText(), lastName.getText(), email.getText(), phoneNumber.getText(), creditField.getText());
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(User.doesManagerExist()) {
           infoPane.getChildren().remove(managerButton);
           managerButton = null;
        }

        //TODO: NEEDS TEST
    }
}
