package biz.dss.ticketbookingsystem.valueobjects;


import biz.dss.ticketbookingsystem.models.Station;

import java.time.LocalDate;

public class TrainSearchDetail {
    private Station source;
    private Station destination;
    private LocalDate date;

    public TrainSearchDetail(Station source, Station destination){
        this.source = source;
        this.destination = destination;
    }

    public TrainSearchDetail(Station source, Station destination, LocalDate date){
        this.source = source;
        this.destination = destination;
        this.date = date;
    }

    public Station getSource() {
        return source;
    }

    public void setSource(Station source) {
        this.source = source;
    }

    public Station getDestination() {
        return destination;
    }

    public void setDestination(Station destination) {
        this.destination = destination;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
