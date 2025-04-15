package biz.dss.ticketbookingsystem;


import biz.dss.ticketbookingsystem.ui.AbstractUI;
import biz.dss.ticketbookingsystem.ui.AdminUI;
import biz.dss.ticketbookingsystem.controller.*;
import biz.dss.ticketbookingsystem.dao.*;
import biz.dss.ticketbookingsystem.doaimpl.jdbcdao.*;
import biz.dss.ticketbookingsystem.factory.DaoFactory;
import biz.dss.ticketbookingsystem.service.*;
import biz.dss.ticketbookingsystem.serviceimpl.*;
import biz.dss.ticketbookingsystem.utils.DbConnection;
import biz.dss.ticketbookingsystem.view.*;

import java.sql.Connection;
import java.sql.SQLOutput;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
//        ExecutorService executorService = Executors.newFixedThreadPool(5);
//
//        Runnable task1 = () -> {
//            System.out.println("==== Executing task1.......");
//            UserDao task1Instance = DaoFactory.getInstanceOfUserDao(UserJdbcDaoImpl.class);
//            System.out.println("--- Instance hashcode: {}" + task1Instance.hashCode());
//
//        };
//
//        Runnable task2 = () -> {
//            System.out.println("==== Executing task2.......");
//            UserDao instanceOfUserDao2 = DaoFactory.getInstanceOfUserDao(UserJdbcDaoImpl.class);
//            System.out.println("--- Instance hashcode: {}" + instanceOfUserDao2.hashCode());
//
//        };
//
//        Runnable task3 = () -> {
//            System.out.println("==== Executing task3.......");
//            UserDao task3Instance = DaoFactory.getInstanceOfUserDao(UserJdbcDaoImpl.class);
//            System.out.println("--- Instance hashcode: {}" + task3Instance.hashCode());
//        };
//
//        Runnable task4 = () -> {
//            System.out.println("==== Executing task4.......");
//            UserDao instanceOfUserDao = DaoFactory.getInstanceOfUserDao(UserJdbcDaoImpl.class);
//            System.out.println("--- Instance hashcode: {}" + instanceOfUserDao.hashCode());
//        };
//
//        executorService.submit(task1);
//        executorService.submit(task2);
//        executorService.submit(task3);
//        executorService.submit(task4);
//        executorService.submit(task3);
//        executorService.submit(task4);
//        executorService.submit(task3);
//        executorService.submit(task4);
//        executorService.submit(task3);
//        executorService.submit(task4);
//        executorService.submit(task3);
//        executorService.submit(task4);        executorService.submit(task3);
//        executorService.submit(task4);
//
//        executorService.submit(task3);
//        executorService.submit(task4);
//        executorService.submit(task3);
//        executorService.submit(task4);
//        executorService.submit(task3);
//        executorService.submit(task4);
//        executorService.submit(task3);
//        executorService.submit(task4);
//        executorService.submit(task3);
//        executorService.submit(task4);
//        executorService.submit(task3);
//        executorService.submit(task4);
//        executorService.submit(task3);
//        executorService.submit(task4);        executorService.submit(task3);
//        executorService.submit(task4);
//        executorService.submit(task3);
//        executorService.submit(task4);
//        executorService.submit(task3);
//        executorService.submit(task4);
//        executorService.submit(task3);
//        executorService.submit(task4);
//        executorService.submit(task3);
//        executorService.submit(task4);
//        executorService.submit(task3);
//        executorService.submit(task4);
//        executorService.submit(task3);
//        executorService.submit(task4);
//        executorService.submit(task3);
//        executorService.submit(task4);
//        executorService.submit(task3);
//        executorService.submit(task4);
//        executorService.submit(task3);
//        executorService.submit(task4);
//        executorService.submit(task3);
//        executorService.submit(task4);
//        executorService.submit(task4);
//        executorService.submit(task3);
//        executorService.submit(task4);
//        executorService.submit(task4);
//        executorService.submit(task3);
//        executorService.submit(task4);
//        executorService.submit(task4);
//        executorService.submit(task3);
//        executorService.submit(task4);
//        executorService.submit(task4);
//        executorService.submit(task3);
//        executorService.submit(task4);
//        executorService.submit(task4);
//        executorService.submit(task3);
//        executorService.submit(task4);
//        executorService.submit(task4);
//        executorService.submit(task3);
//        executorService.submit(task4);
//        executorService.submit(task4);
//        executorService.submit(task3);
//        executorService.submit(task4);
//        executorService.submit(task4);
//        executorService.submit(task3);
//        executorService.submit(task4);
//        executorService.submit(task4);
//        executorService.submit(task3);
//        executorService.submit(task4);
//        executorService.submit(task4);
//        executorService.submit(task3);
//        executorService.submit(task4);
//        executorService.submit(task4);
//        executorService.submit(task3);
//        executorService.submit(task4);
//        executorService.submit(task4);
//        executorService.submit(task3);
//        executorService.submit(task4);
//        executorService.submit(task4);
//        executorService.submit(task3);
//        executorService.submit(task4);executorService.submit(task4);
//        executorService.submit(task3);
//        executorService.submit(task4);
//        executorService.submit(task4);
//        executorService.submit(task3);
//        executorService.submit(task4);
//        executorService.submit(task4);
//        executorService.submit(task3);
//        executorService.submit(task4);executorService.submit(task4);
//        executorService.submit(task3);
//        executorService.submit(task4);
//        executorService.submit(task4);
//        executorService.submit(task3);
//        executorService.submit(task4);
//        executorService.submit(task4);
//        executorService.submit(task3);
//        executorService.submit(task4);
//        executorService.submit(task4);
//        executorService.submit(task3);
//        executorService.submit(task4);executorService.submit(task4);
//        executorService.submit(task3);
//        executorService.submit(task4);
//        executorService.submit(task4);
//        executorService.submit(task3);
//        executorService.submit(task4);
//
//
//
//
//
//
//
//
//
//
//
//
//        try {
//            if(! executorService.awaitTermination(2_000, TimeUnit.SECONDS)) {
//                executorService.shutdownNow();
//            }
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//
//
//        System.out.println("--- Main thread executed.......");


        TrainDao trainDao = DaoFactory.getInstanceOfTrainDao((TrainJdbcDaoImpl.class));
        UserDao userDao = DaoFactory.getInstanceOfUserDao(UserJdbcDaoImpl.class);
        StationDao stationDao = DaoFactory.getInstanceOfStationDao(StationJdbcDaoImpl.class);
        TransactionDao transactionDao = DaoFactory.getInstanceOfTransactionDao(TransactionJdbcDaoImpl.class);
        TrainBookingDao trainBookingDao = DaoFactory.getInstanceOfTrainBookingDao(TrainBookingJdbcImpl.class);

        AuthenticationService authenticationService = new AuthenticationServiceImpl(userDao);
        BookingService bookingService = new BookingServiceImpl(authenticationService, transactionDao, trainBookingDao);
        StationService stationService = new StationServiceImpl(authenticationService, stationDao);
        TrainService trainService = new TrainServiceImpl(authenticationService, trainDao);
        UserService userService = new UserServiceImplNew(authenticationService, userDao);

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

        AbstractUI adminUI = new AdminUI(authenticationController, inputView, userView, trainView, bookingView, stationView);

        adminUI.home();

    }



}