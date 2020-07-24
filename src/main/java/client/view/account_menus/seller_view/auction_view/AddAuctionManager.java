package client.view.account_menus.seller_view.auction_view;

import client.controller.Client;
import client.controller.RequestHandler;
import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.com.google.gson.reflect.TypeToken;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import server.model.Auction;
import server.model.Product;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.ResourceBundle;

public class AddAuctionManager implements Initializable {
    public VBox vBoxItems;
    public ChoiceBox productChoiceBox;
    public Label dateError;
    public Label productError;
    public TextField deadlineField;
    public Button addButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        HashMap<String, String> queries = new HashMap<>();
        queries.put("username", Client.getInstance().getUsername());
        ArrayList<Product> products = RequestHandler.get("/accounts/seller_account_controller/products/", queries,
                true, new TypeToken<ArrayList<Product>>(){}.getType());
        for(Product product : products) {
            setProductsItem(product);
        }
    }

    private void setProductsItem(Product product) {
        try {
            AnchorPane item = FXMLLoader.load(getClass().
                    getResource("/layouts/seller_menus/sellers_auctions_menus/product_item.fxml"));
            HBox hBox = (HBox) item.getChildren().get(0);
            ((Label) hBox.getChildren().get(0)).setText(product.getProductId());
            ((Label) hBox.getChildren().get(1)).setText(product.getName());
            ((Label) hBox.getChildren().get(2)).setText(String.valueOf(product.getPrice()));
            vBoxItems.getChildren().add(hBox);
            productChoiceBox.getItems().add(product.getProductId());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addAuction() {
        try {
            verifyInput();
            Date date = verifyDate();
            HashMap<String, String> queries = new HashMap<>();
            queries.put("product ID", (String) productChoiceBox.getSelectionModel().getSelectedItem());
            queries.put("seller ID", Client.getInstance().getUsername());
            String entity = new YaGson().toJson(date, Date.class);
            RequestHandler.post("/accounts/seller_account_controller/add_auction/", entity, queries,
                    true, null);
            ((Stage)addButton.getScene().getWindow()).close();
        } catch(ParseException e) {
            dateError.setText("Enter date based on given format");
        } catch (Exception ignored) {}
    }

    private Date verifyDate() throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        Date date = dateFormat.parse(deadlineField.getText());
        if(date.before(new Date())) {
            dateError.setText("Choose a date in the future!");
            throw new Exception();
        }
        return date;
    }

    private void verifyInput() throws Exception {
        if(deadlineField.getText().isBlank()) {
            dateError.setText("Fill this field!");
            throw new Exception();
        } else {
            dateError.setText("");
        }
        if(productChoiceBox.getSelectionModel().getSelectedItem() == null) {
            productError.setText("Choose a product!");
            throw new Exception();
        } else {
            productError.setText("");
        }
    }
}
