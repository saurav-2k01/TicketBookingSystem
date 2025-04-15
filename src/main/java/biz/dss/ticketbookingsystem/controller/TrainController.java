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
    private final TrainService trainService;

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

    public Response addCoach(AuthenticatedUser authenticatedUser, List<Coach> coaches) {
        return trainService.addCoach(authenticatedUser,coaches);
    }

    public Response removeCoach(AuthenticatedUser authenticatedUser, Coach coach) {
        return trainService.removeCoach(authenticatedUser,coach);
    }

    public Response getCoach(Integer coachId) {
        return trainService.getCoach(coachId);
    }

    public Response addRoute(AuthenticatedUser authenticatedUser, List<Station> route) {
        return trainService.addRoute(authenticatedUser, route);
    }

    public Response removeRoute(AuthenticatedUser authenticatedUser, List<Station> route) {
        return trainService.removeRoute(authenticatedUser,route);
    }

    public Response getRoute() {
        return trainService.getRoute();
    }

    public Response addRunningDay(AuthenticatedUser authenticatedUser, List<DayOfWeek> days) {
        return trainService.addRunningDays(authenticatedUser, days);
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
