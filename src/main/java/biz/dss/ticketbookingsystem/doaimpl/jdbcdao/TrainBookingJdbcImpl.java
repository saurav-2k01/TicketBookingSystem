package biz.dss.ticketbookingsystem.doaimpl.jdbcdao;

import biz.dss.ticketbookingsystem.dao.TrainBookingDao;
import biz.dss.ticketbookingsystem.enums.TravellingClass;
import biz.dss.ticketbookingsystem.models.Coach;
import biz.dss.ticketbookingsystem.models.Train;
import biz.dss.ticketbookingsystem.models.TrainBooking;
import biz.dss.ticketbookingsystem.utils.DbConnection;
import biz.dss.ticketbookingsystem.utils.SqlQueries;
import biz.dss.ticketbookingsystem.utils.UtilClass;

import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.Option;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TrainBookingJdbcImpl implements TrainBookingDao {
    Connection connection = DbConnection.getConnection();

    @Override
    public List<TrainBooking> getTrainBookings() throws SQLException{
        ResultSet resultSet;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.getAllTrainBooking)) {
            resultSet = preparedStatement.executeQuery();

            List<TrainBooking> trainBookings = new ArrayList<>();
            while (resultSet.next()) {
                int trainNumber = resultSet.getInt("train_number");
                int availableSeats = resultSet.getInt("available_seats");
                LocalDate runningDate = resultSet.getDate("running_date").toLocalDate();

                int coachId = resultSet.getInt("coach_id");
                String travellingClass = resultSet.getString("travelling_class");
                String coachName = resultSet.getString("coach_name");
                int totalSeats = resultSet.getInt("total_seats");
                double fareFactor = resultSet.getDouble("fare_factor");
                Coach coach = new Coach(TravellingClass.valueOf(travellingClass), coachName, totalSeats, fareFactor);
                coach.setId(coachId);

                TrainBooking trainBooking1 = new TrainBooking(trainNumber, coach, runningDate);
                trainBooking1.setAvailableSeats(availableSeats);
                trainBookings.add(trainBooking1);
            }
            return trainBookings;
        }
    }

    @Override
    public Optional<TrainBooking> addTrainBooking(TrainBooking trainBooking) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.addTrainBooking)) {
            preparedStatement.setInt(1, trainBooking.getTrainNumber());
            preparedStatement.setInt(2, trainBooking.getCoach().getId());
            preparedStatement.setInt(3, trainBooking.getAvailableSeats());
            preparedStatement.setDate(4, Date.valueOf(trainBooking.getRunningDate()));
            int rowAffected = preparedStatement.executeUpdate();
            if (rowAffected > 0) {
                return Optional.of(trainBooking);
            }
            return Optional.empty();
        }
    }

    public Optional<TrainBooking> updateTrainBooking(TrainBooking trainBooking) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.updateTrainBooking)) {
            preparedStatement.setInt(1, trainBooking.getAvailableSeats());
            preparedStatement.setInt(2, trainBooking.getTrainNumber());
            preparedStatement.setInt(3, trainBooking.getCoach().getId());
            preparedStatement.setDate(4, Date.valueOf(trainBooking.getRunningDate()));
            int rowAffected = preparedStatement.executeUpdate();
            if(rowAffected>0){
                return Optional.of(trainBooking);
            }
            return Optional.empty();
        }
    }

    public Optional<TrainBooking> getTrainBooking(TrainBooking trainBooking) throws SQLException{
        try(PreparedStatement preparedStatement =  connection.prepareStatement(SqlQueries.getTrainBooking)){
            preparedStatement.setInt(1, trainBooking.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                int id = resultSet.getInt("id");
                int trainNumber = resultSet.getInt("train_number");
                int availableSeats = resultSet.getInt("available_seats");
                LocalDate runningDate = resultSet.getDate("running_date").toLocalDate();

                int coachId = resultSet.getInt("coach_id");
                String travellingClass = resultSet.getString("travelling_class");
                String coachName = resultSet.getString("coach_name");
                int totalSeats = resultSet.getInt("total_seats");
                double fareFactor = resultSet.getDouble("fare_factor");
                Coach coach = new Coach( TravellingClass.valueOf(travellingClass), coachName, totalSeats, fareFactor);
                coach.setId(coachId);
                TrainBooking trainBooking1 = new TrainBooking(trainNumber, coach, runningDate);
                trainBooking1.setId(id);
                trainBooking1.setAvailableSeats(availableSeats);
                return Optional.of(trainBooking1);
            }
            return Optional.empty();
        }
    }
}
