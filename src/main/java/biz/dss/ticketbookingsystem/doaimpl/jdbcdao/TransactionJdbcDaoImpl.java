package biz.dss.ticketbookingsystem.doaimpl.jdbcdao;

import biz.dss.ticketbookingsystem.dao.TrainBookingDao;
import biz.dss.ticketbookingsystem.models.TrainBooking;

import java.util.List;
import java.util.Optional;

public class TransactionJdbcDaoImpl implements TrainBookingDao {
    @Override
    public List<TrainBooking> getTrainBookings() {
        return List.of();
    }

    @Override
    public Optional<TrainBooking> addTrainBooking(TrainBooking trainBooking) {
        return Optional.empty();
    }
}
