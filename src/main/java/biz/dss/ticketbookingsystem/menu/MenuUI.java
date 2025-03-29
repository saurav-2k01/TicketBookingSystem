//package biz.dss.ticketbookingsystem.menu;
//
//
//import biz.dss.ticketbookingsystem.enums.UserType;
//import biz.dss.ticketbookingsystem.utils.UtilClass;
//import biz.dss.ticketbookingsystem.valueobjects.AuthenticatedUser;
//import biz.dss.ticketbookingsystem.views.*;
//
//import java.util.Optional;
//import java.util.Scanner;
//
//import static biz.dss.ticketbookingsystem.enums.UserType.ADMIN;
//import static biz.dss.ticketbookingsystem.enums.UserType.REGISTERED_USER;
//
//public class MenuUI {
//    private final AuthenticationView authenticationView;
//    /**
//     * userController is storing the reference of an instance of UserController class.
//     */
//    private UserView userView;
//    private AdminView adminView;
//    private StationView stationView;
//    private TrainView trainView;
//    private final Scanner input = UtilClass.scanner;
//    private final AdminUi  adminUi;
//
//    public MenuUI(UserView userView, TrainView trainView, StationView stationView, AdminView adminView, AuthenticationView authenticationView) {
//        this.userView = userView;
//        this.adminView = adminView;
//        this.authenticationView = authenticationView;
//        this.adminUi = new AdminUi(adminView, stationView, trainView);
//    }
//
//    public void userOptions() {
//        boolean flag = true;
//
//        while (flag) {
//            System.out.println("\n=================================== User Options ===================================\n");
//            System.out.println("\n[1] Search Trains\n[2] Book Ticket\n[3] View Tickets\n[4] View Single Ticket\n[5] Cancel Ticket\n[6] Logout\n[0] Exit");
//            System.out.print("\nuser choice: ");
//            int userChoice = 0;
//
//            try {
//                userChoice = input.nextInt();
//            } catch (Exception e) {
//                System.out.println("Enter a valid input.");
//                input.nextLine();
//                continue;
//            }
//
//            if (input.hasNextLine()) {
//                input.nextLine();
//            }
//
//            switch (userChoice) {
//                case 1:
//                    userView.searchTrains();
//                    break;
//                case 2:
//                    userView.bookTicket();
//                    break;
//                case 3:
//                    userView.displayTickets();
//                    break;
//                case 4:
//                    userView.displayTicket();
//                    break;
//                case 5:
//                    userView.cancelTicket();
//                    break;
//                case 6:
//                    userView.logout();
//                    mainHome();
//                    return;
//                case 0:
//                    return;
//                default:
//                    System.out.println("Invalid Option.");
//            }
//        }
//    }
//
//    public void mainHome() {
//        boolean flag = true;
//
//        while (flag) {
//            System.out.println("\n=================================== Main Home ===================================\n");
//            System.out.println("\n[1] Search Trains\n[2] Login As Admin\n[3] Login As User\n[4] Register New User\n[0] Exit");
//            System.out.print("\nuser choice: ");
//            int userChoice = 0;
//
//            try {
//                userChoice = input.nextInt();
//            } catch (Exception e) {
//                System.out.println("Enter a valid input.");
//                input.nextLine();
//                continue;
//            }
//
//            if (input.hasNextLine()) {
//                input.nextLine();
//            }
//
//            switch (userChoice) {
//                case 1:
//                    userView.searchTrains();
//                    break;
//                case 2:
//                    Optional<AuthenticatedUser.AuthenticatedUserBuilder> authenticatedUserBuilder = authenticationView.login();
//                    if (authenticatedUserBuilder.isPresent()){
//                        AuthenticatedUser authenticatedUser = authenticatedUserBuilder.get().build();
//                        UserType userType = authenticatedUser.getUserType();
//                        if(userType.equals(ADMIN)){
//                            adminUi.setCurrentAdmin(authenticatedUser);
//                            adminUi.adminOptions();
//                        }else if (userType.equals(REGISTERED_USER)){
//                            userOptions();
//                        }
//                    }
//                    break;
//                case 3:
//                    int userLogin = userView.loginUser();
//                    if (userLogin == 1) {
//                        userOptions();
//                    }
//                    break;
//                case 4:
//                    userView.registerNewUser();
//                    break;
//                case 0:
//                    return;
//                default:
//                    System.out.println("Invalid Option.");
//            }
//        }
//    }
//
//
//
//}
//
