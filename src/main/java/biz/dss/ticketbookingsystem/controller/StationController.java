package biz.dss.ticketbookingsystem.controller;

import biz.dss.ticketbookingsystem.models.Station;
import biz.dss.ticketbookingsystem.service.StationService;
import biz.dss.ticketbookingsystem.utils.Response;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StationController{

    /*Fields*/
    private final StationService stationService;

    /*Methods*/

    
    public Response addStation(Station station) {
        return stationService.addStation(station);
    }

    public Response removeStation(Integer id) {
        return stationService.removeStation(id);
    }

    public Response updateStation(Station station) {
        return stationService.updateStation(station);
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
