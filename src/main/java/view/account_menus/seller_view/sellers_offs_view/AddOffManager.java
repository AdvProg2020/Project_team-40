package view.account_menus.seller_view.sellers_offs_view;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import view.MenuManager;

import java.net.URL;
import java.util.ResourceBundle;

public class AddOffManager extends MenuManager implements Initializable{

    public DatePicker startDatePicker, endDatePicker;
    public JFXTextField percentageField;
    public JFXListView productsListView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

    }

    public void add(){

    }
}
