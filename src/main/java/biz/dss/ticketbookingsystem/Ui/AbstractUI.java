package biz.dss.ticketbookingsystem.Ui;

import biz.dss.ticketbookingsystem.controller.AuthenticationController;
import biz.dss.ticketbookingsystem.utils.Response;
import biz.dss.ticketbookingsystem.valueobjects.AuthenticatedUser;
import biz.dss.ticketbookingsystem.view.InputView;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
abstract public class AbstractUI {
    protected final AuthenticationController authenticationController;
    protected final InputView inputView;
    protected AuthenticatedUser authenticatedUser;
    private Response response;

    public void login(){
        String userName = inputView.getStringInput("Username: ");
        String password = inputView.getStringInput("Password: ");
        response = authenticationController.authenticateUser(userName, password);
        if(Boolean.TRUE.equals(response.isSuccess())){
            authenticatedUser = (AuthenticatedUser) (response.getData());
            switch (authenticatedUser.getUserType()){
                case ADMIN -> adminUI(authenticatedUser);
                case REGISTERED_USER -> userUI(authenticatedUser);
            }
        }else{
            System.out.println(response.getMessage());
        }
    }

    public void logout(){
        response = authenticationController.logout(authenticatedUser);
        System.out.println(response.getMessage());
    }

    public void adminUI(AuthenticatedUser authenticatedUser){}

    public void userUI(AuthenticatedUser authenticatedUser){}

    public void home(){
        while(1>0){

            System.out.println("[1] Login\n[2] Register User\n[3] Search Trains\n");
            Integer choice = inputView.getChoice("Choice: ");
            switch (choice){
                case 1 -> login();

            }
        }

    }
}
