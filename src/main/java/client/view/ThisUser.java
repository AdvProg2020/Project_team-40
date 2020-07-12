package client.view;

public class ThisUser {
    private static String userId;

    public static String getUserId() {
        return userId;
    }

    public static void setUserId(String userId) {
        ThisUser.userId = userId;
    }
}
