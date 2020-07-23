package server.server_resources.shop;

import com.gilecode.yagson.YaGson;
import exceptions.AccountsException;
import javafx.util.Pair;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;
import server.controller.accounts.SellerAccountController;

import java.io.IOException;

public class FileResource extends ServerResource{

    //QUERIES : productId
    @Get
    public String download(){
        Pair<String, byte[]> file = null;
        try {
            file = SellerAccountController.getInstance().getFile(getQueryValue("productId"));
        } catch(AccountsException e) {
            e.printStackTrace();
        }
        return new YaGson().toJson(file, Pair.class);
    }

    //QUERIES : username, fileName, productId
    @Post
    public void upload(String entity){
        byte[] bytes = new YaGson().fromJson(entity, byte[].class);
        try {
            SellerAccountController.getInstance().attachFile(getQueryValue("username"), getQueryValue("productId"), getQueryValue("fileName"), bytes);
        }catch(AccountsException e){
            System.out.println(e.getMessage());
        }
    }

    //QUERIES : username, productId
    @Delete
    public void remove(){
        try {
            SellerAccountController.getInstance().detachFile(getQueryValue("username"), getQueryValue("productId"));
        } catch(AccountsException e) {
            e.printStackTrace();
        }
    }
}
