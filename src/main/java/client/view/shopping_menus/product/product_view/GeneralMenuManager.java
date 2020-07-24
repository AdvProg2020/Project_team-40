package client.view.shopping_menus.product.product_view;

import client.controller.Client;
import client.controller.RequestHandler;
import client.view.MenuManager;
import com.gilecode.yagson.com.google.gson.reflect.TypeToken;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import exceptions.InvalidInputException;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Pair;
import org.restlet.resource.ResourceException;
import server.model.Product;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class GeneralMenuManager extends MenuManager implements Initializable{
    private static  String destination;

    public AnchorPane root;
    public Text productName, sellerName, companyName, category, rating, descriptionText, priceText;
    public Spinner<Integer> countSpinner;
    public ImageView imageView;
    public JFXButton cartButton, downloadButton;
    public Label messageLabel;
    public JFXTextField destinationTextField;
    public JFXButton saveDestinationButton;
    private HashMap<String, String> requestQueries;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        //Set texts using product information
        requestQueries = new HashMap<>();
        Product product = ProductMenuManager.getProduct();
        productName.setText(product.getName());
        sellerName.setText(product.getSellerUsername());
        companyName.setText(product.getCompany());
        category.setText(product.getCategory());
        rating.setText(product.getAverageScore() + " / " + "5.0");
        descriptionText.setText(product.getExplanation());
        priceText.setText(product.getPrice() + " / " + product.getBasePrice());

        String role = Client.getInstance().getRole();
        String username = Client.getInstance().getUsername();
        boolean isLoggedIn = Client.getInstance().isLoggedIn();
        if(!product.hasFile()) {
            root.getChildren().remove(downloadButton);
            root.getChildren().remove(saveDestinationButton);
            root.getChildren().remove(destinationTextField);
        }

        if (!isLoggedIn){
            SpinnerValueFactory<Integer> spinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, product.getCount(), 0);
            countSpinner.setValueFactory(spinnerValueFactory);
        }
        else if (role.equals("Customer")){
            SpinnerValueFactory<Integer> spinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, product.getCount(), 0);
            countSpinner.setValueFactory(spinnerValueFactory);
            requestQueries.clear();
            requestQueries.put("username", username);
            requestQueries.put("productID", product.getProductId());
            boolean hasBought = (boolean) RequestHandler.get("/accounts/customer_account_controller/product/purchase_status/", requestQueries, false, boolean.class);
            if(hasBought) {
                saveDestinationButton.setDisable(false);
                destinationTextField.setDisable(false);
            }
        }
        else {
            cartButton.setDisable(true);
            countSpinner.setDisable(true);
        }
        //Set image
        try {
            Image image = new Image(getClass().getResourceAsStream("/product_images/" + product.getName() + ".jpg"));
            imageView.setImage(image);
        }catch(Exception e){
            System.err.println("Loading images failed");
        }
    }

    public void handleCart(){
        try {
            HashMap<String, String> queries = new HashMap<>();
            queries.put("username", Client.getInstance().getUsername());
            queries.put("count", Integer.toString(countSpinner.getValueFactory().getValue()));
            RequestHandler.put("/shop/cart/", ProductMenuManager.getProduct().getProductId(), queries, false, null);
        } catch(ResourceException e) {
            try {
                System.err.println(RequestHandler.getClientResource().getResponseEntity().getText());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void download(){
        HashMap<String, String> requestQueries = new HashMap<>();
        requestQueries.put("productId", ProductMenuManager.getProduct().getProductId());
        try{
            Pair<String, byte[]> pair = RequestHandler.get("/shop/file/", requestQueries, true, new TypeToken<Pair<String, byte[]>>(){}.getType());
            File file = new File(destination + pair.getKey());
            if (!file.exists())
                if(!file.createNewFile())
                    throw new IOException("File creation failed.");
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(pair.getValue());

        }catch(IOException e){
            System.out.println(e.getMessage());
        }
        catch (ResourceException e){
            try {
                messageLabel.setText(RequestHandler.getClientResource().getResponseEntity().getText());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void handleCheckDestination(ActionEvent event) {
        if (destinationTextField.getText().equals(""))
            messageLabel.setText("Fill the destination field!");
        else
        {
            try {
                String input = destinationTextField.getText();
                if (!(new File(input).exists()))
                    throw new InvalidInputException("Destination not found");
                if (!input.endsWith("\\"))
                    throw new InvalidInputException("Destination must ends with \\ character!");
                destination = input;
                destinationTextField.setDisable(true);
                saveDestinationButton.setDisable(true);
                downloadButton.setDisable(false);
            }
            catch (Exception e){
                messageLabel.setText(e.getMessage());
            }

        }
    }
}
