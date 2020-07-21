package client.view.account_menus.customer_view;

import client.controller.Client;
import client.controller.RequestHandler;
import client.view.MenuManager;
import com.gilecode.yagson.com.google.gson.reflect.TypeToken;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import server.model.chat.Chat;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class SupportItemManager extends MenuManager implements Initializable{
    private static String last;
    private String support;

    public Text name;

    public SupportItemManager() {
        support = last;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        name.setText(support);
    }

    public void choose() {
        HashMap<String, String> requestQueries = new HashMap<>();
        requestQueries.put("username", Client.getInstance().getUsername());
        requestQueries.put("support", support);
        Chat chat = RequestHandler.get("/chat/support_chat/", requestQueries, true, new TypeToken<Chat>(){}.getType());

        if(chat == null){
            requestQueries.put("hasSupport", "true");
            requestQueries.put("name", "Support : " + support);
            RequestHandler.post("/chat/chat/", null, requestQueries, true, null);
            requestQueries.remove("hasSupport");
            requestQueries.remove("name");
            chat = RequestHandler.get("/chat/support_chat/", requestQueries, true, new TypeToken<Chat>(){}.getType());
        }

        AuctionChatManager.setLast(chat);
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/layouts/customer_view/auction_chat_room.fxml"));
        try {
            fxmlLoader.load();
        } catch(IOException e) {
            e.printStackTrace();
        }
        AuctionChatManager auctionChatManager = fxmlLoader.getController();
        auctionChatManager.root.getChildren().remove(auctionChatManager.idText);
        auctionChatManager.root.getChildren().remove(auctionChatManager.number);
        auctionChatManager.root.getChildren().remove(auctionChatManager.moreButton);
    }

    public static void setLast(String last){
        SupportItemManager.last = last;
    }
}
