package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static il.cshaifasweng.OCSFMediatorExample.client.PrimaryController.isLightMode;

public class EmployeesMainWindow {

    @FXML
    private TextField timeTF;

    @FXML
    private AnchorPane parent;

    @FXML
    private ImageView imMode;

    @FXML
    private Button button1;

    @FXML
    private Button button2;

    @FXML
    private Button button3;

    @FXML
    private Button button4;

    @FXML
    private Button button5;

    @FXML
    private Button button6;

    @FXML
    public void changeMode(ActionEvent event){
        PrimaryController.ChangeForAll(parent, imMode);
    }

    @FXML
    public void LogOut(ActionEvent event) throws IOException {
        SimpleClient myClient = SimpleClient.getClient();
        myClient.sendToServer("logout employee:"+App.employee.getId());
        App.history.remove(App.history.size()-1);
        App.setRoot(App.history.get(App.history.size()-1));
    }


    @FXML
    void button1Func(ActionEvent event) throws IOException {
        if(button1.getText().equals("Statistical Information"))
        {
            SimpleClient.getClient().sendToServer("#request: stastistical information list");
            System.out.println();
            App.setRoot("StastisticalInformation");
            App.history.add("StastisticalInformation");
            App.setRoot("StastisticalInformation");
        }
        else if(button1.getText().equals("Change Parking Space Status"))
        {
            App.history.add("Malfunctions");
            App.setRoot("Malfunctions");
        }
        else
        {
            SimpleClient.getClient().sendToServer("#request: complaint table");
            App.history.add("ComplaintsEmployee");
            App.setRoot("ComplaintsEmployee");
        }


    }

    @FXML
    void button2Func(ActionEvent event) throws IOException{

        if(button1.getText().equals("Save Parking Spot"))
        {

        }
        else
        {
            App.history.add("ParkingScreenController");
            App.setRoot("ParkingScreenController");
        }

    }

    @FXML
    void button3Func(ActionEvent event) throws IOException{

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize()
    {
        if(!isLightMode){
            PrimaryController.setDarkMode(parent, imMode);
        }
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();
            timeTF.setText(currentTime.format(dtf));
        }),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();

        // Initializing the gui according to the job.
        // 4 types of Employees: company manager,parking lot manager,parking lot worker,costumer service
        System.out.println(App.employee.getJob());
        switch(App.employee.getJob()){
            case "company manager":
                button1.setText("Statistical Information");
                button2.setText("Get Parking Lot Status");
                button3.setVisible(false);
                button4.setVisible(false);
                button5.setVisible(false);
                button6.setVisible(false);
                break;
            case "parking lot manager":
                button1.setText("Statistical Information");
                button2.setVisible(false);;
                button3.setVisible(false);
                button4.setVisible(false);
                button5.setVisible(false);
                button6.setVisible(false);
                break;
            case "parking lot worker":
                button1.setText("Change Parking Space Status");
                button2.setText("Save Parking Spot");
                button3.setText("Referral to another parking lot");
                button4.setVisible(false);
                button5.setVisible(false);
                button6.setVisible(false);
                break;
            default:
                button1.setText("Handle Complaints");
                button2.setText("Save Parking Spot");
                button3.setVisible(false);
                button4.setVisible(false);
                button5.setVisible(false);
                button6.setVisible(false);
                break;
        }
    }
}

