package com.parkit.parkingsystem.model;

import java.util.Date;

public class Ticket {
    /**
     * private attribute id.
     */
    private int id;
    /**
     * private attribute parkingSpot.
     */
    private ParkingSpot parkingSpot;
    /**
     * private attribute vehicleRegNumber.
     */
    private String vehicleRegNumber;
    /**
     * private attribute price.
     */
    private double price;
    /**
     * private attribute inTime.
     */
    private Date inTime;
    /**
     * private attribute outTime.
     */
    private Date outTime;

    /**
     * getter.
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * setter.
     * @param idField
     */
    public void setId(final int idField) {
        this.id = idField;
    }

    /**
     * getter.
     * @return parkingSpot
     */
    public ParkingSpot getParkingSpot() {
        return parkingSpot;
    }

    /**
     * setter.
     * @param parkingSpotField
     */
    public void setParkingSpot(final ParkingSpot parkingSpotField) {
        this.parkingSpot = parkingSpotField;
    }

    /**
     * getter.
     * @return vehicleRegNumber
     */
    public String getVehicleRegNumber() {
        return vehicleRegNumber;
    }

    /**
     * setter.
     * @param vehicleRegNumberField
     */
    public void setVehicleRegNumber(final String vehicleRegNumberField) {
        this.vehicleRegNumber = vehicleRegNumberField;
    }

    /**
     * getter.
     * @return price
     */
    public double getPrice() {
        return price;
    }

    /**
     * setter.
     * @param priceField
     */
    public void setPrice(final double priceField) {
        this.price = priceField;
    }

    /**
     * getter.
     * @return inTime
     */
    public Date getInTime() {
        return inTime;
    }

    /**
     * setter.
     * @param inTimeField
     */
    public void setInTime(final Date inTimeField) {
        this.inTime = inTimeField;
    }

    /**
     * getter.
     * @return outTime
     */
    public Date getOutTime() {
        return outTime;
    }

    /**
     * setter.
     * @param outTimeField
     */
    public void setOutTime(final Date outTimeField) {
        this.outTime = outTimeField;
    }
}
