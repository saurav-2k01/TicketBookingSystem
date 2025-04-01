package biz.dss.ticketbookingsystem.view;

import biz.dss.ticketbookingsystem.controller.StationController;
import biz.dss.ticketbookingsystem.models.Station;
import biz.dss.ticketbookingsystem.utils.*;
import biz.dss.ticketbookingsystem.valueobjects.AuthenticatedUser;

import java.util.List;

import static biz.dss.ticketbookingsystem.utils.ResponseStatus.FAILURE;


public class StationView {
    private final StationController stationController;
    private final InputView inputView;

    public StationView(InputView inputView, StationController stationController){
        this.inputView = inputView;
        this.stationController = stationController;
    }
    public void addStation(AuthenticatedUser authenticatedUser, Station station){
        String name = inputView.getName("Station Name: ");
        String shortName = inputView.getName("Shortname: ");
        Response response = stationController.addStation(authenticatedUser, new Station(UtilClass.random.nextInt(10_000_000), name, shortName));
        System.out.println(response.getMessage());
    }

    public void removeStation(AuthenticatedUser authenticatedUser){
        displayStations();
        String name = inputView.getName("Enter Station Name: ");
        Station  station = getStationByName(name);
        stationController.removeStation(authenticatedUser, station.getId());
        displayStations();
    }

    public void updateStation(Station station){

    }

    public void displayStations(){
        Response stationsResponse = stationController.getStations();
        System.out.println(stationsResponse.getMessage());
        if (stationsResponse.getStatus().equals(FAILURE)) {
            return;
        }

        List<Station> stations = (List<Station>) (stationsResponse.getData());

        Formatter.tableTemplate(stations);
    }
    public Station getStationByName(String name){
        Response stationResponse = stationController.getStationByName(name);
        if(stationResponse.isSuccess()){
            return (Station) stationResponse.getData();
        }else{
            System.out.println(stationResponse.getMessage());
            return null;
        }
    }
}
