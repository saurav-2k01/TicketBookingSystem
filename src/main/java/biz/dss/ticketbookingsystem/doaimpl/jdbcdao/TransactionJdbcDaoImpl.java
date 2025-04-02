package biz.dss.ticketbookingsystem.doaimpl.jdbcdao;

import biz.dss.ticketbookingsystem.dao.TrainBookingDao;
import biz.dss.ticketbookingsystem.dao.TransactionDao;
import biz.dss.ticketbookingsystem.models.TrainBooking;
import biz.dss.ticketbookingsystem.models.Transaction;
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
        try (PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.addTransaction)) {
            preparedStatement.setInt(1, UtilClass.random.nextInt(1_00_000_000 ,1_000_000_000));
            preparedStatement.setDate(2, Date.valueOf(transaction.getDateOfJourney()));
            preparedStatement.setDouble(3, transaction.getTotalFare());
            preparedStatement.setBoolean(4, transaction.isCancelled());
            preparedStatement.setInt(5, transaction.getUser().getId());
            preparedStatement.setInt(6, transaction.getTrain().getTrainNumber());
            preparedStatement.setInt(7, transaction.getSource().getId());
            preparedStatement.setInt(8, transaction.getDestination().getId());
            int rowAffected = preparedStatement.executeUpdate();
            if(rowAffected>0){
                return Optional.of(transaction);
            }else{
                return Optional.empty();
            }
        }
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
}
