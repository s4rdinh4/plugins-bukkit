package br.com.sgcraft.playerlogger.mysql;

import br.com.sgcraft.playerlogger.config.*;
import java.sql.*;

public class addData
{
    public static void add(final String playername, final String type, final String data, final double x, final double y, final double z, final String worldname, final Boolean staff) {
        PreparedStatement pst = null;
        Connection con = null;
        final long time = System.currentTimeMillis() / 1000L;
        if ((!staff || !getConfig.LogOnlyStaff()) && getConfig.LogOnlyStaff()) {
            return;
        }
        try {
            con = DriverManager.getConnection("jdbc:mysql://" + getConfig.MySQLServer() + "/" + getConfig.MySQLDatabase(), getConfig.MySQLUser(), getConfig.MySQLPassword());
            final String database = "playerlogger";
            pst = con.prepareStatement("INSERT INTO playerlogger(playername, type, time, data, x, y, z, world) VALUES(?, ?, ?, ?, ?, ?, ?, ?)");
            pst.setString(1, playername);
            pst.setString(2, type);
            pst.setLong(3, time);
            pst.setString(4, data);
            pst.setDouble(5, x);
            pst.setDouble(6, y);
            pst.setDouble(7, z);
            pst.setString(8, worldname);
            pst.executeUpdate();
        }
        catch (SQLException ex) {
            System.out.print(ex);
        }
    }
}
