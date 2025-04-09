package biz.dss.ticketbookingsystem.models;

import biz.dss.ticketbookingsystem.enums.TravellingClass;
import biz.dss.ticketbookingsystem.utils.UtilClass;
import lombok.Data;

@Data
public class Coach {
    private Integer id = UtilClass.random.nextInt(100, 10_00_000);
    private TravellingClass travellingClass;
    private String coachName;
    private Integer totalSeats;
    private Integer availableSeats;
    private Double fareFactor;

    public Coach( TravellingClass travellingClass, String coachName, Integer totalSeats,Double fareFactor) {
        this.travellingClass = travellingClass;
        this.coachName = coachName;
        this.totalSeats = totalSeats;
        this.fareFactor = fareFactor;
    }

}
