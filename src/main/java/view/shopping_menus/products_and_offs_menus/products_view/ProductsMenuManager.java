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


    private static ArrayList<String> stringProperties;
    private static ArrayList<String> rangeProperties;

    private static int indexOfLastUser;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        //initializeProducts();
        initializeCategories();
    }

    private void initializeProducts(){
        showMoreItems();
    }

    private void initializeCategories(){

        TreeItem root = new TreeItem("all categories");

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

        handleSelectedCategory();
    }

    private void handleSelectedCategory(){
        categories.getSelectionModel().selectedItemProperty().addListener(new ChangeListener(){
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1){
                if(((String)((TreeItem)t1).getValue()).equals("all categories")){
                    AllProductsController.getInstance().disableFilter("category");
                }else{
                    AllProductsController.getInstance().addFilter("category", (String)((TreeItem)t1).getValue());
                }
                initializeFilter();
            }
        });
    }

    private void initializeFilter(){
        for(String filter : AllProductsController.getInstance().getAvailableStringFilters()) {
            stringProperties.add(filter);
            try {
                filters.getChildren().add((Node) FXMLLoader.load(getClass().getResource("/layouts/filter_text_item.fxml")));
            } catch(IOException e) {
                e.printStackTrace();
            }
        }

        for(String filter : AllProductsController.getInstance().getAvailableValueFilters()) {
            try {
                rangeProperties.add(filter);
                filters.getChildren().add((Node) FXMLLoader.load(getClass().getResource("/layouts/filter_range_item.fxml")));
            } catch(IOException e) {
                e.printStackTrace();
            }
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

    public static String getLastStringProperty(){
        return stringProperties.get(stringProperties.size() - 1);
    }
    public static String getLastRangeProperty(){
        return rangeProperties.get(rangeProperties.size() - 1);
    }

    //TODO : REMOVE LATER
    private void showSampleProducts(int count){

    }

}
