package biz.dss.ticketbookingsystem.service;

import biz.dss.ticketbookingsystem.models.Coach;
import biz.dss.ticketbookingsystem.models.Station;
import biz.dss.ticketbookingsystem.models.Train;
import biz.dss.ticketbookingsystem.utils.Response;
import biz.dss.ticketbookingsystem.valueobjects.TrainSearchDetail;

import java.time.DayOfWeek;
import java.util.List;


//todo complete trainserice class

public interface TrainService {

    Response addTrain(Train train);

    Response removeTrain(Integer trainNumber);

    Response getTrain(Integer trainNumber);

    Response setCurrentTrain(Train train);

    Response getCurrentTrain();

    Response getTrains();

    Response addCoach(Coach coach);

    Response removeCoach(Coach coach);

    Response getCoach(Integer coachId);

    Response addRoute(List<Station> route);

    Response removeRoute(Train train);

    Response getRoute();

    Response addRunningDay(DayOfWeek day);

    Response removeRunningDay(DayOfWeek day);

    Response getRunningDays();

    Response searchTrains(TrainSearchDetail trainSearchDetail);



}
