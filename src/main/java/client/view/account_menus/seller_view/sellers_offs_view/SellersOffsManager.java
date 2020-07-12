package client.view.account_menus.seller_view.sellers_offs_view;

import client.view.account_menus.ThisUser;
import server.controller.accounts.SellerAccountController;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import server.model.Off;
import server.model.users.Seller;
import server.model.users.User;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class SellersOffsManager implements Initializable{

    public VBox offsVBox;
    private Seller seller;

    private static SellersOffsManager sellersOffsManager;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        sellersOffsManager = this;
        seller = (Seller) User.getUserByUsername(ThisUser.getUsername());
        initializeOffs();
    }

    private void initializeOffs(){
        offsVBox.getChildren().clear();
        HashMap<String, Off> offs = SellerAccountController.getInstance().getAllOffs();
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