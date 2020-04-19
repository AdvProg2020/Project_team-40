package Controller.Menus;

import model.Comment;
import model.Product;
import model.properties.Property;

import java.util.ArrayList;

public class ProductController{

    public static ProductController productController;

    public Product digestProduct(String productID){
        return null;
    }

    public void addProductToCart(String productID){

    }

    public void sellectSellerForProduct(String productID, String sellerUsername){

    }

    public ArrayList<Property> getProductAttributes(String productID){
        return null;
    }

    public ArrayList<Comment> getComment(String productID){
        return null;
    }

    public void addComment(String productID, String title, String content){

    }

    private ProductController(){

    }

    public static ProductController getInstance(){
        if(productController == null)
            productController = new ProductController();

        return productController;
    }

}
