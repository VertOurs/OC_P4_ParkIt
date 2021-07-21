package com.parkit.parkingsystem.dao;

import com.parkit.parkingsystem.config.DataBaseConfig;
import com.parkit.parkingsystem.constants.DBConstants;
import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class TicketDAO {

    /**
     * @see Logger
     */
    private static final Logger LOGGER = LogManager.getLogger("TicketDAO");
    /**
     * @see DataBaseConfig
     */
    private DataBaseConfig dataBaseConfig = new DataBaseConfig();

    /**
     * getter.
     * @return dataBaseConfig
     */
    public DataBaseConfig getDataBaseConfig() {
        return dataBaseConfig;
    }

    /**
     * setter.
     * @param dataBaseConfigField
     */
    public void setDataBaseConfig(final DataBaseConfig dataBaseConfigField) {
        this.dataBaseConfig = dataBaseConfigField;
    }

    /**
     * allow to convert date.
     * @param dateToConvert
     * @return a timestamp
     */
    private Timestamp getTimeStamp(final Date dateToConvert) {
        final LocalDateTime hour = dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        return Timestamp.valueOf(hour);

    }

    /**
     * allow to save ticket.
     * @param ticket
     * @return a new ticket
     */
    public boolean saveTicket(final Ticket ticket) {
        Connection con = null;
        try {
            con = dataBaseConfig.getConnection();
            PreparedStatement ps = con.prepareStatement(
                    DBConstants.SAVE_TICKET);
             int parameterIndex = 1;
            ps.setInt(parameterIndex++, ticket.getParkingSpot().getId());
            ps.setString(parameterIndex++, ticket.getVehicleRegNumber());
            ps.setDouble(parameterIndex++, ticket.getPrice());
            ps.setTimestamp(parameterIndex++, getTimeStamp(ticket.getInTime()));
            ps.setTimestamp(parameterIndex++, (ticket.getOutTime() == null)
                    ? null : (new Timestamp(ticket.getOutTime().getTime())));
            return ps.execute();
        } catch (Exception ex) {
            LOGGER.error("Error fetching next available slot", ex);
        } finally {
            dataBaseConfig.closeConnection(con);
            return false;
        }
    }

    /**
     * allow to get ticket.
     * @param vehicleRegNumber
     * @return a ticket
     */
    public Ticket getTicket(final String vehicleRegNumber) {
        Connection con = null;
        Ticket ticket = null;
        try {
            con = dataBaseConfig.getConnection();
            PreparedStatement ps = con.prepareStatement(DBConstants.GET_TICKET);
            //ID, PARKING_NUMBER, VEHICLE_REG_NUMBER, PRICE, IN_TIME, OUT_TIME)
            ps.setString(1, vehicleRegNumber);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int columnIndex = 1;
                int columnIndexValueOf = 6;
                ticket = new Ticket();
                ParkingSpot parkingSpot = new ParkingSpot(
                        rs.getInt(columnIndex++), ParkingType.valueOf(
                        rs.getString((columnIndexValueOf))), false);
                ticket.setParkingSpot(parkingSpot);
                ticket.setId(rs.getInt(columnIndex++));
                ticket.setVehicleRegNumber(vehicleRegNumber);
                ticket.setPrice(rs.getDouble(columnIndex++));
                ticket.setInTime(rs.getTimestamp(columnIndex++));
                ticket.setOutTime(rs.getTimestamp(columnIndex++));
            }
            dataBaseConfig.closeResultSet(rs);
            dataBaseConfig.closePreparedStatement(ps);
        } catch (Exception ex) {
            LOGGER.error("Error fetching next available slot", ex);
        } finally {
            dataBaseConfig.closeConnection(con);
            return ticket;
        }
    }

    /**
     * allow to update ticket.
     * @param ticket
     * @return the value of the Ticket in DB
     */
    public boolean updateTicket(final Ticket ticket) {
        Connection con = null;
        try {
            int parameterIndex = 1;
            con = dataBaseConfig.getConnection();
            PreparedStatement ps = con.prepareStatement(
                    DBConstants.UPDATE_TICKET);
            ps.setDouble(parameterIndex++, ticket.getPrice());
            ps.setTimestamp(parameterIndex++, new Timestamp(
                    ticket.getOutTime().getTime()));
            ps.setInt(parameterIndex++, ticket.getId());
            ps.execute();
            return true;
        } catch (Exception ex) {
            LOGGER.error("Error saving ticket info", ex);
        } finally {
            dataBaseConfig.closeConnection(con);
        }
        return false;
    }

    /**
     * allows to count Ticket.
     * @param vehicleRegNumber
     * @return en integer
     */
    public int countTicket(final String vehicleRegNumber) {
        Connection con = null;
        int ticket = 0;
        try {
            con = dataBaseConfig.getConnection();
            PreparedStatement ps = con.prepareStatement(
                    DBConstants.COUNT_TICKET);
            //ID, PARKING_NUMBER, VEHICLE_REG_NUMBER, PRICE, IN_TIME, OUT_TIME)
            ps.setString(1, vehicleRegNumber);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ticket = rs.getInt(1);
            }
            dataBaseConfig.closeResultSet(rs);
            dataBaseConfig.closePreparedStatement(ps);
        } catch (Exception ex) {
            LOGGER.error("Error count ticket", ex);
        } finally {
            dataBaseConfig.closeConnection(con);
            return ticket;
        }
    }
}
