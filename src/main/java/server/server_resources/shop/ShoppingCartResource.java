package server.server_resources.shop;

import exceptions.MenuException;
import org.restlet.resource.Put;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import server.controller.menus.ProductController;

public class ShoppingCartResource extends ServerResource {
    @Put
    public void addToCart(String productId){
        try {
            ProductController.getInstance().addProductToCart(getQueryValue("username"), productId,
                    Integer.parseInt(getQueryValue("count")));
        } catch (MenuException e) {
            throw new ResourceException(403, e);
        }
    }
}
