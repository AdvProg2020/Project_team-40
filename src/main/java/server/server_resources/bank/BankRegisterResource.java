package server.server_resources.bank;

import com.gilecode.yagson.YaGson;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import server.controller.menus.BankController;
import server.model.users.Manager;
import server.model.users.User;

import java.io.*;
import java.net.Socket;

import static server.server_resources.bank.BankInformation.BANK_PORT;
import static server.server_resources.bank.BankInformation.IP;

public class BankRegisterResource extends ServerResource {

    @Get
    public String registerBankAccount() {
        String banksResponse = null;
        String rawBankResponse = null;
        try {
            Socket socket = new Socket(IP, BANK_PORT);
            String bankUsername = getQueryValue("bank username");
            DataOutputStream outputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            outputStream.writeUTF("create_account " + getQueryValue("first name") + " " +
                    getQueryValue("last name") + " " + bankUsername + " " +
                    getQueryValue("bank password") + " " + getQueryValue("repeat password"));
            outputStream.flush();
            DataInputStream inputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            rawBankResponse = inputStream.readUTF();
            banksResponse = handleResponse(rawBankResponse, getQueryValue("username"),
                    bankUsername, getQueryValue("bank password"));
            socket.close();
        } catch (IOException e) {
            banksResponse = e.getMessage();
        } catch (NumberFormatException e) {
            banksResponse = rawBankResponse;
        }
        return new YaGson().toJson(banksResponse, String.class);
    }

    private String handleResponse(String response, String username, String bankUsername, String bankPassword) throws NumberFormatException  {
        User user = User.getUserByUsername(username);
        user.setBankAccount(Integer.parseInt(response));
        user.setBankUsername(bankUsername);
        if (user instanceof Manager){
            BankController.setManagerUsername(bankUsername);
            BankController.setManagerPassword(bankPassword);
        }
        return response;
    }
}
