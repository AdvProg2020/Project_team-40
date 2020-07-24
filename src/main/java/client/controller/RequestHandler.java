package client.controller;

import com.gilecode.yagson.YaGson;
import org.restlet.data.ChallengeScheme;
import org.restlet.resource.ClientResource;

import java.lang.reflect.Type;
import java.util.HashMap;

public class RequestHandler {
    private static  String endpoint;

    private static ClientResource clientResource;
    private static YaGson mapper;
    static {
        mapper = new YaGson();
    }


    public static ClientResource getClientResource() {
        return clientResource;
    }

    public static <T> T get(String path, HashMap<String, String> queries, boolean mustBeLoggedIn, Type outputType) {
        clientResource = new ClientResource(endpoint + path);
        if (mustBeLoggedIn) {
            Client client = Client.getInstance();
            clientResource.setChallengeResponse(ChallengeScheme.HTTP_BASIC, client.getUsername(), client.getPassword());
        }
        for (String key : queries.keySet()) {
            clientResource.setQueryValue(key, queries.get(key));
        }
        if (outputType == null) {
            clientResource.get();
            return null;
        }
        String response = clientResource.get(String.class);
        return mapper.fromJson(response, outputType);
    }

    public static <T> T put(String path, String entity, HashMap<String, String> queries, boolean mustBeLoggedIn, Type outputType) {
        clientResource = new ClientResource(endpoint + path);
        if (mustBeLoggedIn) {
            Client client = Client.getInstance();
            clientResource.setChallengeResponse(ChallengeScheme.HTTP_BASIC, client.getUsername(), client.getPassword());
        }
        for (String key : queries.keySet()) {
            clientResource.setQueryValue(key, queries.get(key));
        }
        if (outputType == null) {
            clientResource.put(entity);
            return null;
        }
        String response = clientResource.put(entity, String.class);
        return mapper.fromJson(response, outputType);
    }

    public static <T> T post(String path, String entity, HashMap<String, String> queries, boolean mustBeLoggedIn, Type outputType) {
        clientResource = new ClientResource(endpoint + path);
        if (mustBeLoggedIn) {
            Client client = Client.getInstance();
            clientResource.setChallengeResponse(ChallengeScheme.HTTP_BASIC, client.getUsername(), client.getPassword());
        }
        for (String key : queries.keySet()) {
            clientResource.setQueryValue(key, queries.get(key));
        }
        if (outputType == null) {
            clientResource.post(entity);
            return null;
        }
        String response = clientResource.post(entity, String.class);
        return mapper.fromJson(response, outputType);
    }

    public static <T> T delete(String path, HashMap<String, String> queries, boolean mustBeLoggedIn, Type outputType) {
        clientResource = new ClientResource(endpoint + path);
        if (mustBeLoggedIn) {
            Client client = Client.getInstance();
            clientResource.setChallengeResponse(ChallengeScheme.HTTP_BASIC, client.getUsername(), client.getPassword());
        }
        for (String key : queries.keySet()) {
            clientResource.setQueryValue(key, queries.get(key));
        }
        if (outputType == null) {
            clientResource.delete();
            return null;
        }
        String response = clientResource.delete(String.class);
        return mapper.fromJson(response, outputType);
    }

    public static void setEndpoint(String endpoint) {
        RequestHandler.endpoint = endpoint;
    }


}
