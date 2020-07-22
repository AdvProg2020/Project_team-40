package client.view.account_menus.support_view;

import client.view.account_menus.customer_view.AuctionChatManager;
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

public class ChatItemManager implements Initializable{

    private static Chat last;
    private Chat chat;

    public Text name;

    public ChatItemManager(){
        chat = last;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        name.setText(chat.getName());
    }

    public void choose(){
        AuctionChatManager.setLast(chat);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/layouts/customer_menus/auction_chat_room.fxml"));
        Parent parent = null;
        try {
            parent = loader.load();
        } catch(IOException e) {
            e.printStackTrace();
        }
        AuctionChatManager manager = loader.getController();
        manager.root.getChildren().remove(manager.moreButton);
        manager.root.getChildren().remove(manager.idText);
        manager.root.getChildren().remove(manager.number);
        Stage stage = new Stage();
        stage.setTitle(chat.getName());
        stage.setScene(new Scene(parent, 868, 680));
        stage.show();
    }

    public static void setLast(Chat last){
        ChatItemManager.last = last;
    }
}
