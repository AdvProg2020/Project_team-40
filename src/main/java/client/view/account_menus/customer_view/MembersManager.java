package client.view.account_menus.customer_view;

import client.controller.RequestHandler;
import com.gilecode.yagson.com.google.gson.reflect.TypeToken;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import server.model.chat.Chat;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class MembersManager implements Initializable{

    private static Chat last;
    private Chat chat;
    private ArrayList<String> members = new ArrayList<>();

    public JFXListView<String> membersList;
    public JFXTextField userField;

    public MembersManager(){
        chat = last;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        HashMap<String, String> requestQueries = new HashMap<>();
        requestQueries.put("id", chat.getId());
        members = RequestHandler.get("/chat/members/", requestQueries, true, new TypeToken<ArrayList<String>>(){}.getType());

        for(String member : members) {
            membersList.getItems().add(member);
        }
    }

    public void add(){
        if(!userField.getText().isEmpty()){
            HashMap<String, String> requestQueries = new HashMap<>();
            requestQueries.put("id", chat.getId());
            requestQueries.put("name", userField.getText());
            RequestHandler.put("/chat/members/", null, requestQueries, true, null);
        }
    }

    public static void setLast(Chat last){
        MembersManager.last = last;
    }
}
