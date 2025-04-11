package biz.dss.ticketbookingsystem.dao;

import biz.dss.ticketbookingsystem.models.Station;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public interface StationDao {
     Optional<Station> addStation(Station station) throws SQLException;

     Optional<Station> deleteStation(Station station) throws SQLException;

     Optional<Station> getStationByShortName(String shortName) throws SQLException;

     Optional<Station> getStationByName(String name) throws SQLException;

     Optional<Station> getStationById(Integer id) throws SQLException;

     Optional<Station> updateStation(Station station) throws SQLException;

     List<Station> getStations() throws SQLException;

}
