package biz.dss.ticketbookingsystem.dao;

import biz.dss.ticketbookingsystem.models.Station;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public interface StationDao {
     Optional<Station> addStation(Station station);

     Optional<Station> deleteStation(Station station);

     Optional<Station> getStationByShortName(String shortName);

     Optional<Station> getStationByName(String name);

     Optional<Station> getStationById(Integer id);

     Optional<Station> updateStation(Station station);

     List<Station> getStations();

}
