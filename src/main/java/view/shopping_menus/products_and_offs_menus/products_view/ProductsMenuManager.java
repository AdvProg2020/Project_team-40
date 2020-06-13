package view.shopping_menus.products_and_offs_menus.products_view;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import controller.menus.AllProductsController;
import exceptions.AccountsException;
import exceptions.MenuException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.Category;
import model.Product;
import view.MenuManager;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ProductsMenuManager extends MenuManager implements Initializable{
    public Button mostExpensiveSort, leastExpensiveSort, mostVisitedSort, highestSalesSort, highestScoreSort;
    public VBox products;
    public VBox filters;
    public TreeView categories;

    private static int indexOfLastUser;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        initializeProducts();
        initializeCategories();
        initializeFilter();
    }

    private void initializeProducts(){
        showMoreItems();
    }

    private void initializeCategories(){

        TreeItem root = new TreeItem("root");

        ArrayList<String> allCategoryNames = AllProductsController.getInstance().getAllCategories();
        ArrayList<TreeItem> rootCategories = new ArrayList<>();

        for(String categoryName : allCategoryNames) {
            if(Category.getCategoryByName(categoryName).getParentCategory() == null){
                rootCategories.add(new TreeItem(categoryName));
            }
            try {
                for(String subCategoryName : AllProductsController.getInstance().getAllSubCategories(categoryName)) {
                    rootCategories.get(rootCategories.size() - 1).getChildren().add(new TreeItem<>(subCategoryName));
                }
            }catch(AccountsException e){
                e.printStackTrace();
            }
        }
        root.getChildren().addAll(rootCategories);
        categories.setRoot(root);
        categories.setShowRoot(false);

        //TODO : REMOVE SAMPLES LATER
        showSampleCategories();

        handleSelectedCategory();
    }

    private void handleSelectedCategory(){
        categories.getSelectionModel().selectedItemProperty().addListener(new ChangeListener(){
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1){
                try {
                    AllProductsController.getInstance().filter("Category", (String)((TreeItem)t1).getValue());
                } catch(MenuException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initializeFilter(){
        ArrayList<String> stringProperties = AllProductsController.getInstance().getAvailableStringFilters();
        for(String property : stringProperties) {
            HBox item = new HBox();
            Text name = new Text(property);
            JFXTextField field = new JFXTextField();
            field.setPromptText("value");
            field.setOnKeyTyped(keyEvent -> {
                try {
                    AllProductsController.getInstance().disableFilter(property);
                } catch(MenuException e) {
                    e.printStackTrace();
                }
                try {
                    AllProductsController.getInstance().filter(property, field.getText());
                } catch(MenuException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public void showMoreItems(){

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
                products.getChildren().add(nodes[i]);
            }catch(IOException e){
                e.printStackTrace();
            }

            indexOfLastUser++;
        }
    }

    public static int getIndexOfLastUser(){
        return indexOfLastUser;
    }

    //TODO : REMOVE LATER
    private void showSampleProducts(int count){

    }

    private void showSampleCategories(){

        categories.getRoot().getChildren().addAll(
                new TreeItem<>("sample 1"),
                new TreeItem<>("sample 2"),
                new TreeItem<>("sample 3"),
                new TreeItem<>("sample 1"));
    }

}
