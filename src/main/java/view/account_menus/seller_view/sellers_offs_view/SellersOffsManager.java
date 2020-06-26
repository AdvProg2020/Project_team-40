package view.account_menus.seller_view.sellers_offs_view;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Off;
import model.requests.AddOff;
import model.users.Seller;
import model.users.User;
import view.shopping_menus.product.product_view.AddCommentMenuManager;
import view.shopping_menus.product.product_view.ProductMenuManager;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SellersOffsManager implements Initializable{

    public VBox offsVBox;
    private Seller seller;

    private static SellersOffsManager sellersOffsManager;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        sellersOffsManager = this;
        seller = (Seller)User.getLoggedInUser();
        initializeOffs();
    }

    private void initializeOffs(){
        offsVBox.getChildren().clear();
        ArrayList<String> offIds = seller.getOffIds();
        for(String offId : offIds) {
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

    public void refresh(){
        initializeOffs();
    }

    public static SellersOffsManager getInstance(){
        return sellersOffsManager;
    }
}
