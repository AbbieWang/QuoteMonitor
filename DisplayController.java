import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class DisplayController implements Initializable {

    @FXML
    private TitledPane stock1;
    @FXML
    private TextField p1;
    @FXML
    private Label dateText;
    @FXML
    private Label timeText;

    Data data = new Data();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        date();
        time();
        try {
            setGoogLabel();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //gets current time and keeps clock running to show real time
    public void time(){
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
            timeText.setText(LocalDateTime.now().format(formatter));
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    //gets current date and keeps clock running to show real time
    public void date(){
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, MMM dd, yyyy");
            dateText.setText(LocalDateTime.now().format(formatter));
        }), new KeyFrame(Duration.hours(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    public String convertToString(double price) {
        return price+"";
    }

    @FXML
    public void getGoogPrice() throws Exception {
       String price = convertToString(data.getRealTimeData("GOOG"));
       p1.setText(price);
    }

    @FXML
    public void setGoogLabel() throws Exception {
        double change = data.getChangesPercentage("GOOG");
        stock1.setText("Alphabet Inc. (GOOG)                  +" + change + "%");
    }


}
