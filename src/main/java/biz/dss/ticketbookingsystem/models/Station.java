package biz.dss.ticketbookingsystem.models;

import biz.dss.ticketbookingsystem.intefaces.Formatable;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Data
public class Station implements Formatable {
    private Integer id;
    private String name;
    private String shortName;

    public Station(Integer id, String name, String shortName) {
        this.id = id;
        this.name = name;
        this.shortName = shortName;
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
