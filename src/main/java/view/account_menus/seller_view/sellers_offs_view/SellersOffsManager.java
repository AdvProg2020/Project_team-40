package view.account_menus.seller_view.sellers_offs_view;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import model.Off;
import model.users.Seller;
import model.users.User;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SellersOffsManager implements Initializable{

    public VBox offsVBox;
    private Seller seller;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        seller = (Seller)User.getLoggedInUser();
        addOffs();
    }

    private void addOffs(){
        for(String offId : seller.getOffIds()) {
            OffItemManager.setLastOff(Off.getOffByID(offId));

            Node node = null;
            try {
                node = (Node) FXMLLoader.load(getClass().getResource("/layouts/seller_menus/off_item.fxml"));
            } catch(IOException e) {
                e.printStackTrace();
            }

            offsVBox.getChildren().add(node);
        }
    }
}
