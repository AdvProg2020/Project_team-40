package server.server_resources.customer_account_controller;

import com.gilecode.yagson.YaGson;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import server.controller.accounts.CustomerAccountController;
import server.controller.menus.BankController;
import server.model.DiscountCode;
import server.model.Product;
import server.model.log.Log;
import server.model.users.Customer;
import server.model.users.Manager;
import server.model.users.Seller;
import server.model.users.User;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;

import static server.server_resources.bank.BankInformation.BANK_PORT;
import static server.server_resources.bank.BankInformation.IP;

public class PayByBankResource extends ServerResource {
    @Get
    public String payCartByBank() throws ResourceException {
        try {
            String token = loginBankAccount(getQueryValue("bank username"), getQueryValue("bank password"));
            DiscountCode discountCode = DiscountCode.getDiscountCodeByCode(getQueryValue("discount code"));
            int discountPercentage = 0;
            if (discountCode != null)
                discountPercentage = discountCode.getPercentage();
            Customer customer = (Customer)User.getUserByUsername(getQueryValue("username"));
            HashMap<String, Integer> cart = customer.getCart();
            int bankAccountId = customer.getBankAccount();
            handleTransactions(token, cart, bankAccountId, discountPercentage);
            Log log = CustomerAccountController.getInstance().makePayment(getQueryValue("username"),
                    getQueryValue("address"), getQueryValue("discount code"),
                    Double.parseDouble(getQueryValue("amount")),
                    Double.parseDouble(getQueryValue("price without discount")), false);
            return new YaGson().toJson(log, Log.class);
        } catch (Exception e) {
            System.err.println(e.getMessage());
             throw new ResourceException(403 , e);
        }
    }

    private void handleTransactions(String token, HashMap<String, Integer> cart, int sourceID, int discountPercentage) throws Exception {
        for (String productID : cart.keySet()) {
            Product product = Product.getProductById(productID);
            int price = (int) product.getPrice();
            int totalPrice = price * cart.get(productID);
            Seller seller =(Seller) (User.getUserByUsername(product.getSellerUsername()));
                int receiptID = createReceipt(token, Integer.toString((int) (totalPrice * (1 - 0.001 * discountPercentage))), sourceID);
                pay(receiptID);
                handleSellerWalletCharging(seller, totalPrice);

        }
    }

    private void handleSellerWalletCharging(Seller seller,int totalPrice) {
        double wage = Manager.getWage();
        seller.changeWalletCredit(totalPrice * (1 - 0.01*wage));
    }


    private void pay(int receiptId) throws Exception {
        String response = sendMessageToBank("pay " + receiptId);
        if (!response.equals("done successfully"))
            throw new Exception("Receipt payment failed");
    }

    private int createReceipt(String token, String amount, int source) throws Exception {
        int intAmount = (int) Double.parseDouble(amount);
        String response = sendMessageToBank("create_receipt " + token + " move " + intAmount + " " + source + " " +
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
