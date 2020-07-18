package client.view.account_menus.customer_view;

import client.view.MenuManager;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import server.model.chat.Chat;

import java.net.URL;
import java.util.ResourceBundle;

public class ChatItemManager extends MenuManager implements Initializable{
    private static Chat chat;
    public Text name;
    public Text id;
    public Text number;

    public static void setChat(Chat chat){
        ChatItemManager.chat = chat;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        name.setText(chat.getName());
        id.setText(chat.getId());
        number.setText(chat.getMembers().size() + "");
    }

    public void choose(){
        //TODO : open a chat room
    }
}
