package server.server_resources.customer_account_controller;

import com.gilecode.yagson.YaGson;
import javafx.scene.chart.PieChart;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import java.io.*;
import java.net.Socket;

import static server.server_resources.bank.BankInformation.BANK_PORT;
import static server.server_resources.bank.BankInformation.IP;

public class PayByBankResource extends ServerResource {
    @Get
    public String payCartByBank() throws ResourceException {
        try {
            String token = loginBankAccount(getQueryValue("bank username"), getQueryValue("bank password"));

        } catch (Exception e) {
             throw new ResourceException(e);
        }
        return null;
    }

    private String loginBankAccount(String bankUsername, String bankPassword) throws Exception {
        String response = sendMessageToBank("get_token " + bankUsername + " " + bankPassword);
        if(response.equals("invalid username or password"))
            throw  new Exception(response);
        return response;
    }

    private String sendMessageToBank(String message) {
        String response;
        try {
            Socket socket = new Socket(IP, BANK_PORT);
            DataOutputStream outputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            outputStream.writeUTF(message);
            outputStream.flush();
            DataInputStream inputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            response = inputStream.readUTF();
            socket.close();
        } catch (IOException e) {
            response = e.getMessage();
        }
        return response;
    }
}
