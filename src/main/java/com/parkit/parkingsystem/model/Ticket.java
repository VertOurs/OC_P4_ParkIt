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
        if (inTime != null) {
            return new Date(inTime.getTime());
        } else {
            return null;
        }
    }

    /**
     * setter.
     * @param inTimeField
     */
    public void setInTime(final Date inTimeField) {
        if (inTimeField != null) {
            this.inTime = new Date(inTimeField.getTime());
        } else {
            this.inTime = null;
        }
    }

    /**
     * getter.
     * @return outTime
     */
    public Date getOutTime() {
        if (outTime != null) {
            return new Date(outTime.getTime());
        } else {
            return null;
        }
    }

    /**
     * setter.
     * @param outTimeField
     */
    public void setOutTime(final Date outTimeField) {
        if (outTimeField != null) {
            this.outTime = new Date(outTimeField.getTime());
        } else {
            this.outTime = null;
        }
    }
}
