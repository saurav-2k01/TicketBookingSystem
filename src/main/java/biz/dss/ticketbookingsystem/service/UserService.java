package biz.dss.ticketbookingsystem.service;

import biz.dss.ticketbookingsystem.models.User;
import biz.dss.ticketbookingsystem.utils.Response;
import biz.dss.ticketbookingsystem.valueobjects.AuthenticatedUser;

public interface UserService {

    Response registerUser(User user);

    Response getUserById(Integer id);

    Response getUserByEmail(String email);

    Response getUserByUserName(String username);

    Response deleteAdmin(AuthenticatedUser authenticatedUser, String username);

    Response getUsers(AuthenticatedUser authenticatedUser);

    Response getAllAdmins(AuthenticatedUser authenticatedUser);

    Response userExists(String userName);

}
