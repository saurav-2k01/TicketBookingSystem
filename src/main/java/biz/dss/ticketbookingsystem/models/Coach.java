package biz.dss.ticketbookingsystem.models;

import biz.dss.ticketbookingsystem.enums.TravellingClass;
import biz.dss.ticketbookingsystem.intefaces.Formatable;
import biz.dss.ticketbookingsystem.utils.UtilClass;
import lombok.Data;

import java.util.List;

@Data
public class Coach implements Formatable {
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

    @Override
    public List<String> fieldsToDisplay() {
        return List.of("Id", "Coach Name","Travelling Class", "Fare Factor");
    }

    @Override
    public List<String> getFieldValues() throws NullPointerException {

        return List.of(id.toString(), coachName, travellingClass.toString(), fareFactor.toString());
    }

    @Override
    public String getDisplayableTitle() {
        return "Coaches";
    }
}
