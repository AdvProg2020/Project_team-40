package server.server_resources.bank;

import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.com.google.gson.reflect.TypeToken;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;
import server.model.Receipt;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;

import static server.server_resources.bank.BankInformation.BANK_PORT;
import static server.server_resources.bank.BankInformation.IP;

public class ReceiptsResources extends ServerResource {

    @Post
    public String getUsersReceipts(String username) {
        HashMap<Integer, Receipt> usersReceipts = Receipt.getUsersReceipts(username);
        return new YaGson().toJson(usersReceipts, new TypeToken<HashMap<Integer, Receipt>>(){}.getType());
    }

    @Get
    public String payReceipt() {
        String bankResponse;
        try {
            Socket socket = new Socket(IP, BANK_PORT);
            DataOutputStream outputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            outputStream.writeUTF("pay " + getQueryValue("receipt ID"));
            outputStream.flush();
            DataInputStream inputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            bankResponse = inputStream.readUTF();
            if(bankResponse.equals("done successfully"))
                Receipt.getReceipts().get(getQueryValue("receipt ID")).setPaid(true);
        } catch (IOException e) {
            bankResponse = e.getMessage();
        }
        return bankResponse;
    }
}
