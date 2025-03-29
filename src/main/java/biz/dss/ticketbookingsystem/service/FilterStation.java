package biz.dss.ticketbookingsystem.service;

import biz.dss.ticketbookingsystem.utils.Response;

public interface FilterStation {
    Response getStationByName(String name);
    Response getStations();
}
