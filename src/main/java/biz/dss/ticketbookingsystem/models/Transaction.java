package biz.dss.ticketbookingsystem.models;

import biz.dss.ticketbookingsystem.intefaces.Formatable;
import biz.dss.ticketbookingsystem.utils.UtilClass;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class Transaction implements Formatable {
    private final Integer pnr = UtilClass.random.nextInt(1000, 100_000_000);
    private final Train train;
    private final Station source;
    private final Station destination;
    private final LocalDate dateOfJourney;
    private final List<User> passengers;
    private final User user;
    private final Double totalFare;
    @Setter
    private boolean isCancelled;

    public Transaction(Train train, Station source, Station destination, LocalDate dateOfJourney, List<User> passengers, User user, Double totalFare) {
        this.train = train;
        this.source = source;
        this.destination = destination;
        this.dateOfJourney = dateOfJourney;
        this.passengers = passengers;
        this.user = user;
        this.totalFare = totalFare;
    }


    @Override
    public List<String> fieldsToDisplay() {
        return List.of("PNR", "Train Number","Train Name", "Source", "Destination", "User", "Total Fare", "cancelled");
    }

    @Override
    public List<String> getFieldValues() throws NullPointerException{
        return List.of(String.valueOf(this.pnr), String.valueOf(train.getTrainNumber()),train.getTrainName() ,this.source.getName(), this.destination.getName(), this.user.getName(),String.valueOf(totalFare), String.valueOf(isCancelled));
    }

    @Override
    public String getDisplayableTitle() {
        return "Transactions";
    }
}
