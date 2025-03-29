package biz.dss.ticketbookingsystem.view;

import biz.dss.ticketbookingsystem.controller.UserController;
import biz.dss.ticketbookingsystem.enums.Gender;
import biz.dss.ticketbookingsystem.models.User;
import biz.dss.ticketbookingsystem.utils.Formatter;
import biz.dss.ticketbookingsystem.utils.Response;

import java.util.List;

import static biz.dss.ticketbookingsystem.enums.UserType.ADMIN;
import static biz.dss.ticketbookingsystem.enums.UserType.REGISTERED_USER;


public class UserView{
    private final InputView inputView;
    private final UserController userController;
    private Response response;

    public UserView(InputView inputView, UserController userController){
        this.inputView = inputView;
        this.userController = userController;
    }
    
    public void registerUser() {
        String name = inputView.getName("Name: ");
        Integer age = inputView.getAge("Age: ", 18, 100);
        Gender gender = inputView.getGender("Gender: ");
        String userName = inputView.getName("Username: ");
        String email = inputView.getEmail("Email: ");
        String password = inputView.getPassword("Password: ");
        User newUser = User.builder().name(name).age(age).gender(gender).userName(userName).email(email).password(password).userType(REGISTERED_USER).build();
        response = userController.registerUser(newUser);
        System.out.println(response.getMessage());
    }

    public void registerAdmin() {
        String name = inputView.getName("Name: ");
        Integer age = inputView.getAge("Age: ", 18, 100);
        Gender gender = inputView.getGender("Gender: ");
        String userName = inputView.getName("Username: ");
        String email = inputView.getEmail("Email: ");
        String password = inputView.getPassword("Password: ");
        User newUser = User.builder().name(name).age(age).gender(gender).userName(userName).email(email).password(password).userType(ADMIN).build();
        response = userController.registerUser(newUser);
        System.out.println(response.getMessage());
    }


    public void deleteUser() {
        String userName = inputView.getName("Username: ");
        response = userController.deleteUser(userName);
        System.out.println(response.getMessage());
    }

    
    public void getUsers() {
        Response usersResponse = userController.getUsers();
        if(usersResponse.isSuccess()){
            Formatter.tableTemplate((List<User>)(usersResponse.getData()));
        }
    }

    
    public void displayAllAdmins() {
        Response allAdminsResponse = userController.getAllAdmins();
        if(allAdminsResponse.isSuccess()){
            Formatter.tableTemplate((List<User>)(allAdminsResponse.getData()));
        }
    }
}
