package biz.dss.ticketbookingsystem.models;

import biz.dss.ticketbookingsystem.intefaces.Formatable;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Data
public class Station  implements Formatable, Comparable {
    private Integer id;
    private String name;
    private String shortName;
    private Integer sequence_num;

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

    @Override
    public int compareTo(Object o) {
        if(Objects.isNull(o)) return 10000;
        Station other = (Station)(o);
        return sequence_num-other.sequence_num;
    }
}
