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

    public Coach(Integer id, TravellingClass travellingClass, String coachName, Integer totalSeats,Double fareFactor) {
        this.id = id;
        this.travellingClass = travellingClass;
        this.coachName = coachName;
        this.totalSeats = totalSeats;
        this.fareFactor = fareFactor;
    }

}
