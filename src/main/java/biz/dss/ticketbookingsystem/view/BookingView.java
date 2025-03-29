package biz.dss.ticketbookingsystem.view;

import biz.dss.ticketbookingsystem.controller.AuthenticationController;
import biz.dss.ticketbookingsystem.controller.BookingController;
import biz.dss.ticketbookingsystem.controller.TrainController;
import biz.dss.ticketbookingsystem.controller.UserController;
import biz.dss.ticketbookingsystem.enums.TravellingClass;
import biz.dss.ticketbookingsystem.models.*;
import biz.dss.ticketbookingsystem.utils.FareCalculator;
import biz.dss.ticketbookingsystem.utils.Formatter;
import biz.dss.ticketbookingsystem.utils.Response;
import biz.dss.ticketbookingsystem.utils.StationBasedFareCalc;
import biz.dss.ticketbookingsystem.valueobjects.AuthenticatedUser;
import biz.dss.ticketbookingsystem.valueobjects.AvailableSeats;
import biz.dss.ticketbookingsystem.valueobjects.BookingDetail;
import biz.dss.ticketbookingsystem.valueobjects.TrainSearchDetail;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static biz.dss.ticketbookingsystem.utils.ResponseStatus.FAILURE;
import static biz.dss.ticketbookingsystem.utils.ResponseStatus.SUCCESS;

@SuppressWarnings("unchecked")
public class BookingView {
    private final BookingController bookingController;
    private final TrainController trainController;
    private final InputView inputView;
    private final UserController userController;
    private final AuthenticationController authenticationController;
    private Response response;

    public BookingView(InputView inputView, AuthenticationController authenticationController, TrainController trainController, BookingController bookingController, UserController userController) {
        this.inputView = inputView;
        this.trainController = trainController;
        this.authenticationController = authenticationController;
        this.bookingController = bookingController;
        this.userController = userController;
    }

    public void bookTicket(AuthenticatedUser authenticatedUser) {
        Response userResponse = authenticationController.getAuthenticatedUser(authenticatedUser);
        if (Boolean.FALSE.equals(userResponse.isSuccess())) {
            System.out.println(userResponse.getMessage());
            return;
        }
        User user = (User) (userResponse.getData());
        TrainSearchDetail trainSearchInput = getTrainSearchInput(false);
        List<Train> trains = filterTrains(trainSearchInput);
        if (trains.isEmpty()) {
            System.out.println("No train available.");return;
        }
        Formatter.tableTemplate(trains);

        int trainNumber = inputView.getIntegerInput("Enter train no.: ");
        Optional<Train> train = trains.stream().filter(t -> t.getTrainNumber() == trainNumber).findFirst();
        if (train.isEmpty()) {
            System.out.println("Enter valid train number.");
            return;
        }
        List<AvailableSeats> availableSeats = getAvailableSeatsWithFare(train.orElse(null), trainSearchInput);
        Formatter.tableTemplate(availableSeats);
        List<TravellingClass> travellingClasses = availableSeats.stream().map(AvailableSeats::getTravellingClass).toList();
        TravellingClass travellingClass = inputView.getTravellingClass("Travelling Class: ", travellingClasses);
        List<User> passengers = inputView.getPassengersList();
        Optional<AvailableSeats> availableSeat = availableSeats.stream().filter(value -> value.getTravellingClass().equals(travellingClass)).findFirst();

        if (availableSeat.isPresent()) {
            Double totalFare = availableSeat.get().getFare() * passengers.size();
            System.out.printf("Total amount for %d passengers is INR %.2f.%n", passengers.size(), totalFare);
            if (!confirmBooking()) return;

            BookingDetail bookingDetails = BookingDetail.builder().train(train.get()).travellingClass(travellingClass)
                    .from(trainSearchInput.getSource()).to(trainSearchInput.getDestination())
                    .dateOfJourney(trainSearchInput.getDate()).totalFare(totalFare).passengerList(passengers).build();

            Response bookingResponse = bookingController.bookTicket(user, bookingDetails);
            if (Boolean.TRUE.equals(bookingResponse.isSuccess())) {
                Formatter.formatTicket((Transaction) bookingResponse.getData());
            }
            System.out.println(bookingResponse.getMessage());
        } else {
            System.out.println("Unable to get fare.");
        }
    }


