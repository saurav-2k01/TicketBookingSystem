package biz.dss.ticketbookingsystem.utils;

//import enums.Station;

import biz.dss.ticketbookingsystem.models.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static biz.dss.ticketbookingsystem.enums.Gender.MALE;
import static biz.dss.ticketbookingsystem.enums.UserType.ADMIN;
import static biz.dss.ticketbookingsystem.enums.UserType.REGISTERED_USER;

public class PopulateSomeHardCodedData {
//    public static Map<Integer, TrainProtoType> populateTrain(List<List<Station>> routes){
//        Map<Integer, TrainProtoType> trains = new HashMap<>();
//        if (routes.isEmpty()){
//            System.out.println("Please populate routes before populating trains");
//            return null;
//        }
//        TrainProtoType train1 = TrainProtoType.trainProtoTypeFactoryMethod("Geetanjali Express", 1210);
//        TrainProtoType train2 = TrainProtoType.trainProtoTypeFactoryMethod("Geetanjali Express", 1211);
//        TrainProtoType train3 = TrainProtoType.trainProtoTypeFactoryMethod("Patanjali SuperExpress", 1212);
//        TrainProtoType train4 = TrainProtoType.trainProtoTypeFactoryMethod("Patanjali SuperExpress", 1213);
//        TrainProtoType train5= TrainProtoType.trainProtoTypeFactoryMethod("Indian SuperExpress", 1214);
//        TrainProtoType train6= TrainProtoType.trainProtoTypeFactoryMethod("Indian SuperExpress", 1215);
//        TrainProtoType train7 = TrainProtoType.trainProtoTypeFactoryMethod("Avadh Express", 1216);
//        TrainProtoType train8 = TrainProtoType.trainProtoTypeFactoryMethod("Avadh Express", 1217);
//        TrainProtoType train9 = TrainProtoType.trainProtoTypeFactoryMethod("Vande Bharat", 1218);
//        TrainProtoType train10 = TrainProtoType.trainProtoTypeFactoryMethod("Vande Bharat", 1219);
//        train1.setRoute(routes.get(0));
//        train2.setRoute(routes.get(0).reversed());
//        train3.setRoute(routes.get(1));
//        train4.setRoute(routes.get(1).reversed());
//        train5.setRoute(routes.get(2));
//        train6.setRoute(routes.get(2).reversed());
//        train7.setRoute(routes.get(3));
//        train8.setRoute(routes.get(3).reversed());
//        train9.setRoute(routes.get(4));
//        train10.setRoute(routes.get(4).reversed());
//        trains.put(train1.getTrainNumber(),train1);
//        trains.put(train2.getTrainNumber(),train2);
//        trains.put(train3.getTrainNumber(),train3);
//        trains.put(train4.getTrainNumber(),train4);
//        trains.put(train5.getTrainNumber(),train5);
//        trains.put(train6.getTrainNumber(),train6);
//        trains.put(train7.getTrainNumber(),train7);
//        trains.put(train8.getTrainNumber(),train8);
//        trains.put(train9.getTrainNumber(),train9);
//        trains.put(train10.getTrainNumber(),train10);
//
//        return trains;
//    }

    public static List<User> populateUsers(){
        List<User> users = new ArrayList<>();
        User saurav = User.builder().name("Saurav Sharma").age(23).gender(MALE).userName("saurav2k01").email("saurav@mail.com").password("Saurav@23").userType(REGISTERED_USER).build();
        User admin = User.builder().name("Saurav Sharma").age(23).gender(MALE).userName("admin").email("admin@mail.com").password("Admin@23").userType(ADMIN).build();
        users.add(saurav);
        users.add(admin);
        return users;
    }


//    public static List<List<Station>> populateRoutes(){
//        List<List<Station>> routes = new ArrayList<>();
//        List<Station> route1 = new ArrayList<>();
//        List<Station> route2 = new ArrayList<>();
//        List<Station> route3 = new ArrayList<>();
//        List<Station> route4 = new ArrayList<>();
//        List<Station> route5 = new ArrayList<>();
//
//        route1.add(Station.DELHI);
//        route1.add(Station.JAIPUR);
//        route1.add(Station.AHMEDABAD);
//        route1.add(Station.MUMBAI);
//        route1.add(Station.PUNE);
//
//        route2.add(Station.BANGALORE);
//        route2.add(Station.HYDERABAD);
//        route2.add(Station.CHENNAI);
//        route2.add(Station.KOCHI);
//        route2.add(Station.VARANASI);
//
//        route3.add(Station.KOLKATA);
//        route3.add(Station.PATNA);
//        route3.add(Station.RANCHI);
//        route3.add(Station.BHUBANESWAR);
//        route3.add(Station.KANPUR);
//
//        route4.add(Station.LUCKNOW);
//        route4.add(Station.KANPUR);
//        route4.add(Station.INDORE);
//        route4.add(Station.SURAT);
//        route4.add(Station.AHMEDABAD);
//
//        route5.add(Station.MUMBAI);
//        route5.add(Station.PUNE);
//        route5.add(Station.BANGALORE);
//        route5.add(Station.CHENNAI);
//        route5.add(Station.HYDERABAD);
//
//        routes.add(route1);
//        routes.add(route2);
//        routes.add(route3);
//        routes.add(route4);
//        routes.add(route5);
//
//        return routes;
//    }
}
