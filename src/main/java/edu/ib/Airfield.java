package edu.ib;

/**
 * edu.ib.Airfield
 *  A class that represents an airfield. Contains moist important information such as city name and codes.
 *
 * @author FR, MD
 * @version 1.0
 * @since 2022-02-09
 */
public class Airfield {

    private String city;

    private String iataCode;

    private String icaoCode;

    private String airfieldName;

    /**
     * @param city         Citi in which the airfield is located.
     * @param iataCode     Airfields IATA code.
     * @param icaoCode     Airfields ICAO code.
     * @param airfieldName Airfields name.
     */
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
    /*
      To string is overridden to show most important information in ComboBox
     */
    public String toString() {
        if (city.equals("null")) return "None";
        return city + "-" + icaoCode;
    }
}
