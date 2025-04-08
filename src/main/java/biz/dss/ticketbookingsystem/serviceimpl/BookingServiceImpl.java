package biz.dss.ticketbookingsystem.serviceimpl;

import biz.dss.ticketbookingsystem.dao.TrainBookingDao;
import biz.dss.ticketbookingsystem.dao.TransactionDao;
import biz.dss.ticketbookingsystem.enums.TravellingClass;
import biz.dss.ticketbookingsystem.models.*;
import biz.dss.ticketbookingsystem.service.AuthenticationService;
import biz.dss.ticketbookingsystem.service.BookingService;
import biz.dss.ticketbookingsystem.service.TrainService;
import biz.dss.ticketbookingsystem.utils.Formatter;
import biz.dss.ticketbookingsystem.utils.Response;
import biz.dss.ticketbookingsystem.valueobjects.AuthenticatedUser;
import biz.dss.ticketbookingsystem.valueobjects.BookingDetail;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

import static biz.dss.ticketbookingsystem.utils.ResponseStatus.*;

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
        if(!response.isSuccess()) return response;
        User user = (User)(response.getData());
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

//        user.getPnrList().add(pnr);
//        System.out.println(user.getPnrList());
        try {
            transactionDao.addTransaction(transaction);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return new Response(transaction, SUCCESS, "you ticket was booked successfully.");

    }

    private List<TrainBooking> filterTrainBooking(int trainNumber, TravellingClass travellingClass, LocalDate date) {
        try {
            return trainBookingDao.getTrainBookings().stream()
                    .filter(x -> x.getTrainNumber() == trainNumber)
                    .filter(x -> x.getCoach().getTravellingClass().equals(travellingClass))
                    .filter(x -> x.getRunningDate().equals(date))
                    .toList();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<TrainBooking> filterTrainBooking(int trainNumber, LocalDate date) {
        try {
            return trainBookingDao.getTrainBookings().stream()
                    .filter(x -> x.getTrainNumber() == trainNumber)
                    .filter(x -> x.getRunningDate().equals(date))
                    .toList();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void updateTrainBookingData(Train train, LocalDate date) {
        for (Coach coach : train.getCoachList()) {
            TrainBooking trainBooking = new TrainBooking(train.getTrainNumber(), coach, date);
            trainBooking.setAvailableSeats(coach.getTotalSeats());
            try {
                trainBookingDao.addTrainBooking(trainBooking);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public Response getAvailableSeats(Train train, LocalDate date) {
        List<TrainBooking> filteredBookings = filterTrainBooking(train.getTrainNumber(), date);
        if(filteredBookings.isEmpty()){
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
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                return trainBooking.getCoach().getCoachName() + "-" + temp;
            }
        }
        return null;
    }

    @Override
    public Response getTransaction(AuthenticatedUser authenticatedUser, int pnr) {
        response = authenticationService.getAuthenticatedUser(authenticatedUser);
        if(!response.isSuccess()) return response;
        User user = (User)(response.getData());
        Optional<Transaction> transaction = null;
        try {
            transaction = transactionDao.getTransactionByPnr(pnr);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        response = transaction.map(value -> new Response(value, SUCCESS, "Transaction found."))
                .orElseGet(() -> new Response(FAILURE, "No transaction found with specified PNR"));
        return response;
    }



    public Response getTickets(AuthenticatedUser authenticatedUser) {
        response = authenticationService.getAuthenticatedUser(authenticatedUser);
        if(!response.isSuccess()) return response;
        User user = (User)(response.getData());
//        List<Transaction> etickets = user.getPnrList().stream().map(pnr->getTransaction(authenticatedUser, pnr)).filter(Response::isSuccess).map(r -> (Transaction) (r.getData())).toList();
        try {
            List<Transaction> etickets = transactionDao.getTransactionByUserId(authenticatedUser.getId());
            if(etickets.isEmpty()){
                response = new Response(FAILURE, "No e-tickets were found.");
            }else{
                response = new Response(etickets, SUCCESS, "Here are your e-tickets.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return response;
    }

        public Response cancelTicket(AuthenticatedUser authenticatedUser, int pnr) {
            response = authenticationService.getAuthenticatedUser(authenticatedUser);
            if (!response.isSuccess()) return response;
            User user = (User)(response.getData());

        Response transactionResponse = getTransaction(authenticatedUser, pnr);
        if(transactionResponse.getStatus().equals(FAILURE)){
            return transactionResponse;
        }
        Transaction transaction = (Transaction)(transactionResponse.getData());
        if (transaction.isCancelled()) {
            response = new Response(pnr, FAILURE, "Ticket is already cancelled.");
        } else {
            transaction.setCancelled(true);
            try {
               transactionDao.cancelTransaction(transaction);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            response = new Response(pnr, SUCCESS, "Cancellation of ticket was successful.");
        }
        return response;
    }

}
