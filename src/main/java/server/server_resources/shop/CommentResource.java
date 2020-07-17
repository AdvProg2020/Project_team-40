package server.server_resources.shop;

import exceptions.MenuException;
import org.restlet.resource.Put;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import server.controller.menus.ProductController;

public class CommentResource extends ServerResource {
    @Put
    public void addComment(String productID){
        String username = getQueryValue("username");
        String title = getQueryValue("title");
        String content = getQueryValue("content");
        try {
            ProductController.getInstance().addComment(username, productID, title, content);
        } catch (MenuException e) {
            throw new ResourceException(403, e);
        }
    }
}
