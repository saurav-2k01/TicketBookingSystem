package biz.dss.ticketbookingsystem.models;

import biz.dss.ticketbookingsystem.intefaces.Formatable;
import lombok.Builder;
import lombok.Data;

import java.time.DayOfWeek;
import java.util.*;

@Data
@Builder
public class Train implements Formatable {
    private Integer id;
    private Integer trainNumber;
    private String trainName;
    private Station source;
    private Station destination;
    private List<Coach> coachList;
    private List<Station> route;
    private Set<DayOfWeek> runningDays;

    @Override
    public List<String> fieldsToDisplay() {
        return List.of("Train Number", "Train Name", "Source", "Destination", "Running Days");
    }

    @Override
    public List<String> getFieldValues() throws NullPointerException {
        return List.of(trainNumber.toString(), trainName, source.getName(), destination.getName(), Arrays.toString(runningDays.toArray()));

    }

    @Override
    public String getDisplayableTitle() {
        return "Trains";
    }
}
