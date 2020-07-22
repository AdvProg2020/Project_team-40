package client.view.account_menus.customer_view;

import client.controller.Client;
import client.controller.RequestHandler;
import client.view.MenuManager;
import com.gilecode.yagson.com.google.gson.reflect.TypeToken;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
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
        Stage stage = new Stage();
        VBox vBox = new VBox();
        JFXTextField field = new JFXTextField();
        field.setPromptText("Enter name");
        field.setStyle("-fx-font-size: 14");
        JFXButton button = new JFXButton("Create");
        button.setStyle("-fx-font-size: 16");
        button.setOnAction(e -> {
            if(!field.getText().isEmpty()){
                requestQueries.clear();
                requestQueries.put("name", field.getText());
                requestQueries.put("username", Client.getInstance().getUsername());
                requestQueries.put("hasSupport", "false");
                requestQueries.put("support", "null");
                RequestHandler.post("/chat/chat/", null, requestQueries, true, null);
                stage.close();
            }
        });
        vBox.getChildren().addAll(field, button);
        vBox.setSpacing(20);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(20,20,20,20));
        stage.setScene(new Scene(vBox, 400, 200));
        stage.showAndWait();
    }
}
