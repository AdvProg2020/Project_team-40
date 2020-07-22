package client.view.register_login_view;

import client.controller.RequestHandler;
import client.view.ChangeListener;
import client.view.MenuManager;
import client.view.ValidInput;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.restlet.resource.ResourceException;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class RegisterManager extends MenuManager implements Initializable {
    private final String fillError = "This field is required!";
    private final String nameFormatError = "Your name can only contain characters!";


    private static boolean isByManager = false;
    public GridPane infoPane;
    public RadioButton customerButton;
    public RadioButton sellerButton;
    public RadioButton managerButton;
    public RadioButton supportButton;
    public Button register;
    public Button account;
    public Label companyLabel;
    public Label usernameError;
    public Label passwordError;
    public Label firstNameError;
    public Label lastNameError;
    public Label emailError;
    public Label phoneNumberError;
    public Label roleError;
    public Label companyError;
    public TextField companyField;
    public TextField username;
    public PasswordField password;
    public TextField firstName;
    public TextField lastName;
    public TextField email;
    public TextField phoneNumber;
    public Label registerLabel;
    public Label passwordValidationLabel;
    //requestQueries must be cleared before each request
    private HashMap<String, String> requestQueries;

    public void register() {
        try {
            findBlankInput();
            validateInput();
            requestQueries.clear();
            requestQueries.put("username", username.getText());
            boolean doesUserExist = (boolean) RequestHandler.get("/accounts/account/", requestQueries, false, boolean.class);
            if(!doesUserExist) {
                try {
                    if (customerButton.isSelected()) {
                        registerCustomer();
                    } else if (sellerButton.isSelected()) {
                        registerSeller();
                    } else if (supportButton.isSelected()) {
                        registerSupport();
                    } else if (managerButton.isSelected()) {
                        registerManager();
                    }
                    finishRegister();
                }catch (ResourceException e){
                    try {
                        passwordValidationLabel.setText(RequestHandler.getClientResource().getResponseEntity().getText());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
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
        if(customerButton.isSelected() || supportButton.isSelected()) {} else if(sellerButton.isSelected()) {
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
        requestQueries.clear();
        requestQueries.put("username", username.getText());
        requestQueries.put("password", password.getText());
        requestQueries.put("firstName", firstName.getText());
        requestQueries.put("lastName", lastName.getText());
        requestQueries.put("email", email.getText());
        requestQueries.put("phoneNumber", phoneNumber.getText());
        RequestHandler.post("/accounts/manager_account_controller/manager/", null, requestQueries, false, null);
    }

    private void registerSupport() {
        requestQueries.clear();
        requestQueries.put("username", username.getText());
        requestQueries.put("password", password.getText());
        requestQueries.put("firstName", firstName.getText());
        requestQueries.put("lastName", lastName.getText());
        requestQueries.put("email", email.getText());
        requestQueries.put("phoneNumber", phoneNumber.getText());
        RequestHandler.post("/accounts/manager_account_controller/support/", null, requestQueries, false, null);
    }

    private void registerSeller() {
        requestQueries.clear();
        requestQueries.put("username", username.getText());
        requestQueries.put("password", password.getText());
        requestQueries.put("firstName", firstName.getText());
        requestQueries.put("lastName", lastName.getText());
        requestQueries.put("email", email.getText());
        requestQueries.put("phoneNumber", phoneNumber.getText());
        requestQueries.put("credit", "0");
        requestQueries.put("companyInfo", companyField.getText());
        RequestHandler.post("/accounts/seller_account_controller/seller/", null, requestQueries, false, null);
    }

    private void registerCustomer() {
        requestQueries.clear();
        requestQueries.put("username", username.getText());
        requestQueries.put("password", password.getText());
        requestQueries.put("firstName", firstName.getText());
        requestQueries.put("lastName", lastName.getText());
        requestQueries.put("email", email.getText());
        requestQueries.put("phoneNumber", phoneNumber.getText());
        requestQueries.put("credit", "0");
        RequestHandler.post("/accounts/customer_account_controller/customer/", null, requestQueries, false, null);

    }

    public void clickCustomer() {
        companyLabel.setText("");
        removeCompany();
    }

    public void clickSeller() {
        companyLabel.setText("Company:");
        if (companyField == null) {
            companyField = new TextField();
            infoPane.add(companyField, 1, 10);
        }
    }

    public void clickManager() {
        companyLabel.setText("");
        removeCompany();
    }

    public void clickSupport(){
        companyLabel.setText("");
        removeCompany();
    }

    private void removeCompany() {
        if(companyField != null) {
            infoPane.getChildren().remove(companyField);
            companyField = null;
        }
    }

    private void addTextFieldsListeners() {
        username.textProperty().addListener(new ChangeListener(40, username));
        password.textProperty().addListener(new ChangeListener(40, password));
        firstName.textProperty().addListener(new ChangeListener(30, firstName));
        lastName.textProperty().addListener(new ChangeListener(40, lastName));
        email.textProperty().addListener(new ChangeListener(256, email));
        phoneNumber.textProperty().addListener(new ChangeListener(15, phoneNumber));
        if (companyField != null)
            companyField.textProperty().addListener(new ChangeListener(50, companyField));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addTextFieldsListeners();
        requestQueries = new HashMap<>();
        boolean doesManagerExist = (boolean)RequestHandler.get("/accounts/manager_account_controller/manager/", new HashMap<>(), false, boolean.class);
        if(doesManagerExist && !isByManager) {
           infoPane.getChildren().remove(managerButton);
           managerButton = null;
        }
    }

    public static void setIsByManager(boolean isByManager) {
        RegisterManager.isByManager = isByManager;
    }
}
