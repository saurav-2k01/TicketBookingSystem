package biz.dss.ticketbookingsystem.models;

import biz.dss.ticketbookingsystem.enums.Gender;
import biz.dss.ticketbookingsystem.enums.UserType;
import biz.dss.ticketbookingsystem.intefaces.Formatable;
import biz.dss.ticketbookingsystem.utils.UtilClass;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Formattable;
import java.util.List;

import static biz.dss.ticketbookingsystem.enums.UserType.ADMIN;
import static biz.dss.ticketbookingsystem.enums.UserType.REGISTERED_USER;

@Data
@Builder
public class User implements Formatable {
    private Integer id;
    private String name;
    private String userName;
    private Integer age;
    private Gender gender;
    private String email;
    private String password;
    private String seatNumber;
    private UserType userType;
    private Boolean isLoggedIn = false;
    private final List<Integer> pnrList = new ArrayList<>();


    @Override
    public List<String> fieldsToDisplay() {
        if(userType.equals(ADMIN) || userType.equals(REGISTERED_USER)){
            return List.of("Name", "Username", "Age", "Gender", "Email", "User Type");
        }else{
            return List.of("Name", "Age", "Gender", "Seat Number");
        }
    }

    @Override
    public List<String> getFieldValues() throws NullPointerException {
        if(userType.equals(ADMIN) || userType.equals(REGISTERED_USER)){
            return List.of(name, userName,String.valueOf(age), gender.toString(), email, userType.toString());
        }else{
            return List.of(name, String.valueOf(age), gender.toString(), seatNumber);
        }
    }

    @Override
    public String getDisplayableTitle() {
        if (userType.equals(ADMIN)){
            return "Admins";
        } else if (userType.equals(REGISTERED_USER)) {
            return "Registered Users";
        }else{
            return "Passengers";
        }
    }
}


