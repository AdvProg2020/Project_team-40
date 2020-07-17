package server;

import org.restlet.data.ChallengeScheme;
import org.restlet.resource.ServerResource;
import org.restlet.security.ChallengeAuthenticator;
import org.restlet.security.MapVerifier;
import server.model.users.*;
import server.server_resources.RoleAccessibility;

import java.util.HashMap;

public class ServerAuthenticator {
    private  MapVerifier managerVerifier;
    private MapVerifier customerVerifier;
    private MapVerifier sellerVerifier;
    private MapVerifier supportVerifier;
    private static ServerAuthenticator serverAuthenticator = new ServerAuthenticator();

    private ServerAuthenticator(){}

    public static ServerAuthenticator getInstance() {
        return serverAuthenticator;
    }

    public  void initVerifier(){
        managerVerifier = new MapVerifier();
        sellerVerifier = new MapVerifier();
        customerVerifier = new MapVerifier();
        supportVerifier = new MapVerifier();
        HashMap<String, User> allUsers = User.getAllUsers();
        for (String username : allUsers.keySet()) {
            User user = allUsers.get(username);
            if (user instanceof Manager)
                managerVerifier.getLocalSecrets().put(username, user.getPassword().toCharArray());
            else if (user instanceof Customer)
                customerVerifier.getLocalSecrets().put(username, user.getPassword().toCharArray());
            else if (user instanceof Seller)
                sellerVerifier.getLocalSecrets().put(username, user.getPassword().toCharArray());
            else if (user instanceof Support)
                supportVerifier.getLocalSecrets().put(username, user.getPassword().toCharArray());
        }
    }

    public  void addToManagerVerifier(String username, String password){
        managerVerifier.getLocalSecrets().put(username, password.toCharArray());
    }

    public  void addToSellerVerifier(String username, String password){
        sellerVerifier.getLocalSecrets().put(username, password.toCharArray());
    }

    public  void addToCustomerVerifier(String username, String password){
        customerVerifier.getLocalSecrets().put(username, password.toCharArray());
    }

    public void removeIdentifier(String username){
        managerVerifier.getLocalSecrets().remove(username);
        sellerVerifier.getLocalSecrets().remove(username);
        customerVerifier.getLocalSecrets().remove(username);
    }

    public  <T extends ServerResource>  ChallengeAuthenticator getNewGuard(Class<T> serverResource, RoleAccessibility role){
        ChallengeAuthenticator guard = new ChallengeAuthenticator(null, ChallengeScheme.HTTP_BASIC, "realm");
        if (role.equals(RoleAccessibility.MANAGER))
            guard.setVerifier(managerVerifier);
        else if (role.equals(RoleAccessibility.CUSTOMER))
            guard.setVerifier(customerVerifier);
        else if (role.equals(RoleAccessibility.SELLER))
            guard.setVerifier(sellerVerifier);
        else if (role.equals(RoleAccessibility.SUPPORT))
            guard.setVerifier(supportVerifier);
        guard.setNext(serverResource);
        return guard;
    }
}
