package client.view.account_menus.support_view;

import client.controller.Client;
import client.controller.RequestHandler;
import client.view.MenuManager;
import com.gilecode.yagson.com.google.gson.reflect.TypeToken;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import server.model.chat.Chat;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class ChatsManager extends MenuManager implements Initializable{

    public VBox content;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        HashMap<String, String> requestQueries = new HashMap<>();
        requestQueries.put("support", Client.getInstance().getUsername());
        ArrayList<Chat> allChats = RequestHandler.get("/chat/support_customers/", requestQueries, true, new TypeToken<ArrayList<Chat>>(){}.getType());
        for(Chat chat : allChats) {
            Node node = null;
            ChatItemManager.setLast(chat);
            try {
                node = FXMLLoader.load(getClass().getResource("/layouts/support_menus/chat_item.fxml"));
            } catch(IOException e) {
                e.printStackTrace();
            }
            content.getChildren().add(node);
        }
    }
}
