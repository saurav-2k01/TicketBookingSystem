//package biz.dss.ticketbookingsystem.menu;
//
//import biz.dss.ticketbookingsystem.utils.UtilClass;
//import biz.dss.ticketbookingsystem.valueobjects.AuthenticatedUser;
//import biz.dss.ticketbookingsystem.views.AdminView;
//import biz.dss.ticketbookingsystem.views.StationView;
//import biz.dss.ticketbookingsystem.views.TrainView;
//
//import java.util.Scanner;
//
//public class AdminUi {
//    private AdminView adminView;
//    private StationView stationView;
//    private TrainView trainView;
//    private final Scanner input = UtilClass.scanner;
//
//    public AdminUi(AdminView adminView, StationView stationView, TrainView trainView){
//        this.adminView = adminView;
//        this.stationView = stationView;
//        this.trainView = trainView;
//    }
//
//    public void setCurrentAdmin(AuthenticatedUser admin){
//        adminView.setCurrentAdmin(admin);
//    }
//    public void adminOptions() {
//        boolean flag = true;
//
//        while (flag) {
//            System.out.println("\n=================================== Admin Options ===================================\n");
//            System.out.println("\n[1] Manage Admin\n[2] Manage Users\n[3] Manage Booking Details\n[4] Manage Trains\n[5] Logout\n[0] Exit");
//            System.out.print("\nadmin choice: ");
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
//
//                case 1:
//                    manageAdminOptions();
//                    break;
//                case 2:
//                    manageUsersOptions();
//                    break;
//                case 3:
//                    manageBookingDetailsOptions();
//                    break;
//                case 4:
//                    manageTrains();
//                    break;
//                case 5:
//                    adminView.logout();
//                    return;
//                case 0:
//                    return;
//                default:
//                    System.out.println("Invalid Option.");
//            }
//        }
//    }
//
//    public void manageAdminOptions() {
//        boolean flag = true;
//
//        while (flag) {
//            System.out.println("\n=================================== Manage Admins ===================================\n");
//            System.out.println("\n[1] Register New Admin\n[2] Delete Admin\n[3] View All Admins\n[4] Logout\n[0] Exit");
//            System.out.print("\nadmin choice: ");
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
//
//                case 1:
//                    adminView.registerAdmin();
//                    break;
//                case 2:
//                    adminView.deleteAdmin();
//                    break;
//                case 3:
//                    adminView.displayAllAdmins();
//                    break;
//                case 4:
//                    adminView.logout();
////                    mainHome();
//                    break;
//                case 0:
//                    return;
//                default:
//                    System.out.println("Invalid Option.");
//            }
//        }
//    }
//
//    public void manageUsersOptions() {
//        boolean flag = true;
//
//        while (flag) {
//            System.out.println("\n=================================== Manage Users ===================================\n");
//            System.out.println("Admin Options:\n[1] View all user\n[2] Logout\n[0] Exit");
//            System.out.print("admin choice: ");
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
//
//                case 1:
//                    adminView.displayUsers();
//                    break;
//                case 2:
//                    adminView.logout();
//                    return;
//                case 0:
//                    return;
//                default:
//                    System.out.println("Invalid Option.");
//            }
//        }
//    }
//
//    public void manageBookingDetailsOptions() {
//        boolean flag = true;
//
//        while (flag) {
//            System.out.println("\n=================================== Manage Booking Details ===================================\n");
//            System.out.println("\n[1] View Transactions\n[2] View Single Transaction\n[0] Exit");
//            System.out.print("\nadmin choice: ");
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
//
//                case 1:
//                    adminView.displayTransactions();
//                    break;
//                case 2:
//                    adminView.displaySingleTransaction();
//                    break;
//                case 0:
//                    return;
//                default:
//                    System.out.println("Invalid Option.");
//            }
//        }
//    }
//
//    public void manageTrains() {
//        while (true) {
//            System.out.println("1. Add Train\n2. Remove Train\n3. Manage Specific Train\n4. Display All Trains\n5.Search Train\n6.back");
//            Scanner input = new Scanner(System.in);
//            int choice = input.nextInt();
//            input.nextLine();
//            switch (choice) {
//                case 1:
//                    trainView.addTrain();
//                    break;
//                case 2:
//                    trainView.removeTrain();
//                    break;
//                case 3:
//                    trainView.manageSpecificTrain();
//                    manageSpecificTrain();
//                    break;
//                case 4:
//                    trainView.showAllTrains();
//                    break;
//                case 5:
//                    trainView.searchTrain();
//                    break;
//                case 6:
//                    return;
//            }
//        }
//
//    }
//
//    private void manageSpecificTrain() {
//        while (true) {
//            System.out.println("1. Manage Coach\n2.  Manage Route\n3. Manage Running days\n4. Display Train details\n5. Back");
//            Scanner input = new Scanner(System.in);
//            int choice = input.nextInt();
//            input.nextLine();
//            switch (choice) {
//                case 1:
//                    manageCoach();
//                    break;
//                case 2:
//                    manageRoute();
//                    break;
//                case 3:
//                    manageRunningDay();
//                    break;
//                case 4:
//                    trainView.displayTrainDetail();
//                    break;
//                case 5:
//                    return;
//            }
//        }
//
//    }
//
//    private void manageCoach() {
//        Scanner input = new Scanner(System.in);
//        while (true) {
//            System.out.println("1. Add coach\n2. Remove Coach\n3. View Coach Detail\n4. Back");
//            int userChoice = 0;
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
//            switch (userChoice) {
//                case 1:
//                    trainView.addCoach();
//                    break;
//                case 2:
//                    trainView.removeCoach();
//                    break;
//                case 3:
//                    trainView.getCoaches();
//                    break;
//                case 4:
//                    return;
//            }
//        }
//
//    }
//
//    private void manageRoute() {
//        while (true) {
//            System.out.println("1. Add Route\n2. Remove Route\n3. View Route Detail\n4. Back");
//            Scanner input = new Scanner(System.in);
//            int choice = input.nextInt();
//            input.nextLine();
//            switch (choice) {
//                case 1:
//                    trainView.addRoute();
//                    break;
//                case 2:
//                    trainView.removeRoute();
//                    break;
//                case 3:
//                    trainView.getRoute();
//                    break;
//                case 4:
//                    return;
//            }
//        }
//
//    }
//
//    private void manageRunningDay() {
//        while (true) {
//            System.out.println("1. Add Running Day\n2. Remove Running Day\n3. Show Running Days\n4. Back");
//            Scanner input = new Scanner(System.in);
//            int choice = input.nextInt();
//            input.nextLine();
//
//            switch (choice) {
//                case 1:
//                    trainView.addRunningDay();
//                    break;
//                case 2:
//                    trainView.removeRunningDay();
//                    break;
//                case 3:
//                    trainView.getRunningDays();
//                    break;
//                case 4:
//                    return;
//            }
//        }
//
//    }
//
//}
