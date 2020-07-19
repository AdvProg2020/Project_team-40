package server.server_resources.bank;

import com.gilecode.yagson.YaGson;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;
import server.controller.menus.BankController;
import server.model.users.User;

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
        String username = getQueryValue("username");
        if(!User.getUserByUsername(username).getBankUsername().equals(bankUsername))
            return new YaGson().toJson("You must enter your own account!", String.class);
        String password = getQueryValue("bank password");
        String response = null;
        try {
            Socket socket = new Socket(IP, BANK_PORT);
            DataOutputStream outputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            DataInputStream inputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            outputStream.writeUTF("get_token " + bankUsername + " " + password);
            outputStream.flush();
            String bankResponse = inputStream.readUTF();
            if(!bankResponse.equals("invalid username or password")) {
                BankController.getBankController().getUsersTokens().put(username, bankResponse);
            }
            socket.close();
            response = bankResponse;
        } catch (Exception e) {
            response = e.getMessage();
        }
        return new YaGson().toJson(response, String.class);
    }
}
