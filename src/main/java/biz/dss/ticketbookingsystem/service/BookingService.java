package biz.dss.ticketbookingsystem.service;

import biz.dss.ticketbookingsystem.enums.TravellingClass;
import biz.dss.ticketbookingsystem.models.*;
import biz.dss.ticketbookingsystem.utils.Response;
import biz.dss.ticketbookingsystem.valueobjects.AuthenticatedUser;
import biz.dss.ticketbookingsystem.valueobjects.BookingDetail;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static biz.dss.ticketbookingsystem.utils.ResponseStatus.FAILURE;
import static biz.dss.ticketbookingsystem.utils.ResponseStatus.SUCCESS;
//todo pass authenticatedUser to all service methods
public interface BookingService {
    Response bookTicket(AuthenticatedUser authenticatedUser, BookingDetail bookingDetail);
    Response getTransaction(AuthenticatedUser authenticatedUser, int pnr);
    Response getAvailableSeats(Train train, LocalDate date);

    Response getTickets(AuthenticatedUser authenticatedUser) ;

    Response cancelTicket(AuthenticatedUser authenticatedUser, int pnr);
}
