package server.controller.menus;

import java.util.HashMap;

public class BankController {
    private static BankController bankController = new BankController();

    //Key is a users username and value is his token:
    private HashMap<String, String> usersTokens = new HashMap<>();

    private BankController() {}

    public static BankController getBankController() {
        return bankController;
    }

    public HashMap<String, String> getUsersTokens() {
        return usersTokens;
    }
}
