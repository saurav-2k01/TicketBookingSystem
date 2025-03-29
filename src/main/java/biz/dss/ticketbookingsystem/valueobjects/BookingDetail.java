package biz.dss.ticketbookingsystem.valueobjects;

import biz.dss.ticketbookingsystem.enums.TravellingClass;
import biz.dss.ticketbookingsystem.models.Station;
import biz.dss.ticketbookingsystem.models.Train;
import biz.dss.ticketbookingsystem.models.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
public class BookingDetail {
    private Train train;
    private TravellingClass travellingClass;
    private Station from;
    private Station to;
    private LocalDate dateOfJourney;
    private List<User> passengerList;
    private final  Double totalFare;

//    public BookingDetail(Train train, TravellingClass travellingClass, Station from, Station to, LocalDate dateOfJourney) {
//        this.train = train;
//        this.travellingClass = travellingClass;
//        this.from = from;
//        this.to = to;
//        this.dateOfJourney = dateOfJourney;
//    }
}
