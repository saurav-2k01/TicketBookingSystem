package biz.dss.ticketbookingsystem.doaimpl.jdbcdao;

import biz.dss.ticketbookingsystem.dao.StationDao;
import biz.dss.ticketbookingsystem.dao.TrainDao;
import biz.dss.ticketbookingsystem.enums.TravellingClass;
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
import java.util.*;
import java.util.stream.Collectors;

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
//        try {
//            PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.getTrainByTrainNumber);
//            preparedStatement.setInt(1, id);
//            ResultSet resultSet = preparedStatement.executeQuery();
//            while (resultSet.next()) {
//                int trainNumber = resultSet.getInt("train_number");
//                String trainName = resultSet.getString("train_name");
//                int sourceId = resultSet.getInt("source");
//                int destinationId = resultSet.getInt("destination");
//                Optional<Station> sourceOpt = getStationById(sourceId);
//                Optional<Station> destinationOpt = getStationById(destinationId);
//                Train train = Train.builder().trainNumber(trainNumber).trainName(trainName).source(sourceOpt.get()).destination(destinationOpt.get()).build();
//                return Optional.of(train);
//            }
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
        return Optional.empty();
    }

    @Override
    public Optional<Train> getTrainByTrainNumber(Integer trainNumber) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.getTrainByTrainNumber);
            preparedStatement.setInt(1, trainNumber);
            ResultSet resultSet = preparedStatement.executeQuery();
            int trainNumber_=0;
            String trainName=null;
            Set<Coach> coaches = new HashSet<>();
            Set<Station> route = new HashSet<>();
            Set<DayOfWeek> runningDays = new HashSet<>();

            while(resultSet.next()){
                if(trainNumber_==0){
                    trainNumber_ = resultSet.getInt("train_number");
                }
                if(Objects.isNull(trainName)){
                    trainName = resultSet.getString("train_name");
                }
                coaches.add(getCoachFromResultSet(resultSet));
                route.add(getStationFromResultSet(resultSet));
                runningDays.add(getRunningDayFromResultSet(resultSet));
            }
            List<Station> stationList = route.stream().sorted().collect(Collectors.toList());
            Train train = Train.builder().trainNumber(trainNumber).trainName(trainName).source(stationList.getFirst()).destination(stationList.getLast())
                    .coachList(new ArrayList<>(coaches)).runningDays(runningDays).route(new ArrayList<>(route)).build();
            return Optional.of(train);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Train> getTrainByTrainName(String trainName) {
//        try {
//            PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.getTrainByTrainNumber);
//            preparedStatement.setString(1, trainName);
//            ResultSet resultSet = preparedStatement.executeQuery();
//            while (resultSet.next()) {
//                int trainNumber = resultSet.getInt("train_number");
//                String trainName_ = resultSet.getString("train_name");
//                int sourceId = resultSet.getInt("source");
//                int destinationId = resultSet.getInt("destination");
//                Optional<Station> sourceOpt = getStationById(sourceId);
//                Optional<Station> destinationOpt = getStationById(destinationId);
//                Train train = Train.builder().trainNumber(trainNumber).trainName(trainName_).source(sourceOpt.get()).destination(destinationOpt.get()).build();
//                return Optional.of(train);
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
        return Optional.empty();
    }

    @Override
    public Optional<Train> updateTrain(Train train) {
//        try {
//            PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.updateTrain);
//            preparedStatement.setInt(1, train.getTrainNumber());
//            preparedStatement.setString(2, train.getTrainName());
//            preparedStatement.setInt(3, train.getSource().getId());
//            preparedStatement.setInt(4, train.getDestination().getId());
//            preparedStatement.setInt(5, train.getTrainNumber());
//            int affectedRows = preparedStatement.executeUpdate();
//            if(affectedRows>0){
//                return Optional.of(train);
//            }
//            updateTrainCoach(train.getTrainNumber(), train.getCoachList());
//            updateTrainRoute(train.getTrainNumber(), train.getRoute());
//            updateTrainRunningDay(train.getTrainNumber(), train.getRunningDays());
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
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
//        List<Train> trains = new ArrayList<>();
//        try {
//            PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.getAllTrains);
//            ResultSet resultSet = preparedStatement.executeQuery();
//            while (resultSet.next()) {
//                int trainNumber_ = resultSet.getInt("train_number");
//                String trainName = resultSet.getString("train_name");
//                int sourceId = resultSet.getInt("source");
//                int destinationId = resultSet.getInt("destination");
//                Optional<Station> sourceOpt = getStationById(sourceId);
//                Optional<Station> destinationOpt = getStationById(destinationId);
//                Train train = Train.builder().trainNumber(trainNumber_).trainName(trainName).source(sourceOpt.get()).destination(destinationOpt.get()).build();
//                trains.add(train);
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
        try {
            List<Integer> trainNumbers = new ArrayList<>();
            PreparedStatement preparedStatement = connection.prepareStatement("select train_number from train;");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                int trainNumber = resultSet.getInt("train_number");
                trainNumbers.add(trainNumber);
            }
            List<Train> trains = new ArrayList<>();
            for(int train_number : trainNumbers){
                Optional<Train> trainByTrainNumber = getTrainByTrainNumber(train_number);
                trainByTrainNumber.ifPresent(trains::add);
            }
            return trains;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static Coach getCoachFromResultSet(ResultSet resultSet) throws SQLException {
        int coachId = resultSet.getInt("coach_id");
        String travellingClass = resultSet.getString("travelling_class");
        String coachName = resultSet.getString("coach_name");
        int totalSeats = resultSet.getInt("total_seats");
        int availableSeats = resultSet.getInt("available_seats");
        double fareFactor = resultSet.getDouble("fare_factor");
        return new Coach(coachId, TravellingClass.valueOf(travellingClass),coachName, totalSeats, fareFactor);
    }

    private static Station getStationFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("station_id");
        String stationName = resultSet.getString("station_name");
        String shortName = resultSet.getString("short_name");
        int sequenceNum = resultSet.getInt("sequence_num");
        Station station = new Station(id, stationName,shortName);
        station.setSequence_num(sequenceNum);
        return station;
    }

    private static DayOfWeek getRunningDayFromResultSet(ResultSet resultSet) throws SQLException {
        String runningDay = resultSet.getString("running_day");
        return DayOfWeek.valueOf(runningDay);
    }
}