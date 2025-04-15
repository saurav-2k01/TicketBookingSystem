package biz.dss.ticketbookingsystem.doaimpl.collectiondao;

import biz.dss.ticketbookingsystem.dao.TrainBookingDao;
import biz.dss.ticketbookingsystem.models.TrainBooking;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


public class TrainBookingCollectionDaoImpl implements TrainBookingDao {
    private final List<TrainBooking> trainBookings = new ArrayList<>();

    public List<TrainBooking> getTrainBookings() {
        return trainBookings;
    }

    public Optional<TrainBooking> addTrainBooking(TrainBooking trainBooking) {
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

    public Optional<TrainBooking> updateTrainBooking(TrainBooking trainBooking){
        if(Objects.isNull(trainBooking)){
            throw new NullPointerException();
        }
        Integer id = trainBooking.getId();
        Optional<TrainBooking> trainBookingResult = trainBookings.stream().filter(tb -> tb.getId().equals(id)).findFirst();
        if(trainBookingResult.isPresent()){
            int index = trainBookings.indexOf(trainBookingResult.get());
            trainBookings.set(index, trainBooking);
            return Optional.of(trainBooking);
        }else{
            return Optional.empty();
        }
    }
}
