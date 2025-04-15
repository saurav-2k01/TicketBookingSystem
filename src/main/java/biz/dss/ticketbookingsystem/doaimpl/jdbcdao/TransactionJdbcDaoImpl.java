package biz.dss.ticketbookingsystem.doaimpl.jdbcdao;

import biz.dss.ticketbookingsystem.dao.TransactionDao;
import biz.dss.ticketbookingsystem.enums.Gender;
import biz.dss.ticketbookingsystem.enums.TravellingClass;
import biz.dss.ticketbookingsystem.enums.UserType;
import biz.dss.ticketbookingsystem.models.*;
import biz.dss.ticketbookingsystem.utils.DbConnection;
import biz.dss.ticketbookingsystem.utils.SqlQueries;
import lombok.extern.slf4j.Slf4j;

import java.sql.Date;
import java.sql.*;
import java.time.DayOfWeek;
import java.util.*;

@Slf4j
public class TransactionJdbcDaoImpl implements TransactionDao {
    Connection connection = DbConnection.getConnection();

    @Override
    public Optional<Transaction> addTransaction(Transaction transaction) throws SQLException {
        boolean isPassengersAdded = addPassengers(transaction.getPassengers());
        if (!isPassengersAdded) return Optional.empty();

        Optional<Transaction> transaction1 = createTransaction(transaction);
        if (transaction1.isEmpty()) return Optional.empty();

        boolean isPassengerTransactionMapped = mapPassengerTransaction(transaction1.get().getPnr(), transaction1.get().getPassengers());
        if (!isPassengerTransactionMapped) return Optional.empty();

        boolean isUserTransactionMapped = mapUserTransaction(transaction1.get().getUser().getId(), transaction1.get().getPnr());
        if (!isUserTransactionMapped) return Optional.empty();

        return transaction1;
    }

