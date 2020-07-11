package client.view.shopping_menus.product.product_view;

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import server.controller.menus.ProductController;
import exceptions.MenuException;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.util.Callback;
import client.view.MenuManager;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class DigestMenuManager extends MenuManager implements Initializable{

    public JFXTreeTableView<Attribute> treeView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

        JFXTreeTableColumn<Attribute, String> nameCol = new JFXTreeTableColumn<>("Attribute name");
        nameCol.setPrefWidth(500);
        nameCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Attribute, String>, ObservableValue<String>>(){
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Attribute, String> param){
                return param.getValue().getValue().name;
            }
        });

        JFXTreeTableColumn<Attribute, String> valueCol = new JFXTreeTableColumn<>("Attribute value");
        valueCol.setPrefWidth(500);
        valueCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Attribute, String>, ObservableValue<String>>(){
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Attribute, String> param){
                return param.getValue().getValue().value;
            }
        });

        ObservableList<Attribute> attributes = FXCollections.observableArrayList();
        try {
            HashMap<String, String> attributesHashMap = ProductController.getInstance().getProductAttributes(ProductMenuManager.getProduct().getProductId());
            for(Map.Entry<String, String> entry : attributesHashMap.entrySet()) {
                Attribute attribute = new Attribute(entry.getKey(), entry.getValue());
                attributes.add(attribute);
            }
        } catch(MenuException e) {
            e.printStackTrace();
        }

        final TreeItem<Attribute> root = new RecursiveTreeItem<>(attributes, RecursiveTreeObject::getChildren);
        treeView.getColumns().setAll(nameCol, valueCol);
        treeView.setRoot(root);
        treeView.setShowRoot(false);
        treeView.setColumnResizePolicy(TreeTableView.CONSTRAINED_RESIZE_POLICY);
    }



    class Attribute extends RecursiveTreeObject<Attribute>{
        StringProperty name;
        StringProperty value;

        public Attribute(String name, String value){
            this.name = new SimpleStringProperty(name);
            if(value == null)
                value = "-";
            this.value = new SimpleStringProperty(value);
        }
    }
}
