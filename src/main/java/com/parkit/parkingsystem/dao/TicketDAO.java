package com.parkit.parkingsystem.dao;

import com.parkit.parkingsystem.config.DataBaseConfig;
import com.parkit.parkingsystem.constants.DBConstants;
import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.parkit.parkingsystem.constants.Index;

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
        PreparedStatement ps = null;
        try {
            con = dataBaseConfig.getConnection();
            ps = con.prepareStatement(
                    DBConstants.SAVE_TICKET);

            ps.setInt(Index.PARAMETER_INDEX_ONE,
                    ticket.getParkingSpot().getId());
            ps.setString(Index.PARAMETER_INDEX_TWO,
                    ticket.getVehicleRegNumber());
            ps.setDouble(Index.PARAMETER_INDEX_THREE, ticket.getPrice());
            ps.setTimestamp(Index.PARAMETER_INDEX_FOUR,
                    getTimeStamp(ticket.getInTime()));
            ps.setTimestamp(Index.PARAMETER_INDEX_FIVE,
                    (ticket.getOutTime() == null)
                    ? null : (new Timestamp(ticket.getOutTime().getTime())));
            boolean returnPsExecute = ps.execute();
            return returnPsExecute;
        } catch (Exception ex) {
            LOGGER.error("Error fetching next available slot", ex);
        } finally {

            dataBaseConfig.closePreparedStatement(ps);
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
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = dataBaseConfig.getConnection();
            ps = con.prepareStatement(DBConstants.GET_TICKET);
            ps.setString(Index.PARAMETER_INDEX_ONE, vehicleRegNumber);
            rs = ps.executeQuery();
            if (rs.next()) {
                ticket = new Ticket();
                ParkingSpot parkingSpot = new ParkingSpot(
                        rs.getInt(Index.COLUMN_INDEX_ONE),
                        ParkingType.valueOf(
                                rs.getString(Index.COLUMN_INDEX_SIX)),
                        false);
                ticket.setParkingSpot(parkingSpot);
                ticket.setId(rs.getInt(Index.COLUMN_INDEX_TWO));
                ticket.setVehicleRegNumber(vehicleRegNumber);
                ticket.setPrice(rs.getDouble(Index.COLUMN_INDEX_THREE));
                ticket.setInTime(rs.getTimestamp(Index.COLUMN_INDEX_FOUR));
                ticket.setOutTime(rs.getTimestamp(Index.COLUMN_INDEX_FIVE));
            }
            dataBaseConfig.closeResultSet(rs);
            dataBaseConfig.closePreparedStatement(ps);
        } catch (Exception ex) {
            LOGGER.error("Error fetching next available slot", ex);
        } finally {
            dataBaseConfig.closeResultSet(rs);
            dataBaseConfig.closePreparedStatement(ps);
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
        PreparedStatement ps = null;
        try {
            con = dataBaseConfig.getConnection();
            ps = con.prepareStatement(
                    DBConstants.UPDATE_TICKET);
            ps.setDouble(Index.PARAMETER_INDEX_ONE, ticket.getPrice());
            ps.setTimestamp(Index.PARAMETER_INDEX_TWO, new Timestamp(
                    ticket.getOutTime().getTime()));
            ps.setInt(Index.PARAMETER_INDEX_THREE, ticket.getId());
            ps.execute();

            return true;
        } catch (Exception ex) {
            LOGGER.error("Error saving ticket info", ex);
        } finally {
            dataBaseConfig.closePreparedStatement(ps);
            dataBaseConfig.closeConnection(con);
        }
        return false;
    }

    /**
     * allow to Update inTime.
     * @param inTime
     * @param id
     * @return the value of InTime in DB.
     */
    public boolean updateInTime(final Date inTime, final int id) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = dataBaseConfig.getConnection();
            ps = con.prepareStatement(
                    DBConstants.UPDATE_INTIME);

            ps.setTimestamp(Index.PARAMETER_INDEX_ONE, new Timestamp(
                    inTime.getTime()));
            ps.setInt(Index.PARAMETER_INDEX_TWO, id);
            ps.execute();

            return true;
        } catch (Exception ex) {
            LOGGER.error("Error saving ticket info", ex);
        } finally {
            dataBaseConfig.closePreparedStatement(ps);
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
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = dataBaseConfig.getConnection();
            ps = con.prepareStatement(
                    DBConstants.COUNT_TICKET);
            //ID, PARKING_NUMBER, VEHICLE_REG_NUMBER, PRICE, IN_TIME, OUT_TIME)
            ps.setString(Index.PARAMETER_INDEX_ONE, vehicleRegNumber);
            rs = ps.executeQuery();
            if (rs.next()) {
                ticket = rs.getInt(Index.COLUMN_INDEX_ONE);
            }

        } catch (Exception ex) {
            LOGGER.error("Error count ticket", ex);
        } finally {
            dataBaseConfig.closeResultSet(rs);
            dataBaseConfig.closePreparedStatement(ps);
            dataBaseConfig.closeConnection(con);
            return ticket;
        }
    }
}
