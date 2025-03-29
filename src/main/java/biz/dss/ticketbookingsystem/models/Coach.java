package biz.dss.ticketbookingsystem.models;

import biz.dss.ticketbookingsystem.enums.TravellingClass;
import lombok.Data;

@Data
public class Coach {
    private Integer id;
    private TravellingClass travellingClass;
    private String coachName;
    private Integer totalSeats;
    private Integer availableSeats;
    private Double fareFactor;
//    private List<Seat> seatList;


    public Coach(TravellingClass travellingClass, String coachName, Integer totalSeats,Double fareFactor) {
        this.id = (int)(Math.random()*1000);
        this.travellingClass = travellingClass;
        this.coachName = coachName;
        this.totalSeats = totalSeats;
        this.fareFactor = fareFactor;
    }

}
