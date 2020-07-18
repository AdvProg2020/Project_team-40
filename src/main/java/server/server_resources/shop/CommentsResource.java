package server.server_resources.shop;

import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.com.google.gson.reflect.TypeToken;
import exceptions.MenuException;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import server.controller.menus.ProductController;
import server.model.Comment;

import java.util.ArrayList;

public class CommentsResource extends ServerResource {
    @Get
    public String getComments(){
        ArrayList<Comment> comments;
        try {
            comments = ProductController.getInstance().getComments(getQueryValue("productID"));
        } catch (MenuException e) {
            throw new ResourceException(403, e);
        }
        return new YaGson().toJson(comments, new TypeToken<ArrayList<Comment>>(){}.getType());
    }
}
