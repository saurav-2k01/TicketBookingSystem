//package biz.dss.ticketbookingsystem.views;
//
//import biz.dss.ticketbookingsystem.controller.AdminController;
//import biz.dss.ticketbookingsystem.enums.UserType;
//import biz.dss.ticketbookingsystem.models.*;
//import biz.dss.ticketbookingsystem.utils.Formatter;
//import biz.dss.ticketbookingsystem.utils.Response;
//import biz.dss.ticketbookingsystem.utils.UtilClass;
//import biz.dss.ticketbookingsystem.valueobjects.AuthenticatedUser;
//import biz.dss.ticketbookingsystem.valueobjects.Credential;
//
//import java.util.List;
//import java.util.Locale;
//import java.util.Map;
//import java.util.Scanner;
//
//import static biz.dss.ticketbookingsystem.enums.UserType.ADMIN;
//import static biz.dss.ticketbookingsystem.utils.ResponseStatus.*;
//
//public class AdminView { // @TODO extend abstract view
//    private final Scanner input = UtilClass.scanner;
//    private final AdminController adminController;
//
//    public AdminView(AdminController adminController) {
//        this.adminController = adminController;
//    }
//
//    public void registerAdmin() {
//        System.out.println("\n=================================== Register New Admin ===================================\n");
//        System.out.print("Enter UserName: ");
//        String userName = input.nextLine();
//        System.out.print("Enter Password: ");
//        String password = input.nextLine();
//        User admin = User.builder().userName(userName).password(password).userType(ADMIN).build();
//        Response response = adminController.registerAdmin(admin);
//        System.out.println(response.getMessage());
//    }
//
////
////    public boolean login() {
////        System.out.println("\n=================================== Admin Login ===================================\n");
////        System.out.print("Enter UserName: ");
////        String userName = input.nextLine();
////        System.out.print("Enter Password: ");
////        String password = input.nextLine();
////
//////        Response loginResponse = adminController.login(new Credential(userName, password));
////
////        System.out.println(loginResponse.getMessage());
////        return (AuthenticatedUser)(loginResponse.getData());
////    }
//
//
//    public void displayUsers() {
//        System.out.println("\n=================================== display Users ===================================\n");
//        Response usersResponse = adminController.getUsers();
//        if(usersResponse.getStatus().equals(FAILURE)){
//            System.out.println(usersResponse.getMessage());
//            return;
//        }
//        List<User> users = (List<User>) (usersResponse.getData());
//        Formatter.tableTemplate(users);
//    }
//
//    public void displayTransactions() {
//        System.out.println("\n=================================== Display Transactions ===================================\n");
//        Response transactionsResponse = adminController.getTransactions();
//        if(transactionsResponse.getStatus().equals(FAILURE)){
//            System.out.println(transactionsResponse.getMessage());
//            return;
//        }
//        Map<Integer, Transaction> transactions = (Map<Integer, Transaction>)(transactionsResponse.getData());
//        Formatter.tableTemplate(transactions.values().stream().toList());
//    }
//
//    public void displaySingleTransaction() {
//        System.out.println("\n=================================== Transaction ===================================\n");
//        displayTransactions();
//        System.out.println("Enter PNR: ");
//        int pnr = input.nextInt();
//        Response transactionResponse = adminController.getTransaction(pnr);
//        if(transactionResponse.getStatus().equals(FAILURE)){
//            System.out.println(transactionResponse.getMessage());
//            return;
//        }
//        Transaction transaction = (Transaction)(transactionResponse.getData());
//        Formatter.formatTicket(transaction);
//    }
//
//    public void logout() {
//        Response logoutResponse = adminController.logout();
//        System.out.println(logoutResponse.getMessage());
//    }
//
//
//    public void deleteAdmin() {
//        System.out.println("\n=================================== Delete Admin ===================================\n");
//
//        System.out.println("Enter admin username: ");
//        String userName = input.nextLine();
//        Response deleteResponse = adminController.deleteAdmin(userName);
//        System.out.println(deleteResponse.getMessage());
//    }
//
//
//    public void displayTrains() {
//        Response trainsResponse = adminController.getTrains();
//        if(trainsResponse.getStatus().equals(FAILURE)){
//            System.out.println(trainsResponse.getMessage());
//            return;
//        }
//        Map<Integer, Train> trains = (Map<Integer, Train>)(trainsResponse.getData());
//        Formatter.tableTemplate(trains.values().stream().toList());
//    }
//
//    public void displayAllAdmins() {
//        Response adminsResponse = adminController.getAllAdmins();
//        if(adminsResponse.getStatus().equals(FAILURE)){
//            System.out.println(adminsResponse.getMessage());
//            return;
//        }
//        List<User> admins = (List<User>)(adminsResponse.getData());
//        Formatter.tableTemplate(admins);
//    }
//
//    public void setCurrentAdmin(AuthenticatedUser admin){
//        Response response = adminController.setCurrentAdmin(admin);
//        System.out.println(response.getMessage());
//    }
//
//}
