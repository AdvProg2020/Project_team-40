package view.shopping_menus.products_and_offs_menus.products_view;

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
    public Slider minSlider;
    public Slider maxSlider;
    public Button setButton;

    private String property;
    private double min;
    private double max;

    public FilterRangeItemManager(){
        property = ProductsMenuManager.getLastRangeProperty();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

        variableName.setText(property);

        minSlider.valueProperty().addListener(new ChangeListener<Number>(){
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1){
                min = t1.doubleValue();
            }
        });

        maxSlider.valueProperty().addListener(new ChangeListener<Number>(){
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1){
                max = t1.doubleValue();
            }
        });

        setButton.setOnAction(actionEvent -> {
            AllProductsController.getInstance().disableFilter(property);
            try {
                AllProductsController.getInstance().addFilter(property, min, max);
            } catch(MenuException e) {
                e.printStackTrace();
            }
        });
    }
}
