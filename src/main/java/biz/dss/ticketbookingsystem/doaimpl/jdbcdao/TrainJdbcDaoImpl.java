package biz.dss.ticketbookingsystem.doaimpl.jdbcdao;

import biz.dss.ticketbookingsystem.dao.TrainDao;
import biz.dss.ticketbookingsystem.enums.TravellingClass;
import biz.dss.ticketbookingsystem.models.Coach;
import biz.dss.ticketbookingsystem.models.Station;
import biz.dss.ticketbookingsystem.models.Train;
import biz.dss.ticketbookingsystem.utils.DbConnection;
import biz.dss.ticketbookingsystem.utils.SqlQueries;

import java.sql.*;
import java.time.DayOfWeek;
import java.util.*;

public class TrainJdbcDaoImpl implements TrainDao {
    Connection connection = DbConnection.getConnection();

    @Override
    public Optional<Train> addTrain(Train train) throws SQLException {

        int rowAffected;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.ADD_TRAIN)) {
            preparedStatement.setInt(1, train.getTrainNumber());
            preparedStatement.setString(2, train.getTrainName());
            rowAffected = preparedStatement.executeUpdate();
            return rowAffected>0?Optional.of(train):Optional.empty();
        }
    }

    @Override
    public Optional<Train> getTrainById(Integer id) {
        return Optional.empty();
    }

    @Override
    public Optional<Train> getTrainByTrainNumber(Integer trainNumber) throws SQLException {
        ResultSet resultSet;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.GET_TRAIN_BY_TRAIN_NUMBER)) {
            preparedStatement.setInt(1, trainNumber);
            resultSet = preparedStatement.executeQuery();
            int trainNumberRes = 0;
            String trainName = null;
            Set<Coach> coaches = new HashSet<>();
            Set<Station> route = new HashSet<>();
            Set<DayOfWeek> runningDays = new HashSet<>();

            while (resultSet.next()) {
                if (trainNumberRes == 0) {
                    trainNumberRes = resultSet.getInt("train_number");
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
            if (Objects.isNull(trainName)) return Optional.empty();
            List<Station> stationList = route.stream().sorted().toList();

            Train train;
            if (stationList.isEmpty()) {
                train = Train.builder()
                        .trainNumber(trainNumber)
                        .trainName(trainName)
                        .source(null)
                        .destination(null)
                        .coachList(new ArrayList<>(coaches))
                        .runningDays(runningDays)
                        .route(new ArrayList<>(route))
                        .build();
            } else {
                train = Train.builder()
                        .trainNumber(trainNumber)
                        .trainName(trainName)
                        .source(stationList.getFirst())
                        .destination(stationList.getLast())
                        .coachList(new ArrayList<>(coaches))
                        .runningDays(runningDays)
                        .route(new ArrayList<>(route))
                        .build();
            }
            return Optional.of(train);
        }
    }

    @Override
    public Optional<Train> getTrainByTrainName(String trainName) {
        return Optional.empty();
    }

    @Override
    public Optional<Train> updateTrain(Train train) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.UPDATE_TRAIN)) {
            boolean isRouteAdded = updateRoute(train.getTrainNumber(), train.getRoute());
            preparedStatement.setInt(1, train.getTrainNumber());
            preparedStatement.setString(2, train.getTrainName());
            if (isRouteAdded) {
                preparedStatement.setInt(3, train.getRoute().getFirst().getId());
                preparedStatement.setInt(4, train.getRoute().getLast().getId());
            } else {
                preparedStatement.setNull(3, Types.INTEGER);
                preparedStatement.setNull(4, Types.INTEGER);
            }
            preparedStatement.setInt(5, train.getTrainNumber());
            int rowAffected = preparedStatement.executeUpdate();
            boolean isRunningDaysAdded = updateRunningDays(train.getTrainNumber(), train.getRunningDays().stream().toList());
            boolean isCoachesUpdated = updateCoaches(train.getTrainNumber(), train.getCoachList());
            if (isCoachesUpdated || isRunningDaysAdded || isRouteAdded) {
                return Optional.of(train);
            }
            return Optional.empty();
        }
    }

    @Override
    public Optional<Train> deleteTrain(Train train) throws SQLException {
        int rowAffected;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.DELETE_TRAIN)) {
            preparedStatement.setInt(1, train.getTrainNumber());
            rowAffected = preparedStatement.executeUpdate();
            return rowAffected>0?Optional.of(train):Optional.empty();
        }
    }

    @Override
    public List<Train> getTrains() throws SQLException {
        List<Integer> trainNumbers = new ArrayList<>();
        ResultSet resultSet;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.GET_TRAIN_NUMBERS)) {
            resultSet = preparedStatement.executeQuery();
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
        int sequenceNum = resultSet.getInt("seq_number");
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
        if (route.isEmpty()) return false;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.UPDATE_TRAIN_ROUTE)) {
            preparedStatement.setInt(1, trainNumber);
            preparedStatement.setInt(4, trainNumber);
            for (int sequence = 1; sequence <= route.size(); sequence++) {
//                for insert
                preparedStatement.setInt(2, route.get(sequence - 1).getId());
                preparedStatement.setInt(3, sequence);
//                for update
                preparedStatement.setInt(5, route.get(sequence - 1).getId());
                preparedStatement.setInt(6, sequence);
                preparedStatement.addBatch();
            }
            int[] rowsAffected = preparedStatement.executeBatch();
            boolean status = true;
            for (int i : rowsAffected) {
                if (i == 0) {
                    status = false;
                    break;
                }
            }
            if (status) {
                return true;
            } else {
                connection.rollback();
                return false;
            }
        }
    }

    private boolean updateCoaches(int trainNumber, List<Coach> coaches) throws SQLException {
        if (coaches.isEmpty()) return false;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.UPDATE_TRAIN_COACH)) {
            preparedStatement.setInt(1, trainNumber);
            preparedStatement.setInt(3, trainNumber);
            boolean isCoachesAdded = addCoaches(coaches);
            if (Boolean.FALSE.equals(isCoachesAdded)) return false;

            for (Coach coach : coaches) {
                preparedStatement.setInt(2, coach.getId());
                preparedStatement.setInt(4, coach.getId());
                preparedStatement.addBatch();
            }

            int[] rowAffected = preparedStatement.executeBatch();
            boolean status = true;
            for (int i : rowAffected) {
                if (i == 0) {
                    status = false;
                    break;
                }
            }
            if (status) {
                return true;
            } else {
                connection.rollback();
                return false;
            }
        }
    }

    private boolean addCoaches(List<Coach> coaches) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.ADD_COACH)) {
            for (Coach coach : coaches) {
//                for insert
                preparedStatement.setInt(1, coach.getId());
                preparedStatement.setString(2, coach.getTravellingClass().toString());
                preparedStatement.setString(3, coach.getCoachName());
                preparedStatement.setInt(4, coach.getTotalSeats());
                preparedStatement.setDouble(5, coach.getFareFactor());
//                for update
                preparedStatement.setInt(6, coach.getId());
                preparedStatement.setString(7, coach.getTravellingClass().toString());
                preparedStatement.setString(8, coach.getCoachName());
                preparedStatement.setInt(9, coach.getTotalSeats());
                preparedStatement.setDouble(10, coach.getFareFactor());
                preparedStatement.addBatch();
            }
            int[] rowAffected = preparedStatement.executeBatch();
            boolean status = true;
            for (int i : rowAffected) {
                if (i == 0) {
                    status = false;
                    break;
                }
            }
            if (status) {
                return true;
            } else {
                connection.rollback();
                return false;
            }
        }
    }

    private boolean updateRunningDays(int trainNumber, List<DayOfWeek> runningDays) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.UPDATE_TRAIN_RUNNING_DAYS)) {
            preparedStatement.setInt(1, trainNumber);
            preparedStatement.setInt(3, trainNumber);

            for (DayOfWeek day : runningDays) {

                preparedStatement.setInt(2, day.ordinal() + 1);
                preparedStatement.setInt(4, day.ordinal() + 1);
                preparedStatement.addBatch();
            }
            int[] rowAffected = preparedStatement.executeBatch();
            boolean status = true;
            for (int i : rowAffected) {
                if (i == 0) {
                    status = false;
                    break;
                }
            }
            if (status) {
                return true;
            } else {
                connection.rollback();
                return false;
            }

        }
    }

    public boolean removeRunningDay(Train train, List<DayOfWeek> runningDays) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.REMOVE_RUNNING_DAYS)) {
            preparedStatement.setInt(1, train.getTrainNumber());
            for (DayOfWeek day : runningDays) {
                preparedStatement.setInt(2, day.ordinal() + 1);
                preparedStatement.addBatch();
            }
            int[] rowsAffected = preparedStatement.executeBatch();
            boolean status = true;
            for (int i : rowsAffected) {
                if (i == 0) {
                    status = false;
                    break;
                }
            }
            return status;

        }

    }

    public boolean removeCoach(Train train, List<Coach> coaches) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.REMOVE_COACH)) {
            preparedStatement.setInt(1, train.getTrainNumber());
            for (Coach coach : coaches) {
                preparedStatement.setInt(2, coach.getId());
                preparedStatement.addBatch();
            }
            int[] rowsAffected = preparedStatement.executeBatch();
            boolean status = true;
            for (int i : rowsAffected) {
                if (i == 0) {
                    status = false;
                    break;
                }
            }
            return status;

        }

    }

    public boolean removeRoute(Train train, List<Station> route) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.REMOVE_STATION)) {
            preparedStatement.setInt(1, train.getTrainNumber());
            for (Station station : route) {
                preparedStatement.setInt(2, station.getId());
                preparedStatement.addBatch();
            }
            int[] rowsAffected = preparedStatement.executeBatch();
            boolean status = true;
            for (int i : rowsAffected) {
                if (i == 0) {
                    status = false;
                    break;
                }
            }
            return status;

        }
    }
}