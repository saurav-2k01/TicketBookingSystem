package biz.dss.ticketbookingsystem.controller;

import biz.dss.ticketbookingsystem.models.Train;
import biz.dss.ticketbookingsystem.models.User;
import biz.dss.ticketbookingsystem.service.BookingService;
import biz.dss.ticketbookingsystem.utils.Response;
import biz.dss.ticketbookingsystem.valueobjects.AuthenticatedUser;
import biz.dss.ticketbookingsystem.valueobjects.BookingDetail;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@RequiredArgsConstructor
public class BookingController{
    /*Fields*/
    private final BookingService bookingService;

    
    public Response bookTicket(AuthenticatedUser authenticatedUser, BookingDetail bookingDetail) {
        return bookingService.bookTicket(authenticatedUser, bookingDetail);
    }

    
    public Response getTransaction(AuthenticatedUser authenticatedUser, int pnr) {
        return bookingService.getTransaction(authenticatedUser, pnr);
    }

    
    public Response getAvailableSeats(Train train, LocalDate date) {
        return bookingService.getAvailableSeats(train, date);
    }

    
    public Response getTickets(AuthenticatedUser authenticatedUser) {
        return bookingService.getTickets(authenticatedUser);
    }

    
    public Response cancelTicket(AuthenticatedUser authenticatedUser, int pnr) {
        return bookingService.cancelTicket(authenticatedUser, pnr);
    }



    /*Methods*/
}
