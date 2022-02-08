package edu.ib;

/**
 * A class that represents an airfield. Contains moist important information such as city name and codes.
 */
public class Airfield {

    private String city;
    private String iataCode;
    private String icaoCode;
    private String airfieldName;


    public Airfield(String city, String iataCode, String icaoCode, String airfieldName) {
        this.city = city;
        this.iataCode = iataCode;
        this.icaoCode = icaoCode;
        this.airfieldName = airfieldName;
    }

    public String getCity() {
        return city;
    }

    public String getIataCode() {
        return iataCode;
    }

    public String getIcaoCode() {
        return icaoCode;
    }

    public String getAirfieldName() {
        return airfieldName;
    }

    @Override
    public String toString() {
        return  city +"-" +icaoCode ;
    }
}
