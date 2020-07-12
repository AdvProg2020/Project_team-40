package client.view;

public class ThisUser {
    private static String username;

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
}
