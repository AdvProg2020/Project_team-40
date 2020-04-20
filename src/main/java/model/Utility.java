package model;

public class Utility {

    public static String generateId() {
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";
        String id = new String();
        for(int i = 0; i < 20; i++) {
            char c = AlphaNumericString.charAt((int) (AlphaNumericString.length() * Math.random()));
            id = id + c;
        }
        return id;
    }

}
