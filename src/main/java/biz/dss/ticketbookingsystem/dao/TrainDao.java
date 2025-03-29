package biz.dss.ticketbookingsystem.dao;

import biz.dss.ticketbookingsystem.models.Station;
import biz.dss.ticketbookingsystem.models.Train;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public interface TrainDao {
    public Optional<Train> addTrain(Train train);

    public Optional<Train> getTrainById(Integer id);

    public Optional<Train> getTrainByTrainNumber(Integer trainNumber);

    public Optional<Train> getTrainByTrainName(String trainName);

    public Optional<Train> updateTrain(Train train);

    public Optional<Train> deleteTrain(Train train);

    public List<Train> getTrains();
}
