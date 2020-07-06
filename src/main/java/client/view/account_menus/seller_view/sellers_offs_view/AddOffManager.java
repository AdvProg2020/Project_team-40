package client.view.account_menus.seller_view.sellers_offs_view;

import com.jfoenix.controls.JFXTextField;
import server.controller.accounts.SellerAccountController;
import exceptions.AccountsException;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.stage.Stage;
import org.controlsfx.control.CheckListView;
import client.view.MenuManager;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddOffManager extends MenuManager implements Initializable{

    public JFXTextField percentageField, startDateField, endDateField;
    public CheckListView<Item> productsCheckList;

    private ArrayList<String> sellerProducts;
    private ArrayList<String> chosenProducts;
    private String startDate;
    private String endDate;
    private double percentage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        initializeProducts();
    }

    private void initializeProducts(){
        sellerProducts = SellerAccountController.getInstance().getSellerProductIDs();
        chosenProducts = new ArrayList<>();

        ObservableList<Item> items = FXCollections.observableArrayList();
        int x = 0;
        for(String id : sellerProducts) {
                items.add(new Item(id, x));
                x++;
        }
        productsCheckList.setItems(items);

        productsCheckList.setCellFactory(lv -> new CheckBoxListCell<Item>(productsCheckList::getItemBooleanProperty) {
            @Override
            public void updateItem(Item item, boolean b){
                super.updateItem(item, b);
                setText(item == null ? "" : String.format("%s", item.id.getValue()));
            }
        });

        productsCheckList.getCheckModel().getCheckedIndices().addListener(new ListChangeListener<Integer>(){
            @Override
            public void onChanged(Change<? extends Integer> c){
                while (c.next()) {
                    if (c.wasAdded()) {
                        for (int i : c.getAddedSubList()) {
                            chosenProducts.add(productsCheckList.getItems().get(i).idProperty().getValue());
                        }
                    }
                    if (c.wasRemoved()) {
                        for (int i : c.getRemoved()) {
                            chosenProducts.remove(productsCheckList.getItems().get(i).idProperty().getValue());
                        }
                    }
                }
            }
        });
    }

    class Item {
        private StringProperty id;
        private IntegerProperty num;

        public Item(String id, int num){
            this.id = new SimpleStringProperty(id);
            this.num = new SimpleIntegerProperty(num);
        }

        public IntegerProperty numProperty(){
            return num;
        }

        public StringProperty idProperty(){
            return id;
        }
    }

    public void add(){
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
            SellerAccountController.getInstance().addOffToSeller(chosenProducts, startDate, endDate, percentage);
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
}
