package biz.dss.ticketbookingsystem.serviceimpl;

import biz.dss.ticketbookingsystem.dao.StationDao;
import biz.dss.ticketbookingsystem.models.Station;
import biz.dss.ticketbookingsystem.service.StationService;
import biz.dss.ticketbookingsystem.utils.Response;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static biz.dss.ticketbookingsystem.utils.ResponseStatus.FAILURE;
import static biz.dss.ticketbookingsystem.utils.ResponseStatus.SUCCESS;

public class StationServiceImpl implements StationService{


    private Response response;
    private final StationDao stationDao;

    public StationServiceImpl(StationDao stationDao) {
        this.stationDao = stationDao;
    }

    @Override
    public Response addStation(Station station) {

        if(Objects.isNull(station)){
            response = new Response(FAILURE, "Station cannot be null.");
        }else{
            Optional<Station> addedStation = stationDao.addStation(station);
            if(addedStation.isPresent()){
                response = new Response(station, SUCCESS, "Successfully added station.");
            }else{
                response = new Response(FAILURE, String.format("Unable to add station named '%s'", station.getName()));
            }
        }
        return response;
    }

    @Override
    public Response removeStation(Integer id) {
        Optional<Station> station = stationDao.getStationById(id);
        if(station.isPresent()){
            Optional<Station> removedStation = stationDao.deleteStation(station.get());
            removedStation.ifPresent(value -> response = new Response(value, SUCCESS, String.format("Successfully removed station named '%s'", value.getName())));
        }else{
            response = new Response(FAILURE, "Unable to remove station.");
        }
        return response;
    }

    @Override
    public Response updateStation(Station station) {

//        Station updatedStation = stations.put(station.getShortName(), station);
        Optional<Station> updatedStation = stationDao.updateStation(station);
        if (updatedStation.isPresent()) {
            response = new Response(FAILURE, "Updating Station was failed.");
        } else {
            response = new Response(updatedStation, SUCCESS, "Successfully updated the station.");
        }
        return response;
    }

    @Override
    public Response getStation(Integer id) {
        Optional<Station> station = stationDao.getStationById(id);
        if (station.isPresent()) {
            response = new Response(FAILURE, "Station not found.");
        } else {
            response = new Response(station, SUCCESS, "Station found.");
        }
        return response;
    }

    @Override
    public Response getStations() {
        List<Station> stations = stationDao.getStations();
        if (Objects.isNull(stations)) {
            response = new Response(FAILURE, "Stations not found.");
        } else {
            response = new Response(stations, SUCCESS, "Stations found.");
        }
        return response;
    }

    @Override
    public Response getStationByName(String name) {
        if (Objects.isNull(name) || name.isBlank()) {
            response = new Response(FAILURE, "your input was empty.");
            return response;
        }
        List<Station> stations = stationDao.getStations();

        Optional<Station> stationValue = stations.stream().filter(s ->
                        s.getName().equalsIgnoreCase(name) ||
                                (s.getName().toLowerCase().startsWith(name.toLowerCase())&&
                        s.getName().toLowerCase().contains(name.toLowerCase())) ||
                        s.getShortName().equalsIgnoreCase(name)
        ).findFirst();

        response = stationValue.map(value -> new Response(value, SUCCESS, String.format("%s found for input %s", value.getName(), name)))
                .orElseGet(() -> new Response(FAILURE, String.format("Failed to find station for the given input '%s'", name)));
        return response;
    }



}
