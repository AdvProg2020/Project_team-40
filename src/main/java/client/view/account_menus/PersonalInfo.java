package client.view.account_menus;

import client.controller.Client;
import client.controller.RequestHandler;
import client.view.MenuManager;
import client.view.ValidInput;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import org.restlet.data.Status;
import org.restlet.resource.ResourceException;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class PersonalInfo extends MenuManager implements Initializable {

    public GridPane gridPane;
    public Label companyLabel;
    public Label passwordLabel;

    //Labels to show info:
    public Label username;
    public Label firstName;
    public Label lastName;
    public Label email;
    public Label phone;
    public Label company;

    //Labels to show error:
    public Label usernameError;
    public Label firstNameError;
    public Label lastNameError;
    public Label emailError;
    public Label phoneNumberError;
    public Label companyError;

    //Buttons to edit and save changes:
    public Button editFirstName;
    public Button editLastName;
    public Button editPhoneNumber;
    public Button editEmail;
    public Button editCompany;
    public Button changePassword;
    public Button savePassword;

    //Fields to enter new information:
    public TextField newFirstName;
    public TextField newLastName;
    public TextField newEmail;
    public TextField newPhoneNumber;
    public TextField newCompany;
    public TextField newPassword;
    private HashMap<String, String> requestQueries;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        requestQueries = new HashMap<>();
        if(Client.getInstance().getRole().equals("Seller")) {
            companyLabel.setText("Company: ");
            HashMap<String, String> queries = new HashMap<>();
            queries.put("username", Client.getInstance().getUsername());
            try {
                company.setText(RequestHandler.get("/accounts/seller_account_controller/company_info/", queries, true, String.class));
            }catch (ResourceException e){
                if (e.getStatus().equals(Status.CLIENT_ERROR_UNAUTHORIZED))
                    logout();
            }
            editCompany = new Button("edit");
            gridPane.add(editCompany, 3, 6);
            editCompany.setOnMouseClicked(e -> editCompany());
        }
        username.setText(Client.getInstance().getUsername());
        firstName.setText(Client.getInstance().getFirstName());
        lastName.setText(Client.getInstance().getLastName());
        email.setText(Client.getInstance().getEmail());
        phone.setText(Client.getInstance().getPhoneNo());
    }

    public void editFirstName() {
        editFirstName.setText("save");
        editFirstName.setOnMouseClicked(e -> saveFirstName());
        if(newFirstName == null)
            newFirstName = new TextField();
        gridPane.add(newFirstName, 1, 1);
        newFirstName.setText(firstName.getText());
    }

    public void saveFirstName() {
        try {
            if (!newFirstName.getText().isBlank()) {
                if (ValidInput.NAME.getStringMatcher(newFirstName.getText()).matches()) {
                    firstNameError.setText("");
                    requestQueries.clear();
                    requestQueries.put("field", "firstName");
                    requestQueries.put("newAmount", newFirstName.getText());
                    RequestHandler.put("/accounts/user/", Client.getInstance().getUsername(), requestQueries, true, null);
                    Client.getInstance().setFirstName(newFirstName.getText());
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
        }catch (ResourceException e){
            if (e.getStatus().equals(Status.CLIENT_ERROR_UNAUTHORIZED))
                logout();
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

    public void saveLastName() {
        try {
            if (!newLastName.getText().isBlank()) {
                if (ValidInput.NAME.getStringMatcher(newLastName.getText()).matches()) {
                    lastNameError.setText("");
                    requestQueries.clear();
                    requestQueries.put("field", "lastName");
                    requestQueries.put("newAmount", newLastName.getText());
                    RequestHandler.put("/accounts/user/", Client.getInstance().getUsername(), requestQueries, true, null);
                    Client.getInstance().setLastName(newLastName.getText());
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
        }catch (ResourceException e){
            if (e.getStatus().equals(Status.CLIENT_ERROR_UNAUTHORIZED))
                logout();
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

    public void saveEmail() {
        try {

            if (!newEmail.getText().isBlank()) {
                if (ValidInput.EMAIL_ADDRESS.getStringMatcher(newEmail.getText()).matches()) {
                    emailError.setText("");
                    requestQueries.clear();
                    requestQueries.put("field", "email");
                    requestQueries.put("newAmount", newEmail.getText());
                    RequestHandler.put("/accounts/user/", Client.getInstance().getUsername(), requestQueries, true, null);
                    Client.getInstance().setEmail(newEmail.getText());
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
        }catch (ResourceException e)
        {
            if (e.getStatus().equals(Status.CLIENT_ERROR_UNAUTHORIZED))
                logout();
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

    public void savePhoneNumber() {
        try {
            if (!newPhoneNumber.getText().isBlank()) {
                if (ValidInput.PHONE_NUMBER.getStringMatcher(newPhoneNumber.getText()).matches()) {
                    phoneNumberError.setText("");
                    requestQueries.clear();
                    requestQueries.put("field", "phoneNumber");
                    requestQueries.put("newAmount", newPhoneNumber.getText());
                    RequestHandler.put("/accounts/user/", Client.getInstance().getUsername(), requestQueries, true, null);
                    Client.getInstance().setPhoneNo(newPhoneNumber.getText());
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
        }catch (ResourceException e){
            if (e.getStatus().equals(Status.CLIENT_ERROR_UNAUTHORIZED))
                logout();
        }
    }

    public void editCompany() {
        editCompany.setText("save");
        editCompany.setOnMouseClicked(e -> saveCompany());
        if(newCompany == null)
            newCompany = new TextField();
        gridPane.add(newCompany, 1, 6);
        newCompany.setText(company.getText());
    }

    public void saveCompany() {
        try {
            if (!newCompany.getText().isBlank()) {
                companyError.setText("");
                requestQueries.clear();
                requestQueries.put("field", "companyInfo");
                requestQueries.put("newAmount", newCompany.getText());
                RequestHandler.put("/accounts/user/", Client.getInstance().getUsername(), requestQueries, true, null);
                Client.getInstance().setCompany(newCompany.getText());
                gridPane.getChildren().remove(newCompany);
                company.setText(newCompany.getText());
                editCompany.setOnMouseClicked(e -> editCompany());
                editCompany.setText("edit");
            } else {
                companyError.setText("Your Company is Required!");
            }
        }catch (ResourceException e)
        {
            if (e.getStatus().equals(Status.CLIENT_ERROR_UNAUTHORIZED))
                logout();
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
            savePassword = new Button();
        savePassword.setText("save");
        savePassword.setOnMouseClicked(e -> savePassword());
        gridPane.add(savePassword, 3, 7);
    }

    public void savePassword() {
        changePassword.setDisable(false);
        gridPane.getChildren().remove(savePassword);
        gridPane.getChildren().remove(newPassword);
        requestQueries.clear();
        requestQueries.put("field", "password");
        requestQueries.put("newAmount", newPassword.getText());
        RequestHandler.put("/accounts/user/", Client.getInstance().getUsername(), requestQueries, true, null);
        Client.getInstance().setPassword(newPassword.getText());
        newPassword.setText("");
    }
}
