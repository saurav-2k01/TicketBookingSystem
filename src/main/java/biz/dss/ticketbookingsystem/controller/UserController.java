package biz.dss.ticketbookingsystem.controller;


import biz.dss.ticketbookingsystem.models.User;
import biz.dss.ticketbookingsystem.service.UserService;
import biz.dss.ticketbookingsystem.utils.Response;
import biz.dss.ticketbookingsystem.valueobjects.AuthenticatedUser;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserController{
    /*Fields*/
    private final UserService userService;

    /*Methods*/
    public Response registerUser(User user) {
        return userService.registerUser(user);
    }

    public Response getUserById(Integer id) {
        return userService.getUserById(id);
    }

    public Response getUserByEmail(String email) {
        return userService.getUserByEmail(email);
    }

    public Response getUserByUserName(String username) {
        return userService.getUserByUserName(username);
    }

    public Response deleteUser(AuthenticatedUser authenticatedUser, String username) {
        return userService.deleteUser(authenticatedUser, username);
    }

    public Response getUsers(AuthenticatedUser authenticatedUser) {
        return userService.getUsers(authenticatedUser);
    }

    public Response getAllAdmins(AuthenticatedUser authenticatedUser) {
        return userService.getAllAdmins(authenticatedUser);
    }
}
