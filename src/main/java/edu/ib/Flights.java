package edu.ib;

import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class Flights {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField arrivalAirportField;

    @FXML
    private CategoryAxis a_dayOfTheWeek;

    @FXML
    private NumberAxis a_numberOfPlanes;

    @FXML
    private TextField dateFromField;

    @FXML
    private TextField dateToField;

    @FXML
    private TextField departureAirfieldField;

    @FXML
    private BarChart<?, ?> graph;

    @FXML
    private Button plotButton;

    @FXML
    void plotAction(ActionEvent event) {
        String departure = departureAirfieldField.getText().toString();
        String arrival = arrivalAirportField.getText().toString();
        LocalDate to;
        LocalDate from;
        long days;
        try{
            to = LocalDate.parse(dateToField.getText().toString());
        }catch (Exception e){
            return;
        }
        try {
            from = LocalDate.parse(dateFromField.getText().toString());
        } catch (Exception e){
            from = null;
        }
        if (from==null){
            days = 7;
        } else {
            days = ChronoUnit.DAYS.between(from,to);
        }
        RequestMaker maker = new RequestMaker();


        if (arrival.equals("") && departure.equals(""))
            return;
        if (arrival.equals(""))
            maker.

        XYChart.Series series = new XYChart.Series<>();
        series.getData().add(new XYChart.Data<>(DayOfWeek.MONDAY.toString(), 10));
        series.getData().add(new XYChart.Data<>(DayOfWeek.TUESDAY.toString(), 8));
        series.getData().add(new XYChart.Data<>(DayOfWeek.WEDNESDAY.toString(), 4));
        series.getData().add(new XYChart.Data<>(DayOfWeek.THURSDAY.toString(), 5));
        series.getData().add(new XYChart.Data<>(DayOfWeek.FRIDAY.toString(), 3));
        series.getData().add(new XYChart.Data<>(DayOfWeek.SATURDAY.toString(), 2));
        series.getData().add(new XYChart.Data<>(DayOfWeek.SUNDAY.toString(), 1));
        graph.getData().addAll(series);
    }

    @FXML
    void initialize() {
        assert arrivalAirportField != null : "fx:id=\"ArrivalAirportField\" was not injected: check your FXML file 'Flights.fxml'.";
        assert a_dayOfTheWeek != null : "fx:id=\"a_dayOfTheWeek\" was not injected: check your FXML file 'Flights.fxml'.";
        assert a_numberOfPlanes != null : "fx:id=\"a_numberOfPlanes\" was not injected: check your FXML file 'Flights.fxml'.";
        assert dateFromField != null : "fx:id=\"dateFromField\" was not injected: check your FXML file 'Flights.fxml'.";
        assert dateToField != null : "fx:id=\"dateToField\" was not injected: check your FXML file 'Flights.fxml'.";
        assert departureAirfieldField != null : "fx:id=\"departureAirfieldField\" was not injected: check your FXML file 'Flights.fxml'.";
        assert graph != null : "fx:id=\"graph\" was not injected: check your FXML file 'Flights.fxml'.";
        assert plotButton != null : "fx:id=\"plotButton\" was not injected: check your FXML file 'Flights.fxml'.";
        XYChart.Series series = new XYChart.Series<>();
        series.getData().add(new XYChart.Data<>(DayOfWeek.MONDAY.toString(), 0));
        series.getData().add(new XYChart.Data<>(DayOfWeek.TUESDAY.toString(), 0));
        series.getData().add(new XYChart.Data<>(DayOfWeek.WEDNESDAY.toString(), 0));
        series.getData().add(new XYChart.Data<>(DayOfWeek.THURSDAY.toString(), 0));
        series.getData().add(new XYChart.Data<>(DayOfWeek.FRIDAY.toString(), 0));
        series.getData().add(new XYChart.Data<>(DayOfWeek.SATURDAY.toString(), 0));
        series.getData().add(new XYChart.Data<>(DayOfWeek.SUNDAY.toString(), 0));
        graph.getData().addAll(series);
    }

}
