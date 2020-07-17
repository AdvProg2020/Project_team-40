package server.server_resources.bank;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import java.io.*;
import java.net.Socket;

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
          //  banksResponse = handleResponse(inputStream.readUTF(), getQueryValue("username"));
            socket.close();
        } catch (IOException e) {
            banksResponse = e.getMessage();
        }
        return banksResponse;
    }

    /*
    @Put
    public void setBankAccount(String username){

        User.getUserByUsername(username).setBankAccount(Integer.parseInt(getQueryValue("bankAccount")));
    }

     */
}
