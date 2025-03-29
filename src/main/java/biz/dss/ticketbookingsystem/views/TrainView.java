//package biz.dss.ticketbookingsystem.views;
//
//import biz.dss.ticketbookingsystem.controller.TrainController;
//import biz.dss.ticketbookingsystem.enums.TravellingClass;
//import biz.dss.ticketbookingsystem.models.Coach;
//import biz.dss.ticketbookingsystem.models.Station;
//import biz.dss.ticketbookingsystem.models.Train;
//import biz.dss.ticketbookingsystem.service.FilterStation;
//import biz.dss.ticketbookingsystem.utils.Formatter;
//import biz.dss.ticketbookingsystem.utils.Response;
//import biz.dss.ticketbookingsystem.utils.UtilClass;
//
//import java.time.DayOfWeek;
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.Scanner;
//
//import static biz.dss.ticketbookingsystem.utils.ResponseStatus.FAILURE;
//
//
//public class TrainView {
//    private final TrainController trainController;
//    private FilterStation filterStation;
//    private final Scanner input = UtilClass.scanner;
//
//    public TrainView(TrainController trainController, FilterStation filterStation){
//        this.trainController = trainController;
//        this.filterStation = filterStation;
//    }
//
//    public void addTrain() {
//        System.out.println("Enter train number: ");
//        Integer trainNumber = input.nextInt();
//        input.nextLine();
//
//        System.out.println("Enter train name: ");
//        String trainName = input.nextLine();
//
//        Train train = new Train(trainNumber, trainName);
//        Response response = trainController.addTrain(train);
//        System.out.println(response.getMessage());
//
//    }
//
//    public void removeTrain() {
//        displayTrainDetail();
//        System.out.println("Enter train number: ");
//        Integer trainNumber = input.nextInt();
//        Response response = this.trainController.removeTrain(trainNumber);
//        System.out.println(response.getMessage());
//    }
//
//    public void addCoach() {
//        TravellingClass[] travellingClasses = TravellingClass.values();
//
//        System.out.println("===================Travelling Classes======================");
//        for(int i =1;i<=travellingClasses.length;i++){
//            System.out.printf("[%d] %s", i, travellingClasses[i-1]);
//        }
//
//        System.out.println("Select Travelling Class: ");
//        int travellingClassIndex = input.nextInt();
//        input.nextLine();
//        TravellingClass travellingClass = travellingClasses[travellingClassIndex];
//        System.out.println("Enter enter total number of seats: ");
//        Integer totalSeats = input.nextInt();
//        System.out.println("Enter fare factor [input should be number with decimal]: ");
//        Double fareFactor = input.nextDouble();
//        input.nextLine();
//        System.out.println("Enter number of coaches you want to add : ");
//        int qty = input.nextInt();
//        input.nextLine();
//
//        for(int i=0;i<qty;i++){
//            System.out.println("Enter Coach Name for coach "+i+1+": ");
//            String name = input.nextLine();
//            Coach coach = new Coach(travellingClass, name, totalSeats, fareFactor);
//            Response response = trainController.addCoach(coach);
//            System.out.println(response.getMessage());
//        }
//    }
//
//    public void removeCoach() {
//        getCoaches();
//        System.out.println("Select coach to remove: ");
//        int coachId = input.nextInt();
//        Response coachResponse = trainController.getCoach(coachId);
//        if(coachResponse.getStatus().equals(FAILURE)){
//            System.out.println(coachResponse.getMessage());
//            return;
//        }
//        Coach coach = (Coach) (coachResponse.getData());
//        Response response = trainController.removeCoach(coach);
//        System.out.println(response.getMessage());
//    }
//
//
//    public void showAllTrains() {
////        trainController.getTrains().values().stream().forEach(System.out::println);
//        Response trainsResponse = trainController.getTrains();
//        if (trainsResponse.getStatus().equals(FAILURE)){
//            System.out.println(trainsResponse.getMessage());
//            return;
//        }
//        Map<Integer, Train> trains = (Map<Integer, Train>)(trainsResponse.getData());
//        Formatter.tableTemplate(trains.values().stream().toList());
//    }
//
//    public void addRoute() {
//        List<Station> route = new ArrayList<>();
//        Response stationsResponse = filterStation.getStations();
//        if(stationsResponse.getStatus().equals(FAILURE)){
//            System.out.println(stationsResponse.getMessage());
//            return;
//        }
//        Map<String, Station> stations = (Map<String, Station>) (stationsResponse.getData());
//        boolean flag = true;
//        while (flag) {
//            Formatter.tableTemplate(stations.values().stream().toList());
//            System.out.println("enter name or short name of the station, q to stop");
//            String stationName = input.nextLine();
//
//            if (stationName.equals("q")) {
//                flag = false;
//            } else {
//                Response stationByNameResponse = this.filterStation.getStationByName(stationName);
//                if (stationByNameResponse.getStatus().equals(FAILURE)){
//                    System.out.println(stationByNameResponse.getMessage());
//                    continue;
//                }
//                Station station = (Station)(stationByNameResponse.getData());
//                route.add(station);
//            }
//        }
//        Response response = trainController.addRoute(route);
//        System.out.println(response.getMessage());
//    }
//
//    public void removeRoute() {
//        System.out.println("Enter train number: ");
//        Integer trainNumber = input.nextInt();
//        input.nextLine();
//        Response trainResponse = trainController.getTrain(trainNumber);
//        if(trainResponse.getStatus().equals(FAILURE)){
//            System.out.println(trainResponse.getMessage());
//            return;
//        }
//        Train train = (Train)(trainResponse.getData());
//        Response response = this.trainController.removeRoute(train);
//        System.out.println(response.getMessage());
//    }
//
//    public void manageSpecificTrain() {
//        System.out.println("Enter train number: ");
//        Integer trainNumber = input.nextInt();
//        input.nextLine();
//        Response trainResponse = trainController.getTrain(trainNumber);
//        if(trainResponse.getStatus().equals(FAILURE)){
//            System.out.println(trainResponse.getMessage());
//            return;
//        }
//        Train train = (Train)(trainResponse.getData());
//        Response response = trainController.setCurrentTrain(train);
//        System.out.println(response.getMessage());
//    }
//
//
//    public void getRoute() {
//        Response routeResponse = trainController.getRoute();
//        if(routeResponse.getStatus().equals(FAILURE)){
//            System.out.println(routeResponse.getMessage());
//            return;
//        }
//        List<Station> route =(List<Station>)(routeResponse.getData());
//        route.forEach(System.out::println);
//    }
//
//    public void addRunningDay() {
//        System.out.println("Enter Day: ");
//        String dayOfTheWeek = input.nextLine();
//        DayOfWeek day = DayOfWeek.valueOf(dayOfTheWeek.toUpperCase());
//        Response response = trainController.addRunningDay(day);
//        System.out.println(response.getMessage());
//    }
//
//    public void removeRunningDay() {
//        System.out.println("Enter Day: ");
//        String dayOfTheWeek = input.nextLine();
//        DayOfWeek day = DayOfWeek.valueOf(dayOfTheWeek.toUpperCase());
//        Response response = trainController.removeRunningDay(day);
//        System.out.println(response.getMessage());
//    }
//
//    public void getRunningDays() {
//        Response runningDaysResponse = trainController.getRunningDays();
//        if(runningDaysResponse.getStatus().equals(FAILURE)){
//            System.out.println(runningDaysResponse.getMessage());
//            return;
//        }
//        List<DayOfWeek> runningDays = (List<DayOfWeek>)(runningDaysResponse.getData());
//        runningDays.forEach(System.out::println);
//    }
//
//    public void getCoach() {
//        System.out.println("Enter Coach Id: ");
//        int coachId = input.nextInt();
//        Response coachResponse = trainController.getCoach(coachId);
//        System.out.println(coachResponse.getMessage());
//    }
//
//    public void getCoaches() {
//        Response currentTrainResponse = trainController.getCurrentTrain();
//        if(currentTrainResponse.getStatus().equals(FAILURE)){
//            System.out.println(currentTrainResponse.getMessage());
//            return;
//        }
//        Train currentTrain = (Train) (currentTrainResponse.getData());
//        List<Coach> coachList = currentTrain.getCoachList();
//        coachList.forEach(System.out::println);
//    }
//
//    public void displayTrainDetail() {
//        Response currentTrainResponse = trainController.getCurrentTrain();
//        if(currentTrainResponse.getStatus().equals(FAILURE)){
//            System.out.println(currentTrainResponse.getMessage());
//            return;
//        }
//        Train train = (Train) (currentTrainResponse.getData());
//        System.out.println(train.getTrainNumber());
//        System.out.println(train.getTrainName());
//        System.out.println(train.getSource());
//        System.out.println(train.getDestination());
//        System.out.println(train.getRunningDays());
//        System.out.println(train.getCoachList());
//    }
//
//    public void searchTrain() {
////        FilterTrain filterTrain = new FilterTrain(trainController.getTrains().values().stream().toList());
////        System.out.println("Enter Source: ");
////        String sourceInput = input.nextLine();
////        Response sourceResponse = filterStation.getStationByName(sourceInput);
////        if (sourceResponse.getStatus().equals(FAILURE)){
////            System.out.println(sourceResponse.getMessage());
////            return;
////        }
////
////        System.out.println("Enter destination: ");
////        String destinationInput = input.nextLine();
////        Response destinationResponse = filterStation.getStationByName(destinationInput);
////        if (destinationResponse.getStatus().equals(FAILURE)){
////            System.out.println(destinationResponse.getMessage());
////            return;
////        }
////
////        System.out.println("Enter date: ");
////        String date = input.nextLine();
////
////        Station source = (Station)(sourceResponse.getData());
////        Station destination  = (Station)(sourceResponse.getData());
////
////        Response filteredTrainResponse = null;
////        if (date.isEmpty()) {
////            filteredTrainResponse = trainController.searchTrains(source, destination);
////        } else {
////            filteredTrainResponse = trainController.searchTrains(source,destination, LocalDate.parse(date));
////        }
////
////        if(filteredTrainResponse.getStatus().equals(FAILURE)){
////            System.out.println(filteredTrainResponse.getMessage());
////            return;
////        }
////        List<Train> filteredTrains = (List<Train>)(filteredTrainResponse.getData());
////        Formatter.tableTemplate(filteredTrains);
//    }
//}
