package server;

import server.model.Utility;

import java.util.ArrayList;

public class AuthenticationTokenHandler {
    private static ArrayList<String> tokens;
    static {
        tokens = new ArrayList<>();
    }

    public static String getNewToken(){
        String token = Utility.generateId();
        tokens.add(token);
        return token;
    }

    public static void deleteToken(String token){
        tokens.remove(token);
    }

    public static boolean authorize(String token){
        return tokens.contains(token);
    }
}
