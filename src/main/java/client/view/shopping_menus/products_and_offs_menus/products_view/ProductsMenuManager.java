package client.view.shopping_menus.products_and_offs_menus.products_view;

import client.controller.RequestHandler;
import client.controller.products_menu.ProductsMenuController;
import client.view.MenuManager;
import com.gilecode.yagson.com.google.gson.reflect.TypeToken;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import exceptions.MenuException;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.restlet.resource.ResourceException;
import server.model.Category;
import server.model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class ProductsMenuManager extends MenuManager implements Initializable{
    private static ProductsMenuManager productsMenuManager = null;

    public JFXComboBox<String> sortsComboBox;
    public JFXTextField productNameField, productCompanyField, sellerField;
    public JFXToggleButton onlyOffToggle, onlyStockToggle;
    public JFXSlider priceMinSlider, priceMaxSlider;
    public VBox products;
    public VBox filters;
    public VBox extraFilters;
    public VBox filtersSliderMenu;
    public TreeView categories;
    private HashMap<String, String> requestQueries;

    private static ArrayList<String> stringProperties = new ArrayList<>();
    private static ArrayList<String> rangeProperties = new ArrayList<>();
    private static ArrayList<String> shownProducts = new ArrayList<>();

    private static int indexOfLastUser;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        requestQueries = new HashMap<>();
        productsMenuManager = this;
        initializeCategories();
        initializeFilter();
        initializeSorts();
        refresh();
    }

    private void initializeCategories(){

        TreeItem root = new TreeItem("all categories");
        requestQueries.clear();
        ArrayList<Category> allCategories = RequestHandler.get("/shop/all_categories/", requestQueries,
                false, new TypeToken<ArrayList<Category>>(){}.getType());
        ArrayList<TreeItem> rootCategories = new ArrayList<>();

        for(Category category : allCategories) {
            if(category.getParentCategory() == null){
                rootCategories.add(new TreeItem(category));
            }
            try {
                requestQueries.clear();
                requestQueries.put("parentName", category.getName());
                ArrayList<String> subCategories = RequestHandler.get("/shop/all_sub_categories/", requestQueries,
                        false, new TypeToken<ArrayList<String>>(){}.getType());
                for(String subCategoryName :subCategories) {
                    rootCategories.get(rootCategories.size() - 1).getChildren().add(new TreeItem<>(subCategoryName));
                }
            }catch(ResourceException e){
                try {
                    System.err.println(RequestHandler.getClientResource().getResponseEntity().getText());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
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
                    ProductsMenuController.getInstance().disableFilter("category");
                }else{
                    ProductsMenuController.getInstance().addFilter("category", (String)((TreeItem)t1).getValue());
                }
                refreshFilters();
                refresh();
            }
        });
    }

    private void refreshFilters(){
        extraFilters.getChildren().clear();

        for(String filter : ProductsMenuController.getInstance().getAvailableStringFilters()) {
            stringProperties.add(filter);
            try {
                extraFilters.getChildren().add((Node) FXMLLoader.load(getClass().getResource("/layouts/shopping_menus/filter_text_item.fxml")));
            } catch(IOException e) {
                e.printStackTrace();
            }
        }

        for(String filter : ProductsMenuController.getInstance().getAvailableValueFilters()) {
            try {
                rangeProperties.add(filter);
                extraFilters.getChildren().add((Node) FXMLLoader.load(getClass().getResource("/layouts/shopping_menus/filter_range_item.fxml")));
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void initializeFilter(){

        productNameField.textProperty().addListener(((observableValue, s, t1) -> {
            if(t1 == "")
                ProductsMenuController.getInstance().disableFilter("productName");
            else
                ProductsMenuController.getInstance().addFilter("productName", t1);
            refresh();
        }));

        productCompanyField.textProperty().addListener(((observableValue, s, t1) -> {
            if(t1 == "")
                ProductsMenuController.getInstance().disableFilter("companyName");
            else
                ProductsMenuController.getInstance().addFilter("companyName", t1);
            refresh();
        }));

        sellerField.textProperty().addListener(((observableValue, s, t1) -> {
            if(t1 == "")
                ProductsMenuController.getInstance().disableFilter("sellerName");
            else
                ProductsMenuController.getInstance().addFilter("sellerName", t1);
            refresh();
        }));

        double priceCap = 0;
        try {
            priceCap = ProductsMenuController.getInstance().getRangeCap("price");
            priceMaxSlider.setMax(priceCap);
            priceMaxSlider.setValue(priceCap);
            priceMinSlider.setMax(priceCap);
            priceMinSlider.setValue(0);
        } catch(MenuException e) {
            e.printStackTrace();
        }

        priceMinSlider.setOnMouseReleased(mouseEvent -> {
            try {
                ProductsMenuController.getInstance().addFilter("price", priceMinSlider.getValue(), priceMaxSlider.getValue());
            } catch(MenuException e) {
                e.printStackTrace();
            }
            refresh();
        });

        priceMaxSlider.setOnMouseReleased(mouseEvent -> {
            try {
                ProductsMenuController.getInstance().addFilter("price", priceMinSlider.getValue(), priceMaxSlider.getValue());
            } catch(MenuException e) {
                e.printStackTrace();
            }
            refresh();
        });

        onlyOffToggle.setOnAction(actionEvent -> {
            ProductsMenuController.getInstance().setIsOffsOnly(onlyOffToggle.isSelected());
            refresh();
        });

        onlyStockToggle.setOnAction(actionEvent -> {
            boolean turnedOn = onlyStockToggle.isSelected();
            if(turnedOn){
                ProductsMenuController.getInstance().addFilter("status", "existing");
            }else{
                ProductsMenuController.getInstance().disableFilter("status");
            }
            refresh();
        });

    }

    private void initializeSorts(){
        sortsComboBox.getItems().addAll("MOST_EXPENSIVE", "CHEAPEST", "MOST_VISITED", "HIGHEST_SCORE", "HIGHEST_SALES");
        sortsComboBox.setOnAction(actionEvent -> {
            try {
                ProductsMenuController.getInstance().setSort(sortsComboBox.getValue());
            } catch(MenuException e) {
                e.printStackTrace();
            }
            refresh();
        });
    }

    public void refresh(){
        products.getChildren().clear();
        shownProducts.clear();
        ProductsMenuController.getInstance().filterAndSort();
        indexOfLastUser = 0;
        showMoreItems();
    }

    public void showMoreItems(){

        Node[] nodes = new Node[20];
        for(int i = 0; i < 20; ++i){
            Product product = null;

            try {
                product = ProductsMenuController.getInstance().getAllProducts().get(indexOfLastUser);
            }catch(IndexOutOfBoundsException e){
                break;
            }

            if(shownProducts.contains(product.getName())){
                indexOfLastUser++;
                continue;
            }

            try {
                nodes[i] = (Node) FXMLLoader.load(getClass().getResource("/layouts/shopping_menus/product_item.fxml"));
                products.getChildren().add(nodes[i]);
                shownProducts.add(product.getName());
            }catch(IOException e){
                e.printStackTrace();
            }

            indexOfLastUser++;
        }
    }

    public void openFilters(){
        TranslateTransition animation = new TranslateTransition();
        animation.setDuration(Duration.millis(400));
        animation.setNode(filtersSliderMenu);
        animation.setToX(-270);

        animation.play();
    }

    public void closeFilters(){
        TranslateTransition animation = new TranslateTransition();
        animation.setDuration(Duration.millis(400));
        animation.setNode(filtersSliderMenu);
        animation.setToX(+271);

        animation.play();
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

    public static ProductsMenuManager getInstance(){
        return productsMenuManager;
    }
}
