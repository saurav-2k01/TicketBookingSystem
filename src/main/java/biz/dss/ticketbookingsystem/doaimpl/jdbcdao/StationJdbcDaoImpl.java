package biz.dss.ticketbookingsystem.doaimpl.jdbcdao;

import biz.dss.ticketbookingsystem.dao.StationDao;
import biz.dss.ticketbookingsystem.models.Station;

import java.util.List;
import java.util.Optional;

public class StationJdbcDaoImpl implements StationDao {
    @Override
    public Optional<Station> addStation(Station station) {
        return Optional.empty();
    }

    @Override
    public Optional<Station> deleteStation(Station station) {
        return Optional.empty();
    }

    @Override
    public Optional<Station> getStationByShortName(String shortName) {
        return Optional.empty();
    }

    @Override
    public Optional<Station> getStationByName(String name) {
        return Optional.empty();
    }

    @Override
    public Optional<Station> getStationById(Integer id) {
        return Optional.empty();
    }

    @Override
    public Optional<Station> updateStation(Station station) {
        return Optional.empty();
    }

    @Override
    public List<Station> getStations() {
        return List.of();
    }
}
