package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.CancelOrderData;
import il.cshaifasweng.OCSFMediatorExample.entities.OrderData;
import il.cshaifasweng.OCSFMediatorExample.entities.OrdersListData;
import il.cshaifasweng.OCSFMediatorExample.entities.ParkingLotData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static il.cshaifasweng.OCSFMediatorExample.client.PrimaryController.isLightMode;

public class CancelOrder {

        @FXML
        private Button BackBtn;

        @FXML
        private TextField CarNumberTF;

        @FXML
        private TextField IdTF;

        @FXML
        private Button SendBtn;

        @FXML
        private Button btnMode;

        @FXML
        private ImageView imMode;

        @FXML
        private AnchorPane parent;

        ObservableList<OrderData> orderList = FXCollections.observableArrayList();


        @FXML
        void goToMainMenu(ActionEvent event) throws IOException {
            App.setRoot("MainMenuOrder");
        }


        @FXML
        void initialize() {
                EventBus.getDefault().register(this);
                if(!isLightMode){
                        PrimaryController.setDarkMode(parent, imMode);
                }
        }

        // start choice box if its cancel (select the order to cancel).
        @Subscribe
        public void startChoiceBox(ReceivedOrderList event) throws Exception {
                // if not cancel mode, return (can be also tracking).
                if(!Objects.equals(event.getMode(), "cancel")){ return; }

                List<OrderData> eventList = event.getOrderListData();
                OrdersListData deleteList = new OrdersListData(eventList);
                // init orders vector
                for (int i = 0; i < eventList.size(); i++) {
                        orderList.add(eventList.get(i));
                }

                Stage choiceStage = new Stage();
                choiceStage.setTitle("Select orders to cancel");
                Button deleteBtn = new Button("Cancel Orders");

                // create choice box for each order.
                ObservableList<CheckBox> checkBoxes = FXCollections.observableArrayList();
                int i = 1;
                for (OrderData orderData : eventList) {
                        CheckBox checkBox = new CheckBox(i++ + "." + " Order id: " + orderData.getOrderId() +
                                " ,Car Number: " + orderData.getCarNumber() + " ,Expected Arrival Date: "
                                + orderData.getArrivalDate().toString() + " ,From:" + orderData.getLeavingTime()
                                + " ,Expected Leaving Date: " + orderData.getLeavingDate().toString() + " ,Until: " + orderData.getLeavingTime());
                        checkBoxes.add(checkBox);
                }

                VBox vBox = new VBox();
                for(CheckBox checkBox:checkBoxes)
                        vBox.getChildren().add(checkBox);

                // in send cancel - send to server orders list for deletion.
                deleteBtn.setOnAction((actionEvent) -> {
                        for (CheckBox checkBox : checkBoxes)
                                if (!checkBox.isSelected()) {
                                        char index = checkBox.getText().toCharArray()[0];
                                        OrderData orderData = eventList.get(Integer.parseInt(Character.toString(index))-1);
                                        deleteList.remove(orderData);
                                }
                        try{
                                SimpleClient.getClient().sendToServer(deleteList);
                        } catch (IOException e) {
                                e.printStackTrace();}
                });

                // show choices to client
                vBox.getChildren().add(deleteBtn);
                Scene scene = new Scene(vBox, 800, 200);
                choiceStage.setScene(scene);
                choiceStage.show();
        };


        // send cancel request to server - via id and car number.
        @FXML
        void sendCancellation() {
                try{
                        CancelOrderData trackingOrder =
                        new CancelOrderData(Integer.parseInt(IdTF.getText()), Integer.parseInt(CarNumberTF.getText()));
                        SimpleClient.getClient().sendToServer(trackingOrder);
                } catch (IOException e) {
                        e.printStackTrace();}
        }

        // change mode (dark\light).
        @FXML
        public void changeMode(ActionEvent event){
                PrimaryController.ChangeForAll(parent, imMode);
        }


    }

