package biz.dss.ticketbookingsystem.serviceimpl;

import biz.dss.ticketbookingsystem.dao.TrainDao;
import biz.dss.ticketbookingsystem.models.Coach;
import biz.dss.ticketbookingsystem.models.Station;
import biz.dss.ticketbookingsystem.models.Train;
import biz.dss.ticketbookingsystem.models.User;
import biz.dss.ticketbookingsystem.service.AuthenticationService;
import biz.dss.ticketbookingsystem.service.TrainService;
import biz.dss.ticketbookingsystem.utils.FilterTrain;
import biz.dss.ticketbookingsystem.utils.Response;
import biz.dss.ticketbookingsystem.valueobjects.AuthenticatedUser;
import biz.dss.ticketbookingsystem.valueobjects.TrainSearchDetail;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;
import java.time.DayOfWeek;
import java.util.*;

import static biz.dss.ticketbookingsystem.enums.UserType.ADMIN;
import static biz.dss.ticketbookingsystem.utils.ResponseStatus.FAILURE;
import static biz.dss.ticketbookingsystem.utils.ResponseStatus.SUCCESS;

@Slf4j
public class TrainServiceImpl implements TrainService {
    private final TrainDao trainDao;
    private final AuthenticationService authenticationService;
    private Train currentTrain;
    private FilterTrain filterTrain;
    private Response response;

    public TrainServiceImpl(AuthenticationService authenticationService, TrainDao trainDao) {
        this.authenticationService = authenticationService;
        this.trainDao = trainDao;
        try {
            this.filterTrain = new FilterTrain(trainDao.getTrains());
        } catch (SQLException e) {
            log.error("Error occurred while getting trains", e);
        }
    }

    @Override
    public Response addTrain(AuthenticatedUser authenticatedUser, Train train) {
        response = authenticationService.getAuthenticatedUser(authenticatedUser);
        if (Boolean.FALSE.equals(response.isSuccess())) return response;
        User user = (User) (response.getData());
        if (Boolean.FALSE.equals(user.getIsLoggedIn()) && Boolean.FALSE.equals(user.getUserType().equals(ADMIN))) {
            return response = new Response(FAILURE, "only admins can use this feature.");
        }
        try {
            trainDao.addTrain(train);
        } catch (SQLException e) {
            log.error("Error occurred while adding train.", e);
        }
        response = new Response(train, SUCCESS, "train added successfully");
        return response;
    }

    @Override
    public Response removeTrain(AuthenticatedUser authenticatedUser, Integer trainNumber) {
        response = authenticationService.getAuthenticatedUser(authenticatedUser);
        if (Boolean.FALSE.equals(response.isSuccess())) return response;
        User user = (User) (response.getData());
        if (Boolean.FALSE.equals(user.getIsLoggedIn()) && Boolean.FALSE.equals(user.getUserType().equals(ADMIN))) {
            return response = new Response(FAILURE, "only admins can use this feature.");
        }
        Optional<Train> train = Optional.empty();
        try {
            train = trainDao.getTrainByTrainNumber(trainNumber);
        } catch (SQLException e) {
            log.error("Error occurred while getting train", e);
        }
        if (train.isPresent()) {
            Optional<Train> removedTrain = Optional.empty();
            try {
                removedTrain = trainDao.deleteTrain(train.get());
            } catch (SQLException e) {
                log.error("Error occurred while deleting train.", e);
            }
            if (removedTrain.isPresent()) {
                response = new Response(removedTrain, SUCCESS, "Successfully removed the train.");
            }
        } else {
            response = new Response(FAILURE, "Unable to remove train.");
        }
        return response;
    }

    @Override
    public Response addCoach(AuthenticatedUser authenticatedUser, List<Coach> coaches) {
        response = authenticationService.getAuthenticatedUser(authenticatedUser);
        if (Boolean.FALSE.equals(response.isSuccess())) return response;
        User user = (User) (response.getData());
        if (Boolean.FALSE.equals(user.getIsLoggedIn()) && Boolean.FALSE.equals(user.getUserType().equals(ADMIN))) {
            return response = new Response(FAILURE, "only admins can use this feature.");
        }

        currentTrain.setCoaches(coaches);
        try {
            trainDao.updateTrain(currentTrain);
            trainDao.getTrainByTrainNumber(currentTrain.getTrainNumber()).ifPresent(this::setCurrentTrain);
            response = new Response(coaches, SUCCESS, "Coach added successfully.");
        } catch (SQLException e) {
            log.error("Error occurred while updating train.", e);
            response = new Response(FAILURE, "Unable to add coach");
        }
        return response;
    }

