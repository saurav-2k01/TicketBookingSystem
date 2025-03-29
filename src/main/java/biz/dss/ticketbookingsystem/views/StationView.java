//package biz.dss.ticketbookingsystem.views;
//
//import biz.dss.ticketbookingsystem.controller.StationController;
//import biz.dss.ticketbookingsystem.models.Station;
//import biz.dss.ticketbookingsystem.utils.Formatter;
//import biz.dss.ticketbookingsystem.utils.Response;
//import biz.dss.ticketbookingsystem.utils.UtilClass;
//
//import java.util.Map;
//import java.util.Scanner;
//
//import static biz.dss.ticketbookingsystem.utils.ResponseStatus.FAILURE;
//
//public class StationView {
//
//    private final StationController stationController;
//    private final Scanner input = UtilClass.scanner;
//
//    public StationView(StationController stationController) {
//        this.stationController = stationController;
//    }
//
//
//    public void setCurrentAdmin() {
//
//    }
//
//
//    public void addStation() {
//        System.out.println("Enter station's name: ");
//        String name = input.nextLine();
//        System.out.println("Enter Station's shortname: ");
//        String shortName = input.nextLine();
//        Response response = stationController.addStation(new Station(name, shortName));
//        System.out.println(response.getMessage());
//    }
//
//
//    public void removeStation() {
//
//        displayStations();
//
//        System.out.println("Enter station's name: ");
//        String name = input.nextLine();
//        System.out.println("Enter Station's shortname: ");
//        String shortName = input.nextLine();
//        Response response = stationController.addStation(new Station(name, shortName));
//        System.out.println(response.getMessage());
//    }
//
//
//    public void updateStation() {
//
//    }
//
//
//    public void displayStation() {
//        Response stationResponse = stationController.getStations();
//        System.out.println(stationResponse.getMessage());
//        if (stationResponse.getStatus().equals(FAILURE)) {
//            return;
//        }
//        Station station = (Station) (stationResponse.getData());
//        System.out.println(station);
//    }
//
//
//    public void displayStations() {
//        Response stationsResponse = stationController.getStations();
//        System.out.println(stationsResponse.getMessage());
//        if (stationsResponse.getStatus().equals(FAILURE)) {
//            return;
//        }
//
//        Map<String, Station> stations = (Map<String, Station>) (stationsResponse.getData());
//
//        Formatter.tableTemplate(stations.values().stream().toList());
//    }
//}
