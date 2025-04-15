package biz.dss.ticketbookingsystem.controller;

import biz.dss.ticketbookingsystem.models.Station;
import biz.dss.ticketbookingsystem.service.StationService;
import biz.dss.ticketbookingsystem.utils.Response;
import biz.dss.ticketbookingsystem.valueobjects.AuthenticatedUser;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StationController{

    /*Fields*/
    private final StationService stationService;

    /*Methods*/

    
    public Response addStation(AuthenticatedUser authenticatedUser, Station station) {
        return stationService.addStation(authenticatedUser, station);
    }

    public Response removeStation(AuthenticatedUser authenticatedUser, Integer id) {
        return stationService.removeStation(authenticatedUser, id);
    }

    public Response updateStation(AuthenticatedUser authenticatedUser, Station station) {
        return stationService.updateStation(authenticatedUser, station);
    }

    public Response getStation(Integer id) {
        return stationService.getStation(id);
    }

    public Response getStations() {
        return stationService.getStations();
    }

    public Response getStationByName(String name){
        return stationService.getStationByName(name);
    }
}
