package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.util.InputReaderUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;

public class ParkingService {

    /**
     * private attribute logger.
      */
    private static final Logger LOGGER = LogManager.getLogger(
            "ParkingService");
    /**
     * initialisation of FareCalculatorService class.
     */
    private static FareCalculatorService fareCalculatorService =
            new FareCalculatorService();

    /**
     * private attribute inputReaderUtil.
     */
    private InputReaderUtil inputReaderUtil;

    /**
     * private attribute parkingSpotDAO.
     */
    private ParkingSpotDAO parkingSpotDAO;
    /**
     * private attribute ticketDAO.
     */
    private  TicketDAO ticketDAO;

    /**
     * Constructor of ParkingService Class.
     * @param inputReaderUtilField
     * @param parkingSpotDAOField
     * @param ticketDAOField
     */
    public ParkingService(final InputReaderUtil inputReaderUtilField,
                          final ParkingSpotDAO parkingSpotDAOField,
                          final TicketDAO ticketDAOField) {
        this.inputReaderUtil = inputReaderUtilField;
        this.parkingSpotDAO = parkingSpotDAOField;
        this.ticketDAO = ticketDAOField;
    }

    /**
     * allows to create a ticket for a vehicle with all the input data.
     */
    public void processIncomingVehicle() {
        try {
            ParkingSpot parkingSpot = getNextParkingNumberIfAvailable();
            if (parkingSpot != null && parkingSpot.getId() > 0) {
                String vehicleRegNumber = getVehicleRegNumber();
                parkingSpot.setAvailable(false);
                parkingSpotDAO.updateParking(parkingSpot);
                Date inTime = new Date();
                Ticket ticket = new Ticket();
                ticket.setParkingSpot(parkingSpot);
                ticket.setVehicleRegNumber(vehicleRegNumber);
                ticket.setPrice(0);
                ticket.setInTime(inTime);
                ticket.setOutTime(null);
                ticketDAO.saveTicket(ticket);
                System.out.println("Generated Ticket and saved in DB");
                System.out.println("Please park your vehicle in spot number:"
                        + parkingSpot.getId());
                System.out.println("Recorded in-time for vehicle number:"
                        + vehicleRegNumber + " is:" + inTime);
            }
        } catch (Exception e) {
            LOGGER.error("Unable to process incoming vehicle", e);
        }
    }

    /**
     * Allows you to know vehicle reg number.
     * @return the vehicle registration number
     * @throws Exception
     */
    private String getVehicleRegNumber() throws Exception {
        System.out.println("Please type the vehicle registration number"
                + " and press enter key");
        return inputReaderUtil.readVehicleRegistrationNumber();
    }

    /**
     * Allows you to know the next parking number is available.
     * @return the disponiblility of parkingSpot.
     */
    public ParkingSpot getNextParkingNumberIfAvailable() {
        int parkingNumber = 0;
        ParkingSpot parkingSpot = null;
        try {
            ParkingType parkingType = getVehichleType();
            parkingNumber = parkingSpotDAO.getNextAvailableSlot(parkingType);
            if (parkingNumber > 0) {
                parkingSpot = new ParkingSpot(parkingNumber,
                        parkingType,
                        true);
            } else {
                throw new Exception("Error fetching parking number from DB."
                        + " Parking slots might be full");
            }
        } catch (IllegalArgumentException ie) {
            LOGGER.error("Error parsing user input for type of vehicle",
                    ie);
        } catch (Exception e) {
            LOGGER.error("Error fetching next available parking slot",
                    e);
        }
        return parkingSpot;
    }

    /**
     * allows you to know the type of vehicle.
     * @return ParkingType
     */
    private ParkingType getVehichleType() {
        System.out.println("Please select vehicle type from menu");
        System.out.println("1 CAR");
        System.out.println("2 BIKE");
        int input = inputReaderUtil.readSelection();
        switch (input) {
            case 1:
                return ParkingType.CAR;

            case 2:
                return ParkingType.BIKE;

            default:
                System.out.println("Incorrect input provided");
                throw new IllegalArgumentException("Entered input is invalid");

        }
    }

    /**
     * Method that allows you to retrieve the existing ticket and calculate
     * the parking price in order to get the vehicle out of the parking lot.
     */
    public void processExitingVehicle() {
        try {
            String vehicleRegNumber = getVehicleRegNumber();
            Ticket ticket = ticketDAO.getTicket(vehicleRegNumber);
            int count = ticketDAO.countTicket(vehicleRegNumber);
            Date outTime = new Date();
            ticket.setOutTime(outTime);
            fareCalculatorService.calculateFare(ticket, count);

            if (ticketDAO.updateTicket(ticket)) {
                ParkingSpot parkingSpot = ticket.getParkingSpot();
                parkingSpot.setAvailable(true);
                parkingSpotDAO.updateParking(parkingSpot);
                System.out.println("Please pay the parking fare:"
                        + (ticket.getPrice()));
                System.out.println("Recorded out-time for vehicle number:"
                        + ticket.getVehicleRegNumber()
                        + " is:"
                        + outTime);
            } else {
                System.out.println("Unable to update ticket information."
                        + " Error occurred");
            }
        } catch (Exception e) {
            LOGGER.error("Unable to process exiting vehicle", e);
        }
    }
}
