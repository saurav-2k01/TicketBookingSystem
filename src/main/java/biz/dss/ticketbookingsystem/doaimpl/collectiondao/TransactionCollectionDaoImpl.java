package biz.dss.ticketbookingsystem.doaimpl.collectiondao;

import biz.dss.ticketbookingsystem.dao.TransactionDao;
import biz.dss.ticketbookingsystem.models.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class TransactionCollectionDaoImpl implements TransactionDao {
    private final List<Transaction> transactions = new ArrayList<>();

    public TransactionCollectionDaoImpl(){
    }

    public Optional<Transaction> addTransaction(Transaction transaction){
        if(Objects.isNull(transaction)){
            throw new NullPointerException();
        }
        boolean isAdded;

        if(transactions.contains(transaction)){
            isAdded = false;
        }else{
            isAdded = transactions.add(transaction);
        }
        return isAdded? Optional.of(transaction) : Optional.empty();
    }

    public Optional<Transaction> cancelTransaction(Transaction transaction){
        if(Objects.isNull(transaction)){
            throw new NullPointerException();
        }
        boolean isRemoved = transactions.remove(transaction);
        return isRemoved? Optional.of(transaction): Optional.empty();
    }

    public Optional<Transaction> getTransactionByPnr(Integer pnr){
        if(Objects.isNull(pnr)){
            throw new NullPointerException();
        }
        return transactions.stream().filter(train -> train.getPnr().equals(pnr)).findFirst();
    }

    @Override
    public List<Transaction> getTransactionByUserId(Integer userId){
        return List.of();
    }

    public Optional<Transaction> updateTransaction(Transaction transaction){
        if(Objects.isNull(transaction)){
            throw new NullPointerException();
        }
        Integer pnr = transaction.getPnr();
        Optional<Transaction> trainResult = transactions.stream().filter(t -> t.getPnr().equals(pnr)).findFirst();
        if(trainResult.isPresent()){
            int index = transactions.indexOf(trainResult.get());
            transactions.set(index, transaction);
            return Optional.of(transaction);
        }else{
            return Optional.empty();
        }
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }
}
