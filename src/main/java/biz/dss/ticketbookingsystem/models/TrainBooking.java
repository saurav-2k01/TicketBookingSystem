package biz.dss.ticketbookingsystem.models;

import biz.dss.ticketbookingsystem.utils.UtilClass;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDate;

@Data
public class TrainBooking {
    private Integer id;
    private Integer trainNumber;
    private Coach coach;
    private Integer availableSeats;
    private LocalDate runningDate;

    public TrainBooking(Integer trainNumber, Coach coach, LocalDate runningDate) {
        this.trainNumber = trainNumber;
        this.coach = coach;
        this.availableSeats = coach.getTotalSeats();
        this.runningDate = runningDate;
    }
}
