package biz.dss.ticketbookingsystem.valueobjects;

import biz.dss.ticketbookingsystem.enums.UserType;
import biz.dss.ticketbookingsystem.models.User;
import lombok.*;


@Getter
@Builder
@EqualsAndHashCode
public class AuthenticatedUser{
    private final Integer id;
    private final String userName;
    private final UserType userType;
    @Setter
    private Boolean isLoggedIn;
}
