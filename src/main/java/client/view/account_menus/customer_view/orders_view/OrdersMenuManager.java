package client.view.account_menus.customer_view.orders_view;

import client.controller.Client;
import client.controller.RequestHandler;
import client.view.MenuManager;
import com.gilecode.yagson.com.google.gson.reflect.TypeToken;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.restlet.data.Status;
import org.restlet.resource.ResourceException;
import server.model.log.Log;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.ResourceBundle;

public class OrdersMenuManager extends MenuManager implements Initializable {
    public VBox vBoxItems;
    public HBox topHBox;
    public Label titleLabel;
    private HashMap<String, String> requestQueries;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        requestQueries = new HashMap<>();
        try {
            if (Client.getInstance().getRole().equals("Customer")) {
                requestQueries.put("username", Client.getInstance().getUsername());
                HashMap<String, Log> orders =  RequestHandler.get("/accounts/customer_account_controller/orders/",
                        requestQueries, true, new TypeToken<HashMap<String, Log>>(){}.getType());
                assert orders != null;
                loadLogs(orders.values());
            } else if (Client.getInstance().getRole().equals("Seller")) {
                titleLabel.setText("Sales History");
                requestQueries.clear();
                requestQueries.put("username", Client.getInstance().getUsername());
                ArrayList<Log> logs = RequestHandler.get("/accounts/seller_account_controller/sales_history/",
                        requestQueries, true, new TypeToken<ArrayList<Log>>(){}.getType());
                loadLogs(logs);
            }else{
                titleLabel.setText("Sales Status");
                requestQueries.clear();
                ArrayList<Log> logs = RequestHandler.get("/accounts/manager_account_controller/manager_sales_history/",
                        requestQueries, true, new TypeToken<ArrayList<Log>>(){}.getType());
                loadLogs(logs);
            }
        } catch (ResourceException e){
            if (e.getStatus().equals(Status.CLIENT_ERROR_UNAUTHORIZED))
                logout();
        }
    }

    private void loadLogs(Collection<Log> values) {
        for (Log log : values) {
            try {
                AnchorPane item = FXMLLoader.load(getClass().getResource("/layouts/customer_menus/customer_orders_menus/order_item.fxml"));
                HBox hBox = (HBox) item.getChildren().get(0);
                setLabelsContent(log, hBox);
                vBoxItems.getChildren().add(item);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void setLabelsContent(Log log, HBox hBox) {
        Label logIdLabel =((Label) hBox.getChildren().get(0));
        Label dateLabel =((Label) hBox.getChildren().get(1));
        Label costLabel =((Label) hBox.getChildren().get(2));
        Label discountLabel =((Label) hBox.getChildren().get(3));

        logIdLabel.setText(log.getId());
        System.out.println(log.getId());
        dateLabel.setText(log.getDate().toString());
        System.out.println(log.getDate().toString());
        costLabel.setText(Double.toString(log.getCost()));
        System.out.println(log.getCost());
        discountLabel.setText(Double.toString(log.getCostWithoutDiscount()));
        System.out.println(log.getCostWithoutDiscount());
    }
}