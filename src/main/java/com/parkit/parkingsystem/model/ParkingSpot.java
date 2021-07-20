package com.parkit.parkingsystem.model;

import com.parkit.parkingsystem.constants.ParkingType;

public class ParkingSpot {
    /**
     * private attribute number.
     */
    private int number;
    /**
     * Private attribute parkingType.
     */
    private ParkingType parkingType;
    /**
     * Private attribute isAvailable.
     */
    private boolean isAvailable;

    /**
     * constructor of ParkingSpot.
     * @param numberField
     * @param parkingTypeField
     * @param isAvailableField
     */
    public ParkingSpot(final int numberField,
                       final ParkingType parkingTypeField,
                       final boolean isAvailableField) {
        this.number = numberField;
        this.parkingType = parkingTypeField;
        this.isAvailable = isAvailableField;
    }

    /**
     * getter.
     * @return ID
     */
    public int getId() {
        return number;
    }

    /**
     * setter.
     * @param numberField
     */
    public void setId(final int numberField) {
        this.number = numberField;
    }

    /**
     * getter.
     * @return parkingType
     */
    public ParkingType getParkingType() {
        return parkingType;
    }

    /**
     * setter.
     * @param parkingTypeField
     */
    public void setParkingType(final ParkingType parkingTypeField) {
        this.parkingType = parkingTypeField;
    }

    /**
     * Getter.
     * @return is available
     */
    public boolean isAvailable() {
        return isAvailable;
    }

    /**
     * Setter.
     * @param availableField
     */
    public void setAvailable(final boolean availableField) {
        isAvailable = availableField;
    }

    /**
     * allow to change equals() method.
     * @param o
     * @return an equality if this == o
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ParkingSpot that = (ParkingSpot) o;
        return number == that.number;
    }
    /**
     * allow to return number in the hasCode().
     */
    @Override
    public int hashCode() {
        return number;
    }
}