    @Override
    public Response removeCoach(AuthenticatedUser authenticatedUser, Coach coach) {
        response = authenticationService.getAuthenticatedUser(authenticatedUser);
        if (Boolean.FALSE.equals(response.isSuccess())) return response;
        User user = (User) (response.getData());
        if (Boolean.FALSE.equals(user.getIsLoggedIn()) && Boolean.FALSE.equals(user.getUserType().equals(ADMIN))) {
            return response = new Response(FAILURE, "only admins can use this feature.");
        }
        if(Boolean.FALSE.equals(currentTrain.getCoachList().contains(coach))){
            return  new Response(FAILURE, String.format("Train with train number %d does not have %s.",currentTrain.getTrainNumber(), coach.getCoachName()));
        }
        currentTrain.getCoachList().remove(coach);
        try {
            trainDao.removeCoach(currentTrain, List.of(coach));
            trainDao.getTrainByTrainNumber(currentTrain.getTrainNumber()).ifPresent(this::setCurrentTrain);
            response = new Response(coach, SUCCESS, "Coach removed successfully.");
        } catch (SQLException e) {
            log.error("Error occurred while removing coach.", e);
            response = new Response(FAILURE, "Unable to removed coach");
        }
        return response;
    }

    @Override
    public Response getCoach(Integer coachId) {
        Coach coach = currentTrain.getCoachList().stream().filter(c -> Objects.equals(c.getId(), coachId)).toList().getFirst();
        if (Objects.isNull(coach)) {
            response = new Response(FAILURE, "No coach found with specified id.");
        } else {
            response = new Response(coach, SUCCESS, "Coach found.");
        }
        return response;
    }

    @Override
    public Response getTrain(Integer trainNumber) {
        Optional<Train> train = Optional.empty();
        try {
            train = trainDao.getTrainByTrainNumber(trainNumber);
        } catch (SQLException e) {
            log.error("Error occurred while getting train.", e);
        }
        response = train.map(value -> new Response(value, SUCCESS, "Train found."))
                .orElseGet(() -> new Response(FAILURE, String.format("No train found with train number %d.", trainNumber)));
        return response;
    }

    @Override
    public Response getTrains() {
        List<Train> trains = null;
        try {
            trains = trainDao.getTrains();
        } catch (SQLException e) {
            log.error("Error occurred while getting train.", e);
        }
        if (Objects.isNull(trains)) {
            response = new Response(FAILURE, "No trains found.");
        } else {
            response = new Response(trains, SUCCESS, String.format("%d trains found.", trains.size()));
        }
        return response;
    }

    @Override
    public Response addRoute(AuthenticatedUser authenticatedUser, List<Station> route) {
        response = authenticationService.getAuthenticatedUser(authenticatedUser);
        if (Boolean.FALSE.equals(response.isSuccess())) return response;
        User user = (User) (response.getData());
        if (Boolean.FALSE.equals(user.getIsLoggedIn()) && Boolean.FALSE.equals(user.getUserType().equals(ADMIN))) {
            return response = new Response(FAILURE, "only admins can use this feature.");
        }
        currentTrain.setRoute(route);
        try {
            trainDao.updateTrain(currentTrain);
            trainDao.getTrainByTrainNumber(currentTrain.getTrainNumber()).ifPresent(this::setCurrentTrain);
            response = new Response(SUCCESS, "Added route.");
        } catch (SQLException e) {
            log.error("Error occurred while updating train.", e);
            response = new Response(SUCCESS, "Added route.");
        }

        return response;
    }

    @Override
    public Response removeRoute(AuthenticatedUser authenticatedUser, List<Station> route) {
        response = authenticationService.getAuthenticatedUser(authenticatedUser);
        if (Boolean.FALSE.equals(response.isSuccess())) return response;
        User user = (User) (response.getData());
        if (Boolean.FALSE.equals(user.getIsLoggedIn()) && Boolean.FALSE.equals(user.getUserType().equals(ADMIN))) {
            return response = new Response(FAILURE, "only admins can use this feature.");
        }
        if(Boolean.FALSE.equals(new HashSet<>(currentTrain.getRoute()).containsAll(route))){
            return  new Response(FAILURE, String.format("Train with train number %d does not run on this route.",currentTrain.getTrainNumber()));
        }
        currentTrain.getRoute().removeAll(route);
        try {
            trainDao.removeRoute(currentTrain, route);
            trainDao.getTrainByTrainNumber(currentTrain.getTrainNumber()).ifPresent(this::setCurrentTrain);
            response = new Response(SUCCESS, "Removed route from the train.");
        } catch (SQLException e) {
            log.error("Error occurred while removing route.", e);
            response = new Response(FAILURE, "unable to remove.");
        }
        return response;
    }

