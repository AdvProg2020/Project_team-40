package client.view.account_menus.customer_view;

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

public class ChatManager extends MenuManager implements Initializable{
    public VBox content;
    private ArrayList<Chat> chats;
    private HashMap<String, String> requestQueries;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        getChats();
    }

    private void getChats(){
        chats = new ArrayList<>();
        requestQueries = new HashMap<>();
        chats = RequestHandler.get("/chat/chat/", requestQueries, false, new TypeToken<ArrayList<Chat>>(){}.getType());

        for(Chat chat : chats) {
            ChatItemManager.setChat(chat);
            Node node = null;
            try {
                node = FXMLLoader.load(getClass().getResource("/layouts/customer_menus/chat_item.fxml"));
            } catch(IOException e) {
                e.printStackTrace();
            }
            content.getChildren().add(node);
        }
    }

    public void add() {
        //TODO : add chat room
    }
}
