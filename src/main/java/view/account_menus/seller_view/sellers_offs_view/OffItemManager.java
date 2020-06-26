package view.account_menus.seller_view.sellers_offs_view;

import com.jfoenix.controls.JFXButton;
import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Off;
import model.requests.EditOff;
import view.MenuManager;

import java.io.IOException;
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
            Stage stage = new Stage();
            try {
                EditOffManager.setCurrentOff(off);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/layouts/seller_menus/edit_off.fxml"));
                AnchorPane pane = loader.load();
                stage.setScene(new Scene(pane, 600 , 400));
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            }
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
