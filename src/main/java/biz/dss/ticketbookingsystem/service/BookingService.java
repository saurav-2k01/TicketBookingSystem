package biz.dss.ticketbookingsystem.service;

import biz.dss.ticketbookingsystem.models.Train;
import biz.dss.ticketbookingsystem.utils.Response;
import biz.dss.ticketbookingsystem.valueobjects.AuthenticatedUser;
import biz.dss.ticketbookingsystem.valueobjects.BookingDetail;

import java.time.LocalDate;

public interface BookingService {
    Response bookTicket(AuthenticatedUser authenticatedUser, BookingDetail bookingDetail);
    Response getTransaction(AuthenticatedUser authenticatedUser, int pnr);
    Response getAvailableSeats(Train train, LocalDate date);

    Response getTickets(AuthenticatedUser authenticatedUser) ;

    Response cancelTicket(AuthenticatedUser authenticatedUser, int pnr);
}
