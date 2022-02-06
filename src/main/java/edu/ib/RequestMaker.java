package edu.ib;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;

public class RequestMaker {
    //private long unixTime = System.currentTimeMillis() / 1000L; // current Unix time
    private LocalDate date = LocalDate.now();   // dzisiejsza data
    private ZoneId zoneId = ZoneId.systemDefault(); // defioniowanie strey czasowej -- to może stanowić problem (ale nie musi)
    private long epoch = date.atStartOfDay(zoneId).toEpochSecond(); // zamienia czas na unix - używany przez api




    private String url = "https://USERNAME:PASSWORD@opensky-network.org/api/flights/departure?" +
            "airport=EPWR" +
            "&begin="+date.minusDays(5).atStartOfDay(zoneId).toEpochSecond() +  // data 5 dni temu w unixie (test czy działa)
            "&end="+date.atStartOfDay(zoneId).toEpochSecond();                  // data dzisiaj rano w unixie

    public String getData(){

        StringBuffer response = new StringBuffer();

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
