package biz.dss.ticketbookingsystem.exceptions;

public class UserAlreadyExistException extends Exception{
    public UserAlreadyExistException(String message){
        super(message);
    }
}
