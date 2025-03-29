package biz.dss.ticketbookingsystem.controller;

import biz.dss.ticketbookingsystem.models.Train;
import biz.dss.ticketbookingsystem.models.User;
import biz.dss.ticketbookingsystem.service.BookingService;
import biz.dss.ticketbookingsystem.utils.Response;
import biz.dss.ticketbookingsystem.valueobjects.BookingDetail;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@RequiredArgsConstructor
public class BookingController{
    /*Fields*/
    private final BookingService bookingService;

    
    public Response bookTicket(User user, BookingDetail bookingDetail) {
        return bookingService.bookTicket(user, bookingDetail);
    }

    
    public Response getTransaction(int pnr) {
        return bookingService.getTransaction(pnr);
    }

    
    public Response getAvailableSeats(Train train, LocalDate date) {
        return bookingService.getAvailableSeats(train, date);
    }

    
    public Response getTickets(User user) {
        return bookingService.getTickets(user);
    }

    
    public Response cancelTicket(int pnr) {
        return bookingService.cancelTicket(pnr);
    }



    /*Methods*/
}
