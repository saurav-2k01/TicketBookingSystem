package biz.dss.ticketbookingsystem.view;

import biz.dss.ticketbookingsystem.controller.AuthenticationController;
import biz.dss.ticketbookingsystem.controller.BookingController;
import biz.dss.ticketbookingsystem.controller.TrainController;
import biz.dss.ticketbookingsystem.controller.UserController;
import biz.dss.ticketbookingsystem.enums.TravellingClass;
import biz.dss.ticketbookingsystem.models.Train;
import biz.dss.ticketbookingsystem.models.Transaction;
import biz.dss.ticketbookingsystem.models.User;
import biz.dss.ticketbookingsystem.utils.FareCalculator;
import biz.dss.ticketbookingsystem.utils.Formatter;
import biz.dss.ticketbookingsystem.utils.Response;
import biz.dss.ticketbookingsystem.utils.StationBasedFareCalc;
import biz.dss.ticketbookingsystem.valueobjects.AuthenticatedUser;
import biz.dss.ticketbookingsystem.valueobjects.AvailableSeats;
import biz.dss.ticketbookingsystem.valueobjects.BookingDetail;
import biz.dss.ticketbookingsystem.valueobjects.TrainSearchDetail;

import java.util.*;

import static biz.dss.ticketbookingsystem.utils.ResponseStatus.FAILURE;
import static biz.dss.ticketbookingsystem.utils.ResponseStatus.SUCCESS;

@SuppressWarnings("unchecked")
public class BookingView {
    private final BookingController bookingController;
    private final TrainController trainController;
    private final InputView inputView;
    private final AuthenticationController authenticationController;
    private Response response;

    public BookingView(InputView inputView, AuthenticationController authenticationController, TrainController trainController, BookingController bookingController, UserController userController) {
        this.inputView = inputView;
        this.trainController = trainController;
        this.authenticationController = authenticationController;
        this.bookingController = bookingController;
    }

    public void bookTicket(AuthenticatedUser authenticatedUser) {
        if(Boolean.FALSE.equals(authenticatedUser.getIsLoggedIn())) return;
        TrainSearchDetail trainSearchInput = inputView.getTrainSearchInput(false);
        List<Train> trains = filterTrains(trainSearchInput);
        if (Objects.isNull(trains) || trains.isEmpty()) {
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
            Response bookingResponse = bookingController.bookTicket(authenticatedUser, bookingDetails);
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
        Response transactionResponse = bookingController.getTickets(authenticatedUser);
        System.out.println(transactionResponse.getMessage());
        if (Boolean.TRUE.equals(transactionResponse.isSuccess())) {
            List<Transaction> transactions = (List<Transaction>) (transactionResponse.getData());
            Formatter.tableTemplate(transactions);
            if(Boolean.FALSE.equals(transactions.isEmpty())){
                int pnr = inputView.getIntegerInput("PNR: ");
                Optional<Transaction> foundTicket = transactions.stream().filter(t -> t.getPnr() == pnr).findFirst();
                if(foundTicket.isPresent()){
                    Formatter.formatTicket(foundTicket.get());
                }else{
                    System.out.println(String.format("you don't have any ticket with pnr - %d.", pnr));
                }
            }
        }
    }


    public List<AvailableSeats> getAvailableSeatsWithFare(Train train, TrainSearchDetail trainSearchDetail) {
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


    public boolean displayTickets(AuthenticatedUser authenticatedUser) {
        Response ticketsResp = bookingController.getTickets(authenticatedUser);
        System.out.println(ticketsResp.getMessage());
        if (Boolean.TRUE.equals(ticketsResp.isSuccess())) {
            List<Transaction> transactions = (List<Transaction>) (ticketsResp.getData());
            Formatter.tableTemplate(transactions);
            return Boolean.FALSE.equals(transactions.isEmpty());
        }
        return false;
    }


    public void cancelTicket(AuthenticatedUser authenticatedUser) {
        if(Boolean.FALSE.equals( authenticatedUser.getIsLoggedIn())) return;
        boolean ticketDisplayed = displayTickets(authenticatedUser);
        if(ticketDisplayed){
            Integer pnr = inputView.getIntegerInput("PNR: ");
            response = bookingController.cancelTicket(authenticatedUser, pnr);
            System.out.println(response.getMessage());
        }
    }

    private List<Train> filterTrains(TrainSearchDetail trainSearchDetail) {
        response = trainController.searchTrains(trainSearchDetail);

        List<Train> filteredTrains = null;
        if (response.getStatus().equals(FAILURE)) {
            System.out.println(response.getMessage());
        } else {
            filteredTrains = (List<Train>) (response.getData());
        }
        return filteredTrains;
    }



    private boolean confirmBooking() {
        while (true) {
            String choice = inputView.getStringInput("Do you want to book ticket ? (y/n): ");
            if (choice.equalsIgnoreCase("Y")) {
                return true;
            } else if (!choice.equalsIgnoreCase("N")) {
                System.out.println("Enter valid input.");
            } else {
                return false;
            }
        }
    }

    public Integer getTransactionsCountByTrainNumber(AuthenticatedUser authenticatedUser, int trainNumber){
            response = bookingController.getTransactionsCountByTrainNumber(authenticatedUser, trainNumber);
        if(response.isSuccess()){
            return (Integer) (response.getData());
        }else{
            System.out.println(response.getMessage());
            return -1;
        }
    }
}
