package com.parkit.parkingsystem.model;

import com.parkit.parkingsystem.constants.ParkingType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.parkit.parkingsystem.model.ParkingSpot;

import static org.junit.jupiter.api.Assertions.*;

class ParkingSpotTest {

    private ParkingSpot parkingSpot;

    @BeforeEach
    private void setUpPerTest() {
        parkingSpot = new ParkingSpot(2, ParkingType.BIKE,true);
    }


    @Test
    void getId() {
        assertEquals(2,parkingSpot.getId());
    }

    @Test
    void setId() {
        parkingSpot.setId(5);
        assertEquals(5,parkingSpot.getId());
    }

    @Test
    void getParkingType() {
        assertEquals(ParkingType.BIKE,parkingSpot.getParkingType());
    }

    @Test
    void setParkingType() {
        parkingSpot.setParkingType(ParkingType.CAR);
        assertEquals(ParkingType.CAR, parkingSpot.getParkingType());
    }

    @Test
    void isAvailable() {
        assertEquals(true, parkingSpot.isAvailable());
    }

    @Test
    void setAvailable() {
        parkingSpot.setAvailable(false);
        assertEquals(false, parkingSpot.isAvailable());
    }

    @Test
    void testEquals() {
    ParkingSpot parkingSpot1 = new ParkingSpot(1,ParkingType.BIKE,true);
    ParkingSpot parkingSpot2 = parkingSpot1;
    assertEquals(parkingSpot1,parkingSpot2);
    ParkingSpot parkingSpot3 = new ParkingSpot(1,ParkingType.BIKE,true);
    assertEquals(parkingSpot1,parkingSpot3);
    ParkingSpot parkingSpot4 = new ParkingSpot(2,ParkingType.CAR,true);
    assertNotEquals(parkingSpot1,parkingSpot4);
    }


}