package biz.dss.ticketbookingsystem.serviceimpl;

import biz.dss.ticketbookingsystem.dao.TrainBookingDao;
import biz.dss.ticketbookingsystem.dao.TransactionDao;
import biz.dss.ticketbookingsystem.enums.TravellingClass;
import biz.dss.ticketbookingsystem.enums.UserType;
import biz.dss.ticketbookingsystem.models.*;
import biz.dss.ticketbookingsystem.service.AuthenticationService;
import biz.dss.ticketbookingsystem.service.BookingService;
import biz.dss.ticketbookingsystem.utils.Response;
import biz.dss.ticketbookingsystem.valueobjects.AuthenticatedUser;
import biz.dss.ticketbookingsystem.valueobjects.BookingDetail;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

import static biz.dss.ticketbookingsystem.enums.UserType.ADMIN;
import static biz.dss.ticketbookingsystem.utils.ResponseStatus.FAILURE;
import static biz.dss.ticketbookingsystem.utils.ResponseStatus.SUCCESS;

@Slf4j
public class BookingServiceImpl implements BookingService {
    private final TransactionDao transactionDao;
    private final TrainBookingDao trainBookingDao;
    private final AuthenticationService authenticationService;
    private Response response;

    public BookingServiceImpl(AuthenticationService authenticationService, TransactionDao transactionDao, TrainBookingDao trainBookingDao) {
        this.authenticationService = authenticationService;
        this.trainBookingDao = trainBookingDao;
        this.transactionDao = transactionDao;
    }

    @Override
    public Response bookTicket(AuthenticatedUser authenticatedUser, BookingDetail bookingDetail) {
        response = authenticationService.getAuthenticatedUser(authenticatedUser);
        if (Boolean.FALSE.equals(response.isSuccess())) return response;
        User user = (User) (response.getData());
        List<User> passengerList = bookingDetail.getPassengerList();
        TravellingClass travellingClass = bookingDetail.getTravellingClass();
        LocalDate dateOfJourney = bookingDetail.getDateOfJourney();
        Train train = bookingDetail.getTrain();
        List<TrainBooking> trainBookings = filterTrainBooking(train.getTrainNumber(), travellingClass, dateOfJourney);

        if (trainBookings.isEmpty()) {
            updateTrainBookingData(train, dateOfJourney);
            trainBookings = filterTrainBooking(train.getTrainNumber(), travellingClass, dateOfJourney);
        }


        for (User passenger : passengerList) {
            passenger.setSeatNumber(bookSeat(trainBookings));
        }

        Transaction transaction = new Transaction(train, bookingDetail.getFrom(), bookingDetail.getTo(), bookingDetail.getDateOfJourney(), passengerList, user, bookingDetail.getTotalFare());

        try {
            transactionDao.addTransaction(transaction);
        } catch (SQLException e) {
            log.error("Error occurred while booking ticket for user {}", user.getId(), e);
            new Response(FAILURE, "unable to book ticket.");
        }
        return new Response(transaction, SUCCESS, "you ticket was booked successfully.");

    }

    private List<TrainBooking> filterTrainBooking(int trainNumber, TravellingClass travellingClass, LocalDate date) {
        List<TrainBooking> filteredTrainBooking;
        try {
            filteredTrainBooking = trainBookingDao.getTrainBookings().stream()
                    .filter(x -> x.getTrainNumber() == trainNumber)
                    .filter(x -> x.getCoach().getTravellingClass().equals(travellingClass))
                    .filter(x -> x.getRunningDate().equals(date))
                    .toList();
        } catch (SQLException e) {
            filteredTrainBooking = new ArrayList<>();
            log.error("Error occurred while filtering train booking data.", e);
        }
        return filteredTrainBooking;
    }

    private List<TrainBooking> filterTrainBooking(int trainNumber, LocalDate date) {
        List<TrainBooking> filteredTrainBooking;
        try {
            filteredTrainBooking = trainBookingDao.getTrainBookings().stream()
                    .filter(x -> x.getTrainNumber() == trainNumber)
                    .filter(x -> x.getRunningDate().equals(date))
                    .toList();
        } catch (SQLException e) {
            filteredTrainBooking = new ArrayList<>();
            log.error("Error occurred while filtering train booking data.", e);
        }
        return filteredTrainBooking;
    }

