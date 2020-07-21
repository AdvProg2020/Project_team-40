package client.view.account_menus.customer_view;

import client.controller.Client;
import client.controller.RequestHandler;
import client.view.MenuManager;
import com.gilecode.yagson.com.google.gson.reflect.TypeToken;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import server.model.chat.Chat;
import server.model.chat.Message;
import server.model.requests.Request;

import java.net.URL;
import java.util.*;

public class AuctionChatManager extends MenuManager implements Initializable{
    private static final int REFRESH_DELAY = 1000;

    private static Chat last;

    private Chat chat;
    public VBox chatBox;
    public JFXTextArea text;
    public Text name;
    public Text idText;
    public Text number;

    private int size;
    private ArrayList<String> members = new ArrayList<>();

    public AuctionChatManager() {
        chat = last;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        name.setText(chat.getName());
        idText.setText(chat.getId());
        number.setText(chat.getMembers().size() + "");

        ArrayList<Message> messages = chat.getMessages();
        for(Message message : messages) {
            addMessage(message);
        }
        size = messages.size();

        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask(){
            @Override
            public void run(){
                refresh();
            }
        };
        timer.schedule(timerTask, REFRESH_DELAY);
    }

    private void addMessage(Message message){
        VBox messageBox = new VBox();
        Text senderText = new Text(message.getSender());
        Text contentText = new Text(message.getContent());
        Text dateText = new Text((new Date(message.getTime())).toString());

        senderText.setStyle("-fx-text-fill: #002A45");
        senderText.setStyle("-fx-font-size: 16");
        senderText.setStyle("-fx-font-style: bold");
        contentText.setStyle("-fx-font-size: 14");
        dateText.setStyle("-fx-font-size: 10");
        messageBox.setStyle("-fx-background-color: #5DBEFC");

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
        number.setText(members.size() + "");
    }

    public void showMembers() {
        Stage stage = new Stage();
        VBox vBox = new VBox();
        JFXTextField userField = new JFXTextField("Enter username to add");
        JFXButton addButton = new JFXButton("Add member");
        addButton.setOnAction(e -> {
            if(!userField.getText().isEmpty()){
                HashMap<String, String> requestQueries = new HashMap<>();
                requestQueries.put("id", chat.getId());
                requestQueries.put("username", userField.getText());
                RequestHandler.put("/chat/message/", null, requestQueries, true, null);
            }
        });

        ScrollPane membersScroll = new ScrollPane();
        VBox membersBox = new VBox();
        for(String member : members) {
            membersBox.getChildren().add(new Text(member));
        }
        membersScroll.setContent(membersBox);

        vBox.getChildren().addAll(userField, addButton, membersScroll);
        stage.setTitle("Members");
        stage.setScene(new Scene(vBox, 400, 800));
        stage.showAndWait();
    }

    public static void setLast(Chat last){
        AuctionChatManager.last = last;
    }
}
