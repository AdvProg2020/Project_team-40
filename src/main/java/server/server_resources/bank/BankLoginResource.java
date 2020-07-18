package server.server_resources.bank;

import com.gilecode.yagson.YaGson;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;
import server.controller.menus.BankController;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import static server.server_resources.bank.BankInformation.BANK_PORT;
import static server.server_resources.bank.BankInformation.IP;

public class BankLoginResource extends ServerResource {
    @Post
    public String login(String bankUsername) {
        String password = getQueryValue("bank password");
        String response = null;
        try {
            Socket socket = new Socket(IP, BANK_PORT);
            DataOutputStream outputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            DataInputStream inputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            outputStream.writeUTF("get_token " + bankUsername + " " + password);
            outputStream.flush();
            String bankResponse = inputStream.readUTF();
            System.out.println("token = " + bankResponse);
            if(!bankResponse.equals("invalid username or password")) {
                BankController.getBankController().getUsersTokens().put(getQueryValue("username"), bankResponse);
            }
            socket.close();
            response = bankResponse;
        } catch (Exception e) {
            response = e.getMessage();
        }
        return new YaGson().toJson(response, String.class);
    }
}
