package server;

import org.restlet.Component;
import org.restlet.data.Protocol;
import server.server_resources.manager_account_controller.CreateManagerHandler;

public class MainServer extends Component {
    private final int PORT = 8080;
    public static void main(String[] args) throws Exception {
        new MainServer().start();
    }

    public MainServer() {
        getServers().add(Protocol.HTTP, PORT);
        getDefaultHost().attach("/accounts/manager_account_controller/create_manager/", CreateManagerHandler.class);
    }
}
