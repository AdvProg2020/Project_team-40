package view.account_menus.seller_view.sellers_offs_view;

import com.jfoenix.controls.JFXTextField;
import server.controller.accounts.SellerAccountController;
import exceptions.AccountsException;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import server.model.Off;
import view.MenuManager;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

public class EditOffManager extends MenuManager implements Initializable{

    public JFXTextField percentageField, startDateField, endDateField;

    private String startDate;
    private String endDate;
    private double percentage;

    private static Off currentOff = null;
    private Off off;

    public EditOffManager(){
        off = currentOff;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        percentageField.setText(off.getDiscountPercentage() + "");
        startDateField.setText(off.getStartDate());
        endDateField.setText(off.getEndDate());
    }

    public void edit(){
        startDate = startDateField.getText();
        endDate = endDateField.getText();

        try {
            new SimpleDateFormat("dd/MM/yy HH:mm:ss").parse(startDate);
            new SimpleDateFormat("dd/MM/yy HH:mm:ss").parse(endDate);
        }catch(ParseException e){
            alert("error", "Wrong date format.");
            return;
        }

        try {
            percentage = Double.parseDouble(percentageField.getText());
        }catch(NumberFormatException e){
            alert("error", "Percentage must be a double value.");
            return;
        }

        try {
            if(off.getDiscountPercentage() != percentage)
                SellerAccountController.getInstance().editOff(off.getId(), "discount percentage", percentage + "");
            if(!off.getStartDate().equals(startDate))
                SellerAccountController.getInstance().editOff(off.getId(), "start date", startDate);
            if(!off.getEndDate().equals(endDate))
                SellerAccountController.getInstance().editOff(off.getId(), "end date", endDate);
        } catch(AccountsException e) {
            e.printStackTrace();
        }

        ((Stage)startDateField.getScene().getWindow()).close();
    }

    private void alert(String title, String content){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void setCurrentOff(Off currentOff){
        EditOffManager.currentOff = currentOff;
    }
}
