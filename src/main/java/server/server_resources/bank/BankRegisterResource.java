package server.server_resources.bank;

import client.controller.Client;
import client.controller.RequestHandler;
import client.view.ValidInput;
import com.gilecode.yagson.YaGson;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import server.model.users.User;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;

import static server.server_resources.bank.BankInformation.BANK_PORT;
import static server.server_resources.bank.BankInformation.IP;

public class BankRegisterResource extends ServerResource {

    @Get
    public String registerBankAccount() {
        String banksResponse = null;
        String rawBankResponse = null;
        try {
            Socket socket = new Socket(IP, BANK_PORT);
            DataOutputStream outputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));;
            outputStream.writeUTF("create_account " + getQueryValue("first name") + " " +
                    getQueryValue("last name") + " " + getQueryValue("bank username") + " " +
                    getQueryValue("bank password") + " " + getQueryValue("repeat password"));
            outputStream.flush();
            DataInputStream inputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            rawBankResponse = inputStream.readUTF();
            banksResponse = handleResponse(rawBankResponse, getQueryValue("username"));
            socket.close();
        } catch (IOException e) {
            banksResponse = e.getMessage();
        } catch (NumberFormatException e) {
            banksResponse = rawBankResponse;
        }
        return new YaGson().toJson(banksResponse, String.class);
    }

    private String handleResponse(String response, String username) throws NumberFormatException  {
        User.getUserByUsername(username).setBankAccount(Integer.parseInt(response));
        return response;
    }

    /*
    @Put
    public void setBankAccount(String username){

        User.getUserByUsername(username).setBankAccount(Integer.parseInt(getQueryValue("bankAccount")));
    }

     */
}
