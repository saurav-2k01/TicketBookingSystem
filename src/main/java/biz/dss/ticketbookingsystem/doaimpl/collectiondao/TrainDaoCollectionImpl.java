package biz.dss.ticketbookingsystem.doaimpl.collectiondao;

import biz.dss.ticketbookingsystem.dao.TrainDao;
import biz.dss.ticketbookingsystem.models.Train;
import biz.dss.ticketbookingsystem.utils.PopulateData;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class TrainDaoCollectionImpl implements TrainDao {
    private final List<Train> trains = PopulateData.loadTrainsData();

    public TrainDaoCollectionImpl(){
    }

    public Optional<Train> addTrain(Train train){
        if(Objects.isNull(train)){
            throw new NullPointerException();
        }
        boolean isAdded;

        if(trains.contains(train)){
            isAdded = false;
        }else{
            isAdded = trains.add(train);
        }
        return isAdded? Optional.of(train) : Optional.empty();
    }

    public Optional<Train> getTrainById(Integer id){
        if(Objects.isNull(id)){
            throw new NullPointerException();
        }
        return trains.stream().filter(train -> train.getId().equals(id)).findFirst();
    }

    public Optional<Train> getTrainByTrainNumber(Integer trainNumber){
        if(Objects.isNull(trainNumber)){
            throw new NullPointerException();
        }
        return trains.stream().filter(train -> train.getTrainNumber().equals(trainNumber)).findFirst();
    }

    public Optional<Train> getTrainByTrainName(String trainName){
        if(Objects.isNull(trainName)){
            throw new NullPointerException();
        }
        return trains.stream().filter(train -> train.getTrainName().equals(trainName)).findFirst();
    }

    public Optional<Train> updateTrain(Train train){
        if(Objects.isNull(train)){
            throw new NullPointerException();
        }
        Integer id = train.getId();
        Optional<Train> trainResult = trains.stream().filter(t -> t.getId().equals(id)).findFirst();
        if(trainResult.isPresent()){
            int index = trains.indexOf(trainResult.get());
            trains.set(index, train);
            return Optional.of(train);
        }else{
            return Optional.empty();
        }
    }

    public Optional<Train> deleteTrain(Train train){
        if(Objects.isNull(train)){
            throw new NullPointerException();
        }
        boolean isRemoved = trains.remove(train);
        return isRemoved? Optional.of(train): Optional.empty();
    }

    public List<Train> getTrains() {
        return trains;
    }
}