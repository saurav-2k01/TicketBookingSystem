package biz.dss.ticketbookingsystem.Ui;

import biz.dss.ticketbookingsystem.controller.AuthenticationController;
import biz.dss.ticketbookingsystem.view.InputView;
import biz.dss.ticketbookingsystem.view.TrainView;
import biz.dss.ticketbookingsystem.view.UserView;

public class AdminUI extends AbstractUI {
    public AdminUI(AuthenticationController authenticationController, InputView inputView, UserView userView, TrainView trainView) {
        super(authenticationController, inputView, userView, trainView);
    }

    public void adminUI(){

    }
}
