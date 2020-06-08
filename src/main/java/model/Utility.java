package model;

public class Utility {

    public static String generateId() {
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";
        StringBuilder id = new StringBuilder();
        for(int i = 0; i < 20; i++) {
            char c = AlphaNumericString.charAt((int) (AlphaNumericString.length() * Math.random()));
            id.append(c);
        }
        return id.toString();
    }

}
