package edu.ib;

import java.time.DayOfWeek;
import java.time.LocalDate;

/**
 * edu.ib.Flight
 * class holding and displaying information about flight
 *
 * @author FR, MD
 * @version 1.0
 * @since 2022-02-09
 */
public class Flight {
    private String from;
    private String to;
    private LocalDate departure;
    private LocalDate arrival;

    /**
     * Constructor
     * @param from airfields ICAO code
     * @param to airfields ICAO code
     * @param departure date of departure
     * @param arrival date of arrival
     */
    public Flight(String from, String to, LocalDate departure, LocalDate arrival) {
        this.from = from.replace("\"","");
        this.to = to.replace("\"","");
        this.departure = departure;
        this.arrival = arrival;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public LocalDate getDeparture() {
        return departure;
    }

    public void setDeparture(LocalDate departure) {
        this.departure = departure;
    }

    public LocalDate getArrival() {
        return arrival;
    }

    public void setArrival(LocalDate arrival) {
        this.arrival = arrival;
    }

    @Override
    public String toString() {
        return "Flight{" +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", departure=" + departure +
                ", arrival=" + arrival +
                '}';
    }

    /**
     * Returns day of the week of departure
     * @return DayOfWeek
     */
    public DayOfWeek getDepartureDay(){
        LocalDate date = getDeparture();
        return date.getDayOfWeek();
    }

    /**
     * Returns day of the week of arrival
     * @return DayOfWeek
     */
    public DayOfWeek getArrivalDay(){
        LocalDate date = getArrival();
        return date.getDayOfWeek();
    }
}
