package biz.dss.ticketbookingsystem.view;

import biz.dss.ticketbookingsystem.controller.UserController;
import biz.dss.ticketbookingsystem.enums.Gender;
import biz.dss.ticketbookingsystem.enums.UserType;
import biz.dss.ticketbookingsystem.models.User;
import biz.dss.ticketbookingsystem.utils.Formatter;
import biz.dss.ticketbookingsystem.utils.Response;
import biz.dss.ticketbookingsystem.valueobjects.AuthenticatedUser;

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
        Integer age = inputView.getAge("Age: ", 100, 18);
        Gender gender = inputView.getGender("Gender: ");
        String userName;
        while(true){
            userName =  inputView.getName("Username: ");
            response = userController.userExists(userName);
            if(response.isSuccess()){
                boolean userExists = (boolean) (response.getData());
                if(userExists){
                    System.out.println(response.getMessage());
                    System.out.println("Try another username.");
                }else{
                    break;
                }
            }else{
                System.out.println(response.getMessage());
            }
        }
        String email = inputView.getEmail("Email: ");
        String password = inputView.getPassword("Password: ");
        User newUser = User.builder().name(name).age(age).gender(gender).userName(userName).email(email).password(password).userType(REGISTERED_USER).build();
        response = userController.registerUser(newUser);
        System.out.println(response.getMessage());
    }

    public void displayAllUsers(AuthenticatedUser authenticatedUser){
        response = userController.getUsers(authenticatedUser);
//        if(usersResponse instanceof List) {
//            List<User> users = (List<User>) usersResponse.getData();
//            Formatter.tableTemplate(users);
//        }
        if(response.isSuccess()){
            List<User> users = (List<User>) response.getData();
            Formatter.tableTemplate(users);
        }

    }


//    admins
    public boolean displayAllAdmins(AuthenticatedUser authenticatedUser){
        response = userController.getAllAdmins(authenticatedUser);
        if(response.isSuccess()){
            List<User> admins = (List<User>) response.getData();
            Formatter.tableTemplate(admins);
            return true;
        }
        return false;
    }

    public void registerAdmin(AuthenticatedUser authenticatedUser) {
        if(Boolean.FALSE.equals(authenticatedUser.getIsLoggedIn())){
            System.out.println("User must be logged in to use this feature.");
        }

        if(Boolean.FALSE.equals(authenticatedUser.getUserType().equals(ADMIN))){
            System.out.println("This feature is only for admins.");
        }

        String name = inputView.getName("Name: ");
        Integer age = inputView.getAge("Age: ", 100, 18);
        Gender gender = inputView.getGender("Gender: ");
        String userName;
        while(true){
            userName =  inputView.getName("Username: ");
            response = userController.userExists(userName);
            if(response.isSuccess()){
                boolean userExists = (boolean) (response.getData());
                if(userExists){
                    System.out.println(response.getMessage());
                    System.out.println("Try another username.");
                }else{
                    break;
                }
            }else{
                System.out.println(response.getMessage());
            }
        }
        String email = inputView.getEmail("Email: ");
        String password = inputView.getPassword("Password: ");
        User newUser = User.builder().name(name).age(age).gender(gender).userName(userName).email(email).password(password).userType(ADMIN).build();
        response = userController.registerUser(newUser);
        System.out.println(response.getMessage());
    }

    public void removeAdmin(AuthenticatedUser authenticatedUser){
        boolean displayed = displayAllAdmins(authenticatedUser);
        if(displayed){
            String userName = inputView.getStringInput("Enter username: ");
            response = userController.deleteAdmin(authenticatedUser, userName);
            System.out.println(response.getMessage());
        }
    }



}
