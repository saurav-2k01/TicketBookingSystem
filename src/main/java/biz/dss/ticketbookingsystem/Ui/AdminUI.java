package biz.dss.ticketbookingsystem.Ui;

import biz.dss.ticketbookingsystem.controller.AuthenticationController;
import biz.dss.ticketbookingsystem.models.Train;
import biz.dss.ticketbookingsystem.valueobjects.AuthenticatedUser;
import biz.dss.ticketbookingsystem.view.*;

public class AdminUI extends AbstractUI {
    public AdminUI(AuthenticationController authenticationController, InputView inputView, UserView userView, TrainView trainView, BookingView bookingView, StationView stationView) {
        super(authenticationController, inputView, userView, trainView, bookingView, stationView);
    }

    public void displayUi(AuthenticatedUser authenticatedUser){
        this.authenticatedUser = authenticatedUser;
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
        while(true){
            System.out.println("\n[1] Add Admin\n[2] Remove Admin\n[3] View Admins\n[0] Back");
            Integer choice = super.inputView.getChoice("User's choice: ");
            switch (choice){
                case 0 -> {
                    logout();
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

        while(true){
            System.out.println("\n[1] Add Train\n[2] Remove Train\n[3] Manage Specific Train\n[4] View All Trains\n[5] Search Train\n[0] Back");
            Integer choice = inputView.getChoice("User's choice: ");
            switch (choice){
                case 0 -> {
                    return;
                }
                case 1 -> trainView.addTrain(authenticatedUser);
                case 2 -> trainView.removeTrain(authenticatedUser);
                case 3 -> {
                    trainView.manageSpecificTrain();
                    manageSpecificTrain(authenticatedUser);
                }//todo add authenticate user verfication in this method.
                case 4 -> trainView.showAllTrains();
                case 5 -> trainView.searchTrain();
                default -> System.out.println("Invalid Options.");
            }
        }
    }

    private void manageSpecificTrain(AuthenticatedUser authenticatedUser) {
        while (true) {
            System.out.println("1. Manage Coach\n2.  Manage Route\n3. Manage Running days\n4. Display Train details\n5. Back");
            Integer choice = inputView.getChoice("User's choice");
            switch (choice) {
                case 1:
                    manageCoach(authenticatedUser);
                    break;
                case 2:
                    manageRoute(authenticatedUser);
                    break;
                case 3:
                    manageRunningDay(authenticatedUser);
                    break;
                case 4:
                    trainView.displayTrainDetail();
                    break;
                case 5:
                    return;
            }
        }

    }

    private void manageCoach(AuthenticatedUser authenticatedUser) {

        while (true) {
            System.out.println("1. Add coach\n2. Remove Coach\n3. View Coach Detail\n4. Back");
            Integer choice = inputView.getChoice("Admin's choice");
            switch (choice) {
                case 1:
                    trainView.addCoach(authenticatedUser);
                    break;
                case 2:
                    trainView.removeCoach(authenticatedUser);
                    break;
                case 3:
                    trainView.getCoaches();
                    break;
                case 4:
                    return;
            }
        }

    }

    private void manageRoute(AuthenticatedUser authenticatedUser) {
        while (true) {
            System.out.println("1. Add Route\n2. Remove Route\n3. View Route Detail\n4. Back");
            Integer choice = inputView.getChoice("Admin's choice");
            switch (choice) {
                case 1:
                    trainView.addRoute(authenticatedUser);
                    break;
                case 2:
                    trainView.removeRoute(authenticatedUser);
                    break;
                case 3:
                    trainView.getRoute();
                    break;
                case 4:
                    return;
            }
        }

    }

    private void manageRunningDay(AuthenticatedUser authenticatedUser) {
        while (true) {
            System.out.println("1. Add Running Day\n2. Remove Running Day\n3. Show Running Days\n4. Back");
            Integer choice = inputView.getChoice("Admin's choice");

            switch (choice) {
                case 1:
                    trainView.addRunningDay(authenticatedUser);
                    break;
                case 2:
                    trainView.removeRunningDay(authenticatedUser);
                    break;
                case 3:
                    trainView.getRunningDays();
                    break;
                case 4:
                    return;
            }
        }

    }

}
