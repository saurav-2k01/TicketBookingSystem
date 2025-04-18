package biz.dss.ticketbookingsystem.utils;

import biz.dss.ticketbookingsystem.enums.TravellingClass;
import biz.dss.ticketbookingsystem.models.Coach;
import biz.dss.ticketbookingsystem.models.Station;
import biz.dss.ticketbookingsystem.models.Train;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PopulateData {

    public  static List<Train> loadTrainsData(){
        List<Station> stations = loadStations();
        List<Train> trains = new ArrayList<>();
        Train t = Train.builder().trainNumber(12312).trainName("Indian Rail").build();

        t.getRoute().add(stations.stream().filter(station -> station.getShortName().equals("MUM")).findFirst().get());
        t.getRoute().add(stations.stream().filter(station -> station.getShortName().equals("BAN")).findFirst().get());
        t.getRoute().add(stations.stream().filter(station -> station.getShortName().equals("CHE")).findFirst().get());
        t.getRoute().add(stations.stream().filter(station -> station.getShortName().equals("KOL")).findFirst().get());

        t.setRoute(t.getRoute());

        Coach A = new Coach(67817838, TravellingClass.FIRST_AC,"A", 80,  125.0);
        Coach B = new Coach(1314341,TravellingClass.FIRST_AC, "B",80,  125.0);
        Coach C = new Coach(6342423,TravellingClass.SECOND_AC, "C",80,  98.0);
        Coach D = new Coach(45234542,TravellingClass.SECOND_AC, "D", 80,  98.0);
        Coach E = new Coach(2346234,TravellingClass.THIRD_AC,"E", 80,  80.0);
        Coach F = new Coach(234252,TravellingClass.THIRD_AC, "F",80,  80.0);
        Coach SL1 = new Coach(2242362,TravellingClass.SLEEPER, "SL1",80,50.0);
        Coach SL2 = new Coach(634234,TravellingClass.SLEEPER, "SL2",80,  50.0);


        t.getCoachList().add(A);
        t.getCoachList().add(B);
        t.getCoachList().add(C);
        t.getCoachList().add(D);
        t.getCoachList().add(E);
        t.getCoachList().add(F);
        t.getCoachList().add(SL1);
        t.getCoachList().add(SL2);

        t.getRunningDays().add(DayOfWeek.MONDAY);
        t.getRunningDays().add(DayOfWeek.WEDNESDAY);

        trains.add(t);
        return trains;
    }

    public static List<Station> loadStations(){
        List<Station> stations = new ArrayList<>();
        Station Mumbai = new Station(1432,"Mumbai", "MUM");
        Station Delhi = new Station(545234,"Delhi", "DEL");
        Station Chennai = new Station(234252,"Chennai", "CHE");
        Station Kolkata = new Station(23452,"Kolkata", "KOL");
        Station Bangalore = new Station(5234,"Bangalore", "BAN");
        Station Hyderabad = new Station(63423,"Hyderabad", "HYD");
        Station Ahmedabad = new Station(345345,"Ahmedabad", "AMD");
        Station Pune = new Station(342346546,"Pune", "PNE");
        Station Lucknow = new Station(73234,"Lucknow", "LKO");
        Station Jaipur = new Station(2632234,"Jaipur", "JAI");
        Station Kochi = new Station(234634,"Kochi", "COK");
        Station Chandigarh = new Station(635234,"Chandigarh", "CHD");
        Station Indore = new Station(63452,"Indore", "IND");
        Station Patna = new Station(62568322,"Patna", "PAT");
        Station Bhopal = new Station(74563,"Bhopal", "BPL");
        Station Surat = new Station(9567856,"Surat", "SUR");
        Station Nagpur = new Station(23922,"Nagpur", "NGP");
        Station Visakhapatnam = new Station(783091,"Visakhapatnam", "VIZ");
        Station Goa = new Station(6729782,"Goa", "GOA");
        Station Vadodara = new Station(23424,"Vadodara", "VAD");

        stations.add(Mumbai);
        stations.add(Delhi);
        stations.add(Chennai);
        stations.add(Kolkata);
        stations.add(Bangalore);
        stations.add(Hyderabad);
        stations.add(Ahmedabad);
        stations.add(Pune);
        stations.add(Lucknow);
        stations.add(Jaipur);
        stations.add(Kochi);
        stations.add(Chandigarh);
        stations.add(Indore);
        stations.add(Patna);
        stations.add(Bhopal);
        stations.add(Surat);
        stations.add(Nagpur);
        stations.add(Goa);
        stations.add(Vadodara);
        stations.add(Visakhapatnam);
        return stations;
    }
}
