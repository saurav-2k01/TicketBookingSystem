package biz.dss.ticketbookingsystem.intefaces;

import java.util.List;

public interface Formatable { // convert to UI abstract
    int MAX_CHAR_LIMIT = 20;
    int EXTRA_CHAR = 5;

    List<String> fieldsToDisplay();

    List<String> getFieldValues() throws NullPointerException;

    String getDisplayableTitle();// @TODO //camelcase

}