    private void updateTrainBookingData(Train train, LocalDate date) {

        for (Coach coach : train.getCoachList()) {
            TrainBooking trainBooking = new TrainBooking(train.getTrainNumber(), coach, date);
            trainBooking.setAvailableSeats(coach.getTotalSeats());
            try {
                trainBookingDao.addTrainBooking(trainBooking);
            } catch (SQLException e) {
                log.error("Error occurred while updating train booking data.", e);
            }
        }
    }

    public Response getAvailableSeats(Train train, LocalDate date) {
        List<TrainBooking> filteredBookings = filterTrainBooking(train.getTrainNumber(), date);
        if (filteredBookings.isEmpty()) {
            updateTrainBookingData(train, date);
            filteredBookings = filterTrainBooking(train.getTrainNumber(), date);
        }
        Map<TravellingClass, Integer> availableSeats = new HashMap<>();

        for (TrainBooking trainBooking : filteredBookings) {
            TravellingClass travellingClass = trainBooking.getCoach().getTravellingClass();
            if (availableSeats.containsKey(travellingClass)) {
                availableSeats.put(travellingClass, availableSeats.get(travellingClass) + trainBooking.getAvailableSeats());
            } else {
                availableSeats.put(travellingClass, trainBooking.getAvailableSeats());
            }
        }

        response = new Response(availableSeats, SUCCESS, "Available seats.");
        return response;
    }

    private String bookSeat(List<TrainBooking> trainBookings) {
        for (TrainBooking trainBooking : trainBookings) {
            if (trainBooking.getAvailableSeats() >= 1) {
                int temp = trainBooking.getCoach().getTotalSeats() - trainBooking.getAvailableSeats() + 1;
                trainBooking.setAvailableSeats(trainBooking.getAvailableSeats() - 1);
                try {
                    trainBookingDao.updateTrainBooking(trainBooking);
                    return trainBooking.getCoach().getCoachName() + "-" + temp;
                } catch (SQLException e) {
                    log.error("Error occurred while booking seat.", e);
                }
            }
        }
        return null;
    }

    @Override
    public Response getTransaction(AuthenticatedUser authenticatedUser, int pnr) {
        response = authenticationService.getAuthenticatedUser(authenticatedUser);
        if (Boolean.FALSE.equals(response.isSuccess())) return response;
//        User user = (User) (response.getData());
        Optional<Transaction> transaction = Optional.empty();
        try {
            transaction = transactionDao.getTransactionByPnr(pnr);
        } catch (SQLException e) {
            log.error("Error occurred while getting transaction.", e);
        }
        response = transaction.map(value -> new Response(value, SUCCESS, "Transaction found."))
                .orElseGet(() -> new Response(FAILURE, "No transaction found with specified PNR"));
        return response;
    }


    public Response getTickets(AuthenticatedUser authenticatedUser) {
        response = authenticationService.getAuthenticatedUser(authenticatedUser);
        if (Boolean.FALSE.equals(response.isSuccess())) return response;
        User user = (User) (response.getData());
        try {
            List<Transaction> etickets;
            if (user.getUserType().equals(ADMIN)){
                etickets = transactionDao.getTransactions();
            }else{
                etickets = transactionDao.getTransactionByUserId(user.getId());
            }
            response = new Response(etickets, SUCCESS, String.format("%d e-tickets were found.", etickets.size()));
        } catch (SQLException e) {
            log.error("Error occurred while getting tickets.", e);
            response = new Response(FAILURE, "Some error occurred while finding your tickets.");
        }

        return response;
    }

    public Response cancelTicket(AuthenticatedUser authenticatedUser, int pnr) {
        response = authenticationService.getAuthenticatedUser(authenticatedUser);
        if (Boolean.FALSE.equals(response.isSuccess())) return response;
//        User user = (User) (response.getData());

        Response transactionResponse = getTransaction(authenticatedUser, pnr);
        if (transactionResponse.getStatus().equals(FAILURE)) {
            return transactionResponse;
        }
        Transaction transaction = (Transaction) (transactionResponse.getData());
        if (transaction.isCancelled()) {
            response = new Response(pnr, FAILURE, "Ticket is already cancelled.");
        } else {
            transaction.setCancelled(true);
            try {
                transactionDao.cancelTransaction(transaction);
            } catch (SQLException e) {
                log.error("Error occurred while cancelling the ticket.", e);
            }
            response = new Response(pnr, SUCCESS, "Cancellation of ticket was successful.");
        }
        return response;
    }

}
