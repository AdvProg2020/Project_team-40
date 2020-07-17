package server.server_resources.bank;

import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

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
        try {
            Socket socket = new Socket(IP, BANK_PORT);
            DataOutputStream outputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            DataInputStream inputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            outputStream.writeUTF("get_token " + bankUsername + " " + password);
            outputStream.flush();
            String bankResponse = inputStream.readUTF();
            if(!bankResponse.equals("invalid username or password")) {

            }
            socket.close();
        } catch (Exception e) {
            return e.getMessage();
        }
        return null;
    }
}
