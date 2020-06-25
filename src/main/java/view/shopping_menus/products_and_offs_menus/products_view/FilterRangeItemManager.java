package view.shopping_menus.products_and_offs_menus.products_view;

import com.jfoenix.controls.JFXSlider;
import controller.menus.AllProductsController;
import exceptions.MenuException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class FilterRangeItemManager implements Initializable{
    public Text variableName;
    public JFXSlider minSlider, maxSlider;

    private String property;
    private double min;
    private double max;

    public FilterRangeItemManager(){
        property = ProductsMenuManager.getLastRangeProperty();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

        variableName.setText(property);

        try {
            double cap = AllProductsController.getInstance().getRangeCap(property);
            minSlider.setMax(cap);
            maxSlider.setMax(cap);
            minSlider.setValue(0);
            maxSlider.setValue(cap);
        } catch(MenuException e) {
            e.printStackTrace();
        }

        minSlider.setOnMouseReleased(mouseEvent -> {
            min = minSlider.getValue();
            AllProductsController.getInstance().disableFilter(property);
            try {
                AllProductsController.getInstance().addFilter(property, min, max);
            } catch(MenuException e) {
                e.printStackTrace();
            }
            ProductsMenuManager.getInstance().refresh();
        });

        maxSlider.setOnMouseReleased(mouseEvent -> {
            max = maxSlider.getValue();
            AllProductsController.getInstance().disableFilter(property);
            try {
                AllProductsController.getInstance().addFilter(property, min, max);
            } catch(MenuException e) {
                e.printStackTrace();
            }
            ProductsMenuManager.getInstance().refresh();
        });
    }
}
