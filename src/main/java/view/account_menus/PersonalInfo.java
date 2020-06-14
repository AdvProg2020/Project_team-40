package view.account_menus;

import controller.accounts.AccountController;
import controller.accounts.CustomerAccountController;
import controller.accounts.SellerAccountController;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import model.users.Customer;
import model.users.Seller;
import model.users.User;
import view.ValidInput;

import java.net.URL;
import java.util.ResourceBundle;

public class PersonalInfo extends AccountMenu implements Initializable {

    //TODO: EDIT PASSWORD!

    private User user = AccountController.getInstance().getThisUser();
    private CustomerAccountController customerAccountController = CustomerAccountController.getInstance();
    private SellerAccountController sellerAccountController = SellerAccountController.getInstance();
    public GridPane gridPane;
    public Label creditLabel;
    public Label companyLabel;
    public Label passwordLabel;

    //Labels to show info:
    public Label username;
    public Label firstName;
    public Label lastName;
    public Label email;
    public Label phone;
    public Label credit;
    public Label company;

    //Labels to show error:
    public Label usernameError;
    public Label firstNameError;
    public Label lastNameError;
    public Label emailError;
    public Label phoneNumberError;
    public Label creditError;
    public Label companyError;

    //Buttons to edit and save changes:
    public Button editUsername;
    public Button editFirstName;
    public Button editLastName;
    public Button editPhoneNumber;
    public Button editEmail;
    public Button editCompany;
    public Button changePassword;
    public Button savePassword;

