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
import java.net.URL;
import java.util.ResourceBundle;

public class PersonalInfo extends AccountMenu implements Initializable {
    private User user = AccountController.getInstance().getThisUser();
    private CustomerAccountController customerAccountController = CustomerAccountController.getInstance();
    private SellerAccountController sellerAccountController = SellerAccountController.getInstance();
    public GridPane gridPane;
    public Label creditLabel;
    public Label credit;
    public Label company;
    public Label companyLabel;
    public Label username;
    public Label firstName;
    public Label lastName;
    public Label email;
    public Label phone;
    public Button editUsername;
    public Button editFirstName;
    public Button editLastName;
    public Button editPhoneNumber;
    public Button editEmail;
    public Button editCompany;
    public TextField newUsername;
    public TextField newFirstName;
    public TextField newLastName;
    public TextField newEmail;
    public TextField newPhoneNumber;
    public TextField newCredit;
    public TextField newCompany;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(user instanceof Customer) {
            creditLabel.setText("Credit: ");
            credit.setText(Double.toString(customerAccountController.getBalance()));
        }
        if(user instanceof Seller) {
            creditLabel.setText("Credit: ");
            credit.setText(Double.toString(sellerAccountController.getBalance()));
            companyLabel.setText("Company: ");
            company.setText(sellerAccountController.getCompanyInfo());
            editCompany = new Button("edit");
            gridPane.add(editCompany, 3, 6);
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
        accountController.editUser("username", newUsername.getText());
        gridPane.getChildren().remove(newUsername);
        username.setText(newUsername.getText());
        newUsername.setText("");
        editUsername.setOnMouseClicked(e -> editUsername());
        editUsername.setText("edit");
    }

    public void editFirstName() {

    }

    public void editLastName() {
    }

    public void editEmail() {
    }

    public void editPhoneNumber() {
    }
}
