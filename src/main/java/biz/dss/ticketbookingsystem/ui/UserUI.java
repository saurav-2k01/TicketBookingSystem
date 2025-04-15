package biz.dss.ticketbookingsystem.ui;

import biz.dss.ticketbookingsystem.controller.*;
import biz.dss.ticketbookingsystem.valueobjects.AuthenticatedUser;
import biz.dss.ticketbookingsystem.view.BookingView;
import biz.dss.ticketbookingsystem.view.StationView;
import biz.dss.ticketbookingsystem.view.TrainView;
import biz.dss.ticketbookingsystem.view.UserView;
import biz.dss.ticketbookingsystem.view.InputView;

public class UserUI extends AbstractUI {
    private final BookingView bookingView;

    public UserUI(AuthenticationController authenticationController, InputView inputView, UserView userView, BookingView bookingView, TrainView trainView,  StationView stationView) {
        super(authenticationController, inputView, userView, trainView, bookingView, stationView);
        this.bookingView = bookingView;
    }

    @Override
    public void displayUi(AuthenticatedUser authenticatedUser) {
        this.authenticatedUser = authenticatedUser;
        boolean flag = true;
        while(flag){
            System.out.println("\n[1] Search Trains\n[2] Book Ticket\n[3] View Tickets\n[4] View Single Ticket\n[5] Cancel Ticket\n[0] Logout");
            Integer choice = super.inputView.getChoice("User's choice: ");
            switch (choice){
                case 0 -> {
                    flag = false;
                    logout();
                }
                case 1 -> trainView.searchTrain();
                case 2 -> bookingView.bookTicket(this.authenticatedUser);
                case 3 -> bookingView.displayTickets(this.authenticatedUser);
                case 4 -> bookingView.displayTicket(this.authenticatedUser);
                case 5 -> bookingView.cancelTicket(this.authenticatedUser);
                default -> System.out.println("Invalid Options.");
            }
        }
    }
}
