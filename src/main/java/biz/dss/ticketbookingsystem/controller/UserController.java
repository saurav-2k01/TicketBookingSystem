package biz.dss.ticketbookingsystem.controller;


import biz.dss.ticketbookingsystem.models.User;
import biz.dss.ticketbookingsystem.service.UserService;
import biz.dss.ticketbookingsystem.utils.Response;
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

    public Response deleteUser(String username) {
        return userService.deleteUser(username);
    }

    public Response getUsers() {
        return userService.getUsers();
    }

    public Response getAllAdmins() {
        return userService.getAllAdmins();
    }
}
