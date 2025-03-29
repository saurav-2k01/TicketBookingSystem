package biz.dss.ticketbookingsystem.doaimpl.collectiondao;

import biz.dss.ticketbookingsystem.dao.TrainBookingDao;
import biz.dss.ticketbookingsystem.models.TrainBooking;

import java.util.*;


public class TrainBookingCollectionDaoImpl implements TrainBookingDao {
    private final List<TrainBooking> trainBookings = new ArrayList<>();

    public List<TrainBooking> getTrainBookings() {
        return trainBookings;
    }

    public Optional<TrainBooking> addTrainBooking(TrainBooking trainBooking){
        if(Objects.isNull(trainBooking)){
            throw new NullPointerException();
        }

        boolean isAdded;

        if(trainBookings.contains(trainBooking)){
            isAdded = false;
        }else{
            isAdded = trainBookings.add(trainBooking);
        }
        return isAdded? Optional.of(trainBooking) : Optional.empty();
    }
}
