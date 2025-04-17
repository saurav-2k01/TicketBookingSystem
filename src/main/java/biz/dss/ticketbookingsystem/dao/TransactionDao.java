package biz.dss.ticketbookingsystem.dao;

import biz.dss.ticketbookingsystem.models.Transaction;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface TransactionDao {
    Optional<Transaction> addTransaction(Transaction transaction) throws SQLException;

    Optional<Transaction> cancelTransaction(Transaction transaction) throws SQLException;

    Optional<Transaction> getTransactionByPnr(Integer pnr) throws SQLException;

    List<Transaction> getTransactionByUserId(Integer userId) throws SQLException;

    Optional<Transaction> updateTransaction(Transaction transaction);

    List<Transaction> getTransactions() throws SQLException;

    Optional<Integer> getTransactionsCountByTrainNumber(int trainNumber) throws SQLException;
}
