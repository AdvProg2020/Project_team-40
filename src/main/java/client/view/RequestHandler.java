package client.view;

import org.restlet.resource.ClientResource;

import java.util.HashMap;

public class RequestHandler {
    private static String authToken;
    private static boolean isLoggedIn;
    private static final String ENDPOINT = "http://localhost:8080";
    //TODO: Configure the endpoint from vmf file

    public static void setAuthToken(String token){
        authToken = token;
    }

    public static void setIsLoggedIn(boolean isLoggedIn) {
        RequestHandler.isLoggedIn = isLoggedIn;
    }

    public static <T> Object get(String path, HashMap<String, String> queries, boolean doesSendToken, Class<T> outputClass) {
        ClientResource clientResource = new ClientResource(ENDPOINT + path);
        if (doesSendToken)
            clientResource.setQueryValue("auth-token", authToken);
        for (String key : queries.keySet()) {
            clientResource.setQueryValue(key, queries.get(key));
        }
        return clientResource.get(outputClass);
    }
}
