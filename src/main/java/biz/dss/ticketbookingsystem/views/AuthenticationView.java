//package biz.dss.ticketbookingsystem.views;
//
//import biz.dss.ticketbookingsystem.service.AuthenticationService;
//import biz.dss.ticketbookingsystem.utils.Response;
//import biz.dss.ticketbookingsystem.valueobjects.AuthenticatedUser;
//
//import java.util.Optional;
//
//public class AuthenticationView {
//    private final AuthenticationService authenticationService;
//    private final InputView inputView;
//
//    public AuthenticationView(AuthenticationService authenticationService, InputView inputView){
//        this.authenticationService = authenticationService;
//        this.inputView = inputView;
//    }
//    public Optional<AuthenticatedUser.AuthenticatedUserBuilder> login() {
//        System.out.println("\n=================================== Admin Login ===================================\n");
//        System.out.println("enter '0' anytime to go back to previous menu.");
//        String userName = inputView.getStringInput("Username: ");
//        if(userName.equals("0")) return Optional.empty();
//        String password = inputView.getStringInput("Password: ");
//        if(password.equals("0")) return Optional.empty();
//        Response response = authenticationService.authenticateUser(userName, password);
//        System.out.println(response.getMessage());
//        if(Boolean.TRUE.equals(response.isSuccess())){
//           AuthenticatedUser.AuthenticatedUserBuilder authenticatedUser = (AuthenticatedUser.AuthenticatedUserBuilder) response.getData();
//           return Optional.of(authenticatedUser);
//        }
//        return Optional.empty();
//    }
//}
