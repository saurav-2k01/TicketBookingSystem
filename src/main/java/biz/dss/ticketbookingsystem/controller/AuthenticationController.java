package biz.dss.ticketbookingsystem.controller;

import biz.dss.ticketbookingsystem.service.AuthenticationService;
import biz.dss.ticketbookingsystem.utils.Response;
import biz.dss.ticketbookingsystem.valueobjects.AuthenticatedUser;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AuthenticationController {

    /*Fields*/
    private final AuthenticationService authenticationService;

    /*Methods*/
    public Response authenticateUser(String userName, String password) {
        return authenticationService.authenticateUser(userName, password);
    }

    public Response logout(AuthenticatedUser authenticatedUser) {
        return authenticationService.logout(authenticatedUser);
    }


    public Response getAuthenticatedUser(AuthenticatedUser authenticatedUser) {
        return authenticationService.getAuthenticatedUser(authenticatedUser);
    }
}
