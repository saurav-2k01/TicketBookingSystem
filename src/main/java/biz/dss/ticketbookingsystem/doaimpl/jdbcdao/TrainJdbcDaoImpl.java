package biz.dss.ticketbookingsystem.doaimpl.jdbcdao;

import biz.dss.ticketbookingsystem.dao.TrainDao;
import biz.dss.ticketbookingsystem.models.Train;

import java.util.List;
import java.util.Optional;

public class TrainJdbcDaoImpl implements TrainDao {
    @Override
    public Optional<Train> addTrain(Train train) {
        return Optional.empty();
    }

    @Override
    public Optional<Train> getTrainById(Integer id) {
        return Optional.empty();
    }

    @Override
    public Optional<Train> getTrainByTrainNumber(Integer trainNumber) {
        return Optional.empty();
    }

    @Override
    public Optional<Train> getTrainByTrainName(String trainName) {
        return Optional.empty();
    }

    @Override
    public Optional<Train> updateTrain(Train train) {
        return Optional.empty();
    }

    @Override
    public Optional<Train> deleteTrain(Train train) {
        return Optional.empty();
    }

    @Override
    public List<Train> getTrains() {
        return List.of();
    }
}
