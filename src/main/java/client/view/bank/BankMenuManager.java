package client.view.bank;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class BankMenuManager implements Initializable {
    public Pane innerPane;
    public Button exitButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setInnerPane("/layouts/bank_menus/default_bank_menu.fxml");
    }

    public void goToHome() {

    }

    public void goToReceipts() {
    }

    public void goToTransactions() {
    }

    public void viewBalance() {

    }

    public void exitBankAccount() {
        ((Stage)(exitButton.getScene().getWindow())).close();
    }

    private void setInnerPane(String rootLocation) {
        innerPane.getChildren().clear();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource(rootLocation));
        } catch (IOException e) {
            e.printStackTrace();
        }
        innerPane.getChildren().add(root);
    }
}
