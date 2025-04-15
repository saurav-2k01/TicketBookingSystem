package biz.dss.ticketbookingsystem.models;

import biz.dss.ticketbookingsystem.intefaces.Formatable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Data
@EqualsAndHashCode(exclude = "sequence_num")
public class Station  implements Formatable, Comparable<Station> {
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
    public int compareTo(Station o) {
        if(Objects.isNull(sequence_num)||Objects.isNull(o)) return 10000;
        return sequence_num-o.sequence_num;
    }

//   todo come back here solve compare to method's station o parameter problem
}
