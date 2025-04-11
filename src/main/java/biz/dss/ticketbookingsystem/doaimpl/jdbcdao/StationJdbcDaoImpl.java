package biz.dss.ticketbookingsystem.doaimpl.jdbcdao;

import biz.dss.ticketbookingsystem.dao.StationDao;
import biz.dss.ticketbookingsystem.models.Station;
import biz.dss.ticketbookingsystem.utils.DbConnection;
import biz.dss.ticketbookingsystem.utils.SqlQueries;

import javax.swing.text.html.Option;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StationJdbcDaoImpl implements StationDao {
    Connection connection = DbConnection.getConnection();

    @Override
    public Optional<Station> addStation(Station station) throws SQLException {
        int rowAffected;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.addStation)) {
            preparedStatement.setInt(1, station.getId());
            preparedStatement.setString(2, station.getName());
            preparedStatement.setString(3, station.getShortName());
            rowAffected = preparedStatement.executeUpdate();
            if (rowAffected > 0) {
                return Optional.of(station);
            }
            return Optional.empty();
        }
    }

    @Override
    public Optional<Station> deleteStation(Station station) throws SQLException {
        int rowAffected;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.deleteStatoinbyName)) {
            preparedStatement.setString(1, station.getName());
            rowAffected = preparedStatement.executeUpdate();

            if (rowAffected > 0) {
                return Optional.of(station);
            }
            return Optional.empty();
        }
    }

    @Override
    public Optional<Station> getStationByShortName(String shortName) throws SQLException {
        ResultSet resultSet;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.getStationByShortName)) {
            preparedStatement.setString(1, shortName);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String shortName_ = resultSet.getString("shortname");
                Station station = new Station(id, name, shortName_);
                return Optional.of(station);
            }
            return Optional.empty();
        }
    }

    @Override
    public Optional<Station> getStationByName(String name) throws SQLException {
        ResultSet resultSet;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.getStationByName)) {
            preparedStatement.setString(1, name);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name_ = resultSet.getString("name");
                String shortName = resultSet.getString("shortname");
                Station station = new Station(id, name_, shortName);
                return Optional.of(station);
            }
            return Optional.empty();
        }
    }

    @Override
    public Optional<Station> getStationById(Integer id) throws SQLException {
        ResultSet resultSet;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.getStationById)) {
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                int id_ = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String shortName = resultSet.getString("shortname");
                Station station = new Station(id_, name, shortName);
                return Optional.of(station);
            }
            return Optional.empty();
        }
    }

    @Override
    public Optional<Station> updateStation(Station station) throws SQLException {
        int affectedRows;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.updateStationById)) {
            preparedStatement.setInt(1, station.getId());
            preparedStatement.setString(2, station.getName());
            preparedStatement.setString(3, station.getShortName());
            preparedStatement.setInt(4, station.getId());
            affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                return Optional.of(station);
            }
            return Optional.empty();
        }
    }

    @Override
    public List<Station> getStations() throws SQLException {
        List<Station> stations = new ArrayList<>();

        ResultSet resultSet;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.getAllStations)) {
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String shortName = resultSet.getString("short_name");
                Station station = new Station(id, name, shortName);
                stations.add(station);
            }
            return stations;
        }
    }
}