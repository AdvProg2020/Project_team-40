package view.shopping_menus.products_and_offs_menus.products_view;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXToggleButton;
import controller.menus.AllProductsController;
import exceptions.AccountsException;
import exceptions.MenuException;
import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import model.Category;
import model.Product;
import view.MenuManager;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ProductsMenuManager extends MenuManager implements Initializable{
    public JFXComboBox<String> sortsComboBox;
    public TextField productNameField, productCompanyField, sellerField;
    public JFXToggleButton onlyOffToggle, onlyStockToggle;
    public JFXSlider priceMinSlider, priceMaxSlider;
    public VBox products;
    public VBox filters;
    public VBox extraFilters;
    public VBox filtersSliderMenu;
    public TreeView categories;

    private static ArrayList<String> stringProperties = new ArrayList<>();
    private static ArrayList<String> rangeProperties = new ArrayList<>();
    private static ArrayList<Product> productsList = new ArrayList<>();

    private static int indexOfLastUser;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        initializeProducts();
        initializeCategories();
        initializeFilter();
        initializeSorts();
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
        extraFilters.getChildren().clear();

        for(String filter : AllProductsController.getInstance().getAvailableStringFilters()) {
            stringProperties.add(filter);
            try {
                extraFilters.getChildren().add((Node) FXMLLoader.load(getClass().getResource("/layouts/shopping_menus/filter_text_item.fxml")));
            } catch(IOException e) {
                e.printStackTrace();
            }
        }

        for(String filter : AllProductsController.getInstance().getAvailableValueFilters()) {
            try {
                rangeProperties.add(filter);
                extraFilters.getChildren().add((Node) FXMLLoader.load(getClass().getResource("/layouts/shopping_menus/filter_range_item.fxml")));
            } catch(IOException e) {
                e.printStackTrace();
            }
        }

        productNameField.textProperty().addListener(((observableValue, s, t1) -> {
            if(t1 == "")
                AllProductsController.getInstance().disableFilter("productName");
            else
                AllProductsController.getInstance().addFilter("productName", t1);
            refresh();
        }));

        productCompanyField.textProperty().addListener(((observableValue, s, t1) -> {
            if(t1 == "")
                AllProductsController.getInstance().disableFilter("companyName");
            else
                AllProductsController.getInstance().addFilter("companyName", t1);
            refresh();
        }));

        sellerField.textProperty().addListener(((observableValue, s, t1) -> {
            if(t1 == "")
                AllProductsController.getInstance().disableFilter("sellerName");
            else
                AllProductsController.getInstance().addFilter("sellerName", t1);
            refresh();
        }));

        double priceCap = 0;
        try {
            priceCap = AllProductsController.getInstance().getRangeCap("price");
            priceMaxSlider.setMax(priceCap);
            priceMaxSlider.setValue(priceCap/2);
            priceMinSlider.setMax(priceCap);
        } catch(MenuException e) {
            e.printStackTrace();
        }

        priceMinSlider.setOnMouseReleased(mouseEvent -> {
            try {
                AllProductsController.getInstance().addFilter("price", priceMinSlider.getValue(), priceMaxSlider.getValue());
            } catch(MenuException e) {
                e.printStackTrace();
            }
            refresh();
        });

        priceMaxSlider.setOnMouseReleased(mouseEvent -> {
            try {
                AllProductsController.getInstance().addFilter("price", priceMinSlider.getValue(), priceMaxSlider.getValue());
            } catch(MenuException e) {
                e.printStackTrace();
            }
            refresh();
        });

        onlyOffToggle.setOnAction(actionEvent -> {
            AllProductsController.getInstance().setIsOffsOnly(onlyOffToggle.isSelected());
            refresh();
        });

        onlyStockToggle.setOnAction(actionEvent -> {
            boolean turnedOn = onlyStockToggle.isSelected();
            System.out.println(turnedOn);
            if(turnedOn){
                AllProductsController.getInstance().addFilter("status", "existing");
            }else{
                AllProductsController.getInstance().disableFilter("status");
            }
            refresh();
        });

    }

    private void initializeSorts(){
        sortsComboBox.getItems().addAll("MOST_EXPENSIVE", "CHEAPEST", "MOST_VISITED", "HIGHEST_SCORE", "HIGHEST_SALES");
        sortsComboBox.setOnAction(actionEvent -> {
            try {
                AllProductsController.getInstance().setSort(sortsComboBox.getValue());
            } catch(MenuException e) {
                e.printStackTrace();
            }
            refresh();
        });
    }

    public void refresh(){
        products.getChildren().clear();
        AllProductsController.getInstance().filterAndSort();
        indexOfLastUser = 0;
        showMoreItems();
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
                nodes[i] = (Node) FXMLLoader.load(getClass().getResource("/layouts/shopping_menus/product_item.fxml"));
                products.getChildren().add(nodes[i]);
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
        animation.setToX(-271);

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


}
