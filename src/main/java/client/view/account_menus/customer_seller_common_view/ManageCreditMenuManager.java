package client.view.account_menus.customer_seller_common_view;

import client.controller.Client;
import client.controller.RequestHandler;
import client.view.MenuManager;
import com.gilecode.yagson.com.google.gson.reflect.TypeToken;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.restlet.data.Status;
import org.restlet.resource.ResourceException;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class ManageCreditMenuManager extends MenuManager implements Initializable {
    private static boolean isToWallet;
    public TextField usernameField;
    public PasswordField passwordField;
    public TextField amountField;
    public Label messageLabel;
    private HashMap<String, String> requestQueries;
    private HashMap<String, String> bankAccountInfo;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        requestQueries = new HashMap<>();
        bankAccountInfo = RequestHandler.get("/accounts/manager_account_controller/store_bank_account/",
                requestQueries, false, new TypeToken<HashMap<String, String>>(){}.getType());
        assert bankAccountInfo != null;
    }

    public void moveCredit() {
        if (isToWallet)
            moveCreditToWallet();
        else
            moveCreditFromWallet();
    }

    private void moveCreditFromWallet() {
        double minWalletCredit = Double.parseDouble(bankAccountInfo.get("min balance"));
        requestQueries.put("username", Client.getInstance().getUsername());
        String response = RequestHandler.get("/accounts/seller_customer_common/wallet/", requestQueries, false, String.class);
        assert response != null;
        double walletCredit = Double.parseDouble((response.split("\\s")[1]));
        double amount = Double.parseDouble(amountField.getText());
        if (amount > walletCredit) {
            messageLabel.setText("Your wallet credit is not enough.");
            return;
        }
        if ((walletCredit - amount) < minWalletCredit) {
            messageLabel.setText("Remained credit would be less than the minimum valid credit (" + minWalletCredit + ")");
            return;
        }
        handleTransaction("Move credit to user's bank account.", false);


    }

    private void moveCreditToWallet() {
        handleTransaction( "Move credit to user's wallet.", true);
    }

    private void handleTransaction(String description, boolean doIncrease)
    {
        requestQueries.clear();
        requestQueries.put("username", Client.getInstance().getUsername());
        requestQueries.put("receipt type", "move");
        requestQueries.put("money", amountField.getText());
        if (isToWallet){
            requestQueries.put("source", String.valueOf(Client.getInstance().getBankAccount()));
            requestQueries.put("destination", bankAccountInfo.get("bank id"));
        }
        else {
            requestQueries.put("source", bankAccountInfo.get("bank id"));
            requestQueries.put("destination", String.valueOf(Client.getInstance().getBankAccount()));
        }
        requestQueries.put("description", description);
        try {
            String receiptID = RequestHandler.get("/bank/create_receipt_resources/", requestQueries, true, String.class);
            requestQueries.clear();
            requestQueries.put("receipt ID", receiptID);
            String paymentStatus = RequestHandler.get("/bank/receipts/", requestQueries, true, String.class);
            assert paymentStatus != null;
            if (paymentStatus.equals("done successfully"))
                handleChangeWalletCredit(doIncrease);
            messageLabel.setText(paymentStatus);
        }
        catch (ResourceException e){
            if (e.getStatus().equals(Status.CLIENT_ERROR_UNAUTHORIZED))
                logout();
            else {
                try {
                    messageLabel.setText(RequestHandler.getClientResource().getResponseEntity().getText());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    private void handleChangeWalletCredit(boolean doIncrease) {
        String path = Client.getInstance().getRole().equals("Customer")
                ? "/accounts/customer_account_controller/wallet/"
                : "/accounts/seller_account_controller/wallet/";

        requestQueries.clear();
        if (doIncrease)
            requestQueries.put("amount", amountField.getText());
        else
            requestQueries.put("amount", "-" + amountField.getText());

        RequestHandler.put(path, Client.getInstance().getUsername(), requestQueries, true, null);
    }

    public static void setIsToWallet(boolean isToWallet) {
        ManageCreditMenuManager.isToWallet = isToWallet;
    }
}
