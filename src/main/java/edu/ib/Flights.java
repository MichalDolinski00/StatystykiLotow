package edu.ib;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class Flights {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private CategoryAxis a_dayOfTheWeek;

    @FXML
    private NumberAxis a_numberOfPlanes;

    @FXML
    private Button bt_arrivals;

    @FXML
    private Button bt_departures;

    @FXML
    private BarChart<?, ?> graph;

    @FXML
    private TextField tf_airfield;

    @FXML
    void arrivalsCount(ActionEvent event) {

    }

    @FXML
    void departuresCount(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert a_dayOfTheWeek != null : "fx:id=\"a_dayOfTheweek\" was not injected: check your FXML file 'edu.ib.Flights.fxml'.";
        assert a_numberOfPlanes != null : "fx:id=\"a_numberOfPlanes\" was not injected: check your FXML file 'edu.ib.Flights.fxml'.";
        assert bt_arrivals != null : "fx:id=\"bt_arrivals\" was not injected: check your FXML file 'edu.ib.Flights.fxml'.";
        assert bt_departures != null : "fx:id=\"bt_departures\" was not injected: check your FXML file 'edu.ib.Flights.fxml'.";
        assert graph != null : "fx:id=\"graph\" was not injected: check your FXML file 'edu.ib.Flights.fxml'.";
        assert tf_airfield != null : "fx:id=\"tf_airfield\" was not injected: check your FXML file 'edu.ib.Flights.fxml'.";

    }

}
