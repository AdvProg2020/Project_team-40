package server.controller.menus;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.HashMap;

import static server.server_resources.bank.BankInformation.BANK_PORT;
import static server.server_resources.bank.BankInformation.IP;

public class BankController {
    private static BankController bankController = new BankController();
    private static String shopBankAccountToken;
    private static String managerUsername;
    private static String managerPassword;

    //Key is a users username and value is his token:
    private HashMap<String, String> usersTokens = new HashMap<>();

    private BankController() {}
    public static void setManagerUsername(String managerUsername) {
        BankController.managerUsername = managerUsername;
    }

    public static void setManagerPassword(String managerPassword) {
        BankController.managerPassword = managerPassword;
    }

    public static BankController getBankController() {
        return bankController;
    }

    public static String getShopBankAccountToken() {
        return shopBankAccountToken;
    }

    public static void setShopBankAccountToken() {

        try {
            Socket socket = new Socket(IP, BANK_PORT);
            DataOutputStream outputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            DataInputStream inputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            outputStream.writeUTF("get_token " + managerUsername + " " + managerPassword);
            outputStream.flush();
            String bankResponse = inputStream.readUTF();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public HashMap<String, String> getUsersTokens() {
        return usersTokens;
    }
}
