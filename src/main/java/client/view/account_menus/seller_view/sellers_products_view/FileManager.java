package client.view.account_menus.seller_view.sellers_products_view;

import client.controller.Client;
import client.controller.RequestHandler;
import com.gilecode.yagson.YaGson;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class FileManager implements Initializable{
    private static String last;
    private String productId;

    public Text nameText;

    public FileManager(){
        productId = last;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        ((Stage)nameText.getScene().getWindow()).setTitle(productId);
    }

    public static void setLast(String last){
        FileManager.last = last;
    }

    public void attach(){
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(nameText.getScene().getWindow());
        if(file != null){
            try {
                FileInputStream fis = new FileInputStream(file.getPath());
                byte[] bytes = fis.readAllBytes();
                String entity = new YaGson().toJson(bytes, byte[].class);
                String fileName = file.getName();

                HashMap<String, String> requestQueries = new HashMap<>();
                requestQueries.put("username", Client.getInstance().getUsername());
                requestQueries.put("productId", productId);
                requestQueries.put("fileName", fileName);

                RequestHandler.post("/shop/file/", entity, requestQueries, true, null);
            } catch(FileNotFoundException e) {
                e.printStackTrace();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    public void detach(){
        HashMap<String, String> requestQueries = new HashMap<>();
        requestQueries.put("username", Client.getInstance().getUsername());
        requestQueries.put("productId", productId);

        RequestHandler.delete("/shop/file/", requestQueries, true, null);
    }

    public void finish(){
        ((Stage)nameText.getScene().getWindow()).close();
    }
}
