package view.account_menus.manager_view.requests_view;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.requests.Request;
import model.users.Manager;
import view.MenuManager;

import java.net.URL;
import java.util.ResourceBundle;

public class RequestMenuManager extends MenuManager implements Initializable {
    public VBox vBoxItems;
    private controller.accounts.ManagerAccountController managerAccountController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        managerAccountController = controller.accounts.ManagerAccountController.getInstance();
        loadRequests();
    }

    private void loadRequests() {
        for (Request request : Manager.getRequests()) {
            try {
                AnchorPane item = (AnchorPane) FXMLLoader.load(getClass().getResource("/layouts/request_menus/request_item.fxml"));
                HBox hBox = (HBox) item.getChildren().get(0);
                setLabelsContent(request, hBox);
                vBoxItems.getChildren().add(item);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void setLabelsContent(Request request, HBox hBox) {
        Label requestIdLabel =((Label) hBox.getChildren().get(0));
        Label requestTypeLabel =((Label) hBox.getChildren().get(1));
        Label requestStatusLabel =((Label) hBox.getChildren().get(2));
        requestIdLabel.setText(request.getRequestId());
        requestTypeLabel.setText(request.getType());
        requestStatusLabel.setText(request.getStatus());
    }

}
