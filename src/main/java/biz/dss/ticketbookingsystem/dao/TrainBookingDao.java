package biz.dss.ticketbookingsystem.dao;

import biz.dss.ticketbookingsystem.models.TrainBooking;
import biz.dss.ticketbookingsystem.utils.SqlQueries;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface TrainBookingDao {
    List<TrainBooking> getTrainBookings() throws SQLException;
    Optional<TrainBooking> addTrainBooking(TrainBooking trainBooking) throws SQLException;
    Optional<TrainBooking> updateTrainBooking(TrainBooking trainBooking) throws SQLException;
}
