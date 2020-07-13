package br.com.sgcraft.sglogger.mysql;

import java.sql.*;

import br.com.sgcraft.sglogger.config.*;

public class mysql
{
    public static void createDatabase() {
        if (getConfig.MySQLEnabled()) {
            Connection connection = null;
            Statement st = null;
            int rs = 0;
            try {
                connection = DriverManager.getConnection("jdbc:mysql://" + getConfig.MySQLServer() + "/" + getConfig.MySQLDatabase(), getConfig.MySQLUser(), getConfig.MySQLPassword());
                st = connection.createStatement();
                rs = st.executeUpdate("CREATE TABLE IF NOT EXISTS `SgLogger`( `id` MEDIUMINT NOT NULL AUTO_INCREMENT, `playername` text, `type` text, `time` INT(255), `data` text, `x` MEDIUMINT(255), `y` MEDIUMINT(255), `z` MEDIUMINT(255), `world` text, PRIMARY KEY (`id`))");
            }
            catch (SQLException e) {
                e.printStackTrace();
                System.out.print(rs);
            }
            finally {
                try {
                    if (st != null) {
                        st.close();
                    }
                    if (connection != null) {
                        connection.close();
                    }
                }
                catch (SQLException ex) {}
            }
            try {
                if (st != null) {
                    st.close();
                }
                if (connection != null) {
                    connection.close();
                }
            }
            catch (SQLException ex2) {}
        }
    }
}
