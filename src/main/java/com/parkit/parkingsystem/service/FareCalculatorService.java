package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.Duration;
import java.util.Date;



public class FareCalculatorService {

    private LocalDateTime convertLocalDateTime(final Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    public static double calculatePercentage(final double total, final double remise) {

        final int cent = 100;
        return total *  remise / cent;
    }

    private void calculate(final Ticket ticket, final int count) {
        if ((ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime()))) {
            throw new IllegalArgumentException("Out time provided is incorrect:" + ticket.getOutTime().toString());
        }
        LocalDateTime inHour = convertLocalDateTime(ticket.getInTime());
        LocalDateTime outHour = convertLocalDateTime(ticket.getOutTime());
        long duration = Duration.between(inHour, outHour).toMinutes();
        long durationThirtyMinutes = duration;


        // features  free 30 minutes--------------------------------------------------------
        final int freeMinutes = 30;
        if (duration < freeMinutes) {
            durationThirtyMinutes = 0;
        }

        //features  5%-----------------------------------------------------------------------

        double carRateWithDiscount = Fare.CAR_RATE_PER_HOUR;
        double bikeRateWithDiscount = Fare.BIKE_RATE_PER_HOUR;

        if (count > 0) {
            carRateWithDiscount =  (Fare.CAR_RATE_PER_HOUR -
                    calculatePercentage(Fare.CAR_RATE_PER_HOUR, Fare.DISCOUNT_FOR_OLD_CUSTOMER));
            bikeRateWithDiscount = (Fare.BIKE_RATE_PER_HOUR - calculatePercentage(Fare.BIKE_RATE_PER_HOUR, Fare.DISCOUNT_FOR_OLD_CUSTOMER));
        }

        // le switch
        final int oneHour = 60;
        switch (ticket.getParkingSpot().getParkingType()) {
            case CAR: {
                ticket.setPrice((durationThirtyMinutes * carRateWithDiscount) / oneHour);

                break;
            }
            case BIKE: {
                ticket.setPrice((durationThirtyMinutes * bikeRateWithDiscount) / oneHour);
                break;
            }
            default: throw new IllegalArgumentException("Unkown Parking Type");
        }
    }

    public void calculateFare(final Ticket ticket){

        calculate(ticket, 0);
    }
    public void calculateFare(final Ticket ticket, final int count) {
        calculate(ticket, count);
    }
}