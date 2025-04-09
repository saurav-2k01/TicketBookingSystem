package biz.dss.ticketbookingsystem.doaimpl.jdbcdao;

import biz.dss.ticketbookingsystem.dao.StationDao;
import biz.dss.ticketbookingsystem.dao.TrainDao;
import biz.dss.ticketbookingsystem.enums.Day;
import biz.dss.ticketbookingsystem.enums.TravellingClass;
import biz.dss.ticketbookingsystem.models.Coach;
import biz.dss.ticketbookingsystem.models.Station;
import biz.dss.ticketbookingsystem.models.Train;
import biz.dss.ticketbookingsystem.utils.DbConnection;
import biz.dss.ticketbookingsystem.utils.SqlQueries;
import biz.dss.ticketbookingsystem.utils.UtilClass;

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
//            preparedStatement.setInt(3, null);
//            preparedStatement.setInt(4, train.getDestination().getId());
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
            int trainNumber_ = 0;
            String trainName = null;
            Set<Coach> coaches = new HashSet<>();
            Set<Station> route = new HashSet<>();
            Set<DayOfWeek> runningDays = new HashSet<>();

            while (resultSet.next()) {
                if (trainNumber_ == 0) {
                    trainNumber_ = resultSet.getInt("train_number");
                }
                if (Objects.isNull(trainName)) {
                    trainName = resultSet.getString("train_name");
                }
                Coach coachFromResultSet = getCoachFromResultSet(resultSet);
                if (Boolean.FALSE.equals(Objects.isNull(coachFromResultSet))) {
                    coaches.add(coachFromResultSet);
                }
                Station stationFromResultSet = getStationFromResultSet(resultSet);
                if (Boolean.FALSE.equals(Objects.isNull(stationFromResultSet))) {
                    route.add(stationFromResultSet);
                }
                DayOfWeek runningDayFromResultSet = getRunningDayFromResultSet(resultSet);
                if (Boolean.FALSE.equals(Objects.isNull(runningDayFromResultSet))) {
                    runningDays.add(runningDayFromResultSet);
                }
            }
            List<Station> stationList = route.stream().sorted().collect(Collectors.toList());
            Train train;
            if (stationList.isEmpty()) {
                train = Train.builder().trainNumber(trainNumber).trainName(trainName).source(null).destination(null)
                        .coachList(new ArrayList<>(coaches)).runningDays(runningDays).route(new ArrayList<>(route)).build();
            } else {
                train = Train.builder().trainNumber(trainNumber).trainName(trainName).source(stationList.getFirst()).destination(stationList.getLast())
                        .coachList(new ArrayList<>(coaches)).runningDays(runningDays).route(new ArrayList<>(route)).build();
            }

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
    public Optional<Train> updateTrain(Train train) throws SQLException {
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
        try (PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.updateTrain)) {
            boolean isRouteAdded = updateRoute(train.getTrainNumber(), train.getRoute());
            preparedStatement.setInt(1, train.getTrainNumber());
            preparedStatement.setString(2, train.getTrainName());
            if(isRouteAdded){
                preparedStatement.setInt(3, train.getRoute().getFirst().getId());
                preparedStatement.setInt(4, train.getRoute().getLast().getId());
            }else{
                preparedStatement.setInt(3, 0);
                preparedStatement.setInt(4, 0);
            }
            preparedStatement.setInt(5, train.getTrainNumber());
            int rowAffected = preparedStatement.executeUpdate();
            boolean isRunningDaysAdded = updateRunningDays(train.getTrainNumber(), train.getRunningDays().stream().toList());
            boolean isCoachesUpdated = updateCoaches(train.getTrainNumber(), train.getCoachList());
            if(isCoachesUpdated || isRunningDaysAdded || isRouteAdded){
                return Optional.of(train);
            }
        }

        return Optional.empty();
    }

    @Override
    public Optional<Train> deleteTrain(Train train) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.deleteTrain);
            preparedStatement.setInt(1, train.getTrainNumber());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
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
            while (resultSet.next()) {
                int trainNumber = resultSet.getInt("train_number");
                trainNumbers.add(trainNumber);
            }
            List<Train> trains = new ArrayList<>();
            for (int train_number : trainNumbers) {
                Optional<Train> trainByTrainNumber = getTrainByTrainNumber(train_number);
                trainByTrainNumber.ifPresent(trains::add);
            }
            return trains;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Coach getCoachFromResultSet(ResultSet resultSet) throws SQLException {
        int coachId = resultSet.getInt("coach_id");
        if (coachId == 0) return null;
        String travellingClass = resultSet.getString("travelling_class");
        String coachName = resultSet.getString("coach_name");
        int totalSeats = resultSet.getInt("total_seats");
        double fareFactor = resultSet.getDouble("fare_factor");

        Coach coach = new Coach(TravellingClass.valueOf(travellingClass), coachName, totalSeats, fareFactor);
        coach.setId(coachId);
        return coach;
    }

    private Station getStationFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("station_id");
        if (id == 0) return null;
        String stationName = resultSet.getString("station_name");
        String shortName = resultSet.getString("short_name");
        int sequenceNum = resultSet.getInt("sequence_num");
        Station station = new Station(id, stationName, shortName);
        station.setSequence_num(sequenceNum);
        return station;
    }

    private DayOfWeek getRunningDayFromResultSet(ResultSet resultSet) throws SQLException {
        String runningDay = resultSet.getString("running_day");
        if (Objects.isNull(runningDay)) return null;
        return DayOfWeek.valueOf(runningDay);
    }

    private boolean updateRoute(int trainNumber, List<Station> route) throws SQLException {
        if(route.isEmpty()) return false;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.updateTrainRoute)) {
            preparedStatement.setInt(1, trainNumber);
            for (int sequence = 1; sequence <= route.size(); sequence++) {
                preparedStatement.setInt(2, route.get(sequence - 1).getId());
                preparedStatement.setInt(3, sequence);
                preparedStatement.addBatch();
            }
            int[] rowsAffected = preparedStatement.executeBatch();
            boolean status = true;
            for (int i: rowsAffected){
                if (i==0){
                    status= false;
                    break;
                }
            }
            if(status){
                return true;
            }else{
                connection.rollback();
                return false;
            }
        }
    }

    private boolean updateCoaches(int trainNumber, List<Coach> coaches) throws SQLException {
        if(coaches.isEmpty()) return false;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.updateTrainCoach)) {
        preparedStatement.setInt(1, trainNumber);
            boolean isCoachesAdded = addCoaches(coaches);
            if(Boolean.FALSE.equals(isCoachesAdded)) return false;

            for(Coach coach : coaches){
                preparedStatement.setInt(2, coach.getId());
                preparedStatement.addBatch();
            }

            int[] rowAffected = preparedStatement.executeBatch();
            boolean status = true;
            for(int i: rowAffected){
                if(i==0){
                    status = false;
                    break;
                }
            }
            if(status){
                return true;
            }else{
                connection.rollback();
                return  false;
            }
        }
    }
    private boolean addCoaches(List<Coach> coaches) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.addCoach)) {
            for (Coach coach: coaches){
                preparedStatement.setInt(1, coach.getId());
                preparedStatement.setString(2, coach.getTravellingClass().toString());
                preparedStatement.setString(3, coach.getCoachName());
                preparedStatement.setInt(4, coach.getTotalSeats());
                preparedStatement.setDouble(5, coach.getFareFactor());
                preparedStatement.addBatch();
            }
            int[] rowAffected = preparedStatement.executeBatch();
            boolean status = true;
            for(int i: rowAffected){
                if(i==0){
                    status = false;
                    break;
                }
            }
            if(status){
                return true;
            }else{
                connection.rollback();
                return false;
            }
        }
    }

    private boolean updateRunningDays(int trainNumber, List<DayOfWeek> runningDays) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.updateTrainRunningDays)) {
            preparedStatement.setInt(1, trainNumber);
            for (DayOfWeek day: runningDays){
                preparedStatement.setInt(2, day.ordinal());
                preparedStatement.addBatch();
            }
            int[] rowAffected = preparedStatement.executeBatch();
            boolean status = true;
            for(int i: rowAffected){
                if(i==0){
                    status = false;
                    break;
                }
            }
            if(status){
                return true;
            }else{
                connection.rollback();
                return false;
            }

        }
    }
}