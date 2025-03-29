package biz.dss.ticketbookingsystem.factory;

import biz.dss.ticketbookingsystem.dao.*;
import biz.dss.ticketbookingsystem.doaimpl.collectiondao.UserCollectionDaoImpl;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

public class DaoFactory {

    private static UserDao userDao;
    private static TrainDao trainDao;
    private static StationDao stationDao;
    private static TransactionDao transactionDao;
    private static TrainBookingDao trainBookingDao;

    private DaoFactory(){}


    public static UserDao getInstanceOfUserDao(Class<?> implementationClass){
        if (Objects.isNull(userDao)){
            synchronized (DaoFactory.class){
                if(Objects.isNull(userDao)){
                    try {
                        userDao = (UserDao) implementationClass.getConstructor().newInstance();
                    } catch (NoSuchMethodException | InvocationTargetException |InstantiationException | IllegalAccessException | ClassCastException e) {
                        System.out.println(e.getMessage());

                    }
                }
            }
        }
        return userDao;
    }

    public static TrainDao getInstanceOfTrainDao(Class<?> implementationClass){
        if (Objects.isNull(trainDao)){
            synchronized (DaoFactory.class){
                if(Objects.isNull(trainDao)){
                    try {
                        trainDao = (TrainDao) implementationClass.getConstructor().newInstance();
                    } catch (NoSuchMethodException | InvocationTargetException |InstantiationException | IllegalAccessException | ClassCastException e) {
                        System.out.println(e.getLocalizedMessage());
                    }
                }
            }
        }
        return trainDao;
    }

    public static StationDao getInstanceOfStationDao(Class<?> implementationClass){
        if (Objects.isNull(stationDao)){
            synchronized (DaoFactory.class){
                if(Objects.isNull(stationDao)){
                    try {
                        stationDao = (StationDao) implementationClass.getConstructor().newInstance();
                    } catch (NoSuchMethodException | InvocationTargetException |InstantiationException | IllegalAccessException | ClassCastException e) {
                        System.out.println(e.getLocalizedMessage());
                    }
                }
            }
        }
        return stationDao;
    }

    public static TransactionDao getInstanceOfTransaction(Class<?> implementationClass){
        if (Objects.isNull(transactionDao)){
            synchronized (DaoFactory.class){
                if(Objects.isNull(transactionDao)){
                    try {
                        transactionDao = (TransactionDao) implementationClass.getConstructor().newInstance();
                    } catch (NoSuchMethodException | InvocationTargetException |InstantiationException | IllegalAccessException | ClassCastException e) {
                        System.out.println(e.getLocalizedMessage());
                    }
                }
            }
        }
        return transactionDao;
    }

    public static TrainBookingDao getInstanceOfTrainBookingDao(Class<?> implementationClass){
        if (Objects.isNull(trainBookingDao)){
            synchronized (DaoFactory.class){
                if(Objects.isNull(trainBookingDao)){
                    try {
                        trainBookingDao = (TrainBookingDao) implementationClass.getConstructor().newInstance();
                    } catch (NoSuchMethodException | InvocationTargetException |InstantiationException | IllegalAccessException | ClassCastException e) {
                        System.out.println(e.getLocalizedMessage());
                    }
                }
            }
        }
        return trainBookingDao;
    }


}