    @Override
    public Response setCurrentTrain(Train train) {
        if (Objects.isNull(train)) {
            return response = new Response(FAILURE, "train cannot be null.");
        }
        currentTrain = train;
        response = new Response(SUCCESS, "current train has been set.");
        return response;
    }

    @Override
    public Response getCurrentTrain() {
        if (Objects.isNull(currentTrain)) {
            response = new Response(FAILURE, "There is no current train.");
        } else {
            response = new Response(currentTrain, SUCCESS, "current train.");
        }
        return response;


    }

    @Override
    public Response getRoute() {
        List<Station> route = currentTrain.getRoute();
        if (Objects.isNull(route)) {
            response = new Response(FAILURE, "No route found.");
        } else {
            response = new Response(route, SUCCESS, "route found.");
        }
        return response;
    }


    @Override
    public Response addRunningDays(AuthenticatedUser authenticatedUser, List<DayOfWeek> days) {
        response = authenticationService.getAuthenticatedUser(authenticatedUser);
        if (Boolean.FALSE.equals(response.isSuccess())) return response;
        User user = (User) (response.getData());
        if (Boolean.FALSE.equals(user.getIsLoggedIn()) && Boolean.FALSE.equals(user.getUserType().equals(ADMIN))) {
            return response = new Response(FAILURE, "only admins can use this feature.");
        }
        currentTrain.setRunningDays(days);
        Optional<Train> train = Optional.empty();
        try {
            train = trainDao.updateTrain(currentTrain);
            trainDao.getTrainByTrainNumber(currentTrain.getTrainNumber()).ifPresent(this::setCurrentTrain);
        } catch (SQLException e) {
            log.error("Error occurred while updating train.", e);
        }
        if (train.isEmpty()) {
            response = new Response(FAILURE, "Unable to add running day");
        } else {
            response = new Response(days, SUCCESS, "Added a running day.");
        }
        return response;
    }

    @Override
    public Response removeRunningDay(AuthenticatedUser authenticatedUser, DayOfWeek day) {
        response = authenticationService.getAuthenticatedUser(authenticatedUser);
        if (Boolean.FALSE.equals(response.isSuccess())) return response;
        User user = (User) (response.getData());
        if (Boolean.FALSE.equals(user.getIsLoggedIn()) && Boolean.FALSE.equals(user.getUserType().equals(ADMIN))) {
            return response = new Response(FAILURE, "only admins can use this feature.");
        }
        if(Boolean.FALSE.equals(currentTrain.getRunningDays().contains(day))){
            return  new Response(FAILURE, String.format("Train with train number %d does not run on the %s.",currentTrain.getTrainNumber(), day));
        }
        currentTrain.getRunningDays().remove(day);
        try {
            trainDao.removeRunningDay(currentTrain, List.of(day));
            trainDao.getTrainByTrainNumber(currentTrain.getTrainNumber()).ifPresent(this::setCurrentTrain);
            response = new Response(day, SUCCESS, "Removed running day successfully.");
        } catch (SQLException e) {
            log.error("Error occurred while removing running day.", e);
            response = new Response(FAILURE, "unable to remove running day.");
        }
        return response;
    }

    @Override
    public Response getRunningDays() {
        Set<DayOfWeek> runningDays = currentTrain.getRunningDays();
        if (Objects.isNull(runningDays)) {
            response = new Response(FAILURE, "Running days not found.");
        } else {
            response = new Response(new ArrayList<>(runningDays), SUCCESS, "Removed running day successfully.");
        }
        return response;
    }


    @Override
    public Response searchTrains(TrainSearchDetail trainSearchDetail) {
        if (Objects.isNull(trainSearchDetail.getSource()) || Objects.isNull(trainSearchDetail.getDestination())) {
            new Response(FAILURE, "Invalid Station");
        }

        List<Train> filteredTrains;
        if (Objects.isNull(trainSearchDetail.getDate())) {
            filteredTrains = filterTrain.filterTrain(trainSearchDetail.getSource(), trainSearchDetail.getDestination());
        } else {
            filteredTrains = filterTrain.filterTrain(trainSearchDetail.getSource(), trainSearchDetail.getDestination(), trainSearchDetail.getDate());
        }

        if (Objects.isNull(filteredTrains) || filteredTrains.isEmpty()) {
            response = new Response(FAILURE, "No trains found.");
        } else {
            response = new Response(filteredTrains, SUCCESS, String.format("%d trains found.", filteredTrains.size()));
        }
        return response;
    }


}
