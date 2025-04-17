package biz.dss.ticketbookingsystem.view;

import biz.dss.ticketbookingsystem.controller.UserController;
import biz.dss.ticketbookingsystem.enums.Gender;
import biz.dss.ticketbookingsystem.models.User;
import biz.dss.ticketbookingsystem.utils.Formatter;
import biz.dss.ticketbookingsystem.utils.Response;
import biz.dss.ticketbookingsystem.valueobjects.AuthenticatedUser;

import java.util.List;

import static biz.dss.ticketbookingsystem.enums.UserType.ADMIN;

public class AdminView {

    private final UserController userController;
    private final InputView inputView;
    private Response response;

    public AdminView(UserController userController, InputView inputView) {
        this.userController = userController;
        this.inputView = inputView;
    }

    public void registerAdmin(AuthenticatedUser authenticatedUser) {
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

    public void deleteAdmin(AuthenticatedUser authenticatedUser) {
        String userName = inputView.getName("Username: ");
        response = userController.deleteAdmin(authenticatedUser, userName);
        System.out.println(response.getMessage());
    }


    public void getUsers(AuthenticatedUser authenticatedUser) {
        Response usersResponse = userController.getUsers(authenticatedUser);
        if(Boolean.TRUE.equals(usersResponse.isSuccess())){
            Formatter.tableTemplate((List<User>)(usersResponse.getData()));
        }
    }


    public void displayAllAdmins(AuthenticatedUser authenticatedUser) {
        Response allAdminsResponse = userController.getAllAdmins(authenticatedUser);
        if(allAdminsResponse.isSuccess()){
            Formatter.tableTemplate((List<User>)(allAdminsResponse.getData()));
        }
    }
}
