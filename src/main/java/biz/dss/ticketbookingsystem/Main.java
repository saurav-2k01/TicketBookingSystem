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
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
//        ExecutorService executorService = Executors.newFixedThreadPool(4);
//        Set<Integer> hascodes = new HashSet<>();
//        Runnable task1 = () -> {
//            System.out.println("==== Executing task1.......");
//            UserDao task1Instance = DaoFactory.getInstanceOfUserDao(UserJdbcDaoImpl.class);
//            System.out.println("--- Instance hashcode: {}" + task1Instance.hashCode());
//            hascodes.add(task1Instance.hashCode());
//        };
//
//        Runnable task2 = () -> {
//            System.out.println("==== Executing task2.......");
//            UserDao instanceOfUserDao2 = DaoFactory.getInstanceOfUserDao(UserJdbcDaoImpl.class);
//            System.out.println("--- Instance hashcode: {}" + instanceOfUserDao2.hashCode());
//            hascodes.add(instanceOfUserDao2.hashCode());
//        };
//
//        Runnable task3 = () -> {
//            System.out.println("==== Executing task3.......");
//            UserDao task3Instance = DaoFactory.getInstanceOfUserDao(UserJdbcDaoImpl.class);
//            System.out.println("--- Instance hashcode: {}" + task3Instance.hashCode());
//            hascodes.add(task3Instance.hashCode());
//        };
//
//        Runnable task4 = () -> {
//            System.out.println("==== Executing task4.......");
//            UserDao instanceOfUserDao = DaoFactory.getInstanceOfUserDao(UserJdbcDaoImpl.class);
//            System.out.println("--- Instance hashcode: {}" + instanceOfUserDao.hashCode());
//            hascodes.add(instanceOfUserDao.hashCode());
//        };
//
//        List<Runnable> runnableList =List.of(task1, task2,task3, task4);
//
//        for(int i=0;i<4;i++){
//            System.out.println(i+1);
//            executorService.submit(runnableList.get(i%runnableList.size()));
//        }
//
//        try {
//            if(! executorService.awaitTermination(2_000, TimeUnit.MILLISECONDS)) {
//                executorService.shutdownNow();
//            }
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//        System.out.println(hascodes);
//
//
//        System.out.println("--- Main thread executed.......");


//        TrainDao trainDao = DaoFactory.getInstanceOfTrainDao((TrainJdbcDaoImpl.class));
//        UserDao userDao = DaoFactory.getInstanceOfUserDao(UserJdbcDaoImpl.class);
//        StationDao stationDao = DaoFactory.getInstanceOfStationDao(StationJdbcDaoImpl.class);
//        TransactionDao transactionDao = DaoFactory.getInstanceOfTransactionDao(TransactionJdbcDaoImpl.class);
//        TrainBookingDao trainBookingDao = DaoFactory.getInstanceOfTrainBookingDao(TrainBookingJdbcImpl.class);

        TrainDao trainDao = new TrainJdbcDaoImpl();
        UserDao userDao = new UserJdbcDaoImpl();
        StationDao stationDao = new StationJdbcDaoImpl();
        TransactionDao transactionDao = new TransactionJdbcDaoImpl();
        TrainBookingDao trainBookingDao = new TrainBookingJdbcImpl();

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