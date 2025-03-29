package biz.dss.ticketbookingsystem.view;

import biz.dss.ticketbookingsystem.controller.StationController;
import biz.dss.ticketbookingsystem.controller.TrainController;
import biz.dss.ticketbookingsystem.enums.TravellingClass;
import biz.dss.ticketbookingsystem.models.Coach;
import biz.dss.ticketbookingsystem.models.Station;
import biz.dss.ticketbookingsystem.models.Train;
import biz.dss.ticketbookingsystem.utils.Formatter;
import biz.dss.ticketbookingsystem.utils.Response;
import biz.dss.ticketbookingsystem.valueobjects.AuthenticatedUser;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static biz.dss.ticketbookingsystem.utils.ResponseStatus.FAILURE;

public class TrainView {
    private final TrainController trainController;
    private final StationController stationController;
    private final InputView inputview;

    public TrainView(InputView inputView, TrainController trainController, StationController stationController){
        this.inputview = inputView;
        this.trainController = trainController;
        this.stationController = stationController;
    }

    public void addTrain(AuthenticatedUser authenticatedUser) {
        
        Integer trainNumber = inputview.getIntegerInput("Enter Train Number: ");
        String trainName = inputview.getName("Enter Train Name: ");

        Train train = new Train(trainNumber, trainName);
        Response response = trainController.addTrain(authenticatedUser, train);
        System.out.println(response.getMessage());

    }

    public void removeTrain(AuthenticatedUser authenticatedUser) {
        displayTrainDetail();
        Integer trainNumber = inputview.getIntegerInput("Enter train number: ");
        Response response = this.trainController.removeTrain(authenticatedUser, trainNumber);
        System.out.println(response.getMessage());
    }

    public void addCoach(AuthenticatedUser authenticatedUser) {
        List<TravellingClass> travellingClasses = Arrays.stream(TravellingClass.values()).toList();
        travellingClasses.forEach(System.out::println);//todo show index
        TravellingClass travellingClass = inputview.getTravellingClass("Select Travelling CLass: ", travellingClasses);
        System.out.println("Enter enter total number of seats: ");
        Integer totalSeats = inputview.getIntegerInput("Enter total number of seats: ");
        System.out.println("Enter fare factor [input should be number with decimal]: ");
        Double fareFactor = inputview.getDoubleInput("Enter fare factor [input should be number with decimal]: ");
        System.out.println("Enter number of coaches you want to add : ");
        int qty = inputview.getIntegerInput("Enter number of coaches you want to add : ");
        
        for(int i=0;i<qty;i++){
            String name = inputview.getName("Enter Coach Name for coach "+i+1+": ");
            Coach coach = new Coach(travellingClass, name, totalSeats, fareFactor);
            Response response = trainController.addCoach(authenticatedUser, coach);
            System.out.println(response.getMessage());
        }
    }

    public void removeCoach(AuthenticatedUser authenticatedUser) {
        getCoaches();
        int coachId = inputview.getIntegerInput("Enter Coach Id: ");
        Response coachResponse = trainController.getCoach(coachId);
        if(coachResponse.getStatus().equals(FAILURE)){
            System.out.println(coachResponse.getMessage());
            return;
        }
        Coach coach = (Coach) (coachResponse.getData());
        Response response = trainController.removeCoach(authenticatedUser, coach);
        System.out.println(response.getMessage());
    }


    public void showAllTrains() {
//        trainController.getTrains().values().stream().forEach(System.out::println);
        Response trainsResponse = trainController.getTrains();
        if (trainsResponse.getStatus().equals(FAILURE)){
            System.out.println(trainsResponse.getMessage());
            return;
        }
        Map<Integer, Train> trains = (Map<Integer, Train>)(trainsResponse.getData());
        Formatter.tableTemplate(trains.values().stream().toList());
    }

    public void addRoute(AuthenticatedUser authenticatedUser) {
        List<Station> route = new ArrayList<>();
        Response stationsResponse = stationController.getStations();
        if(stationsResponse.getStatus().equals(FAILURE)){
            System.out.println(stationsResponse.getMessage());
            return;
        }
        Map<String, Station> stations = (Map<String, Station>) (stationsResponse.getData());
        boolean flag = true;
        while (flag) {
            Formatter.tableTemplate(stations.values().stream().toList());
            String stationName = inputview.getName("enter name or short name of the station, q to stop");
            if (stationName.equals("q")) {
                flag = false;
            } else {
                Response stationByNameResponse = this.stationController.getStationByName(stationName);
                if (stationByNameResponse.getStatus().equals(FAILURE)){
                    System.out.println(stationByNameResponse.getMessage());
                    continue;
                }
                Station station = (Station)(stationByNameResponse.getData());
                route.add(station);
            }
        }
        Response response = trainController.addRoute(authenticatedUser, route);
        System.out.println(response.getMessage());
    }

