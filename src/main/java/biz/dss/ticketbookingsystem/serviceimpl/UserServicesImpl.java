//package biz.dss.ticketbookingsystem.serviceimpl;
//
//import biz.dss.ticketbookingsystem.dao.UserDao;
//import biz.dss.ticketbookingsystem.models.*;
//import biz.dss.ticketbookingsystem.service.BookingService;
//import biz.dss.ticketbookingsystem.service.FilterStation;
//import biz.dss.ticketbookingsystem.service.TrainService;
//import biz.dss.ticketbookingsystem.service.UserService;
//import biz.dss.ticketbookingsystem.utils.Response;
//import biz.dss.ticketbookingsystem.valueobjects.BookingDetail;
//import biz.dss.ticketbookingsystem.valueobjects.Credential;
//import biz.dss.ticketbookingsystem.valueobjects.TrainSearchDetail;
//
//import java.time.LocalDate;
//import java.util.List;
//import java.util.Objects;
//import java.util.Optional;
//
//import static biz.dss.ticketbookingsystem.utils.ResponseStatus.*;
//
//public class UserServicesImpl implements UserService {
//    private Response response;
//    private User currentUser;
//    private final UserDao userDao;
//
//
//    public UserServicesImpl(UserDao userDoa) {
//     // todo do not do this BookingService bookingService, TrainService trainService, FilterStation filterStation) {
//        this.userDao = userDoa;
//
//    }
//    @Override
//    public User getCurrentUser() {
//        return currentUser;
//    }
//
//
//    public Response register(User user) {
//        Optional<User> registeredUser = userDao.addUser(user);
//        if (registeredUser.isPresent()) {
//            response = new Response(registeredUser.get(), SUCCESS, "User has been registered successfully.");
//            return response;
//        } else {
//            response = new Response(FAILURE, "User registration was failed.");
//        }
//        return response;
//
//    }
//
////    public Response addPassenger(Passenger passenger) {
////        if(checkLogin().getStatus().equals(FAILURE)){
////            return checkLogin();
////        }
////
////        boolean status = currentUser.addPassenger(passenger);
////        if (status) {
////            response = new Response(status, SUCCESS, "Passenger was added to the master's list successfully.");
////        } else {
////            response = new Response(FAILURE, "Passenger was not added");
////        }
////        return response;
////    }
//
////    public Response bookTicket(BookingDetail bookingDetail) {
////        if(checkLogin().getStatus().equals(FAILURE)){
////            return checkLogin();
////        }
////        return bookingService.bookTicket(currentUser, bookingDetail);
////
////    }
//
////    public Response cancelTicket(int pnr) {
////        if(checkLogin().getStatus().equals(FAILURE)){
////            return checkLogin();
////        }
////
////        Response transactionResponse = this.bookingService.getTransaction(pnr);
////        if(transactionResponse.getStatus().equals(FAILURE)){
////            return transactionResponse;
////        }
////        Transaction transaction = (Transaction)(transactionResponse.getData());
////        if (transaction.isCancelled()) {
////            response = new Response(pnr, FAILURE, "Ticket is already cancelled.");
////        } else {
////            transaction.setCancelled(true);
////            response = new Response(pnr, SUCCESS, "Cancellation of ticket was successful.");
////        }
////        return response;
////    }
//
//
//    public Response getTicket(int pnr) {
//        if(checkLogin().getStatus().equals(FAILURE)){
//            return checkLogin();
//        }
//
//        Response transactionResponse = this.bookingService.getTransaction(pnr);
//        if(transactionResponse.getStatus().equals(FAILURE)){
//            return transactionResponse;
//        }
//        Transaction transaction = (Transaction)(transactionResponse.getData());
//        if (Objects.isNull(transaction)){
//            response = new Response(FAILURE, "No e-ticket with such pnr was found.");
//        }else{
//            ETicket eTicket = new ETicket(transaction);
//            response = new Response(eTicket, SUCCESS, "Your e-ticket.");
//        }
//        return response;
//    }
//
//    public Response getTickets() {
//        if(checkLogin().getStatus().equals(FAILURE)){
//            return checkLogin();
//        }
//
//        List<Response> etickets = currentUser.getPnrList().stream().map(bookingService::getTransaction).toList();
//        if(etickets.isEmpty()){
//            response = new Response(FAILURE, "No e-tickets were found.");
//        }else{
//            response = new Response(etickets, SUCCESS, "Here are your e-tickets.");
//        }
//        return response;
//    }
//
//    public Response logout() {
//        if(checkLogin().getStatus().equals(FAILURE)){
//            return checkLogin();
//        }
//
//        currentUser.setLoggedIn(false);
//        currentUser = null;
//        response = new Response(SUCCESS, "User is now logged out.");
//        return response;
//    }
//
////    public Response getCurrentUser() {
////        if(checkLogin().getStatus().equals(FAILURE)){
////            return checkLogin();
////        }
////
////        if(Objects.isNull(currentUser)){
////         response = new Response(FAILURE, "There is no current user.");
////        }else{
////            response = new Response(currentUser, SUCCESS, "Current user.");
////        }
////        return response;
////    }
//
//    public Response checkLogin() {
//        if (currentUser == null || ! currentUser.getIsLoggedIn()) {
//            response = new Response(false,FAILURE,"Login Required.");
//        }else{
//            response = new Response(true, SUCCESS, "User is Logged in.");
//        }
//        return response;
//    }
//
////    public Response getStationByName(String name){
////         return filterStation.getStationByName(name);
////    }
////
////    public Response getAvailableSeats(int trainNumber, LocalDate date){
////         return bookingService.getAvailableSeats(trainNumber, date);
////    }
//}
