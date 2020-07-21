package client.view.account_menus.customer_view;

import client.controller.RequestHandler;
import client.view.MenuManager;
import com.gilecode.yagson.com.google.gson.reflect.TypeToken;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class SupportManager extends MenuManager implements Initializable{

    public VBox content;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        HashMap<String, String> requestQueries = new HashMap<>();
        ArrayList<String> supports = RequestHandler.get("/chat/supports/", requestQueries, true, new TypeToken<ArrayList<String>>(){}.getType());
        for(String support : supports) {
            SupportItemManager.setLast(support);
            Node node = null;
            try {
                node = FXMLLoader.load(getClass().getResource("/layouts/customer_menus/support_item.fxml"));

            } catch(IOException e) {
                e.printStackTrace();
            }
            content.getChildren().add(node);
        }
    }
}
