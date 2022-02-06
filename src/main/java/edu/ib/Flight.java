package edu.ib;

import java.time.LocalDate;

public class Flight {
    private String from;
    private String to;
    private LocalDate departure;
    private LocalDate arrival;

    public Flight(String from, String to, LocalDate departure, LocalDate arrival) {
        this.from = from;
        this.to = to;
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
}
