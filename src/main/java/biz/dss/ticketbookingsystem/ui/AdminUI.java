package biz.dss.ticketbookingsystem.ui;

import biz.dss.ticketbookingsystem.controller.AuthenticationController;
import biz.dss.ticketbookingsystem.valueobjects.AuthenticatedUser;
import biz.dss.ticketbookingsystem.view.*;

public class AdminUI extends AbstractUI {

    public AdminUI(AuthenticationController authenticationController, InputView inputView, UserView userView, TrainView trainView, BookingView bookingView, StationView stationView) {
        super(authenticationController, inputView, userView, trainView, bookingView, stationView);
    }

    public void displayUi(AuthenticatedUser authenticatedUser){
        this.authenticatedUser = authenticatedUser;
        System.out.printf("==============================================WELCOME %s==============================================%n", authenticatedUser.getUserName());
        boolean flag = true;
        while(flag){
            System.out.println("\n[1] View Users\n[2] Manage Admins\n[3] Manage Trains\n[4] View All Transaction\n[5] View Single Transaction\n[0] Logout");
            Integer choice = super.inputView.getChoice("User's choice: ");
            switch (choice){
                case 0 -> {
                    flag = false;
                    logout();
                }
                case 1 -> userView.displayAllUsers(authenticatedUser);
                case 2 -> manageAdmin(authenticatedUser);
                case 3 -> manageAllTrains(authenticatedUser);
                case 4 -> bookingView.displayTickets(authenticatedUser);
                case 5 -> bookingView.displayTicket(authenticatedUser);
                default -> System.out.println("Invalid Options.");
            }
        }
    }



    private void manageAdmin(AuthenticatedUser authenticatedUser){
        this.authenticatedUser = authenticatedUser;
        System.out.printf("==============================================MANAGE ADMIN==============================================%n", authenticatedUser.getUserName());
        while(true){
            System.out.println("\n[1] Add Admin\n[2] Remove Admin\n[3] View Admins\n[0] Back");
            Integer choice = super.inputView.getChoice("User's choice: ");
            switch (choice){
                case 0 -> {
                    return;
                }
                case 1 -> userView.registerAdmin(authenticatedUser);
                case 2 -> userView.removeAdmin(authenticatedUser);
                case 3 -> userView.displayAllAdmins(authenticatedUser);
                default -> System.out.println("Invalid Options.");
            }
        }
    }

    private void  manageAllTrains(AuthenticatedUser authenticatedUser){
        this.authenticatedUser = authenticatedUser;
        System.out.printf("==============================================MANAGE ALL TRAINS==============================================%n", authenticatedUser.getUserName());
        while(true){
            System.out.println("\n[1] Add Train\n[2] Remove Train\n[3] Manage Specific Train\n[4] View All Trains\n[5] Search Train\n[0] Back");
            Integer choice = inputView.getChoice("User's choice: ");
            switch (choice){
                case 0 -> {return;}
                case 1 -> trainView.addTrain(authenticatedUser);
                case 2 -> trainView.removeTrain(authenticatedUser);
                case 3 -> {
                    boolean status = trainView.manageSpecificTrain();
                    if(Boolean.TRUE.equals(status)) {
                        manageSpecificTrain(authenticatedUser);
                    }
                }
                case 4 -> trainView.showAllTrains();
                case 5 -> trainView.searchTrain();
                default -> System.out.println("Invalid Options.");
            }
        }
    }

    private void manageSpecificTrain(AuthenticatedUser authenticatedUser) {
        System.out.printf("==============================================MANAGE SPECIFIC TRAIN==============================================%n", authenticatedUser.getUserName());

        while (true) {
            System.out.println("[1] Manage Coach\n[2]  Manage Route\n[3] Manage Running days\n[4] Display Train details\n[0] Back");
            Integer choice = inputView.getChoice("User's choice");
            switch (choice) {
                case 0->{return;}
                case 1->manageCoach(authenticatedUser);
                case 2->manageRoute(authenticatedUser);
                case 3->manageRunningDay(authenticatedUser);
                case 4-> trainView.displayTrainDetail();
                default-> System.out.println("Invalid option");
            }
        }

    }

    private void manageCoach(AuthenticatedUser authenticatedUser) {
        System.out.printf("==============================================MANAGE COACH==============================================%n", authenticatedUser.getUserName());

        while (true) {
            System.out.println("[1] Add coach\n[2] Remove Coach\n[3] View Coach Detail\n[0] Back");
            Integer choice = inputView.getChoice("Admin's choice");
            switch (choice) {
                case 0->{return;}
                case 1-> trainView.addCoach(authenticatedUser);
                case 2->trainView.removeCoach(authenticatedUser);
                case 3-> trainView.displayCoaches();
                default-> System.out.println("Invalid option");
            }
        }

    }

    private void manageRoute(AuthenticatedUser authenticatedUser) {
        System.out.printf("==============================================MANAGE ROUTE==============================================%n", authenticatedUser.getUserName());

        while (true) {
            System.out.println("[1] Add Route\n[2] Remove Route\n[3] View Route Detail\n[0] Back");
            Integer choice = inputView.getChoice("Admin's choice");
            switch (choice) {
                case 0-> {return;}
                case 1-> trainView.addRoute(authenticatedUser);
                case 2-> trainView.removeRoute(authenticatedUser);
                case 3-> trainView.getRoute();
                default-> System.out.println("Invalid option");
            }
        }

    }

    private void manageRunningDay(AuthenticatedUser authenticatedUser) {
        System.out.printf("==============================================MANAGE RUNNING DAYS==============================================%n", authenticatedUser.getUserName());

        while (true) {
            System.out.println("[1] Add Running Day\n[2] Remove Running Day\n[3] Show Running Days\n[0] Back");
            Integer choice = inputView.getChoice("Admin's choice");

            switch (choice) {
                case 0-> {return;}
                case 1-> trainView.addRunningDay(authenticatedUser);
                case 2-> trainView.removeRunningDay(authenticatedUser);
                case 3-> trainView.getRunningDays();
                default -> System.out.println("Invalid options");

            }
        }

    }

}
