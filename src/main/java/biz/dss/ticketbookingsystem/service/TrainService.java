package biz.dss.ticketbookingsystem.service;

import biz.dss.ticketbookingsystem.models.Coach;
import biz.dss.ticketbookingsystem.models.Station;
import biz.dss.ticketbookingsystem.models.Train;
import biz.dss.ticketbookingsystem.utils.Response;
import biz.dss.ticketbookingsystem.valueobjects.AuthenticatedUser;
import biz.dss.ticketbookingsystem.valueobjects.TrainSearchDetail;

import java.time.DayOfWeek;
import java.util.List;


//todo complete trainserice class

public interface TrainService {

    Response addTrain(AuthenticatedUser authenticatedUser, Train train);

    Response removeTrain(AuthenticatedUser authenticatedUser, Integer trainNumber);

    Response getTrain(Integer trainNumber);

    Response setCurrentTrain(Train train);

    Response getCurrentTrain();

    Response getTrains();

    Response addCoach(AuthenticatedUser authenticatedUser, Coach coach);

    Response removeCoach(AuthenticatedUser authenticatedUser, Coach coach);

    Response getCoach(Integer coachId);

    Response addRoute(AuthenticatedUser authenticatedUser, List<Station> route);

    Response removeRoute(AuthenticatedUser authenticatedUser, Train train);

    Response getRoute();

    Response addRunningDay(AuthenticatedUser authenticatedUser, DayOfWeek day);

    Response removeRunningDay(AuthenticatedUser authenticatedUser, DayOfWeek day);

    Response getRunningDays();

    Response searchTrains(TrainSearchDetail trainSearchDetail);



}
