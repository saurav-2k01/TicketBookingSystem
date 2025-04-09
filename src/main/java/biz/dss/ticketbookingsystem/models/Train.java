package biz.dss.ticketbookingsystem.models;

import biz.dss.ticketbookingsystem.intefaces.Formatable;
import lombok.*;

import java.time.DayOfWeek;
import java.util.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Train implements Formatable {
    private Integer trainNumber;
    private String trainName;
    private Station source;
    private Station destination;
    @Builder.Default
    private List<Coach> coachList = new ArrayList<>();
    @Builder.Default
    private final List<Station> route = new ArrayList<>();
    @Builder.Default
    private final Set<DayOfWeek> runningDays = new HashSet<>();

    @Override
    public List<String> fieldsToDisplay() {
        return List.of("Train Number", "Train Name", "Source", "Destination", "Running Days");
    }

    @Override
    public List<String> getFieldValues() throws NullPointerException {
        return List.of(trainNumber.toString(), trainName, Objects.isNull(source)?"N/A":source.getName(), Objects.isNull(destination)?"N/A":destination.getName(), runningDays.isEmpty()?"N/A":Arrays.toString(runningDays.toArray()));
    }

    @Override
    public String getDisplayableTitle() {
        return "Trains";
    }

    public void setRoute(List<Station> route) {
        this.route.clear();
        this.route.addAll(route);
    }

    public void setCoaches(List<Coach> coaches) {
        this.coachList.clear();
        this.coachList.addAll(coaches);
    }

    public void setRunningDays(List<DayOfWeek> runningDays) {
        this.runningDays.clear();
        this.runningDays.addAll(runningDays);
    }
}
