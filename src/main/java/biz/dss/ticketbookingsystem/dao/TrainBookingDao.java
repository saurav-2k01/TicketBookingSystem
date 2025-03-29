package biz.dss.ticketbookingsystem.dao;

import biz.dss.ticketbookingsystem.models.TrainBooking;

import java.util.List;
import java.util.Optional;

public interface TrainBookingDao {
    List<TrainBooking> getTrainBookings();
    Optional<TrainBooking> addTrainBooking(TrainBooking trainBooking);
}
