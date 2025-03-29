package biz.dss.ticketbookingsystem.models;

import biz.dss.ticketbookingsystem.intefaces.Formatable;

import java.util.List;
import java.util.Objects;

public class Station implements Formatable {
    private Integer id;
    private String name;
    private String shortName;
//    private int k = 100_000;

    public Station(String name, String shortName) {
//        new Random().nextInt(10_000);
        this.id = (int)(Math.random()*10000) ;
        this.name = name;
        this.shortName = shortName;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    @Override
    public String toString() {
        return this.name.toUpperCase()+" "+this.shortName.toUpperCase();
    }

    @Override
    public int hashCode() {
        return (Objects.hash(this.name));
    }

    @Override
    public boolean equals(Object obj) {
        if (Objects.isNull(obj)) {
            return false;
        }

        if (! obj.getClass().equals(this.getClass())){
            return false;
        }

        Station other = (Station) (obj);
        return this.name.equals(other.name);
    }


    @Override
    public List<String> fieldsToDisplay() {
        return List.of("Name", "ShortName");
    }

    @Override
    public List<String> getFieldValues() throws NullPointerException{
        return List.of(name, shortName);
    }

    @Override
    public String getDisplayableTitle() {
        return "Stations";
    }
}
