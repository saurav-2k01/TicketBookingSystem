package biz.dss.ticketbookingsystem.doaimpl.jdbcdao;

import biz.dss.ticketbookingsystem.dao.StationDao;
import biz.dss.ticketbookingsystem.dao.TrainDao;
import biz.dss.ticketbookingsystem.models.Coach;
import biz.dss.ticketbookingsystem.models.Station;
import biz.dss.ticketbookingsystem.models.Train;
import biz.dss.ticketbookingsystem.utils.DbConnection;
import biz.dss.ticketbookingsystem.utils.SqlQueries;

import javax.swing.text.html.Option;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class TrainJdbcDaoImpl implements TrainDao {
    Connection connection = DbConnection.getConnection();

    @Override
    public Optional<Train> addTrain(Train train) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.addTrain);
            preparedStatement.setInt(1, train.getTrainNumber());
            preparedStatement.setString(2, train.getTrainName());
            preparedStatement.setInt(3, train.getSource().getId());
            preparedStatement.setInt(4, train.getDestination().getId());
            int affectedRow = preparedStatement.executeUpdate();
            if (affectedRow > 0) {
                return Optional.of(train);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Train> getTrainById(Integer id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.getTrainByTrainNumber);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int trainNumber = resultSet.getInt("train_number");
                String trainName = resultSet.getString("train_name");
                int sourceId = resultSet.getInt("source");
                int destinationId = resultSet.getInt("destination");
                Optional<Station> sourceOpt = getStationById(sourceId);
                Optional<Station> destinationOpt = getStationById(destinationId);
                Train train = Train.builder().trainNumber(trainNumber).trainName(trainName).source(sourceOpt.get()).destination(destinationOpt.get()).build();
                return Optional.of(train);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Train> getTrainByTrainNumber(Integer trainNumber) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.getTrainByTrainNumber);
            preparedStatement.setInt(1, trainNumber);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int trainNumber_ = resultSet.getInt("train_number");
                String trainName = resultSet.getString("train_name");
                int sourceId = resultSet.getInt("source");
                int destinationId = resultSet.getInt("destination");
                Optional<Station> sourceOpt = getStationById(sourceId);
                Optional<Station> destinationOpt = getStationById(destinationId);
                Train train = Train.builder().trainNumber(trainNumber_).trainName(trainName).source(sourceOpt.get()).destination(destinationOpt.get()).build();
                return Optional.of(train);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Train> getTrainByTrainName(String trainName) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.getTrainByTrainNumber);
            preparedStatement.setString(1, trainName);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int trainNumber = resultSet.getInt("train_number");
                String trainName_ = resultSet.getString("train_name");
                int sourceId = resultSet.getInt("source");
                int destinationId = resultSet.getInt("destination");
                Optional<Station> sourceOpt = getStationById(sourceId);
                Optional<Station> destinationOpt = getStationById(destinationId);
                Train train = Train.builder().trainNumber(trainNumber).trainName(trainName_).source(sourceOpt.get()).destination(destinationOpt.get()).build();
                return Optional.of(train);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Train> updateTrain(Train train) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.updateTrain);
            preparedStatement.setInt(1, train.getTrainNumber());
            preparedStatement.setString(2, train.getTrainName());
            preparedStatement.setInt(3, train.getSource().getId());
            preparedStatement.setInt(4, train.getDestination().getId());
            preparedStatement.setInt(5, train.getTrainNumber());
            int affectedRows = preparedStatement.executeUpdate();
            if(affectedRows>0){
                return Optional.of(train);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Train> deleteTrain(Train train) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.deleteTrain);
            preparedStatement.setInt(1,train.getTrainNumber());
            int affectedRows = preparedStatement.executeUpdate();
            if(affectedRows>0){
                return Optional.of(train);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public List<Train> getTrains() {
        return List.of();
    }

    private Optional<Station> getStationById(Integer id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.getStationById);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id_ = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String shortName = resultSet.getString("shortname");
                Station station = new Station(id_, name, shortName);
                return Optional.of(station);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    private boolean updateTrainRoute(int trainNumber, List<Station> route){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.updateTrainRoute);
            preparedStatement.setInt(1, train_number);
            for(int i=1;i<=route.size();i++){
                preparedStatement.setInt(2, route.get(i-1).getId());
                preparedStatement.setInt(1, i);
                preparedStatement.addBatch();
            }
            int[] affectedRows = preparedStatement.executeBatch();
            if(affectedRows.length>0){
                return true;            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    private boolean updateTrainCoach(int trainNumber, List<Coach> coaches){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.updateTrainCoach);
            preparedStatement.setInt(1, trainNumber);
            for(int i=1;i<=coaches.size();i++){
                preparedStatement.setInt(2, coaches.get(i-1).getId());
                preparedStatement.addBatch();
            }
            int[] affectedRows = preparedStatement.executeBatch();
            if(affectedRows.length>0){
                return true;            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    private boolean updateTrainRunningDate(int trainNumber, List<DayOfWeek> runningDays){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.updateTrainCoach);
            preparedStatement.setInt(1, trainNumber);
            for(int i=1;i<=runningDays.size();i++){
                preparedStatement.setInt(2, runningDays.get(i-1).ordinal());
                preparedStatement.addBatch();
            }
            int[] affectedRows = preparedStatement.executeBatch();
            if(affectedRows.length>0){
                return true;            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}

// todo complete this incomplete class
