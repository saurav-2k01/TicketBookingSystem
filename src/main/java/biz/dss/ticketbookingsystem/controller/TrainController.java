package biz.dss.ticketbookingsystem.controller;

import biz.dss.ticketbookingsystem.models.Coach;
import biz.dss.ticketbookingsystem.models.Station;
import biz.dss.ticketbookingsystem.models.Train;
import biz.dss.ticketbookingsystem.service.TrainService;
import biz.dss.ticketbookingsystem.utils.Response;
import biz.dss.ticketbookingsystem.valueobjects.AuthenticatedUser;
import biz.dss.ticketbookingsystem.valueobjects.TrainSearchDetail;
import lombok.RequiredArgsConstructor;

import java.time.DayOfWeek;
import java.util.List;

@RequiredArgsConstructor
public class TrainController {

    /*Fields*/
    private final TrainService trainService;

    /*Methods*/
    public Response addTrain(AuthenticatedUser authenticatedUser, Train train) {
        return trainService.addTrain(authenticatedUser, train);
    }

    public Response removeTrain(AuthenticatedUser authenticatedUser, Integer trainNumber) {
        return trainService.removeTrain(authenticatedUser,trainNumber);
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

    public Response addCoach(AuthenticatedUser authenticatedUser, Coach coach) {
        return trainService.addCoach(authenticatedUser,coach);
    }

    public Response removeCoach(AuthenticatedUser authenticatedUser, Coach coach) {
        return trainService.removeCoach(authenticatedUser,coach);
    }

    public Response getCoach(Integer coachId) {
        return trainService.getTrain(coachId);
    }

    public Response addRoute(AuthenticatedUser authenticatedUser, List<Station> route) {
        return trainService.addRoute(authenticatedUser, route);
    }

    public Response removeRoute(AuthenticatedUser authenticatedUser, Train train) {
        return trainService.removeRoute(authenticatedUser, train);
    }

    public Response getRoute() {
        return trainService.getRoute();
    }

    public Response addRunningDay(AuthenticatedUser authenticatedUser, DayOfWeek day) {
        return trainService.addRunningDay(authenticatedUser, day);
    }

    public Response removeRunningDay(AuthenticatedUser authenticatedUser, DayOfWeek day) {
        return trainService.removeRunningDay(authenticatedUser, day);
    }

    public Response getRunningDays() {
        return trainService.getRunningDays();
    }

    public Response searchTrains(TrainSearchDetail trainSearchDetail) {
        return trainService.searchTrains(trainSearchDetail);
    }

}
