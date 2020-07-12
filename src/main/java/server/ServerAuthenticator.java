package server;

import org.restlet.data.ChallengeScheme;
import org.restlet.resource.ServerResource;
import org.restlet.security.ChallengeAuthenticator;
import org.restlet.security.MapVerifier;
import server.model.users.User;

import java.util.HashMap;

public class ServerAuthenticator {
    private  MapVerifier verifier;
    private static ServerAuthenticator serverAuthenticator = new ServerAuthenticator();

    private ServerAuthenticator(){}

    public static ServerAuthenticator getInstance() {
        return serverAuthenticator;
    }

    public  void initVerifier(){
        verifier = new MapVerifier();
        HashMap<String, User> allUsers = User.getAllUsers();
        for (String username : allUsers.keySet()) {
            verifier.getLocalSecrets().put(username, allUsers.get(username).getPassword().toCharArray());
        }
    }

    public  void addToVerifier(String username, String password){
        verifier.getLocalSecrets().put(username, password.toCharArray());
    }


    public  <T extends ServerResource>  ChallengeAuthenticator getNewGuard(Class<T> serverResource){
        ChallengeAuthenticator guard = new ChallengeAuthenticator(null, ChallengeScheme.HTTP_BASIC, "realm");
        guard.setVerifier(verifier);
        guard.setNext(serverResource);
        return guard;
    }
}
