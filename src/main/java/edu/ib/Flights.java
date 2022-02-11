package edu.ib;

import java.io.*;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Window;

/**
 * edu.ib.Flights
 * A class used to interact with user
 *
 * @author FR, MD
 * @version 1.0
 * @since 2022-02-09
 */
public class Flights {
    private String departure;
    private String arrival;
    private LocalDate from;
    private LocalDate to;
    private long days;
    private boolean stopped;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;


    @FXML
    private CategoryAxis a_dayOfTheWeek;


    @FXML
    private NumberAxis a_numberOfPlanes;

    @FXML
    private TextField dateFromField;

    @FXML
    private Button saveButton;

    @FXML
    private TextField dateToField;

    @FXML
    private TextField departureAirfieldField;

    @FXML
    private BarChart<String, Integer> graph;

    @FXML
    private Button plotButton;
    @FXML
    private ComboBox<Airfield> cb1;

    @FXML
    private ComboBox<Airfield> cb2;

    /**
     * Clears the graph
     * @param event on button click
     */
    @FXML
    void clearAction(ActionEvent event) {
        graph.getData().clear();
    }

    private ArrayList<Airfield> GettingAirfields() {
        // uwaga na lokalizację pliku
        File file = new File("C:\\Users\\olins\\Desktop\\Projekty IntelliJ\\StatystykiLotow\\src\\main\\java\\Lotniska.txt");

        ArrayList<Airfield> airfields = new ArrayList<>();
        try {
            Reader reader = new FileReader(file);
            BufferedReader br = new BufferedReader(reader);
            String st1;
            String[] st2;
            while ((st1 = br.readLine()) != null) {
                st2 = st1.split("\t");
                if (st2.length == 4)
                    airfields.add(new Airfield(st2[0], st2[1], st2[2], st2[0]));
                else
                    airfields.add(new Airfield(st2[0], st2[1], st2[2], "-"));
            }
            br.close();
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return airfields;

    }

    /**
     * Class gets users input from fields and shows alerts if rogue data appears
     */
    private void getUserInput(){
        Window owner = plotButton.getScene().getWindow();;
        if (cb1.getValue() != null && !cb1.getValue().getIcaoCode().equals("null")) departure = cb1.getValue().getIcaoCode();
        else departure = "";
        if (cb2.getValue() != null && !cb2.getValue().getIcaoCode().equals("null")) arrival = cb2.getValue().getIcaoCode();
        else arrival = "";
        if (arrival.equals("") && departure.equals("")){
            stopped = true;
            showAlert(Alert.AlertType.ERROR, owner, "Invalid airfields!", "Arrival or departure airfield must be given!");
            return;
        }
        try{
            to = LocalDate.parse(dateToField.getText());
        }catch (Exception e){
            if (!dateToField.getText().equals("")){
                stopped = true;
                showAlert(Alert.AlertType.ERROR, owner, "Invalid date format!", "To date must be given in YYYY-MM-DD format");
                return;
            }
            to = LocalDate.now();
        }
        try {
            from = LocalDate.parse(dateFromField.getText());
        } catch (Exception e){
            if (!dateFromField.getText().equals("")){
                stopped = true;
                showAlert(Alert.AlertType.ERROR, owner, "Invalid date format!", "From date must be given in YYYY-MM-DD format");
                return;
            }
            from = null;
        }
        if (from==null){
            days = 7;
            from = to.minusDays(7);
        } else {
            days = ChronoUnit.DAYS.between(from,to);
        }
    }

    /**
     * Methode displays a specific alert
     * @param alertType type of alert
     * @param owner window stopped by alert
     * @param title tile of alert
     * @param message message of alert
     */
    private void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }

