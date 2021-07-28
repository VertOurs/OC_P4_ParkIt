package com.parkit.parkingsystem.constants;

public final class Fare {
    /**
     * private constructor for avoid "HideUtilityClassConstructor".
     */
    private Fare() {

    }
    /**
     * Bike rate per hour.
     */
    public static final double BIKE_RATE_PER_HOUR = 1.0;
    /**
     * Car rate per hour.
     */
    public static final double CAR_RATE_PER_HOUR = 1.5;
    /**
     * Discount for old customer.
     */
    public static final double DISCOUNT_FOR_OLD_CUSTOMER = 5.0;
    /**
     * Round with two decimals.
     */
    public static final double ROUND_TWO_DECIMALS = 100.0;
}
