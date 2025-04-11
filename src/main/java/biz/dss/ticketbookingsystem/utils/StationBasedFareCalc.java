package biz.dss.ticketbookingsystem.utils;

import biz.dss.ticketbookingsystem.enums.TravellingClass;
import biz.dss.ticketbookingsystem.models.Coach;
import biz.dss.ticketbookingsystem.models.Station;
import biz.dss.ticketbookingsystem.models.Train;

public class StationBasedFareCalc  implements FareCalculator {
    @Override
    public double calculateFare(Train train, TravellingClass travellingClass, Station source, Station destination) {
        Coach coach = train.getCoachList().stream().filter((c) -> c.getTravellingClass().equals(travellingClass)).toList().getFirst();
        int index1 = train.getRoute().indexOf(source);
        int index2 = train.getRoute().indexOf(destination);
        int distance = index2-index1;
        return distance * coach.getFareFactor();
    }
}
