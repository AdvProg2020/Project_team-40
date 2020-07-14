package client.view.account_menus.seller_view.sellers_offs_view;

import client.controller.Client;
import client.controller.RequestHandler;
import client.view.MenuManager;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.restlet.data.Status;
import org.restlet.resource.ResourceException;
import server.model.Off;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.ResourceBundle;

public class EditOffManager extends MenuManager implements Initializable{

    public JFXTextField percentageField, startDateField, endDateField;

    private String startDate;
    private String endDate;
    private double percentage;

    private static Off currentOff = null;
    private Off off;
    private HashMap<String, String> requestQueries;

    public EditOffManager(){
        off = currentOff;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        requestQueries = new HashMap<>();
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

        handleEditOffRequest();

        ((Stage)startDateField.getScene().getWindow()).close();
    }

    private void handleEditOffRequest() {
        try {
            requestQueries.clear();
            requestQueries.put("username", Client.getInstance().getUsername());

            if(off.getDiscountPercentage() != percentage) {
                requestQueries.put("field", "discount percentage");
                requestQueries.put("newField", percentage + "");
            }
            if(!off.getStartDate().equals(startDate))
            {
                requestQueries.put("field", "start date");
                requestQueries.put("newField", startDate);
            }
            if(!off.getEndDate().equals(endDate)){
                requestQueries.put("field", "end date");
                requestQueries.put("newField", endDate);
            }
            RequestHandler.put("/accounts/seller_account_controller/off/", off.getId(), requestQueries, true, null);
        } catch(ResourceException e) {
            if (e.getStatus().equals(Status.CLIENT_ERROR_UNAUTHORIZED)) {
                ((Stage)startDateField.getScene().getWindow()).close();
                logout();
            }
            else
                e.printStackTrace();
        }
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
