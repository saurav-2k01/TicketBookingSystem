package biz.dss.ticketbookingsystem.Ui;

import biz.dss.ticketbookingsystem.controller.AuthenticationController;
import biz.dss.ticketbookingsystem.utils.Response;
import biz.dss.ticketbookingsystem.valueobjects.AuthenticatedUser;
import biz.dss.ticketbookingsystem.view.*;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
abstract public class AbstractUI {
    protected final AuthenticationController authenticationController;
    protected final InputView inputView;
    protected  final UserView userView;
    protected final TrainView trainView;
    protected final BookingView bookingView;
    protected final StationView stationView;
    protected AuthenticatedUser authenticatedUser;
    private AbstractUI ui;
    private Response response;



    public void login(){
        String userName = inputView.getStringInput("Username: ");
        String password = inputView.getStringInput("Password: ");
        response = authenticationController.authenticateUser(userName, password);
        if(Boolean.TRUE.equals(response.isSuccess())){
            authenticatedUser = (AuthenticatedUser) (response.getData());
            switch (authenticatedUser.getUserType()){
                case ADMIN -> ui = new AdminUI(authenticationController, inputView, userView, trainView, bookingView, stationView);
                case REGISTERED_USER -> ui = new UserUI(authenticationController, inputView, userView, bookingView, trainView, stationView);
            }
            ui.displayUi(authenticatedUser);
        }else{
            System.out.println("Wrong Credential");
        }
    }

    public void logout(){
        response = authenticationController.logout(authenticatedUser);
        System.out.println(response.getMessage());
        if(response.isSuccess()){
            authenticatedUser = (AuthenticatedUser) (response.getData());
            home();
        }
    }

    public abstract void displayUi(AuthenticatedUser authenticatedUser);

    public void home(){
        while(1>0){

            System.out.println("[1] Login\n[2] Register User\n[3] Search Trains\n");
            Integer choice = inputView.getChoice("Choice: ");
            switch (choice){
                case 1 -> login();
                case 2 -> userView.registerUser();
                case 3 -> trainView.searchTrain();
            }
        }

    }


}
