package biz.dss.ticketbookingsystem.dao;

import biz.dss.ticketbookingsystem.models.Transaction;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public interface TransactionDao {
    Optional<Transaction> addTransaction(Transaction transaction) throws SQLException;

    Optional<Transaction> deleteTransaction(Transaction transaction);

    Optional<Transaction> getTransactionByPnr(Integer pnr);

    Optional<Transaction> updateTransaction(Transaction transaction);

    List<Transaction> getTransactions();
}
