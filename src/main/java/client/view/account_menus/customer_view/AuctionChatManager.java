package client.view.account_menus.customer_view;

import client.view.MenuManager;
import com.jfoenix.controls.JFXTextArea;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import server.model.chat.Chat;
import server.model.chat.Message;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

public class AuctionChatManager extends MenuManager implements Initializable{
    private static Chat last;
    private Chat chat;
    public VBox chatBox;
    public JFXTextArea text;
    public Text name;
    public Text idText;
    public Text number;

    private int size;

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
        timer.schedule(timerTask, 1000);
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

    }

    public void refresh() {

    }

    public void showMembers() {

    }

    public static void setLast(Chat last){
        AuctionChatManager.last = last;
    }
}
