package server.controller.menus;

import com.gilecode.yagson.YaGson;
import exceptions.DataException;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import static server.server_resources.bank.BankInformation.BANK_PORT;
import static server.server_resources.bank.BankInformation.IP;

public class BankController implements Serializable{
    private static BankController bankController = new BankController();
    private static final String PATH = "src/main/resources/";
    private static String shopBankAccountToken;
    private static String managerUsername;
    private static String managerPassword;


    private static String managerBankId;

    //Key is a users username and value is his token:
    private HashMap<String, String> usersTokens = new HashMap<>();

    private BankController() {}
    public static void setManagerUsername(String managerUsername) {
        BankController.managerUsername = managerUsername;
    }

    public static void setManagerPassword(String managerPassword) {
        BankController.managerPassword = managerPassword;
    }

    public static BankController getBankController() {
        return bankController;
    }

    public static String getShopBankAccountToken() {
        return shopBankAccountToken;
    }

    public static void setShopBankAccountToken() {

        try {
            Socket socket = new Socket(IP, BANK_PORT);
            DataOutputStream outputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            DataInputStream inputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            outputStream.writeUTF("get_token " + managerUsername + " " + managerPassword);
            outputStream.flush();
            shopBankAccountToken = inputStream.readUTF();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setManagerBankId(String managerBankId) {
        BankController.managerBankId = managerBankId;
    }

    public static String getManagerBankId() {
        return managerBankId;
    }

    public HashMap<String, String> getUsersTokens() {
        return usersTokens;
    }

    public static void saveData() throws DataException{
        try {
            ArrayList<String> data = new ArrayList<>();
            data.add(shopBankAccountToken);
            data.add(managerUsername);
            data.add(managerPassword);
            String dataJson = new YaGson().toJson(data, ArrayList.class);
            FileOutputStream fileOutputStream = new FileOutputStream(PATH + "BankController");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(dataJson);
            fileOutputStream.close();
            objectOutputStream.close();
        }catch(FileNotFoundException e){
            System.out.println(e.getMessage());
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    public static void loadData() throws DataException{
        try {
            FileInputStream fileInputStream = new FileInputStream(PATH + "BankController");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            String dataJson = (String) objectInputStream.readObject();
            fileInputStream.close();
            objectInputStream.close();
            new File(PATH + "BankController").delete();
            ArrayList<String> data = new YaGson().fromJson(dataJson, ArrayList.class);
            shopBankAccountToken = data.get(0);
            managerUsername = data.get(1);
            managerPassword = data.get(2);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
