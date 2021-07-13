package com.parkit.parkingsystem.model;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.model.Ticket;
import org.junit.jupiter.api.*;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class TicketTest {

   private Ticket ticket;

   @BeforeEach
    private void setUpPerTest() {
        ticket = new Ticket();
    }


    @Test
    public void checkGetIDReturn0(){
        assertEquals(0,ticket.getId());
    }

    @Test
    public void checkSetIDReturn5(){
        ticket.setId(5);
        assertEquals(5, ticket.getId());
    }

    @Test
    public void checkGetParkingSpot(){
        assertNull(ticket.getParkingSpot());
    }

    @Test
    public void checkSetParkingSpot(){
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE,true);
        ticket.setParkingSpot(parkingSpot);
        assertEquals( parkingSpot, ticket.getParkingSpot());
    }

    @Test
    public void checkGetVehicleRegNumber(){
        assertNull(ticket.getVehicleRegNumber());
    }

    @Test
    public void checkSetVehicleRegNumber(){
        ticket.setVehicleRegNumber("85");
        assertEquals("85",ticket.getVehicleRegNumber());
    }
    @Test
    public void checkGetPrice(){
        assertEquals(0.0, ticket.getPrice());
    }

    @Test
    public void checkSetPrice(){
        ticket.setPrice(5.0);
        assertEquals(5.0, ticket.getPrice());
    }

    @Test
    public void checkGetInTime(){
        assertNull(ticket.getInTime());
    }

    @Test
    public void checkSetInTime(){
        Date a = new Date();
        ticket.setInTime(a);
        assertEquals(a, ticket.getInTime());
    }
    @Test
    public void checkGetOutTime(){
        assertNull(ticket.getOutTime());
    }

    @Test
    public void checkSetOuTime(){
        Date a = new Date();
        ticket.setOutTime(a);
        assertEquals(a, ticket.getOutTime());
    }

}