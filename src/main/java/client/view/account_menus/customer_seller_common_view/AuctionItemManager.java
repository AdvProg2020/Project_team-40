package client.view.account_menus.customer_seller_common_view;

import client.controller.RequestHandler;
import client.view.MenuManager;
import client.view.account_menus.customer_view.AuctionChatManager;
import com.gilecode.yagson.com.google.gson.reflect.TypeToken;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import server.model.Auction;
import server.model.chat.Chat;
import server.model.requests.Request;

import java.io.IOException;
import java.util.HashMap;

public class AuctionItemManager extends MenuManager {
    public Label idLabel;

    public void viewAuctionInfo() {
        AuctionManager.setAuctionID(idLabel.getText());
        Stage stage = new Stage();
        try {
            Parent root = FXMLLoader.load(getClass().
                    getResource("/layouts/customer_seller_common_menus/auctions_menus/auction.fxml"));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root, 800, 600));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void goToChatRoom() {
        String auctionId = idLabel.getId();
        HashMap<String, String> requestQueries = new HashMap<>();
        requestQueries.put("auction ID", auctionId);
        Auction auction = RequestHandler.get("/accounts/seller_customer_common/auction/", requestQueries, true, new TypeToken<Auction>(){}.getType());
        requestQueries.clear();
        requestQueries.put("id", auction.getChatId());
        Chat chat = RequestHandler.get("/chat/chat_id/", requestQueries, true, new TypeToken<Chat>(){}.getType());
        AuctionChatManager.setLast(chat);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/layouts/customer_menus/auction_chat_room.fxml"));
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
