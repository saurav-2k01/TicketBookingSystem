package biz.dss.ticketbookingsystem.service;

import biz.dss.ticketbookingsystem.utils.Response;
import biz.dss.ticketbookingsystem.valueobjects.AuthenticatedUser;

public interface AuthenticationService {
    Response authenticateUser(String userName, String password);
    Response logout(AuthenticatedUser authenticatedUser);
    Response getAuthenticatedUser(AuthenticatedUser authenticatedUser);
}