    @Override
    public Optional<Transaction> cancelTransaction(Transaction transaction) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.CANCEL_TICKET)) {
            preparedStatement.setInt(1, transaction.getPnr());
            int rowAffected = preparedStatement.executeUpdate();
            if (rowAffected > 0) {
                return Optional.of(transaction);
            } else {
                return Optional.empty();
            }
        }

    }

    @Override
    public Optional<Transaction> getTransactionByPnr(Integer pnr) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.GET_TRANSACTION)) {
            preparedStatement.setInt(1, pnr);
            ResultSet resultSet = preparedStatement.executeQuery();
            int pnr1 = 0;
            int trainNumber = 0;
            int sourceId = 0;
            int destinationId = 0;
            double totalFare = 0.0;
            Date dateOfJourney = null;
            boolean isCancelled = false;
            User user = null;
            Train train;
            Station source;
            Station destination;
            List<User> passengers = new ArrayList<>();
            while (resultSet.next()) {
//                transaction
                if (pnr1 == 0 && trainNumber == 0 && sourceId == 0 && destinationId == 0) {
                    pnr1 = resultSet.getInt("pnr");
                    trainNumber = resultSet.getInt("train_number");
                    sourceId = resultSet.getInt("source");
                    destinationId = resultSet.getInt("destination");
                }
                if (totalFare == 0.0 && Objects.isNull(dateOfJourney) && Boolean.FALSE.equals(isCancelled)) {
                    dateOfJourney = resultSet.getDate("date_of_journey");
                    totalFare = resultSet.getDouble("total_fare");
                    isCancelled = resultSet.getBoolean("is_cancelled");
                }
//                user
                User userFromResultSet = getUserFromResultSet(resultSet);
                if (Objects.isNull(user) && Boolean.FALSE.equals(Objects.isNull(userFromResultSet))) {
                    user = userFromResultSet;
                }
//                passenger
                User passenger = getPassengerFromResultSet(resultSet);
                if (Objects.nonNull(passenger)) {
                    passengers.add(passenger);
                }
            }


//            if (Objects.isNull(train)) {
//                Optional<Train> trainByTrainNumber = getTrainByTrainNumber(trainNumber);
//                if (trainByTrainNumber.isPresent()){
//                    train = trainByTrainNumber.get();
//                    if (Objects.isNull(source) && Objects.isNull(destination)) {
//                        int finalSourceId = sourceId;
//                        source = train.getRoute().stream().filter(s -> s.getId() == finalSourceId).findFirst().orElse(null);
//                        int finalDestinationId = destinationId;
//                        destination = train.getRoute().stream().filter(d -> d.getId() == finalDestinationId).findFirst().orElse(null);
//                    }
//                }else{
//                    return Optional.empty();
//                }
//            }
                Optional<Train> trainByTrainNumber = getTrainByTrainNumber(trainNumber);
                if (trainByTrainNumber.isPresent()){
                    train = trainByTrainNumber.get();
                    int finalSourceId = sourceId;
                    source = train.getRoute().stream().filter(s -> s.getId() == finalSourceId).findFirst().orElse(null);
                    int finalDestinationId = destinationId;
                    destination = train.getRoute().stream().filter(d -> d.getId() == finalDestinationId).findFirst().orElse(null);
                }else{
                    return Optional.empty();
                }
            Transaction transaction = new Transaction(train, source, destination, Objects.isNull(dateOfJourney) ? null : dateOfJourney.toLocalDate(), passengers, user, totalFare);
            transaction.setPnr(pnr1);
            transaction.setCancelled(isCancelled);
            return Optional.of(transaction);
        }
    }

    @Override
    public List<Transaction> getTransactionByUserId(Integer userId) throws SQLException {
        ResultSet resultSet;
        try (PreparedStatement preparedStatement = connection.prepareStatement("select pnr from transaction where user_id =?")) {
            preparedStatement.setInt(1, userId);
            resultSet = preparedStatement.executeQuery();
            List<Integer> pnrList = new ArrayList<>();
            while (resultSet.next()) {
                int pnr = resultSet.getInt("pnr");
                pnrList.add(pnr);
            }
            List<Transaction> transactions = new ArrayList<>();

            for(int pnr: pnrList){
                Optional<Transaction> transactionByPnr = getTransactionByPnr(pnr);
                transactionByPnr.ifPresent(transactions::add);
            }
            return transactions;
        }
    }

    @Override
    public Optional<Transaction> updateTransaction(Transaction transaction) {
        return Optional.empty();
    }

    @Override
    public List<Transaction> getTransactions() throws SQLException {
        ResultSet resultSet;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.GET_ALL_PNR)) {
            resultSet = preparedStatement.executeQuery();
            List<Integer> pnrList = new ArrayList<>();
            while (resultSet.next()) {
                int pnr = resultSet.getInt("pnr");
                pnrList.add(pnr);
            }
            List<Transaction> transactions = new ArrayList<>();
            pnrList.forEach(p -> {
                try {
                    Optional<Transaction> transactionByPnr = getTransactionByPnr(p);
                    transactionByPnr.ifPresent(transactions::add);
                } catch (SQLException e) {
                    log.error("Error occurred while getting transaction.", e);
                }
            });
            return transactions;
        }
    }

    private boolean addPassengers(List<User> passengers) throws SQLException {
        boolean status = true;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.ADD_PASSENGER)) {
            for (User passenger : passengers) {
                preparedStatement.setInt(1, passenger.getId());
                preparedStatement.setString(2, passenger.getName());
                preparedStatement.setString(3, passenger.getGender().toString());
                preparedStatement.setInt(4, passenger.getAge());
                preparedStatement.setString(5, passenger.getSeatNumber());
                preparedStatement.setString(6, UserType.PASSENGER.toString());
                preparedStatement.addBatch();
            }
            int[] rowAffected = preparedStatement.executeBatch();
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

    private Optional<Transaction> createTransaction(Transaction transaction) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.ADD_TRANSACTION)) {
            preparedStatement.setInt(1, transaction.getPnr());
            preparedStatement.setDate(2, Date.valueOf(transaction.getDateOfJourney()));
            preparedStatement.setDouble(3, transaction.getTotalFare());
            preparedStatement.setBoolean(4, transaction.isCancelled());
            preparedStatement.setInt(5, transaction.getUser().getId());
            preparedStatement.setInt(6, transaction.getTrain().getTrainNumber());
            preparedStatement.setInt(7, transaction.getSource().getId());
            preparedStatement.setInt(8, transaction.getDestination().getId());
            int rowAffected = preparedStatement.executeUpdate();
            if (rowAffected > 0) {
                return Optional.of(transaction);
            } else {
                return Optional.empty();
            }
        }
    }

    private boolean mapPassengerTransaction(int pnr, List<User> passengers) throws SQLException {
        boolean status = true;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.MAP_PASSENGER_TRANSACTION)) {
            preparedStatement.setInt(1, pnr);
            for (User passenger : passengers) {
                preparedStatement.setInt(2, passenger.getId());
                preparedStatement.addBatch();
            }
            int[] rowAffected = preparedStatement.executeBatch();
            for (int i : rowAffected) {
                if (i == 0){
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

    private boolean mapUserTransaction(int userId, int pnr) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.MAP_USER_TRANSACTIONS)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, pnr);
            int rowAffected = preparedStatement.executeUpdate();
            if (rowAffected > 0) {
                return true;
            } else {
                connection.rollback();
                return false;
            }
        }
    }

    private Optional<Train> getTrainByTrainNumber(Integer trainNumber) throws SQLException {
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
                route.add(getStationFromResultSet(resultSet));
                runningDays.add(getRunningDayFromResultSet(resultSet));
            }
            if (Objects.isNull(trainName)) {
                return Optional.empty();
            }
            List<Station> stationList = route.stream().sorted().toList();
            Train train = Train.builder().trainNumber(trainNumber).trainName(trainName).source(stationList.getFirst()).destination(stationList.getLast()).coachList(new ArrayList<>(coaches)).runningDays(runningDays).route(new ArrayList<>(route)).build();
            return Optional.of(train);
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

    private User getUserFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("user_id");
        if (id == 0) return null;
        String name = resultSet.getString("user_name");
        String userUsername = resultSet.getString("user_username");
        int userAge = resultSet.getInt("user_age");
        String userGender = resultSet.getString("user_gender");
        String userEmail = resultSet.getString("user_email");
        String userPassword = resultSet.getString("password");
        String seatNumber = resultSet.getString("seat_number");
        String userUserType = resultSet.getString("user_user_type");
        String isLoggedIn = resultSet.getString("is_logged_in");

        return User.builder().id(id).name(name).userName(userUsername).age(userAge).gender(Gender.valueOf(userGender)).email(userEmail).password(userPassword).seatNumber(seatNumber).userType(UserType.valueOf(userUserType)).isLoggedIn(Boolean.valueOf(isLoggedIn)).build();
    }

    private User getPassengerFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("passenger_id");
        if (id == 0) return null;
        String passengerName = resultSet.getString("passenger_name");
        int passengerAge = resultSet.getInt("passenger_age");
        String passengerGender = resultSet.getString("passenger_gender");
        String seatNumber = resultSet.getString("passenger_seat_number");
        String passengerUserType = resultSet.getString("passenger_user_type");
        return User.builder().id(id).name(passengerName).age(passengerAge).gender(Gender.valueOf(passengerGender)).seatNumber(seatNumber).userType(UserType.valueOf(passengerUserType)).build();
    }
}
