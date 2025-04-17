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
import biz.dss.ticketbookingsystem.valueobjects.AvailableSeats;
import biz.dss.ticketbookingsystem.valueobjects.TrainSearchDetail;
import lombok.extern.slf4j.Slf4j;

import javax.print.attribute.standard.MediaSize;
import java.time.DayOfWeek;
import java.util.*;

import static biz.dss.ticketbookingsystem.utils.ResponseStatus.FAILURE;

@Slf4j
public class TrainView {
    private final TrainController trainController;
    private final StationController stationController;
    private final InputView inputview;
    private final BookingView bookingView;

    public TrainView(InputView inputView, TrainController trainController, StationController stationController, BookingView bookingView) {
        this.inputview = inputView;
        this.trainController = trainController;
        this.stationController = stationController;
        this.bookingView = bookingView;
    }

    public void addTrain(AuthenticatedUser authenticatedUser) {

        Integer trainNumber = inputview.getIntegerInput("Enter Train Number: ");
        String trainName = inputview.getName("Enter Train Name: ");

        Train train = Train.builder().trainName(trainName).trainNumber(trainNumber).build();
        Response response = trainController.addTrain(authenticatedUser, train);
        System.out.println(response.getMessage());

    }

    public void removeTrain(AuthenticatedUser authenticatedUser) {
        boolean trainsDisplayed = showAllTrains();
        if (trainsDisplayed) {
            Integer trainNumber = inputview.getIntegerInput("Enter train number: ");
            Integer transactionCount = bookingView.getTransactionsCountByTrainNumber(authenticatedUser, trainNumber);
            boolean choice = false;
            if(transactionCount>0){
                System.out.println("This train may have multiple transaction. All of those transactions will get deleted.");
                while(!choice){
                    String choiceInput = inputview.getStringInput(" Do you really want to remove this train ? (y/n)");
                    if(choiceInput.equalsIgnoreCase("y")){
                        choice = true;
                    } else if (Boolean.FALSE.equals(choiceInput.equalsIgnoreCase("N"))) {
                        System.out.println("Enter a valid input choice.");
                    }else{
                        break;
                    }
                }
                if(choice) {
                    Response response = this.trainController.removeTrain(authenticatedUser, trainNumber);
                    System.out.println(response.getMessage());
                }
            }else{
                Response response = this.trainController.removeTrain(authenticatedUser, trainNumber);
                System.out.println(response.getMessage());
            }
        }
    }

    public void addCoach(AuthenticatedUser authenticatedUser) {
        List<Coach> coaches = new ArrayList<>();
        List<TravellingClass> travellingClasses = Arrays.stream(TravellingClass.values()).toList();
        System.out.println("===========TRAVELLING CLASS===========");
        travellingClasses.forEach(travellingClass -> System.out.printf("[%d] %s%n", travellingClass.ordinal() + 1, travellingClass));
        TravellingClass travellingClass = inputview.getTravellingClass("Select Travelling CLass: ", travellingClasses);
        Integer totalSeats = inputview.getIntegerInput("Enter total number of seats: ");
        Double fareFactor = inputview.getDoubleInput("Enter fare factor [input should be number with decimal]: ");
        int qty = inputview.getIntegerInput("Enter number of coaches you want to add : ");

        boolean isCoachNameCorrect = true;
        for (int i = 0; i < qty; i++) {
            if (Boolean.FALSE.equals(isCoachNameCorrect)) {
                i = i - 1;
            }
            String name = inputview.getStringInput("Enter coach name for coach " + (i + 1) + ": ");
            isCoachNameCorrect = true;
            if (name.length() >= 15) {
                System.out.println("Coach name cannot be greater than 15 characters.");
                isCoachNameCorrect = false;
                continue;
            }
            Coach coach = new Coach(travellingClass, name, totalSeats, fareFactor);
            coaches.add(coach);
        }
        Response response = trainController.addCoach(authenticatedUser, coaches);
        System.out.println(response.getMessage());
    }

    public void removeCoach(AuthenticatedUser authenticatedUser) {
        boolean displayedCoaches = displayCoaches();
        if(displayedCoaches){
            int coachId = inputview.getIntegerInput("Enter Coach Id: ");
            Response coachResponse = trainController.getCoach(coachId);
            if (coachResponse.getStatus().equals(FAILURE)) {
                System.out.println(coachResponse.getMessage());
                return;
            }
            Coach coach = (Coach) (coachResponse.getData());
            Response response = trainController.removeCoach(authenticatedUser, coach);
            System.out.println(response.getMessage());
        }
    }


