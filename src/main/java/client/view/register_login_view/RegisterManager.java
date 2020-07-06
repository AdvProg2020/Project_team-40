package client.view.register_login_view;

import server.controller.accounts.AccountController;
import server.controller.accounts.CustomerAccountController;
import server.controller.accounts.ManagerAccountController;
import server.controller.accounts.SellerAccountController;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import server.model.users.User;
import client.view.MenuManager;
import client.view.ValidInput;

import java.net.URL;
import java.util.ResourceBundle;

public class RegisterManager extends MenuManager implements Initializable {
    private final String fillError = "This field is required!";
    private final String nameFormatError = "Your name can only contain characters!";


    private static boolean isByManager = false;
    public GridPane infoPane;
    public RadioButton customerButton;
    public RadioButton sellerButton;
    public RadioButton managerButton;
    public Button register;
    public Button account;
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
    public Label registerLabel;

    public void register() {
        try {
            findBlankInput();
            validateInput();
            if(!AccountController.getInstance().doesUserExistWithThisUsername(username.getText())) {
                if (customerButton.isSelected()) {
                    registerCustomer();
                } else if (sellerButton.isSelected()) {
                    registerSeller();
                } else if (managerButton.isSelected()) {
                    registerManager();
                }
                finishRegister();
            } else {
                usernameError.setText("This username has been used!");
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private void finishRegister() {
        if (!isByManager) {
            account = new Button("Login!");
            mainInnerPane.getChildren().add(account);
            account.setLayoutX(register.getLayoutX());
            account.setLayoutY(register.getLayoutY());
            account.setPrefWidth(155);

            register.setDisable(true);
            username.setDisable(true);
            password.setDisable(true);
            firstName.setDisable(true);
            lastName.setDisable(true);
            email.setDisable(true);
            phoneNumber.setDisable(true);
            if (companyField != null)
                companyField.setDisable(true);
            if (creditField != null)
                creditField.setDisable(true);

            account.setOnAction(e -> goToAccountsMenu());
        }
        else
            ((Stage)register.getScene().getWindow()).close();
    }

    private void validateInput() throws Exception {
        boolean isValid = true;
        if(!ValidInput.NAME.getStringMatcher(firstName.getText()).matches()) {
            firstNameError.setText(nameFormatError);
            isValid = false;
        }
        if(!ValidInput.NAME.getStringMatcher(lastName.getText()).matches()) {
            lastNameError.setText(nameFormatError);
            isValid = false;
        }
        if(!ValidInput.EMAIL_ADDRESS.getStringMatcher(email.getText()).matches()) {
            emailError.setText("Enter a valid email!");
            isValid = false;
        }
        if(!ValidInput.PHONE_NUMBER.getStringMatcher(phoneNumber.getText()).matches()) {
            phoneNumberError.setText("Enter a valid phone number!");
            isValid = false;
        }
        if(customerButton.isSelected() || sellerButton.isSelected()) {
            if(!ValidInput.DOUBLE.getStringMatcher(creditField.getText()).matches()) {
                creditError.setText("Enter a valid number!");
                isValid = false;
            }
        }
        if(!isValid)
            throw new Exception("Invalid input");
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
        ManagerAccountController.getInstance().createManagerAccount(username.getText(), password.getText(),
                firstName.getText(), lastName.getText(), email.getText(), phoneNumber.getText());
    }

    private void registerSeller() {
        SellerAccountController.getInstance().createSellerAccount(username.getText(), password.getText(),
                firstName.getText(), lastName.getText(), email.getText(), phoneNumber.getText(),
                Double.parseDouble(creditField.getText()), companyField.getText());
    }

    private void registerCustomer() {
        CustomerAccountController.getInstance().createCustomerAccount(username.getText(), password.getText(),
                firstName.getText(), lastName.getText(), email.getText(), phoneNumber.getText(),
                Double.parseDouble(creditField.getText()));
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
        if(User.doesManagerExist() && !isByManager) {
           infoPane.getChildren().remove(managerButton);
           managerButton = null;
        }
    }

    public static void setIsByManager(boolean isByManager) {
        RegisterManager.isByManager = isByManager;
    }

}
