
package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.ComplaintData;
import il.cshaifasweng.OCSFMediatorExample.entities.ComplaintListData;
import il.cshaifasweng.OCSFMediatorExample.server.Complaint;
import il.cshaifasweng.OCSFMediatorExample.server.Complaints;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class ComplaintsEmployee {

    int updateID = -1;
    int compValue = 0;

    TableView<ComplaintData> table = new TableView<ComplaintData>();

    @FXML // fx:id="idCol"
    private TableColumn<ComplaintData, Integer> idCol; // Value injected by FXMLLoader

    @FXML // fx:id="timeCol"
    private TableColumn<ComplaintData, LocalDate> timeCol; // Value injected by FXMLLoader

    @FXML // fx:id="DescCol"
    private TableColumn<ComplaintData, String> DescCol; // Value injected by FXMLLoader

    @FXML // fx:id="statusCol"
    private TableColumn<ComplaintData, String> statusCol; // Value injected by FXMLLoader

    @FXML // fx:id="closeBtn"
    private Button closeBtn; // Value injected by FXMLLoader

    @FXML // fx:id="Vbox"
    private VBox Vbox; // Value injected by FXMLLoader

    @FXML // fx:id="idList"
    private ComboBox<Integer> idList; // Value injected by FXMLLoader

    @FXML // fx:id="compTxt"
    private TextField compTxt; // Value injected by FXMLLoader

    ObservableList<ComplaintData> complaintsList = FXCollections.observableArrayList();

    @FXML
    private Button MainMenuButton;

    @Subscribe
    public void onReceivedComplaints(ReceivedComplaintsEvent event) throws IOException{
        List<ComplaintData> eventList = event.getComplaints();
        for(int i = 0; i < eventList.size(); i++){
            complaintsList.add(eventList.get(i));
        }
        buildPricesTable();
    }

    private void buildPricesTable(){
        idCol.setCellValueFactory(new PropertyValueFactory("id"));
        timeCol.setCellValueFactory(new PropertyValueFactory("date"));
        DescCol.setCellValueFactory(new PropertyValueFactory("complaintTxt"));
        statusCol.setCellValueFactory(new PropertyValueFactory("status"));

        table.getColumns().addAll(idCol, timeCol, DescCol, statusCol);
        table.setItems(complaintsList);
        Vbox.getChildren().clear();
        Vbox.getChildren().add(table);
        assert idList != null : "fx:id=\"idList\" was not injected: check your FXML file 'primary.fxml'.";

        if(complaintsList != null) {
            for (int i = 0; i < complaintsList.size(); i++) {
                idList.getItems().add((complaintsList.get(i)).getId());
            }
        }
    }

    private ObservableList<ComplaintData> getUserList() {
        return complaintsList;
    }

    @FXML
    void initialize() throws IOException {
        EventBus.getDefault().register(this);
    }

    @FXML
    void goToMainMenu(ActionEvent event) throws IOException {
        App.setRoot("primary");
    }

    @FXML
    void closeComp(ActionEvent event) throws IOException {
        try{
            compValue = Integer.parseInt(compTxt.getText());
        }
        catch (NumberFormatException ex){
            ex.printStackTrace();
        }
        SimpleClient myclient=SimpleClient.getClient();
        System.out.format("Sending update to the server \n");
        myclient.changeCompStatus(compValue, updateID);
        //database should be updated here

        ObservableList<ComplaintData> list = getUserList();

        // only for example, should be deleted later:

        table.setItems(list);
        Vbox.getChildren().addAll(table);

    }

    @FXML
    void chooseFromIDList(ActionEvent event) {
        int intChosen = idList.getSelectionModel().getSelectedItem(); //id combo box
        updateID = intChosen;
    }

}