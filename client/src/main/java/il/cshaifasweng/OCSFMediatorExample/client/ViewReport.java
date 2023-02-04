package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.client.App;
import il.cshaifasweng.OCSFMediatorExample.client.PrimaryController;
import il.cshaifasweng.OCSFMediatorExample.entities.ParkingLotData;
import il.cshaifasweng.OCSFMediatorExample.entities.ParkingPricesData;
import il.cshaifasweng.OCSFMediatorExample.entities.ReportData;
import il.cshaifasweng.OCSFMediatorExample.entities.ReportListData;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.print.*;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.transform.Scale;
import javafx.util.Duration;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.lang.Object;

import static il.cshaifasweng.OCSFMediatorExample.client.PrimaryController.isLightMode;

public class ViewReport {

    @FXML
    private Button BackToMain;

    @FXML
    private Button btnMode;

    @FXML
    private TextField casualTxt;

    @FXML
    private TextField compaintsTxt;

    @FXML
    private TextField endDateTxt;

    @FXML
    private TextField idTxt;

    @FXML
    private ImageView imMode;

    @FXML
    private TextField inAdvanceTxt;

    @FXML
    private TextField malfunctuinTxt;

    @FXML
    private AnchorPane parent;

    @FXML
    private ComboBox<String> reportCombo;

    @FXML
    private TextField startDateTxt;

    @FXML
    private TextField timeTF;

    ObservableList<ReportData> reportsList = FXCollections.observableArrayList();

   @Subscribe
    public void onReceivedReportList(ReceivedReportsEvent event) throws IOException {
        List<ReportData> eventList = event.getReportDataList();
        for (int i = 0; i < eventList.size(); i++) {
            reportsList.add(eventList.get(i));
        }
      //  assert reportCombo != null : "fx:id=\"reportCombo\" was not injected: check your FXML file 'primary.fxml'.";
        for (ReportData reportData : reportsList) {
            reportCombo.getItems().add(reportData.getReportNumber() + " - in: " + reportData .getLotId() + ", from: "
                    + reportData.startDate.format(DateTimeFormatter.ofPattern("dd-MM-yy")) + ", to: "
                            + reportData.endDate.format(DateTimeFormatter.ofPattern("dd-MM-yy")));
        }
    }

    public void showReport(ReportData report){
        startDateTxt.setText(report.getStartDate().format(DateTimeFormatter.ofPattern("dd-MM-yy")));
        endDateTxt.setText(report.getEndDate().format(DateTimeFormatter.ofPattern("dd-MM-yy")));
        idTxt.setText(report.getLotId());
        casualTxt.setText(Integer.toString(report.getCasualNumber()));
        inAdvanceTxt.setText(Integer.toString(report.getInAdvanceNumber()));
        compaintsTxt.setText(Integer.toString(report.getComplaintsNumber()));
        malfunctuinTxt.setText(Integer.toString(report.getMalfunctuinsNumber()));

   }

    @FXML
    void initialize() {
        if(!isLightMode){
            PrimaryController.setDarkMode(parent, imMode);
        }
       EventBus.getDefault().register(this);
        try {
            SimpleClient.getClient().sendToServer("#request: reports list");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
            timeTF.setText(currentTime.format(dtf));
        }),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    @FXML
    public void changeMode(ActionEvent event){
        PrimaryController.ChangeForAll(parent, imMode);
    }


    @FXML
    void chooseReport(ActionEvent event) {
       String reportNumber = reportCombo.getSelectionModel().getSelectedItem();
       String arr[] = reportNumber.split(" ", 2);
       String firstWord = arr[0];
       int reportID = Integer.valueOf(firstWord);
       for (ReportData reportData : reportsList) {
           if (reportData.getReportNumber() == reportID) {
               showReport(reportData);
               return ;
           }
        }

    }

    @FXML
    void goToMainMenu(ActionEvent event) throws IOException {
        App.history.remove(App.history.size()-1);
        App.setRoot(App.history.get(App.history.size()-1));
    }

    @FXML
    void printReport(ActionEvent event) {
        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null) {
            AnchorPane temp = new AnchorPane(parent);
            PageLayout layout = job.getPrinter()
                    .createPageLayout(Paper.A4, PageOrientation.LANDSCAPE, Printer.MarginType.HARDWARE_MINIMUM);
            job.getJobSettings().setPageLayout(layout);
            job.printPage(parent);
            job.endJob();
        }
    }

}