    //Fields to enter new information:
    public TextField newUsername;
    public TextField newFirstName;
    public TextField newLastName;
    public TextField newEmail;
    public TextField newPhoneNumber;
    public TextField newCompany;
    public TextField newPassword;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(user instanceof Customer) {
            creditLabel.setText("Credit: ");
            credit.setText(Double.toString(customerAccountController.getBalance()));
            editCompany.setOnMouseClicked(e -> editCompany());
        }
        if(user instanceof Seller) {
            creditLabel.setText("Credit: ");
            credit.setText(Double.toString(sellerAccountController.getBalance()));
            companyLabel.setText("Company: ");
            company.setText(sellerAccountController.getCompanyInfo());
            editCompany = new Button("edit");
            gridPane.add(editCompany, 3, 6);
            editCompany.setOnMouseClicked(e -> editCompany());
        }
        username.setText(user.getUsername());
        firstName.setText(user.getFirstName());
        lastName.setText(user.getLastName());
        email.setText(user.getEmail());
        phone.setText(user.getPhoneNo());
    }

    public void editUsername() {
        editUsername.setText("save");
        editUsername.setOnMouseClicked(e -> saveUsername());
        if(newUsername == null)
            newUsername = new TextField();
        gridPane.add(newUsername, 1, 0);
        newUsername.setText(username.getText());
    }

    private void saveUsername() {
        if(!newUsername.getText().isBlank()) {
            usernameError.setText("");
            accountController.editUser("username", newUsername.getText());
            gridPane.getChildren().remove(newUsername);
            username.setText(newUsername.getText());
            newUsername.setText("");
            editUsername.setOnMouseClicked(e -> editUsername());
            editUsername.setText("edit");
        } else {
            usernameError.setText("Username required!");
        }
    }

    public void editFirstName() {
        editFirstName.setText("save");
        editFirstName.setOnMouseClicked(e -> saveFirstName());
        if(newFirstName == null)
            newFirstName = new TextField();
        gridPane.add(newFirstName, 1, 1);
        newFirstName.setText(firstName.getText());
    }

    private void saveFirstName() {
        if(!newFirstName.getText().isBlank()) {
            if (ValidInput.NAME.getStringMatcher(newFirstName.getText()).matches()) {
                firstNameError.setText("");
                accountController.editUser("firstName", newFirstName.getText());
                gridPane.getChildren().remove(newFirstName);
                firstName.setText(newFirstName.getText());
                newFirstName.setText("");
                editFirstName.setOnMouseClicked(e -> editFirstName());
                editFirstName.setText("edit");
            } else {
                firstNameError.setText("Can only contain alphabetic characters!");
            }
        } else {
            firstNameError.setText("First Name Required!");
        }
    }

    public void editLastName() {
        editLastName.setText("save");
        editLastName.setOnMouseClicked(e -> saveLastName());
        if(newLastName == null)
            newLastName = new TextField();
        gridPane.add(newLastName, 1, 2);
        newLastName.setText(lastName.getText());
    }

    private void saveLastName() {
        if(!newLastName.getText().isBlank()) {
            if (ValidInput.NAME.getStringMatcher(newLastName.getText()).matches()) {
                lastNameError.setText("");
                accountController.editUser("lastName", newLastName.getText());
                gridPane.getChildren().remove(newLastName);
                lastName.setText(newLastName.getText());
                newLastName.setText("");
                editLastName.setOnMouseClicked(e -> editLastName());
                editLastName.setText("edit");
            } else {
                lastNameError.setText("Can only contain alphabetic characters!");
            }
        } else {
            lastNameError.setText("Last Name Required!");
        }
    }

    public void editEmail() {
        editEmail.setText("save");
        editEmail.setOnMouseClicked(e -> saveEmail());
        if(newEmail == null)
            newEmail = new TextField();
        gridPane.add(newEmail, 1, 3);
        newEmail.setText(email.getText());
    }

    private void saveEmail() {
        if(!newEmail.getText().isBlank()) {
            if(ValidInput.EMAIL_ADDRESS.getStringMatcher(newEmail.getText()).matches()) {
                emailError.setText("");
                accountController.editUser("email", newEmail.getText());
                gridPane.getChildren().remove(newEmail);
                email.setText(newEmail.getText());
                newEmail.setText("");
                editEmail.setOnMouseClicked(e -> editEmail());
                editEmail.setText("edit");
            } else {
                emailError.setText("Invalid Email address!");
            }
        } else {
            emailError.setText("Email Required!");
        }
    }

    public void editPhoneNumber() {
        editPhoneNumber.setText("save");
        editPhoneNumber.setOnMouseClicked(e -> savePhoneNumber());
        if(newPhoneNumber == null)
            newPhoneNumber = new TextField();
        gridPane.add(newPhoneNumber, 1, 4);
        newPhoneNumber.setText(phone.getText());
    }

    private void savePhoneNumber() {
        if(!newPhoneNumber.getText().isBlank()) {
            if(ValidInput.PHONE_NUMBER.getStringMatcher(newPhoneNumber.getText()).matches()) {
                phoneNumberError.setText("");
                accountController.editUser("phoneNumber", newPhoneNumber.getText());
                gridPane.getChildren().remove(newPhoneNumber);
                phone.setText(newPhoneNumber.getText());
                editPhoneNumber.setOnMouseClicked(e -> editPhoneNumber());
                editPhoneNumber.setText("edit");
            } else {
                phoneNumberError.setText("Invalid Phone Number!");
            }
        } else {
            phoneNumberError.setText("Phone Number Required!");
        }
    }

    private void editCompany() {
        editCompany.setText("save");
        editCompany.setOnMouseClicked(e -> saveCompany());
        if(newCompany == null)
            newCompany = new TextField();
        gridPane.add(newCompany, 1, 6);
        newCompany.setText(company.getText());
    }

    private void saveCompany() {
        if(!newCompany.getText().isBlank()) {
            companyError.setText("");
            accountController.editUser("companyInfo", newCompany.getText());
            gridPane.getChildren().remove(newCompany);
            company.setText(newCompany.getText());
            editCompany.setOnMouseClicked(e -> editCompany());
            editCompany.setText("edit");
        } else {
            companyError.setText("Your Company is Required!");
        }
    }

    public void changePassword() {
        if(newPassword == null) {
            newPassword = new TextField();
            gridPane.add(newPassword, 1, 7);
        }
        passwordLabel.setText("Enter new password:");
        changePassword.setDisable(true);
        if(savePassword == null)
            changePassword = new Button();
        changePassword.setText("save");
        changePassword.setOnMouseClicked(e -> savePassword());
    }

    private void savePassword() {

    }
}
