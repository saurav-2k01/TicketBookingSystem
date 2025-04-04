package biz.dss.ticketbookingsystem.doaimpl.jdbcdao;

import biz.dss.ticketbookingsystem.dao.TrainBookingDao;
import biz.dss.ticketbookingsystem.dao.TransactionDao;
import biz.dss.ticketbookingsystem.enums.Gender;
import biz.dss.ticketbookingsystem.enums.TravellingClass;
import biz.dss.ticketbookingsystem.enums.UserType;
import biz.dss.ticketbookingsystem.models.*;
import biz.dss.ticketbookingsystem.utils.DbConnection;
import biz.dss.ticketbookingsystem.utils.SqlQueries;
import biz.dss.ticketbookingsystem.utils.UtilClass;

import javax.swing.text.html.Option;
import java.sql.*;
import java.sql.Date;
import java.time.DayOfWeek;
import java.util.*;
import java.util.stream.Collectors;

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
        try (PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.cancelTicket)) {
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
        try (PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.getTransaction)) {
            preparedStatement.setInt(1, pnr);
            ResultSet resultSet = preparedStatement.executeQuery();
            int pnr1=0;
            int trainNumber=0;
            int sourceId=0;
            int destinationId=0;
            double totalFare = 0.0;
            Date dateOfJourney = null;
            boolean isCancelled= false;
            User user=null;
            Train train = null;
            Station source = null;
            Station destination = null;
            List<User> passengers = new ArrayList<>();
            while (resultSet.next()) {
//                transaction
                if(pnr1==0   && trainNumber==0 && sourceId==0 && destinationId==0 ){
                    pnr1 = resultSet.getInt("pnr");
                    trainNumber = resultSet.getInt("train_number");
                    sourceId = resultSet.getInt("source");
                    destinationId = resultSet.getInt("destination");
                }
                if(totalFare==0.0 && dateOfJourney.equals(null) && isCancelled==false){
                    dateOfJourney = resultSet.getDate("date_of_journey");
                    totalFare = resultSet.getDouble("total_fare");
                    isCancelled = resultSet.getBoolean("is_cancelled");
                }
//                user
                if (Objects.isNull(user)){
                    user = getUserFromResultSet(resultSet);
                }
//                passenger
                User passenger = getPassengerFromResultSet(resultSet);
                passengers.add(passenger);
                if(Objects.isNull(train)){
                    Optional<Train> trainByTrainNumber = getTrainByTrainNumber(trainNumber);
                    if (trainByTrainNumber.isEmpty()) return Optional.empty();
                    train = trainByTrainNumber.get();
                    if(Objects.isNull(source) && Objects.isNull(destination)){
                        int finalSourceId = sourceId;
                        source = train.getRoute().stream().filter(s -> s.getId() == finalSourceId).findFirst().get();
                        int finalDestinationId = destinationId;
                        destination = train.getRoute().stream().filter(d -> d.getId() == finalDestinationId).findFirst().get();
                    }
                }
                Transaction transaction = new Transaction(train, source, destination, dateOfJourney.toLocalDate(), passengers, user, totalFare);
                return Optional.of(transaction);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<Transaction> updateTransaction(Transaction transaction) {
        return Optional.empty();
    }

    @Override
    public List<Transaction> getTransactions() {
        return List.of();
    }

    private boolean addPassengers(List<User> passengers) throws SQLException {
        boolean status = true;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.addPassenger)) {
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
                if (i == 0) status = false;
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

    private Optional<Transaction> createTransaction(Transaction transaction) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.addTransaction)) {
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
        try (PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.mapPassengerTransaction)) {
            preparedStatement.setInt(1, pnr);
            for (User passenger : passengers) {
                preparedStatement.setInt(2, passenger.getId());
                preparedStatement.addBatch();
            }
            int[] rowAffected = preparedStatement.executeBatch();
            for (int i : rowAffected) {
                if (i == 0) status = false;
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

    private boolean mapUserTransaction(int userId, int pnr) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.mapUserTransaction)) {
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

    private Optional<Train> getTrainByTrainNumber(Integer trainNumber) {
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

    private Coach getCoachFromResultSet(ResultSet resultSet) throws SQLException {
        int coachId = resultSet.getInt("coach_id");
        String travellingClass = resultSet.getString("travelling_class");
        String coachName = resultSet.getString("coach_name");
        int totalSeats = resultSet.getInt("total_seats");
//        int availableSeats = resultSet.getInt("available_seats");
        double fareFactor = resultSet.getDouble("fare_factor");
        return new Coach(coachId, TravellingClass.valueOf(travellingClass), coachName, totalSeats, fareFactor);
    }

    private Station getStationFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("station_id");
        String stationName = resultSet.getString("station_name");
        String shortName = resultSet.getString("short_name");
        int sequenceNum = resultSet.getInt("sequence_num");
        Station station = new Station(id, stationName, shortName);
        station.setSequence_num(sequenceNum);
        return station;
    }

    private DayOfWeek getRunningDayFromResultSet(ResultSet resultSet) throws SQLException {
        String runningDay = resultSet.getString("running_day");
        return DayOfWeek.valueOf(runningDay);
    }

    private User getUserFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("user_id");
        String name = resultSet.getString("user_name");
        String userUsername = resultSet.getString("user_username");
        int userAge = resultSet.getInt("user_age");
        String userGender = resultSet.getString("user_gender");
        String userEmail = resultSet.getString("user_email");
        String userPassword = resultSet.getString("user_password");
        String seatNumber = resultSet.getString("seat_number");
        String userUserType = resultSet.getString("user_user_type");
        String isLoggedIn = resultSet.getString("is_logged_in");

        return User.builder().id(id).name(name).userName(userUsername).age(userAge)
                .gender(Gender.valueOf(userGender)).email(userEmail).password(userPassword)
                .seatNumber(seatNumber).userType(UserType.valueOf(userUserType))
                .isLoggedIn(Boolean.valueOf(isLoggedIn)).build();
    }

    private User getPassengerFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("passenger_id");
        String passengerName = resultSet.getString("passenger_name");
        int passengerAge = resultSet.getInt("passenger_age");
        String passengerGender = resultSet.getString("passenger_gender");
        String seatNumber = resultSet.getString("passenger_seat_number");
        String passengerUserType = resultSet.getString("passenger_user_type");
        return User.builder().id(id).name(passengerName).age(passengerAge)
                .gender(Gender.valueOf(passengerGender)).seatNumber(seatNumber)
                .userType(UserType.valueOf(passengerUserType)).build();
    }
}