    /**
     * Methode saves flight of set parameters to chosen file
     * @param event on button click
     */
    @FXML
    void saveToFileAction(ActionEvent event) {
        stopped = false;
        getUserInput();
        if (stopped)
            return;
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Notepad", ".txt"),
                new FileChooser.ExtensionFilter("Excel", ".xlsx"),
                new FileChooser.ExtensionFilter("CSV", ".csv")
        );
        File file = fileChooser.showSaveDialog(null);
        if (file != null){
            String fileToSave = file.getAbsolutePath();
            File fileToWrite = new File(fileToSave);
            FileWriter fileWriter;
            BufferedWriter bufferedWriter;
            try {
                RequestMaker maker;
                if (departure.equals("")){
                    maker = new RequestMaker(to, arrival, (int)days);
                    maker.setDeparture(false);
                } else maker = new RequestMaker(to, departure, (int)days);
                    fileWriter = new FileWriter(fileToWrite);
                    bufferedWriter = new BufferedWriter(fileWriter);
                    bufferedWriter.write("Flight from\tTo\tDeparture Date\tArrival Date\tDeparture Day\tArrival Day");
                    bufferedWriter.newLine();
                    if (arrival.equals(""))
                    for (Flight flight: maker.getFlights()){
                        bufferedWriter.write(flight.getFrom() + "\t" + flight.getTo() + "\t" + flight.getDeparture()
                                + "\t" + flight.getArrival() + "\t" + flight.getDepartureDay() +"\t"+ flight.getArrivalDay());
                        bufferedWriter.newLine();
                    }
                    else
                        for (Flight flight: maker.getFlights()){
                            if (flight.getTo().equals(arrival)){
                                bufferedWriter.write(flight.getFrom() + "\t" + flight.getTo() + "\t" + flight.getDeparture()
                                        + "\t" + flight.getArrival() + "\t" + flight.getDepartureDay() +"\t"+ flight.getArrivalDay());
                                bufferedWriter.newLine();
                            }
                    }
                    bufferedWriter.close();
                    fileWriter.close();
            } catch (IOException e){
                System.out.println(e.getMessage());
            }
        }}

    /**
     * Methode plots flights of set parameters on graph
     * @param event on button click
     */
    @FXML
    void plotAction(ActionEvent event) {
        stopped = false;
        getUserInput();
        if (stopped) return;
        RequestMaker maker;
        int[] daysOfWeek = new int[7];
        XYChart.Series series = new XYChart.Series<>();
        if (arrival.equals("")){
            getUserInput();
            series.setName("From " + departure + " since " + from + " to " + to);
            maker = new RequestMaker(to, departure, (int)days);
            for (Flight flight: maker.getFlights()){
                switch (flight.getDepartureDay()){
                    case MONDAY -> daysOfWeek[0]++;
                    case TUESDAY -> daysOfWeek[1]++;
                    case WEDNESDAY -> daysOfWeek[2]++;
                    case THURSDAY -> daysOfWeek[3]++;
                    case FRIDAY -> daysOfWeek[4]++;
                    case SATURDAY -> daysOfWeek[5]++;
                    case SUNDAY -> daysOfWeek[6]++;
                }
            }
        } else if (departure.equals("")){
            series.setName("To " + arrival + " since " + from + " to " + to);
            maker = new RequestMaker(to, arrival, (int)days);
            maker.setDeparture(false);
            for (Flight flight: maker.getFlights())
                switch (flight.getArrivalDay()){
                    case MONDAY -> daysOfWeek[0]++;
                    case TUESDAY -> daysOfWeek[1]++;
                    case WEDNESDAY -> daysOfWeek[2]++;
                    case THURSDAY -> daysOfWeek[3]++;
                    case FRIDAY -> daysOfWeek[4]++;
                    case SATURDAY -> daysOfWeek[5]++;
                    case SUNDAY -> daysOfWeek[6]++;
                }
        } else {
            series.setName("From " + departure + " to "+arrival+" since " + from + " to " + to);
            maker = new RequestMaker(to, departure, (int)days);
            for (Flight flight: maker.getFlights()){
                if (flight.getTo().equals(arrival))
                    switch (flight.getDepartureDay()){
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

    /**
     * Methode initializes scene
     */
    @FXML
    void initialize() {
        assert a_dayOfTheWeek != null : "fx:id=\"a_dayOfTheWeek\" was not injected: check your FXML file 'Flights.fxml'.";
        assert a_numberOfPlanes != null : "fx:id=\"a_numberOfPlanes\" was not injected: check your FXML file 'Flights.fxml'.";
        assert dateFromField != null : "fx:id=\"dateFromField\" was not injected: check your FXML file 'Flights.fxml'.";
        assert dateToField != null : "fx:id=\"dateToField\" was not injected: check your FXML file 'Flights.fxml'.";
        assert departureAirfieldField != null : "fx:id=\"departureAirfieldField\" was not injected: check your FXML file 'Flights.fxml'.";
        assert graph != null : "fx:id=\"graph\" was not injected: check your FXML file 'Flights.fxml'.";
        assert plotButton != null : "fx:id=\"plotButton\" was not injected: check your FXML file 'Flights.fxml'.";
        graph.setAnimated(false);
        ArrayList<Airfield> airfields = GettingAirfields();
        ObservableList<Airfield> options = FXCollections.observableArrayList(airfields);
        cb1.setItems(options);
        cb2.setItems(options);

        dateToField.setPromptText(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    }

}
