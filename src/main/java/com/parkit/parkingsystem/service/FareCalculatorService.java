package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;
import java.time.*;
import java.util.Date;
import java.sql.*;


public class FareCalculatorService {

    private LocalDateTime convertToLocalDateTimeViaInstant(Date dateToConvert) {
            ZoneId a = ZoneId.systemDefault();

        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    public static double calculatePercentage(double total, double remise) {
        return total *  remise/100;
    }

    public void calculateFare(Ticket ticket){
        if( (ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime())) ){
            throw new IllegalArgumentException("Out time provided is incorrect:"+ticket.getOutTime().toString());
        }

            LocalDateTime inHour = convertToLocalDateTimeViaInstant(ticket.getInTime());
            LocalDateTime outHour = convertToLocalDateTimeViaInstant(ticket.getOutTime());
            long duration = Duration.between(inHour, outHour).toMinutes();
            long durationThirtyMinutes = duration;

           // features  free 30 minutes--------------------------------------------------------
            if(duration < 30) {
                duration = 0;
            }

            //features  5%-----------------------------------------------------------------------
            long carRateWithDiscount = (long) Fare.CAR_RATE_PER_HOUR;
            long bikeRateWithDiscount = (long) Fare.BIKE_RATE_PER_HOUR;

             //if(){
        carRateWithDiscount = (long) (Fare.CAR_RATE_PER_HOUR - calculatePercentage(Fare.CAR_RATE_PER_HOUR, Fare.DISCOUNT_FOR_OLD_CUSTOMER));
        bikeRateWithDiscount = (long) (Fare.BIKE_RATE_PER_HOUR - calculatePercentage(Fare.BIKE_RATE_PER_HOUR, Fare.DISCOUNT_FOR_OLD_CUSTOMER));
               // }
            // le switch
        switch (ticket.getParkingSpot().getParkingType()){
            case CAR: {
                ticket.setPrice((durationThirtyMinutes * carRateWithDiscount)/60);

                break;
            }
            case BIKE: {
                ticket.setPrice((durationThirtyMinutes * bikeRateWithDiscount)/60);
                break;
            }
            default: throw new IllegalArgumentException("Unkown Parking Type");
        }
    }
}