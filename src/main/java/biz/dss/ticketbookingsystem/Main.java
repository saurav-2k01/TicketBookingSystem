package biz.dss.ticketbookingsystem;


import biz.dss.ticketbookingsystem.Ui.UserUI;
import biz.dss.ticketbookingsystem.controller.*;
import biz.dss.ticketbookingsystem.dao.*;
import biz.dss.ticketbookingsystem.doaimpl.collectiondao.*;
import biz.dss.ticketbookingsystem.doaimpl.jdbcdao.*;
import biz.dss.ticketbookingsystem.factory.DaoFactory;
import biz.dss.ticketbookingsystem.service.*;
import biz.dss.ticketbookingsystem.serviceimpl.*;
import biz.dss.ticketbookingsystem.view.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        TrainDao trainDao = DaoFactory.getInstanceOfTrainDao((TrainJdbcDaoImpl.class));
//        UserDao userDao = DaoFactory.getInstanceOfUserDao(UserCollectionDaoImpl.class);
        UserDao userDao = DaoFactory.getInstanceOfUserDao(UserJdbcDaoImpl.class);
        StationDao stationDao = DaoFactory.getInstanceOfStationDao(StationJdbcDaoImpl.class);
        TransactionDao transactionDao = DaoFactory.getInstanceOfTransaction(TransactionJdbcDaoImpl.class);
        TrainBookingDao trainBookingDao = DaoFactory.getInstanceOfTrainBookingDao(TrainBookingJdbcImpl.class);


        AuthenticationService authenticationService = new AuthenticationServiceImpl(userDao);
        BookingService bookingService = new BookingServiceImpl(authenticationService, transactionDao, trainBookingDao);
        StationService stationService = new StationServiceImpl(authenticationService, stationDao);
        TrainService trainService = new TrainServiceImpl(authenticationService, trainDao);
        UserService userService = new UserServiceImplNew(authenticationService,userDao);

        AuthenticationController authenticationController = new AuthenticationController(authenticationService);
        BookingController bookingController = new BookingController(bookingService);
        StationController stationController = new StationController(stationService);
        TrainController trainController = new TrainController(trainService);
        UserController userController = new UserController(userService);

        InputView inputView = new InputView(userController, stationController);
        BookingView bookingView = new BookingView(inputView, authenticationController, trainController, bookingController, userController);
        StationView stationView = new StationView(inputView, stationController);
        TrainView trainView = new TrainView(inputView, trainController, stationController, bookingView);
        UserView userView = new UserView(inputView, userController);

        UserUI userUI = new UserUI(authenticationController,inputView, userView, bookingView, trainView, stationView);

        userUI.home();


    }

}