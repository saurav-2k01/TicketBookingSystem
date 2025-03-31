package biz.dss.ticketbookingsystem.models;

import biz.dss.ticketbookingsystem.intefaces.Formatable;
import lombok.Builder;
import lombok.Data;

import java.time.DayOfWeek;
import java.util.*;

@Data
@Builder
public class Train implements Formatable {
    private Integer trainNumber;
    private String trainName;
    private Station source;
    private Station destination;
    @Builder.Default
    private List<Coach> coachList = new ArrayList<>();
    private final List<Station> route = new ArrayList<>();
    private final Set<DayOfWeek> runningDays = new HashSet<>();

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

    public void setRoute(List<Station> route) {
    }
}
