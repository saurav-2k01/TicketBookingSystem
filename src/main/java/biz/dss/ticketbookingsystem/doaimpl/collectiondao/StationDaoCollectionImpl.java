package biz.dss.ticketbookingsystem.doaimpl.collectiondao;

import biz.dss.ticketbookingsystem.dao.StationDao;
import biz.dss.ticketbookingsystem.models.Station;
import biz.dss.ticketbookingsystem.utils.PopulateData;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class StationDaoCollectionImpl implements StationDao {
    private final List<Station> stations  = PopulateData.loadStations();

    public StationDaoCollectionImpl(){
    }
    public Optional<Station> addStation(Station station){
        if(Objects.isNull(station)){
            throw new NullPointerException();
        }
        boolean isAdded;

        if(stations.contains(station)){
            isAdded = false;
        }else{
            isAdded = stations.add(station);
        }
        return isAdded? Optional.of(station) : Optional.empty();
    }

    public Optional<Station> deleteStation(Station station){
        if(Objects.isNull(station)){
            throw new NullPointerException();
        }
        boolean isRemoved = stations.remove(station);
        return isRemoved? Optional.of(station): Optional.empty();
    }

    public Optional<Station> getStationByShortName(String shortName){
        if(Objects.isNull(shortName)){
            throw new NullPointerException();
        }
        return stations.stream().filter(train -> train.getShortName().equals(shortName)).findFirst();
    }

    public Optional<Station> getStationByName(String name){
        if(Objects.isNull(name)){
            throw new NullPointerException();
        }
        return stations.stream().filter(train -> train.getName().equals(name)).findFirst();
    }

    public Optional<Station> getStationById(Integer id){
        if(Objects.isNull(id)){
            throw new NullPointerException();
        }
        return stations.stream().filter(train -> train.getId().equals(id)).findFirst();
    }

    public Optional<Station> updateStation(Station station){
        if(Objects.isNull(station)){
            throw new NullPointerException();
        }
        Integer id = station.getId();
        Optional<Station> trainResult = stations.stream().filter(t -> t.getId().equals(id)).findFirst();
        if(trainResult.isPresent()){
            int index = stations.indexOf(trainResult.get());
            stations.set(index, station);
            return Optional.of(station);
        }else{
            return Optional.empty();
        }
    }

    public List<Station> getStations() {
        return stations;
    }
}
