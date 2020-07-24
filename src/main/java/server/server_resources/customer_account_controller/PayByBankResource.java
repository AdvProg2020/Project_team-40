package server.server_resources.customer_account_controller;

import com.gilecode.yagson.YaGson;
import javafx.scene.chart.PieChart;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import server.controller.accounts.CustomerAccountController;
import server.controller.menus.BankController;
import server.model.log.Log;
import server.model.users.Customer;
import server.model.users.Manager;
import server.model.users.User;

import java.io.*;
import java.net.Socket;

import static server.server_resources.bank.BankInformation.BANK_PORT;
import static server.server_resources.bank.BankInformation.IP;

public class PayByBankResource extends ServerResource {
    @Get
    public String payCartByBank() throws ResourceException {
        try {
            String token = loginBankAccount(getQueryValue("bank username"), getQueryValue("bank password"));
            int bankAccountId = (User.getUserByUsername(getQueryValue("username"))).getBankAccount();
            int receiptId = createReceipt(token, getQueryValue("amount"), bankAccountId);
            Log log = pay(receiptId);
            return new YaGson().toJson(log, Log.class);
        } catch (Exception e) {
            e.printStackTrace();
             throw new ResourceException(e);
        }
    }

    private server.model.log.Log pay(int receiptId) throws Exception {
        String response = sendMessageToBank("pay " + receiptId);
        if(response.equals("done successfully")) {
            return CustomerAccountController.getInstance().makePayment(getQueryValue("username"),
                    getQueryValue("address"), getQueryValue("discount code"),
                    Double.parseDouble(getQueryValue("amount")),
                    Double.parseDouble(getQueryValue("price without discount")));
        } else {
            throw new Exception(response);
        }
    }

    private int createReceipt(String token, String amount, int bankAccountId) throws Exception {
        int intAmount = (int) Double.parseDouble(amount);
        String response = sendMessageToBank("create_receipt " + token + " move " + amount + " " + bankAccountId + " " +
                BankController.getManagerBankId());
        try {
            return Integer.parseInt(response);
        } catch (NumberFormatException e) {
            throw new Exception(response);
        }
    }

    private String loginBankAccount(String bankUsername, String bankPassword) throws Exception {
        String response = sendMessageToBank("get_token " + bankUsername + " " + bankPassword);
        if(response.equals("invalid username or password"))
            throw  new Exception(response);
        return response;
    }

    private String sendMessageToBank(String message) {
        String response;
        try {
            Socket socket = new Socket(IP, BANK_PORT);
            DataOutputStream outputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            outputStream.writeUTF(message);
            outputStream.flush();
            DataInputStream inputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            response = inputStream.readUTF();
            socket.close();
        } catch (IOException e) {
            response = e.getMessage();
        }
        return response;
    }
}
