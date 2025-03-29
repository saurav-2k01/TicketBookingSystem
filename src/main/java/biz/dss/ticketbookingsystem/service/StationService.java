package biz.dss.ticketbookingsystem.service;

import biz.dss.ticketbookingsystem.models.Station;
import biz.dss.ticketbookingsystem.utils.Response;

public interface StationService {

    Response addStation(Station station);

    Response removeStation(Integer id);

    Response updateStation(Station station);

    Response getStation(Integer id);

    Response getStations();
    Response getStationByName(String name);

}
