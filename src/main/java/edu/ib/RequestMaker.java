package edu.ib;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

public class RequestMaker {
    private long unixTime = System.currentTimeMillis() / 1000L; // current Unix time
    private LocalDate date;   // dzisiejsza data
    private ZoneId zoneId = ZoneId.systemDefault(); // defioniowanie strey czasowej -- to może stanowić problem (ale nie musi)
    private long epoch; // zamienia czas na unix - używany przez api
    private String airport = "EPWR";
    private int period = 7;

    public RequestMaker(LocalDate date, String airport, int period) {
        this.date = date;
        this.airport = airport;
        this.period = period;
        epoch  = date.atStartOfDay(zoneId).toEpochSecond();
    }


    public RequestMaker(LocalDate date, String airport) {
        this.date = date;
        this.airport = airport;
        epoch  = date.atStartOfDay(zoneId).toEpochSecond();
    }

    public RequestMaker(LocalDate date, int period) {
        this.date = date;
        this.period = period;
        epoch  = date.atStartOfDay(zoneId).toEpochSecond();
    }

    public RequestMaker(LocalDate date) {
        this.date = date;
        epoch  = date.atStartOfDay(zoneId).toEpochSecond();
    }

    public void printFlights(){
        for (Flight flight: getFlights())
            System.out.println(flight);
    }

    public Flight[] getFlights(){
        String[] departures = getDepartures();
        Flight[] flights = new Flight[departures.length];
        LocalDate departureDate;
        LocalDate arrivalDate;
        for (int i=0; i< flights.length; i++){
            String departure = departures[i];
            departure = departure.split(",\"firstSeen\":")[1];
            long longDeparture = Long.parseLong(departure.split(",\"estDepartureAirport\":")[0]);
            departure = departure.split(",\"estDepartureAirport\":")[1];
            String from = departure.split(",\"lastSeen\":")[0];
            departure = departure.split(",\"lastSeen\":")[1];
            long longArrival = Long.parseLong(departure.split(",\"estArrivalAirport\":")[0]);
            departure = departure.split(",\"estArrivalAirport\":")[1];
            String to = departure.split(",\"callsign\":")[0];
            departureDate =
                    Instant.ofEpochSecond(longDeparture).atZone(ZoneId.systemDefault()).toLocalDate();
            arrivalDate =
                    Instant.ofEpochSecond(longArrival).atZone(ZoneId.systemDefault()).toLocalDate();
            flights[i] = new Flight(from,to,departureDate, arrivalDate);
        }
        return flights;
    }


    public void printDepartures(){
        for (String result : getDepartures())
            System.out.println(result);
    }

    public String[] getDepartures(){
        return getData().split("},");

    }

    public String getData(){
        String url = "https://USERNAME:PASSWORD@opensky-network.org/api/flights/departure?" +
                "airport=" + airport +
                "&begin="+date.minusDays(5).atStartOfDay(zoneId).toEpochSecond() +  // data 5 dni temu w unixie (test czy działa)
                "&end="+date.atStartOfDay(zoneId).toEpochSecond();
        StringBuffer response = new StringBuffer();
        System.out.println(url);

        try {
            URL obj = new URL(url);

            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
            connection.setRequestMethod("GET");

            int responceCode = connection.getResponseCode();
            System.out.println("Code" + responceCode); // powinien być 200

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null){
                response.append(line);
            }
            reader.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response.toString();

    }








}