    public void displayTicket(AuthenticatedUser authenticatedUser) {
        Response userResponse = authenticationController.getAuthenticatedUser(authenticatedUser);
        if (Boolean.FALSE.equals(userResponse.isSuccess())) {
            System.out.println(userResponse.getMessage());
            return;
        }
        User user = (User) (userResponse.getData());
        int pnr = inputView.getIntegerInput("PNR: ");
        Response transactionResponse = bookingController.getTransaction(pnr);
        if (Boolean.TRUE.equals(transactionResponse.isSuccess())) {
            Transaction transaction = (Transaction) (transactionResponse.getData());
            Formatter.formatTicket(transaction);
        } else {
            System.out.println(transactionResponse.getMessage());
        }
    }


    private List<AvailableSeats> getAvailableSeatsWithFare(Train train, TrainSearchDetail trainSearchDetail) {
        FareCalculator fareCalculator = new StationBasedFareCalc();
        response = bookingController.getAvailableSeats(train, trainSearchDetail.getDate());

        List<AvailableSeats> availableSeatsList = new ArrayList<>();
        if (response.getStatus().equals(SUCCESS)) {

            Map<TravellingClass, Integer> availableSeatsData = (Map<TravellingClass, Integer>) response.getData();

            int count = 1;

            for (Map.Entry<TravellingClass, Integer> travellingClassIntegerEntry : availableSeatsData.entrySet()) {
                TravellingClass travellingClass = travellingClassIntegerEntry.getKey();
                Integer availableSeat = travellingClassIntegerEntry.getValue();
                AvailableSeats availableSeats1 = new AvailableSeats(count++, travellingClass, availableSeat, fareCalculator.calculateFare(train, travellingClass, trainSearchDetail.getSource(), trainSearchDetail.getDestination()));
                availableSeatsList.add(availableSeats1);
            }
        } else {
            System.out.println(response.getMessage());
        }
        return availableSeatsList;
    }


    public void displayTickets(AuthenticatedUser authenticatedUser) {
        Response userResponse = userController.getUserById(authenticatedUser.getId());
        if (!userResponse.isSuccess()) {
            System.out.println(userResponse.getMessage());
            return;
        }
        User user = (User) (userResponse.getData());
        Response tickets = bookingController.getTickets(user);
        if (tickets.isSuccess()) {
            List<Transaction> transactions = (List<Transaction>) (tickets.getData());
            Formatter.tableTemplate(transactions);
        } else {
            System.out.println(tickets.getMessage());
        }
    }


    public void cancelTicket(AuthenticatedUser authenticatedUser) {
        Response userResponse = userController.getUserById(authenticatedUser.getId());
        if (!userResponse.isSuccess()) {
            System.out.println(userResponse.getMessage());
            return;
        }
        User user = (User) (userResponse.getData());
        displayTickets(authenticatedUser);
        Integer pnr = inputView.getIntegerInput("PNR: ");
        response = bookingController.cancelTicket(pnr);
        System.out.println(response.getMessage());
    }

    private List<Train> filterTrains(TrainSearchDetail trainSearchDetail) {
        Response response = trainController.searchTrains(trainSearchDetail);

        List<Train> filteredTrains = null;
        if (response.getStatus().equals(FAILURE)) {
            System.out.println(response.getMessage());
        } else {
            filteredTrains = (List<Train>) (response.getData());
        }
        return filteredTrains;
    }

    private TrainSearchDetail getTrainSearchInput(boolean isDateOptional) {
        Station source = inputView.getStation("Enter Source: ");
        Station destination = inputView.getStation("Enter destination: ");
        while (true) {
            if (source.equals(destination)) {
                System.out.println("source and destination cannot be same.");
                destination = inputView.getStation("Enter destination: ");
            }else{
                break;
            }
        }
        LocalDate date;

        if (isDateOptional) {
            System.out.println("Do you want to search train for specific date? (Y/N): ");
            String choice = inputView.getStringInput("Choice: ");
            while (true) {
                if (choice.equalsIgnoreCase("Y")) {
                    date = inputView.getDate("Enter Date: ");
                    break;
                } else if (!choice.equalsIgnoreCase("N")) {
                    System.out.println("Enter a valid input.");
                    choice = inputView.getStringInput("Choice: ");
                }
            }
        } else {
            date = inputView.getDate("Enter Date: ");
        }
        return new TrainSearchDetail(source, destination, date);
    }

    private boolean confirmBooking() {
        String choice = inputView.getStringInput("Choice: ");
        while (1 > 0) {
            if (choice.equalsIgnoreCase("Y")) {
                return true;
            } else if (!choice.equalsIgnoreCase("N")) {
                System.out.println("Enter valid input.");
                choice = inputView.getStringInput("Choice: ");
            } else {
                return false;
            }
        }
    }
}
