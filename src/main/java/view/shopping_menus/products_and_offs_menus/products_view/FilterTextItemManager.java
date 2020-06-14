package view.shopping_menus.products_and_offs_menus.products_view;

import controller.menus.AllProductsController;
import exceptions.MenuException;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class FilterTextItemManager implements Initializable{
    public Text variableName;
    public TextField textField;
    public Button setButton;

    private String property;
    private ArrayList<String> values;

    public FilterTextItemManager(){
        property = ProductsMenuManager.getLastStringProperty();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        variableName.setText(property);

        setButton.setOnAction(actionEvent -> {
            values.clear();
            values.add(textField.getText());
            for(String value : values) {
                if(value.isEmpty())
                    value = null;
            }
            AllProductsController.getInstance().disableFilter(property);
            try {
                AllProductsController.getInstance().addFilter(property, values);
            } catch(MenuException e) {
                e.printStackTrace();
            }
        });
    }
}
