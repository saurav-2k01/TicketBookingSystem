package biz.dss.ticketbookingsystem.serviceimpl;//package serviceimplementations;
//
//
//import enums.TravellingClass;
//import models.*;
//import serviceinterfaces.BookingService;
//import serviceinterfaces.TrainService;
//import utilpackage.Response;
//import wrappers.BookingDetail;
//
//import java.time.LocalDate;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import static utilpackage.ResponseStatus.SUCCESS;
//
//public class BookingServiceImpl1 implements BookingService {
//    private final TrainService trainService;
//    private final Map<Integer, TrainBooking> trainBookingMap = new HashMap<>();
//    private final Map<Integer, Transaction> transactions;
//    private Admin currentAdmin;
//
//    public BookingServiceImpl1(TrainService trainService, Map<Integer, Transaction> transactions) {
//        this.trainService = trainService;
//        this.transactions = transactions;
//    }
//
//    @Override
//    public Response bookTicket(User user, BookingDetail bookingDetail) {
//
//        List<Passenger> passengerList = bookingDetail.getPassengerList();
//        int trainNumber = bookingDetail.getTrainNumber();
//        Train train = trainService.getTrain(trainNumber);
//        TravellingClass travellingClass = bookingDetail.getTravellingClass();
//        LocalDate dateOfJourney = bookingDetail.getDateOfJourney();
//
//        List<TrainBooking> trainBookings = filterTrainBooking(trainNumber, travellingClass, dateOfJourney);
//
//        if (trainBookings.isEmpty()) {
//            updateTrainBookingData(train, dateOfJourney);
//            trainBookings = filterTrainBooking(trainNumber, travellingClass, dateOfJourney);
//        }
//
//        for (Passenger passenger : passengerList) {
//            passenger.setSeatNumber(bookSeat(trainBookings));
//        }
//
//        Transaction transaction = new Transaction(bookingDetail.getTrainNumber(), train.getTrainName(), bookingDetail.getFrom(), bookingDetail.getTo(), bookingDetail.getDateOfJourney(), passengerList, user);
//        int pnr = transaction.getPnr();
//        user.getPnrList().add(pnr);
//        Transaction put = transactions.put(pnr, transaction);
//        ETicket eticket = new ETicket(transaction);
//        return new Response(eticket, SUCCESS, "you ticket was booked successfully.");
//    }
//
//    public void displayTrainBookingData() {
//        this.trainBookingMap.values().forEach(System.out::println);
//    }
//
//    @Override
//    public Transaction getTransaction(int pnr) {
//        Transaction transaction = transactions.get(pnr);
//        return transaction;
//    }
//
//    public Map<TravellingClass, Integer> getAvailableSeats(int trainNumber, LocalDate date) {
//
//        List<TrainBooking> filteredBookings = filterTrainBooking(trainNumber, date);
//        if (filteredBookings.isEmpty()) {
//            updateTrainBookingData(trainService.getTrain(trainNumber), date);
//            filteredBookings = filterTrainBooking(trainNumber, date);
//        }
//        Map<TravellingClass, Integer> availableSeats = new HashMap<>();
//        for (TrainBooking trainBooking : filteredBookings) {
//            TravellingClass travellingClass = trainBooking.getCoach().getTravellingClass();
//            if (availableSeats.containsKey(travellingClass)) {
//                availableSeats.put(travellingClass, availableSeats.get(travellingClass) + trainBooking.getAvailableSeats());
//            } else {
//                availableSeats.put(travellingClass, trainBooking.getAvailableSeats());
//            }
//        }
//        return availableSeats;
//    }
//
//    private List<TrainBooking> filterTrainBooking(int trainNumber, TravellingClass travellingClass, LocalDate date) {
//        return trainBookingMap.values().stream()
//                .filter(x -> x.getTrainNumber() == trainNumber)
//                .filter(x -> x.getCoach().getTravellingClass().equals(travellingClass))
//                .filter(x -> x.getRunningDate().equals(date))
//                .toList();
//    }
//
//    private List<TrainBooking> filterTrainBooking(int trainNumber, LocalDate date) {
//        return trainBookingMap.values().stream()
//                .filter(x -> x.getTrainNumber() == trainNumber)
//                .filter(x -> x.getRunningDate().equals(date))
//                .toList();
//    }
//
//    private void updateTrainBookingData(Train train, LocalDate date) {
//        for (Coach coach : train.getCoachList()) {
//            TrainBooking trainBooking = new TrainBooking(train.getTrainNumber(), coach, date);
//            trainBooking.setAvailableSeats(coach.getTotalSeats());
//            trainBookingMap.put(trainBooking.getId(), trainBooking);
//        }
//    }
//
//    private String bookSeat(List<TrainBooking> trainBookings) {
//        for (TrainBooking trainBooking : trainBookings) {
//            if (trainBooking.getAvailableSeats() >= 1) {
//                int temp = trainBooking.getCoach().getTotalSeats() - trainBooking.getAvailableSeats() + 1;
//                trainBooking.setAvailableSeats(trainBooking.getAvailableSeats() - 1);
//                trainBookingMap.put(trainBooking.getId(), trainBooking);
//                return trainBooking.getCoach().getCoachName() + "-" + temp;
//            }
//        }
//        return null;
//    }
//}
//
