package biz.dss.ticketbookingsystem.models;

import biz.dss.ticketbookingsystem.intefaces.Formatable;

import java.time.DayOfWeek;
import java.util.*;

public class Train implements Formatable {
    private Integer id;
    private Integer trainNumber;
    private String trainName;
    private Station source;
    private Station destination;
    private List<Coach> coachList;
    private List<Station> route;
    private Set<DayOfWeek> runningDays;

    public Train(Integer trainNumber, String trainName) {
        this.id = (int) (Math.random() * 10000);
        this.trainNumber = trainNumber;
        this.trainName = trainName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(Integer trainNumber) {
        this.trainNumber = trainNumber;
    }

    public String getTrainName() {
        return trainName;
    }

    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }

    public Station getSource() {
        return source;
    }

    public void setSource(Station source) {
        this.source = source;
    }

    public Station getDestination() {
        return destination;
    }

    public void setDestination(Station destination) {
        this.destination = destination;
    }

    public List<Coach> getCoachList() {
        if (Objects.isNull(this.coachList)) {
            return this.coachList = new ArrayList<>();
        }
        return this.coachList;
    }

    public void setCoachList(List<Coach> coachList) {
        this.coachList = coachList;
    }

    public Set<DayOfWeek> getRunningDays() {
        if (Objects.isNull(this.runningDays)) return this.runningDays = new HashSet<>();
        return this.runningDays;
    }

    public void setRunningDays(Set<DayOfWeek> runningDays) {
        this.runningDays = runningDays;
    }

    @Override
    public String toString() {
        return String.format("%d %s", this.trainNumber, this.trainName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.trainNumber, this.trainName);
    }

    @Override
    public boolean equals(Object obj) {
        Train t = (Train) obj;
        return this.id == t.id;
    }

    public List<Station> getRoute() {
        if (Objects.isNull(this.route)) {
            return this.route = new ArrayList<>();
        }
        return this.route;
    }

    public void setRoute(List<Station> route) {
        this.route = route;
        this.source = route.getFirst();
        this.destination = route.getLast();
    }

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
