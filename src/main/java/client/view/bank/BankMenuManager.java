package client.view.bank;

import client.view.MenuManager;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class BankMenuManager extends MenuManager {
    public Button exitButton;

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
}
