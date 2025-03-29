package biz.dss.ticketbookingsystem.controller;

import biz.dss.ticketbookingsystem.models.Coach;
import biz.dss.ticketbookingsystem.models.Station;
import biz.dss.ticketbookingsystem.models.Train;
import biz.dss.ticketbookingsystem.service.TrainService;
import biz.dss.ticketbookingsystem.utils.Response;
import biz.dss.ticketbookingsystem.valueobjects.TrainSearchDetail;
import lombok.RequiredArgsConstructor;

import java.time.DayOfWeek;
import java.util.List;

@RequiredArgsConstructor
public class TrainController {

    /*Fields*/
    private final TrainService trainService;

    /*Methods*/
    public Response addTrain(Train train) {
        return trainService.addTrain(train);
    }

    public Response removeTrain(Integer trainNumber) {
        return trainService.removeTrain(trainNumber);
    }

    public Response getTrain(Integer trainNumber) {
        return trainService.getTrain(trainNumber);
    }

    public Response setCurrentTrain(Train train) {
        return trainService.setCurrentTrain(train);
    }

    public Response getCurrentTrain() {
        return trainService.getCurrentTrain();
    }

    public Response getTrains() {
        return trainService.getTrains();
    }

    public Response addCoach(Coach coach) {
        return trainService.addCoach(coach);
    }

    public Response removeCoach(Coach coach) {
        return trainService.removeCoach(coach);
    }

    public Response getCoach(Integer coachId) {
        return trainService.getTrain(coachId);
    }

    public Response addRoute(List<Station> route) {
        return trainService.addRoute(route);
    }

    public Response removeRoute(Train train) {
        return trainService.removeRoute(train);
    }

    public Response getRoute() {
        return trainService.getRoute();
    }

    public Response addRunningDay(DayOfWeek day) {
        return trainService.addRunningDay(day);
    }

    public Response removeRunningDay(DayOfWeek day) {
        return trainService.removeRunningDay(day);
    }

    public Response getRunningDays() {
        return trainService.getRunningDays();
    }

    public Response searchTrains(TrainSearchDetail trainSearchDetail) {
        return trainService.searchTrains(trainSearchDetail);
    }

}
