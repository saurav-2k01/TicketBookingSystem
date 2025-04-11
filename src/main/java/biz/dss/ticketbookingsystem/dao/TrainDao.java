package biz.dss.ticketbookingsystem.dao;

import biz.dss.ticketbookingsystem.models.Coach;
import biz.dss.ticketbookingsystem.models.Station;
import biz.dss.ticketbookingsystem.models.Train;

import java.sql.SQLException;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public interface TrainDao {
    Optional<Train> addTrain(Train train) throws SQLException;

    Optional<Train> getTrainById(Integer id);

    Optional<Train> getTrainByTrainNumber(Integer trainNumber) throws SQLException;

    Optional<Train> getTrainByTrainName(String trainName);

    Optional<Train> updateTrain(Train train) throws SQLException;

    Optional<Train> deleteTrain(Train train) throws SQLException;

    List<Train> getTrains() throws SQLException;

    boolean removeRunningDay(Train train, List<DayOfWeek> runningDays) throws SQLException;

    boolean  removeCoach(Train train, List<Coach> runningDays) throws SQLException;

    boolean  removeRoute(Train train, List<Station> route) throws SQLException;
}