    public void removeRoute(AuthenticatedUser authenticatedUser) {
        Integer trainNumber = inputview.getIntegerInput("Enter Train Number: ");
        Response trainResponse = trainController.getTrain(trainNumber);
        if(trainResponse.getStatus().equals(FAILURE)){
            System.out.println(trainResponse.getMessage());
            return;
        }
        Train train = (Train)(trainResponse.getData());
        Response response = this.trainController.removeRoute(authenticatedUser, train);
        System.out.println(response.getMessage());
    }

    public void manageSpecificTrain() {
        System.out.println("Enter train number: ");
        Integer trainNumber = inputview.getIntegerInput("Enter Train Number: ");
        Response trainResponse = trainController.getTrain(trainNumber);
        if(trainResponse.getStatus().equals(FAILURE)){
            System.out.println(trainResponse.getMessage());
            return;
        }
        Train train = (Train)(trainResponse.getData());
        Response response = trainController.setCurrentTrain(train);
        System.out.println(response.getMessage());
    }


    public void getRoute() {
        Response routeResponse = trainController.getRoute();
        if(routeResponse.getStatus().equals(FAILURE)){
            System.out.println(routeResponse.getMessage());
            return;
        }
        List<Station> route =(List<Station>)(routeResponse.getData());
        route.forEach(System.out::println);
    }

    public void addRunningDay(AuthenticatedUser authenticatedUser) {
        DayOfWeek runningDay = inputview.getRunningDay("Enter Day: ");
        Response response = trainController.addRunningDay(authenticatedUser, runningDay);
        System.out.println(response.getMessage());
    }

    public void removeRunningDay(AuthenticatedUser authenticatedUser) {
        DayOfWeek runningDay = inputview.getRunningDay("Enter Day: ");
        Response response = trainController.removeRunningDay(authenticatedUser, runningDay);
        System.out.println(response.getMessage());
    }

    public void getRunningDays() {
        Response runningDaysResponse = trainController.getRunningDays();
        if(runningDaysResponse.getStatus().equals(FAILURE)){
            System.out.println(runningDaysResponse.getMessage());
            return;
        }
        List<DayOfWeek> runningDays = (List<DayOfWeek>)(runningDaysResponse.getData());
        runningDays.forEach(System.out::println);
    }

    public void getCoach() {
        int coachId = inputview.getIntegerInput("Enter Coach Id: ");
        Response coachResponse = trainController.getCoach(coachId);
        System.out.println(coachResponse.getMessage());
    }

    public void getCoaches() {
        Response currentTrainResponse = trainController.getCurrentTrain();
        if(currentTrainResponse.getStatus().equals(FAILURE)){
            System.out.println(currentTrainResponse.getMessage());
            return;
        }
        Train currentTrain = (Train) (currentTrainResponse.getData());
        List<Coach> coachList = currentTrain.getCoachList();
        coachList.forEach(System.out::println);
    }

    public void displayTrainDetail() {
        Response currentTrainResponse = trainController.getCurrentTrain();
        if(currentTrainResponse.getStatus().equals(FAILURE)){
            System.out.println(currentTrainResponse.getMessage());
            return;
        }
        Train train = (Train) (currentTrainResponse.getData());
        System.out.println(train.getTrainNumber());
        System.out.println(train.getTrainName());
        System.out.println(train.getSource());
        System.out.println(train.getDestination());
        System.out.println(train.getRunningDays());
        System.out.println(train.getCoachList());
    }

//    public void searchTrain() {
//      FilterTrain filterTrain = new FilterTrain(trainController.getTrains().values().stream().toList());
//        System.out.println("Enter Source: ");
//        String sourceInput = input.nextLine();
//        Response sourceResponse = stationController.getStationByName(sourceInput);
//        if (sourceResponse.getStatus().equals(FAILURE)){
//            System.out.println(sourceResponse.getMessage());
//            return;
//        }
//
//        System.out.println("Enter destination: ");
//        String destinationInput = input.nextLine();
//        Response destinationResponse = stationController.getStationByName(destinationInput);
//        if (destinationResponse.getStatus().equals(FAILURE)){
//            System.out.println(destinationResponse.getMessage());
//            return;
//        }
//
//        System.out.println("Enter date: ");
//        String date = input.nextLine();
//
//        Station source = (Station)(sourceResponse.getData());
//        Station destination  = (Station)(sourceResponse.getData());
//
//        Response filteredTrainResponse = null;
//        if (date.isEmpty()) {
//            filteredTrainResponse = trainController.searchTrains(source, destination);
//        } else {
//            filteredTrainResponse = trainController.searchTrains(source,destination, LocalDate.parse(date));
//        }
//
//        if(filteredTrainResponse.getStatus().equals(FAILURE)){
//            System.out.println(filteredTrainResponse.getMessage());
//            return;
//        }
//        List<Train> filteredTrains = (List<Train>)(filteredTrainResponse.getData());
//        Formatter.tableTemplate(filteredTrains);
//    }
}
