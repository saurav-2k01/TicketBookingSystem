package biz.dss.ticketbookingsystem.valueobjects;

import biz.dss.ticketbookingsystem.enums.TravellingClass;
import biz.dss.ticketbookingsystem.intefaces.Formatable;

import java.util.List;

public class AvailableSeats implements Formatable {
    private final int serialNo;
    private final TravellingClass travellingClass;
    private final int availableSeats;
    private final double fare;

    public AvailableSeats(int serialNo, TravellingClass travellingClass, int availableSeats, double fare){
        this.serialNo = serialNo;
        this.travellingClass = travellingClass;
        this.availableSeats = availableSeats;
        this.fare = fare;
    }

    public TravellingClass getTravellingClass() {
        return travellingClass;
    }

    public int getAvailableSeats(){
        return availableSeats;
    }

    public double getFare() {
        return fare;
    }

    @Override
    public List<String> fieldsToDisplay() {
        return List.of("Sr. No.", "Travelling Class", "Available Seats", "Fare");
    }

    @Override
    public List<String> getFieldValues() {
        return List.of(String.valueOf(serialNo), travellingClass.toString(), String.valueOf(availableSeats),  String.valueOf(fare));
    }

    @Override
    public String getDisplayableTitle() {
        return "Available Seats with Fare";
    }
}
