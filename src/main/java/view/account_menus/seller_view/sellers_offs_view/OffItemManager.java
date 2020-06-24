package view.account_menus.seller_view.sellers_offs_view;

import com.jfoenix.controls.JFXButton;
import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import model.Off;
import view.MenuManager;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class OffItemManager extends MenuManager implements Initializable{

    private static Off lastOff = null;
    private Off off;

    public HBox offItemHBox;
    public JFXButton editButton, removeButton;
    public Text offIdText, percentageText, dateText;

    public OffItemManager(){
        off = lastOff;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        offIdText.setText(off.getId());
        percentageText.setText(off.getDiscountPercentage() + "%");
        dateText.setText(off.getStartDate() + " to " + off.getEndDate());

        editButton.setOnAction(actionEvent -> {
            //TODO : implement edit
        });

        removeButton.setOnAction(actionEvent -> {
            Off.removeOff(off.getId());
            offItemHBox.getChildren().clear();
        });
    }

    public static void setLastOff(Off lastOff){
        OffItemManager.lastOff = lastOff;
    }
}
