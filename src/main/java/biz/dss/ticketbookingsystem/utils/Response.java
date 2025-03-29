package biz.dss.ticketbookingsystem.utils;

import com.sun.net.httpserver.Authenticator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import static biz.dss.ticketbookingsystem.utils.ResponseStatus.FAILURE;
import static biz.dss.ticketbookingsystem.utils.ResponseStatus.SUCCESS;

@Getter
@AllArgsConstructor
public class Response {
    private Object data;
    private ResponseStatus status = FAILURE;
    private String message;

    public Response(ResponseStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public Boolean isSuccess(){
        return status.equals(SUCCESS);
    }
}
