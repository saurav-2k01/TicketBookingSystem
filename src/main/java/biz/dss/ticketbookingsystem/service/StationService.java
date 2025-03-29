package biz.dss.ticketbookingsystem.service;

import biz.dss.ticketbookingsystem.models.Station;
import biz.dss.ticketbookingsystem.utils.Response;
import biz.dss.ticketbookingsystem.valueobjects.AuthenticatedUser;

public interface StationService {

    Response addStation(AuthenticatedUser authenticatedUser, Station station);

    Response removeStation(AuthenticatedUser authenticatedUser, Integer id);

    Response updateStation(AuthenticatedUser authenticatedUser, Station station);

    Response getStation(Integer id);

    Response getStations();
    Response getStationByName(String name);

}
