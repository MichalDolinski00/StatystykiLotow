package edu.ib;

import java.io.*;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

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
    private Button clearButton;

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
    void clearAction(ActionEvent event) {
        graph.getData().clear();
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


    @FXML
    void plotAction(ActionEvent event) {


//        String departure = departureAirfieldField.getText().toString();
//        String arrival = arrivalAirportField.getText().toString();

        String departure = cb1.getValue().getIcaoCode();
        String arrival =  cb2.getValue().getIcaoCode();


        LocalDate to;
        LocalDate from;
        long days;
        try {
            to = LocalDate.parse(dateToField.getText().toString());
        } catch (Exception e) {
            return;
        }
        try {
            from = LocalDate.parse(dateFromField.getText().toString());
        } catch (Exception e) {
            from = null;
        }
        if (from == null) {
            days = 7;
            from = to.minusDays(7);
        } else {
            days = ChronoUnit.DAYS.between(from, to);
        }
        if (arrival.equals("") && departure.equals(""))
            return;
        RequestMaker maker;
        int[] daysOfWeek = new int[7];
        XYChart.Series series = new XYChart.Series<>();
        if (arrival.equals("")) {
            series.setName("From " + departure + " since " + from + " to " + to);
            maker = new RequestMaker(to, departure, (int) days);
            for (Flight flight : maker.getFlights()) {
                System.out.println(flight);
                switch (flight.getDepartureDay()) {
                    case MONDAY -> daysOfWeek[0]++;
                    case TUESDAY -> daysOfWeek[1]++;
                    case WEDNESDAY -> daysOfWeek[2]++;
                    case THURSDAY -> daysOfWeek[3]++;
                    case FRIDAY -> daysOfWeek[4]++;
                    case SATURDAY -> daysOfWeek[5]++;
                    case SUNDAY -> daysOfWeek[6]++;
                }
            }
        } else if (departure.equals("")) {
            series.setName("To " + arrival + " since " + from + " to " + to);
            maker = new RequestMaker(to, arrival, (int) days);
            maker.setDeparture(false);
            for (Flight flight : maker.getFlights())
                switch (flight.getArrivalDay()) {
                    case MONDAY -> daysOfWeek[0]++;
                    case TUESDAY -> daysOfWeek[1]++;
                    case WEDNESDAY -> daysOfWeek[2]++;
                    case THURSDAY -> daysOfWeek[3]++;
                    case FRIDAY -> daysOfWeek[4]++;
                    case SATURDAY -> daysOfWeek[5]++;
                    case SUNDAY -> daysOfWeek[6]++;
                }
        } else {
            series.setName("From " + departure + " to " + arrival + " since " + from + " to " + to);
            maker = new RequestMaker(to, departure, (int) days);
            for (Flight flight : maker.getFlights()) {
                System.out.println(flight.getTo());
                if (flight.getTo().equals(arrival))
                    switch (flight.getDepartureDay()) {
                        case MONDAY -> daysOfWeek[0]++;
                        case TUESDAY -> daysOfWeek[1]++;
                        case WEDNESDAY -> daysOfWeek[2]++;
                        case THURSDAY -> daysOfWeek[3]++;
                        case FRIDAY -> daysOfWeek[4]++;
                        case SATURDAY -> daysOfWeek[5]++;
                        case SUNDAY -> daysOfWeek[6]++;
                    }
            }

        }

        series.getData().add(new XYChart.Data<>(DayOfWeek.MONDAY.toString(), daysOfWeek[0]));
        series.getData().add(new XYChart.Data<>(DayOfWeek.TUESDAY.toString(), daysOfWeek[1]));
        series.getData().add(new XYChart.Data<>(DayOfWeek.WEDNESDAY.toString(), daysOfWeek[2]));
        series.getData().add(new XYChart.Data<>(DayOfWeek.THURSDAY.toString(), daysOfWeek[3]));
        series.getData().add(new XYChart.Data<>(DayOfWeek.FRIDAY.toString(), daysOfWeek[4]));
        series.getData().add(new XYChart.Data<>(DayOfWeek.SATURDAY.toString(), daysOfWeek[5]));
        series.getData().add(new XYChart.Data<>(DayOfWeek.SUNDAY.toString(), daysOfWeek[6]));
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


        ArrayList<Airfield> airfields = GettingAirfields();
        ObservableList<Airfield> options = FXCollections.observableArrayList(airfields);
        cb1.setItems(options);
        cb2.setItems(options);


    }

    /**
     * Creates a list of Airfield items based on a file containing data.
     * @return a list of Airfield items
     */
    private ArrayList<Airfield> GettingAirfields() {
        // naprawde nie wiem czemu nie widzie tego pliku kiedy podaję mu tylko część ścieżki
        File file = new File("C:\\Users\\olins\\Desktop\\Projekty IntelliJ\\StatystykiLotow\\src\\main\\java\\Lotniska.txt");

        ArrayList<Airfield> airfields = new ArrayList<Airfield>();
        try {
            Reader reader = new FileReader(file);
            BufferedReader br = new BufferedReader(reader);
            String st1;
            String[] st2 = new String[4];
            while ((st1 = br.readLine()) != null) {
                st2 = st1.split("\t");
                if (st2.length == 4)
                    airfields.add(new Airfield(st2[0], st2[1], st2[2], st2[0]));
                else
                    airfields.add(new Airfield(st2[0], st2[1], st2[2], "-"));   // jak nie ma ostatniego to jest bez nazwy
            }


            br.close();
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        for (Airfield airfield : airfields){
//            System.out.println(airfield.getIcaoCode());
//        }

        return airfields;

    }

    @FXML
    private ComboBox<Airfield> cb1;


    @FXML
    private ComboBox<Airfield> cb2;


}
