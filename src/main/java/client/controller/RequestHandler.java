package client.controller;

import org.restlet.data.ChallengeScheme;
import org.restlet.resource.ClientResource;

import java.util.HashMap;

public class RequestHandler {
    private static final String ENDPOINT = "http://localhost:8080";

    //TODO: Configure the endpoint from vmf file


    public static <T> Object get(String path, HashMap<String, String> queries, boolean mustBeLoggedIn, Class<T> outputClass) {
        ClientResource clientResource = new ClientResource(ENDPOINT + path);
        if (mustBeLoggedIn) {
            Client client = Client.getInstance();
            clientResource.setChallengeResponse(ChallengeScheme.HTTP_BASIC, client.getUsername(), client.getPassword());
        }
        for (String key : queries.keySet()) {
            clientResource.setQueryValue(key, queries.get(key));
        }
        if (outputClass == null) {
            clientResource.get();
            return null;
        }
        return clientResource.get(outputClass);
    }

    public static <T> Object put(String path, Object entity, HashMap<String, String> queries, boolean mustBeLoggedIn, Class<T> outputClass) {
        ClientResource clientResource = new ClientResource(ENDPOINT + path);
        if (mustBeLoggedIn) {
            Client client = Client.getInstance();
            clientResource.setChallengeResponse(ChallengeScheme.HTTP_BASIC, client.getUsername(), client.getPassword());
        }
        for (String key : queries.keySet()) {
            clientResource.setQueryValue(key, queries.get(key));
        }
        if (outputClass == null) {
            clientResource.put(entity);
            return null;
        }
        return clientResource.put(entity, outputClass);
    }

    public static <T> Object post(String path, Object entity, HashMap<String, String> queries, boolean mustBeLoggedIn, Class<T> outputClass) {
        ClientResource clientResource = new ClientResource(ENDPOINT + path);
        if (mustBeLoggedIn) {
            Client client = Client.getInstance();
            clientResource.setChallengeResponse(ChallengeScheme.HTTP_BASIC, client.getUsername(), client.getPassword());
        }
        for (String key : queries.keySet()) {
            clientResource.setQueryValue(key, queries.get(key));
        }
        if (outputClass == null) {
            clientResource.post(entity);
            return null;
        }
        return clientResource.post(entity, outputClass);
    }

    public static <T> Object delete(String path, HashMap<String, String> queries, boolean mustBeLoggedIn, Class<T> outputClass) {
        ClientResource clientResource = new ClientResource(ENDPOINT + path);
        if (mustBeLoggedIn) {
            Client client = Client.getInstance();
            clientResource.setChallengeResponse(ChallengeScheme.HTTP_BASIC, client.getUsername(), client.getPassword());
        }
        for (String key : queries.keySet()) {
            clientResource.setQueryValue(key, queries.get(key));
        }
        if (outputClass == null) {
            clientResource.delete();
            return null;
        }
        return clientResource.delete(outputClass);
    }


}
