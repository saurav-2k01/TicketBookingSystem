package biz.dss.ticketbookingsystem.utils;

import biz.dss.ticketbookingsystem.enums.TravellingClass;
import biz.dss.ticketbookingsystem.models.Station;
import biz.dss.ticketbookingsystem.models.Train;

public interface FareCalculator {
    double calculateFare(Train train, TravellingClass travellingClass, Station source, Station destination);
}
