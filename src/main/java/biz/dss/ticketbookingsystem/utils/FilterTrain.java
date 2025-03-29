package biz.dss.ticketbookingsystem.utils;

import biz.dss.ticketbookingsystem.models.Station;
import biz.dss.ticketbookingsystem.models.Train;

import java.time.LocalDate;
import java.util.List;

public class FilterTrain {
    private List<Train> trains;

    public FilterTrain(List<Train> trains) {
        this.trains = trains;
    }

    public List<Train> filterTrain(Station source, Station destination) {
        return this.trains.stream().filter(train ->
                        train.getRoute().contains(source) && train.getRoute().contains(destination)
                        &&
                        train.getRoute().indexOf(source) < train.getRoute().indexOf(destination)
        ).toList();
    }

    public List<Train> filterTrain(Station source, Station destination, LocalDate date) {
        return this.trains.stream().filter(train ->
                        train.getRoute().contains(source) && train.getRoute().contains(destination)
                        &&
                        train.getRoute().indexOf(source) < train.getRoute().indexOf(destination)
                        &&
                        train.getRunningDays().contains(date.getDayOfWeek())
        ).toList();
    }


}
