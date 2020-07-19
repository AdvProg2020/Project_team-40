package server.server_resources.bank;

import com.gilecode.yagson.YaGson;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import server.controller.menus.BankController;

import java.io.*;
import java.net.Socket;

import static server.server_resources.bank.BankInformation.BANK_PORT;
import static server.server_resources.bank.BankInformation.IP;

public class CreateReceiptResources extends ServerResource {
    @Get
    public String createReceipt() {
        String bankResponse;
        try {
            Socket socket = new Socket(IP, BANK_PORT);
            DataOutputStream outputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            String message = "create_receipt " + BankController.getBankController().getUsersTokens().
                    get(getQueryValue("username")) + " " +  getQueryValue("receipt type") + " " +
                    getQueryValue("money") + " " + getQueryValue("source") + " " +
                    getQueryValue("destination") + " " + getQueryValue("description");
            outputStream.writeUTF(message.trim());
            outputStream.flush();
            DataInputStream inputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            bankResponse = inputStream.readUTF();
            socket.close();
        } catch (IOException e) {
            bankResponse =  e.getMessage();
        }
        return new YaGson().toJson(bankResponse, String.class);
    }
}
