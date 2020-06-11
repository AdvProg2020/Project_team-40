package view.shopping_menus.products_and_offs_menus.products_view;

import com.jfoenix.controls.JFXButton;
import controller.menus.AllProductsController;
import exceptions.AccountsException;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.VBox;
import model.Product;
import view.MenuManager;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ProductsMenuManager extends MenuManager implements Initializable{
    public ChoiceBox sortChoiceBox;
    public VBox productsBox;
    public JFXButton showMoreButton;
    public Label loading;
    public TreeView categories;

    private static int indexOfLastUser;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        initializeProducts();
        initializeCategories();
        initializeFilter();
        showMoreButton.setOnAction(event -> showMoreItems());
    }

    private void initializeProducts(){
        showMoreItems();
    }

    private void initializeCategories(){
        ArrayList<String> allCategoryNames = AllProductsController.getInstance().getAllCategories();
        ArrayList<TreeItem> rootCategories = new ArrayList<>();

        //TODO : remove samples later
        rootCategories.add(new TreeItem("sample 1"));
        rootCategories.add(new TreeItem("sample 2"));
        rootCategories.add(new TreeItem("sample 2"));

        for(String categoryName : allCategoryNames) {
            rootCategories.add(new TreeItem(categoryName));
            try {
                for(String subCategoryName : AllProductsController.getInstance().getAllSubCategories(categoryName)) {
                    rootCategories.get(rootCategories.size() - 1).getChildren().add(subCategoryName);
                }
            }catch(AccountsException e){
                e.printStackTrace();
            }
        }
        TreeItem root = new TreeItem("root");
        root.getChildren().addAll(rootCategories);
        categories.setRoot(root);
        categories.setShowRoot(false);
    }

    private void initializeFilter(){

    }

    private void showMoreItems(){
        loading.setText("loading...");

        try {
            Thread.sleep(200);
        }catch(Exception e){

        }

        Node[] nodes = new Node[20];
        for(int i = 0; i < 20; ++i){
            Product product = null;

            try {
                product = AllProductsController.getInstance().getAllProducts().get(indexOfLastUser);
            }catch(IndexOutOfBoundsException e){
                break;
            }

            try {
                nodes[i] = (Node) FXMLLoader.load(getClass().getResource("/layouts/product_item.fxml"));
                productsBox.getChildren().add(nodes[i]);
            }catch(IOException e){
                e.printStackTrace();
            }

            indexOfLastUser++;
        }
        loading.setText("");
    }

    public static int getIndexOfLastUser(){
        return indexOfLastUser;
    }
}