    public boolean showAllTrains() {
        Response trainsResponse = trainController.getTrains();
        if (trainsResponse.getStatus().equals(FAILURE)) {
            System.out.println(trainsResponse.getMessage());
            return false;
        }
        List<Train> trains = (List<Train>) (trainsResponse.getData());
        Formatter.tableTemplate(trains);
        return Boolean.FALSE.equals(trains.isEmpty());
    }

    public void addRoute(AuthenticatedUser authenticatedUser) {
        List<Station> route = new ArrayList<>();
        Response stationsResponse = stationController.getStations();
        if (stationsResponse.getStatus().equals(FAILURE)) {
            System.out.println(stationsResponse.getMessage());
            return;
        }
        List<Station> stations = (List<Station>) (stationsResponse.getData());
        boolean flag = true;
        while (flag) {
            Formatter.tableTemplate(stations);
            String stationName = inputview.getStringInput("enter name or short name of the station, q to stop");
            if (stationName.equalsIgnoreCase("q")) {
                flag = false;
            } else {
                Response stationByNameResponse = this.stationController.getStationByName(stationName);
                if (stationByNameResponse.getStatus().equals(FAILURE)) {
                    System.out.println(stationByNameResponse.getMessage());
                    continue;
                }
                Station station = (Station) (stationByNameResponse.getData());
                route.add(station);
            }
        }
        Formatter.tableTemplate(route);
        Response response = trainController.addRoute(authenticatedUser, route);
        System.out.println(response.getMessage());
    }

    public void removeRoute(AuthenticatedUser authenticatedUser) {
        Response trainResponse = trainController.getCurrentTrain();
        if (trainResponse.getStatus().equals(FAILURE)) {
            System.out.println(trainResponse.getMessage());
            return;
        }
        Train train = (Train) (trainResponse.getData());
        Formatter.tableTemplate(train.getRoute().stream().sorted().toList());
        Station station = inputview.getStation("Select Station: ");
        Response response = this.trainController.removeRoute(authenticatedUser, List.of(station));
        System.out.println(response.getMessage());
    }

    public boolean manageSpecificTrain() {
        boolean trainsDisplayed = showAllTrains();
        if (trainsDisplayed) {
            Integer trainNumber = inputview.getIntegerInput("Enter Train Number: ");
            Response trainResponse = trainController.getTrain(trainNumber);
            if (trainResponse.getStatus().equals(FAILURE)) {
                System.out.println(trainResponse.getMessage());
                return false;
            }
            Train train = (Train) (trainResponse.getData());
            Response response = trainController.setCurrentTrain(train);
            System.out.println(response.getMessage());
            return true;
        } else {
            return false;
        }
    }


    public void getRoute() {
        Response routeResponse = trainController.getRoute();
        if (routeResponse.getStatus().equals(FAILURE)) {
            System.out.println(routeResponse.getMessage());
            return;
        }
        List<Station> route = (List<Station>) (routeResponse.getData());
        Formatter.tableTemplate(route.stream().sorted().toList());
    }

    public void addRunningDay(AuthenticatedUser authenticatedUser) {
        List<DayOfWeek> runningDays = new ArrayList<>();
        while (true) {
            DayOfWeek runningDay = inputview.getRunningDay("Enter Day: ");
            runningDays.add(runningDay);
            String choice = inputview.getStringInput("Do you want to add more running days? (y/n)");
            if (choice.equalsIgnoreCase("N")) {
                break;
            } else if (!choice.equalsIgnoreCase("y")) {
                System.out.println("Enter a valid input.");
            }
        }
        Response response = trainController.addRunningDay(authenticatedUser, runningDays);
        System.out.println(response.getMessage());
    }

    public void removeRunningDay(AuthenticatedUser authenticatedUser) {
        DayOfWeek runningDay = inputview.getRunningDay("Enter Day: ");
        Response response = trainController.removeRunningDay(authenticatedUser, runningDay);
        System.out.println(response.getMessage());
    }

    public void getRunningDays() {
        Response runningDaysResponse = trainController.getRunningDays();
        if (runningDaysResponse.getStatus().equals(FAILURE)) {
            System.out.println(runningDaysResponse.getMessage());
            return;
        }
        List<DayOfWeek> runningDays = (List<DayOfWeek>) (runningDaysResponse.getData());
        runningDays.stream().sorted().forEach(System.out::println);
    }

