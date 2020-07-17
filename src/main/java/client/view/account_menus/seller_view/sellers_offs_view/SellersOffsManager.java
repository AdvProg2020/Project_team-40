package client.view.account_menus.seller_view.sellers_offs_view;

import client.controller.Client;
import client.controller.RequestHandler;
import com.gilecode.yagson.com.google.gson.reflect.TypeToken;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import server.model.Off;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class SellersOffsManager implements Initializable{

    public VBox offsVBox;

    private static SellersOffsManager sellersOffsManager;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        sellersOffsManager = this;
        initializeOffs();
    }

    private void initializeOffs(){
        offsVBox.getChildren().clear();
        HashMap<String, String> queries = new HashMap<>();
        queries.put("username", Client.getInstance().getUsername());
        HashMap<String, Off> offs =  RequestHandler.get("/accounts/seller_account_controller/all_offs/", queries, true, new TypeToken<HashMap<String, Off>>(){}.getType());
        assert offs != null;
        for(Map.Entry<String, Off> entry : offs.entrySet()) {
            OffItemManager.setLastOff(entry.getValue());

            Node node = null;
            try {
                node = (Node) FXMLLoader.load(getClass().getResource("/layouts/seller_menus/off_item.fxml"));
            } catch(IOException e) {
                e.printStackTrace();
            }

            offsVBox.getChildren().add(node);
        }
    }

    public void addOff(){
        Stage stage = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/layouts/seller_menus/add_off.fxml"));
            AnchorPane pane = loader.load();
            stage.setScene(new Scene(pane, 600 , 400));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static SellersOffsManager getInstance(){
        return sellersOffsManager;
    }
}
