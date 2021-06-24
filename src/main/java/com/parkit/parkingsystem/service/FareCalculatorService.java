package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;
import java.time.*;
import java.util.Date;


public class FareCalculatorService {

    private LocalDateTime convertToLocalDateTimeViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    public void calculateFare(Ticket ticket){
        if( (ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime())) ){
            throw new IllegalArgumentException("Out time provided is incorrect:"+ticket.getOutTime().toString());
        }

        LocalDateTime inHour = convertToLocalDateTimeViaInstant(ticket.getInTime());
        LocalDateTime outHour = convertToLocalDateTimeViaInstant(ticket.getOutTime());


        //TODO: Some tests are failing here. Need to check if this logic is correct

        long duration = Duration.between(inHour, outHour).toMinutes();

        switch (ticket.getParkingSpot().getParkingType()){
            case CAR: {
                ticket.setPrice((duration * Fare.CAR_RATE_PER_HOUR)/60);
                break;
            }
            case BIKE: {
                ticket.setPrice((duration * Fare.BIKE_RATE_PER_HOUR)/60);
                break;
            }
            default: throw new IllegalArgumentException("Unkown Parking Type");
        }
    }
}