package client.view.shopping_menus.products_and_offs_menus.products_view;

import client.controller.products_menu.ProductsMenuController;
import com.jfoenix.controls.JFXTextField;
import exceptions.MenuException;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class FilterTextItemManager implements Initializable{
    public Text variableName;
    public JFXTextField textField;

    private String property;
    private ArrayList<String> values;

    public FilterTextItemManager(){
        property = ProductsMenuManager.getLastStringProperty();
        values = new ArrayList<>();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        variableName.setText(property);

        textField.textProperty().addListener(((observableValue, s, t1) -> {
            values.clear();
            values.add(t1);

            for(String value : values) {
                if(value.isEmpty())
                    value = null;
            }

            ProductsMenuController.getInstance().disableFilter(property);

            try {
                ProductsMenuController.getInstance().addFilter(property, values);
            } catch(MenuException e) {
                e.printStackTrace();
            }
            ProductsMenuManager.getInstance().refresh();
        }));
    }
}
