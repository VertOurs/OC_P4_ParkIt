package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.Duration;
import java.util.Date;



public class FareCalculatorService {
    /**
     * Allow to convert Date in LocalDateTime.
     * @param dateToConvert
     * @return an localDateTime
     */
    private LocalDateTime convertLocalDateTime(final Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    /**
     * Allow calculate the pourcentage of discount.
     * @param total
     * @param remise
     * @return poucentage of discount
     */
    public static double calculatePercentage(final double total,
                                             final double remise) {

        final int cent = 100;
        return total *  remise / cent;
    }

    /**
     * Allow to calculate the price of the ticket with 5% features
     * and 30 minutes free.
     * @param ticket
     * @param count
     */
    private void calculate(final Ticket ticket, final int count) {
        if ((ticket.getOutTime() == null) || (ticket.getOutTime().before(
                ticket.getInTime()))) {
            throw new IllegalArgumentException("Out time provided is incorrect:"
                    + ticket.getOutTime().toString());
        }
        LocalDateTime inHour = convertLocalDateTime(ticket.getInTime());
        LocalDateTime outHour = convertLocalDateTime(ticket.getOutTime());
        double duration = Duration.between(inHour, outHour).getSeconds();
        double durationThirtyMinutes = duration;

        final int freeMinutes = 1800;
        if (duration < freeMinutes) {
            durationThirtyMinutes = 0;
        }

        double carRateWithDiscount = Fare.CAR_RATE_PER_HOUR;
        double bikeRateWithDiscount = Fare.BIKE_RATE_PER_HOUR;

        if (count > 0) {
            carRateWithDiscount =  (Fare.CAR_RATE_PER_HOUR
                    - calculatePercentage(Fare.CAR_RATE_PER_HOUR,
                            Fare.DISCOUNT_FOR_OLD_CUSTOMER));
            bikeRateWithDiscount = (Fare.BIKE_RATE_PER_HOUR
                    - calculatePercentage(Fare.BIKE_RATE_PER_HOUR,
                            Fare.DISCOUNT_FOR_OLD_CUSTOMER));
        }

        final int oneHour = 3600;
        switch (ticket.getParkingSpot().getParkingType()) {
            case CAR:
                ticket.setPrice((durationThirtyMinutes /oneHour) * carRateWithDiscount);

                break;

            case BIKE:
                ticket.setPrice((durationThirtyMinutes / oneHour) * bikeRateWithDiscount);
                break;

            default: throw new IllegalArgumentException("Unkown Parking Type");
        }
    }

    /**
     * method for use Calculate method.
     * @param ticket
     */
    public void calculateFare(final Ticket ticket) {

        calculate(ticket, 0);
    }

    /**
     * surcharged method for use Calculate method.
     * @param ticket
     * @param count
     */
    public void calculateFare(final Ticket ticket, final int count) {
        calculate(ticket, count);
    }
}
