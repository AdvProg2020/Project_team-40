package client.view.account_menus.customer_view;

import client.view.MenuManager;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import server.model.chat.Chat;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ChatItemManager extends MenuManager implements Initializable{
    private static Chat last;
    private Chat chat;
    public Text name;
    public Text id;
    public Text number;

    public ChatItemManager() {
        chat = last;
    }

    public static void setLast(Chat last){
        ChatItemManager.last = last;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        name.setText(chat.getName());
        id.setText(chat.getId());
        number.setText(chat.getMembers().size() + "");
    }

    public void choose(){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/layouts/customer_menus/auction_chat_room.fxml"));
        AuctionChatManager.setLast(chat);
        Parent parent = null;
        try {
            parent = loader.load();
        } catch(IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setTitle(chat.getName());
        stage.setScene(new Scene(parent, 868, 680));
        stage.show();
    }
}
