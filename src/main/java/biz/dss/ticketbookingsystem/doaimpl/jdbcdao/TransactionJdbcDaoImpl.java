package biz.dss.ticketbookingsystem.doaimpl.jdbcdao;

import biz.dss.ticketbookingsystem.dao.TrainBookingDao;
import biz.dss.ticketbookingsystem.dao.TransactionDao;
import biz.dss.ticketbookingsystem.enums.UserType;
import biz.dss.ticketbookingsystem.models.TrainBooking;
import biz.dss.ticketbookingsystem.models.Transaction;
import biz.dss.ticketbookingsystem.models.User;
import biz.dss.ticketbookingsystem.utils.DbConnection;
import biz.dss.ticketbookingsystem.utils.SqlQueries;
import biz.dss.ticketbookingsystem.utils.UtilClass;

import javax.swing.text.html.Option;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class TransactionJdbcDaoImpl implements TransactionDao {
    Connection connection = DbConnection.getConnection();

    @Override
    public Optional<Transaction> addTransaction(Transaction transaction) throws SQLException {
        boolean isPassengersAdded = addPassengers(transaction.getPassengers());
        if(!isPassengersAdded) return Optional.empty();

        Optional<Transaction> transaction1 = createTransaction(transaction);
        if (transaction1.isEmpty()) return Optional.empty();

        boolean isPassengerTransactionMapped = mapPassengerTransaction(transaction1.get().getPnr(), transaction1.get().getPassengers());
        if (! isPassengerTransactionMapped) return Optional.empty();

        boolean isUserTransactionMapped = mapUserTransaction(transaction1.get().getUser().getId(), transaction1.get().getPnr());
        if(! isUserTransactionMapped) return Optional.empty();

        return transaction1;
    }

    @Override
    public Optional<Transaction> deleteTransaction(Transaction transaction) {
        return Optional.empty();
    }

    @Override
    public Optional<Transaction> getTransactionByPnr(Integer pnr) {
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
            if(rowAffected>0){
                return true;
            }else{
                connection.rollback();
                return false;
            }
        }


    }


}
