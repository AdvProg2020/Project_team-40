package server.server_resources.bank;

import com.gilecode.yagson.YaGson;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import server.controller.menus.BankController;
import server.model.Receipt;
import server.model.enumerations.ReceiptTypes;

import java.io.*;
import java.net.Socket;

import static server.server_resources.bank.BankInformation.BANK_PORT;
import static server.server_resources.bank.BankInformation.IP;

public class CreateReceiptResources extends ServerResource {
    @Get
    public String createReceipt() {
        String bankResponse = null;
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
            new Receipt(Integer.parseInt(bankResponse), getQueryValue("username"), getQueryValue("description"),
                    Integer.parseInt(getQueryValue("money")), Integer.parseInt(getQueryValue("source")),
                    Integer.parseInt(getQueryValue("destination")), getReceiptType(getQueryValue("receipt type")));
        } catch (IOException e) {
            bankResponse =  e.getMessage();
        } catch (NumberFormatException ignored) {}
        return new YaGson().toJson(bankResponse, String.class);
    }

    private ReceiptTypes getReceiptType(String receipt_type) {
        if(receipt_type.equals("deposit"))
            return ReceiptTypes.DEPOSIT;
        if(receipt_type.equals("move"))
            return ReceiptTypes.MOVE;
        return ReceiptTypes.WITHDRAW;
    }
}