    public void getCoach() {
        int coachId = inputview.getIntegerInput("Enter Coach Id: ");
        Response coachResponse = trainController.getCoach(coachId);
        System.out.println(coachResponse.getMessage());
    }

    public boolean displayCoaches() {
        Response currentTrainResponse = trainController.getCurrentTrain();
        if (currentTrainResponse.getStatus().equals(FAILURE)) {
            System.out.println(currentTrainResponse.getMessage());
            return false;
        }
        Train currentTrain = (Train) (currentTrainResponse.getData());
        List<Coach> coachList = currentTrain.getCoachList();
        Formatter.tableTemplate(coachList);
        return Boolean.FALSE.equals(coachList.isEmpty());
    }

    public void displayTrainDetail() {
        Response currentTrainResponse = trainController.getCurrentTrain();
        if (currentTrainResponse.getStatus().equals(FAILURE)) {
            System.out.println(currentTrainResponse.getMessage());
            return;
        }
        Train train = (Train) (currentTrainResponse.getData());
//        System.out.println(train.getTrainNumber());
//        System.out.println(train.getTrainName());
//        System.out.println(train.getSource());
//        System.out.println(train.getDestination());
//        System.out.println(train.getRunningDays());
//        System.out.println(train.getRoute());
//        System.out.println(train.getCoachList());
        String line = Formatter.multiplyChar(100, '=');
        System.out.println(line);
        System.out.printf("Train Number: %d                   Train Name: %s%n", train.getTrainNumber(), train.getTrainName());
        System.out.printf("Source: %s                     Destination: %s%n", train.getSource().getName(), train.getDestination().getName());
        System.out.printf("Running Days: %s%n", String.join(", ",train.getRunningDays().stream().map(Enum::toString).toList()));
        Formatter.tableTemplate(train.getRoute());
        Formatter.tableTemplate(train.getCoachList());
        System.out.println(line);
    }

    public void searchTrain() {
        TrainSearchDetail trainSearchInput = inputview.getTrainSearchInput(true);
        Response filteredTrainResponse = trainController.searchTrains(trainSearchInput);

        if (Boolean.FALSE.equals(filteredTrainResponse.isSuccess())) {
            System.out.println(filteredTrainResponse.getMessage());
            return;
        }


//        Object data = trainController.getTrains().getData();
        List<Train> filteredTrains = (List<Train>) (filteredTrainResponse.getData());
        Formatter.tableTemplate(filteredTrains);
        if (Objects.isNull(trainSearchInput.getDate())) {
            System.out.println("Do you want to check available seats ? (y/n)");
            String choice = inputview.getStringInput("Choice: ");
            while (true) {
                if (choice.equalsIgnoreCase("y")) {
                    trainSearchInput.setDate(inputview.getDate("Enter date of journey: "));
                    break;
                } else if (!choice.equalsIgnoreCase("n")) {
                    choice = inputview.getStringInput("Choice: ");
                    System.out.println("Enter a valid choice input.");
                } else {
                    return;
                }
            }
        }
        List<Train> filteredTrainList = filteredTrains.stream().
                filter(train -> train.getRunningDays().contains(trainSearchInput.getDate().getDayOfWeek()))
                .toList();

        if(filteredTrainList.isEmpty()){
            System.out.println("Above listed trains does not run on your selected date.");
            return;
        }
        Optional<Train> train;
        while (true) {
            int trainNumber = inputview.getIntegerInput("Enter train no.: ");
            if (trainNumber < 0) {
                System.out.println("Enter a valid train number.");
            }

            train = filteredTrains.stream().filter(t -> t.getTrainNumber() == trainNumber).findFirst();
            if (train.isPresent() && Boolean.FALSE.equals(train.get().getRunningDays().contains(trainSearchInput.getDate().getDayOfWeek()))) {
                System.out.println("selected train does not run on your selected date.");
                Response newlyFilteredTrain = trainController.searchTrains(trainSearchInput);
                if (newlyFilteredTrain.isSuccess()) {
                    System.out.println("Below listed trains runs on your selected date.");
                    Formatter.tableTemplate((List<Train>) newlyFilteredTrain.getData());
                }
                continue;
            }
            if (train.isEmpty()) {
                System.out.println("Enter valid train number.");
            } else {
                break;
            }
        }

        List<AvailableSeats> availableSeats = bookingView.getAvailableSeatsWithFare(train.orElse(null), trainSearchInput);
        Formatter.tableTemplate(availableSeats);
    }
}
