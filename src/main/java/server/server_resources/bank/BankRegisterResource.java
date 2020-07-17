package server.server_resources.bank;

import client.controller.Client;
import client.controller.RequestHandler;
import client.view.ValidInput;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;

import static server.server_resources.bank.BankInformation.BANK_PORT;
import static server.server_resources.bank.BankInformation.IP;

public class BankRegisterResource extends ServerResource {

    @Get
    public String registerBankAccount() {
        String banksResponse = null;
        try {
            Socket socket = new Socket(IP, BANK_PORT);
            DataOutputStream outputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));;
            outputStream.writeUTF("create_account " + getQueryValue("first name") + " " +
                    getQueryValue("last name") + " " + getQueryValue("bank username") + " " + "bank password"
                    + " " + getQueryValue("repeat password"));
            outputStream.flush();
            DataInputStream inputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            banksResponse = handleResponse(inputStream.readUTF(), getQueryValue("username"));
            socket.close();
        } catch (IOException e) {
            banksResponse = e.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return banksResponse;
    }

    private String handleResponse(String response, String username) throws Exception {
        if(!ValidInput.INTEGER.getStringMatcher(response).matches())
            throw new Exception(response);
        //TODO
        return null;
    }

    /*
    @Put
    public void setBankAccount(String username){

        User.getUserByUsername(username).setBankAccount(Integer.parseInt(getQueryValue("bankAccount")));
    }

     */
}
