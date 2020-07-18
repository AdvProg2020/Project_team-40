package server.server_resources.bank;

import com.gilecode.yagson.YaGson;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import server.controller.menus.BankController;

import java.io.*;
import java.net.Socket;

import static server.server_resources.bank.BankInformation.BANK_PORT;
import static server.server_resources.bank.BankInformation.IP;

public class BankBalance extends ServerResource {
    @Get
    public String getBalance() {
        String token = BankController.getBankController().getUsersTokens().get(getQueryValue("username"));
        try {
            Socket socket = new Socket(IP, BANK_PORT);
            DataOutputStream outputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            outputStream.writeUTF("get_balance " + token);
            outputStream.flush();
            DataInputStream inputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            String bankResponse = inputStream.readUTF();
            socket.close();
            return new YaGson().toJson(bankResponse, String.class);
        } catch (IOException e) {
            return new YaGson().toJson(e.getMessage(), String.class);
        }
    }
}
