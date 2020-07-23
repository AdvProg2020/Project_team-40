package client.view.account_menus.customer_view;

import client.controller.Client;
import client.controller.RequestHandler;
import client.view.MenuManager;
import com.gilecode.yagson.com.google.gson.reflect.TypeToken;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import server.model.chat.Chat;
import server.model.chat.Message;
import server.model.requests.Request;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class AuctionChatManager extends MenuManager implements Initializable{
    private static final int REFRESH_DELAY = 1000;

    private static Chat last;

    private Chat chat;
    public AnchorPane root;
    public VBox chatBox;
    public JFXTextArea text;
    public Text name;
    public Text idText;
    public Text number;
    public Button moreButton;

    private boolean exit;

    private int size = 0;
    private ArrayList<String> members = new ArrayList<>();

    public AuctionChatManager() {
        chat = last;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        name.setText("Name : " + chat.getName());
        idText.setText("ID : " + chat.getId());
        number.setText("Members : " + chat.getMembers().size());

        chatBox.setPadding(new Insets(20,20,20,20));
        chatBox.setSpacing(10);
        ArrayList<Message> messages = chat.getMessages();
        for(Message message : messages) {
            addMessage(message);
        }
        size = messages.size();

        Thread refreshT = new Thread(new Runnable(){
            @Override
            public void run(){
                Runnable refresher = new Runnable(){
                    @Override
                    public void run(){
                        refresh();
                    }
                };
                loop:
                while(!exit){
                    try {
                        Thread.sleep(1000);
                    } catch(InterruptedException e) {
                        e.printStackTrace();
                    }
                    text.getScene().getWindow().setOnCloseRequest(e -> {
                        exit = true;
                    });
                    Platform.runLater(refresher);
                }
            }
        });
        refreshT.setDaemon(true);
        refreshT.start();
    }

    private void addMessage(Message message){
        VBox messageBox = new VBox();
        Text senderText = new Text(message.getSender());
        Text contentText = new Text(message.getContent());
        Text dateText = new Text((new Date(message.getTime())).toString());

        senderText.setStyle("-fx-font-size: 24;"
            + "-fx-text-fill: #181242;");
        contentText.setStyle("-fx-font-size: 18");
        dateText.setStyle("-fx-font-size: 10");
        messageBox.setSpacing(5);
        messageBox.setStyle("-fx-border-width: 2;"
            + "-fx-background-color: #a3deff");

        messageBox.getChildren().addAll(senderText, contentText, dateText);
        chatBox.getChildren().add(messageBox);
    }

    public void sendMessage() {
        String chadId = chat.getId();
        String content = text.getText();
        String sender = Client.getInstance().getUsername();

        HashMap<String, String> requestQueries = new HashMap<>();
        requestQueries.put("chatId", chadId);
        requestQueries.put("content", content);
        requestQueries.put("username", sender);
        RequestHandler.put("/chat/message/", null, requestQueries, true, null);
        text.setText("");
    }

    public void refresh() {
        HashMap<String, String> requestQueries = new HashMap<>();
        requestQueries.put("chatId", chat.getId());
        requestQueries.put("size", size + "");

        ArrayList<Message> messages = RequestHandler.get("/chat/message/", requestQueries, true, new TypeToken<ArrayList<Message>>(){}.getType());

        size += messages.size();
        for(Message message : messages) {
            addMessage(message);
        }

        requestQueries.clear();
        requestQueries.put("id", chat.getId());
        members = RequestHandler.get("/chat/members/", requestQueries, true, new TypeToken<ArrayList<String>>(){}.getType());
        number.setText("Members : " + chat.getMembers().size());
    }

    public void showMembers() {
        try {
            Stage stage = new Stage();
            stage.setTitle("Members");
            MembersManager.setLast(chat);
            Parent parent = FXMLLoader.load(getClass().getResource("/layouts/customer_menus/members.fxml"));
            stage.setScene(new Scene(parent, 280, 556));
            stage.showAndWait();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static void setLast(Chat last){
        AuctionChatManager.last = last;
    }
}
