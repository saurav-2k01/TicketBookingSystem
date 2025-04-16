package biz.dss.ticketbookingsystem.factory;

import biz.dss.ticketbookingsystem.dao.*;
import biz.dss.ticketbookingsystem.doaimpl.collectiondao.UserCollectionDaoImpl;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

@Slf4j
public class DaoFactory {

    private static UserDao userDao;
    private static TrainDao trainDao;
    private static StationDao stationDao;
    private static TransactionDao transactionDao;
    private static TrainBookingDao trainBookingDao;

    private DaoFactory(){}

    public static UserDao getInstanceOfUserDao(Class<?> implementationClass){
//        if (Objects.isNull(userDao)){
//            synchronized (DaoFactory.class){
                if(Objects.isNull(userDao)){
                    try {
                        userDao = (UserDao) implementationClass.getConstructor().newInstance();
                    } catch (NoSuchMethodException | InvocationTargetException |InstantiationException | IllegalAccessException | ClassCastException e) {
                        System.err.println("Error occurred while instantiating UserDao.");
                        log.error("Error occurred while instantiating UserDao of class {}.", implementationClass, e);
                    }
                }
//            }
//        }
        return userDao;
    }

    public static TrainDao getInstanceOfTrainDao(Class<?> implementationClass){
//        if (Objects.isNull(trainDao)){
//            synchronized (DaoFactory.class){
                if(Objects.isNull(trainDao)){
                    try {
                        trainDao = (TrainDao) implementationClass.getConstructor().newInstance();
                    } catch (NoSuchMethodException | InvocationTargetException |InstantiationException | IllegalAccessException | ClassCastException e) {
                        System.err.println("Error occurred while instantiating TrainDao.");
                        log.error("Error occurred while instantiating TrainDao of class {}.", implementationClass, e);
                    }
//                }
//            }
        }
        return trainDao;
    }

    public static StationDao getInstanceOfStationDao(Class<?> implementationClass){
//        if (Objects.isNull(stationDao)){
//            synchronized (DaoFactory.class){
                if(Objects.isNull(stationDao)){
                    try {
                        stationDao = (StationDao) implementationClass.getConstructor().newInstance();
                    } catch (NoSuchMethodException | InvocationTargetException |InstantiationException | IllegalAccessException | ClassCastException e) {
                        System.err.println("Error occurred while instantiating StationDao.");
                        log.error("Error occurred while instantiating StationDao of class {}.", implementationClass, e);
                    }
//                }
//            }
        }
        return stationDao;
    }

    public static TransactionDao getInstanceOfTransactionDao(Class<?> implementationClass){
//        if (Objects.isNull(transactionDao)){
//            synchronized (DaoFactory.class){
                if(Objects.isNull(transactionDao)){
                    try {
                        transactionDao = (TransactionDao) implementationClass.getConstructor().newInstance();
                    } catch (NoSuchMethodException | InvocationTargetException |InstantiationException | IllegalAccessException | ClassCastException e) {
                        System.err.println("Error occurred while instantiating TransactionDao.");
                        log.error("Error occurred while instantiating TransactionDao of class {}.", implementationClass, e);
                    }
//                }
//            }
        }
        return transactionDao;
    }

    public static TrainBookingDao getInstanceOfTrainBookingDao(Class<?> implementationClass){
//        if (Objects.isNull(trainBookingDao)){
//            synchronized (DaoFactory.class){
                if(Objects.isNull(trainBookingDao)){
                    try {
                        trainBookingDao = (TrainBookingDao) implementationClass.getConstructor().newInstance();
                    } catch (NoSuchMethodException | InvocationTargetException |InstantiationException | IllegalAccessException | ClassCastException e) {
                        System.err.println("Error occurred while instantiating TrainBookingDao.");
                        log.error("Error occurred while instantiating TrainBookingDao of class {}.", implementationClass, e);
                    }
//                }
//            }
        }
        return trainBookingDao;
    }


}
