package com.parkit.parkingsystem.dao;

import com.parkit.parkingsystem.config.DataBaseConfig;
import com.parkit.parkingsystem.constants.DBConstants;
import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.model.ParkingSpot;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ParkingSpotDAO {
    /**
     * @see Logger
     */
    private static final Logger LOGGER = LogManager.getLogger("ParkingSpotDAO");

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
     * allow to get the Next Available Slot.
     * @param parkingType
     * @return an Integer that represents the next available spot
     */
    public int getNextAvailableSlot(final ParkingType parkingType) {
        Connection con = null;
        int result = -1;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = dataBaseConfig.getConnection();
            ps = con.prepareStatement(
                    DBConstants.GET_NEXT_PARKING_SPOT);
            ps.setString(1, parkingType.toString());
            rs = ps.executeQuery();
            if (rs.next()) {
                result = rs.getInt(1);
            }
            dataBaseConfig.closeResultSet(rs);
            dataBaseConfig.closePreparedStatement(ps);

        } catch (Exception ex) {
            LOGGER.error("Error fetching next available slot", ex);
        } finally {
            dataBaseConfig.closeResultSet(rs);
            dataBaseConfig.closePreparedStatement(ps);
            dataBaseConfig.closeConnection(con);

        }
        return result;
    }

    /**
     * allows to update parkingSpot.
     * @param parkingSpot
     * @return an updated ParkingSpot
     */
    public boolean updateParking(final ParkingSpot parkingSpot) {
        //update the availability fo that parking slot
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = dataBaseConfig.getConnection();
            ps = con.prepareStatement(
                    DBConstants.UPDATE_PARKING_SPOT);
            ps.setBoolean(1, parkingSpot.isAvailable());
            ps.setInt(2, parkingSpot.getId());
            int updateRowCount = ps.executeUpdate();
            dataBaseConfig.closePreparedStatement(ps);
            return (updateRowCount == 1);
        } catch (Exception ex) {
            LOGGER.error("Error updating parking info", ex);
            return false;
        } finally {
            dataBaseConfig.closePreparedStatement(ps);
            dataBaseConfig.closeConnection(con);
        }
    }

}
