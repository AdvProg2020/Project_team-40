package client.view.account_menus;

public class ThisUser {
    private static String username;
    private static String latestToken;

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        ThisUser.username = username;
    }

    public static boolean isUserLoggedIn() {
        return username != null;
    }

    public static void logout() {
        username = null;
    }

    public static void setLatestToken(String latestToken) {
        ThisUser.latestToken = latestToken;
    }
}
