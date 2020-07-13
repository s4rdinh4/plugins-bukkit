package br.com.sgcraft.gladiador.utils;

import java.io.*;
import java.sql.*;
import org.bukkit.entity.*;

import br.com.sgcraft.gladiador.*;

public class SQLite
{
    private String user;
    private String password;
    private String database;
    private String host;
    private Connection connection;
    private Statement stmt;
    public static Main pl;
    
    static {
        SQLite.pl = Main.pl;
    }
    
    public SQLite() {
        try {
            Class.forName("org.sqlite.JDBC");
            this.connection = DriverManager.getConnection("jdbc:sqlite:" + SQLite.pl.getDataFolder().getAbsolutePath() + File.separator + "database.db");
            (this.stmt = this.connection.createStatement()).execute("CREATE TABLE IF NOT EXISTS eventos (wins INTEGER, clan VARCHAR(255))");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e2) {
            e2.printStackTrace();
        }
    }
    
    public void addNew(final String clan, final int wins) {
        try {
            Class.forName("org.sqlite.JDBC");
            final String sql = "INSERT INTO eventos (clan, wins) VALUES ('" + clan + "', '" + wins + "');";
            this.stmt.executeUpdate(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void updateWins(final String clan, final int wins) {
        try {
            Class.forName("org.sqlite.JDBC");
            final String sql = "UPDATE eventos SET wins='" + (this.getWins(clan) + wins) + "' WHERE clan='" + clan + "';";
            this.stmt.executeUpdate(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public int getWins(final String clan) {
        try {
            Class.forName("org.sqlite.JDBC");
            final String sql = "SELECT wins FROM eventos WHERE clan='" + clan + "';";
            final ResultSet rs = this.stmt.executeQuery(sql);
            if (rs.next()) {
                return rs.getInt("wins");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    public boolean hasClan(final String clan) {
        try {
            Class.forName("org.sqlite.JDBC");
            final String sql = "SELECT * FROM eventos WHERE clan='" + clan + "'";
            final ResultSet rs = this.stmt.executeQuery(sql);
            return rs.next() && rs.getString("clan").equalsIgnoreCase(clan);
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public void getTOPWins(final Player p) {
        try {
            p.sendMessage(SQLite.pl.getConfig().getString("Top_Vencedores").replace("&", "§"));
            Class.forName("org.sqlite.JDBC");
            final String sql = "SELECT * FROM eventos ORDER BY wins DESC LIMIT 10";
            final ResultSet rs = this.stmt.executeQuery("SELECT * FROM eventos ORDER BY wins DESC LIMIT 10");
            int i = 0;
            while (rs.next()) {
                if (++i == 0) {
                    p.sendMessage("§6[Gladiador] §cNenhum clan ganhou o gladiador ainda!");
                }
                else {
                    p.sendMessage(SQLite.pl.getConfig().getString("Top_Vencedores_Posicao").replace("&", "§").replace("@posicao", new StringBuilder(String.valueOf(i)).toString()).replace("@clan", rs.getString("clan")).replace("@vitorias", new StringBuilder(String.valueOf(rs.getInt("wins"))).toString()));
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void addWinnerPoint(final String clan) {
        if (this.hasClan(clan)) {
            this.updateWins(clan, 1);
        }
        else {
            this.addNew(clan, 1);
        }
    }
    
    public void purgeRows() {
        try {
            Class.forName("org.sqlite.JDBC");
            final String sql = "DELETE FROM eventos;";
            this.stmt.executeUpdate("DELETE FROM eventos;");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public String getUser() {
        return this.user;
    }
    
    public void setUser(final String user) {
        this.user = user;
    }
    
    public String getPassword() {
        return this.password;
    }
    
    public void setPassword(final String password) {
        this.password = password;
    }
    
    public String getDatabase() {
        return this.database;
    }
    
    public void setDatabase(final String database) {
        this.database = database;
    }
    
    public String getHost() {
        return this.host;
    }
    
    public void setHost(final String host) {
        this.host = host;
    }
    
    public Connection getConnection() {
        return this.connection;
    }
    
    public void setConnection(final Connection connection) {
        this.connection = connection;
    }
    
    public Statement getStmt() {
        return this.stmt;
    }
    
    public void setStmt(final Statement stmt) {
        this.stmt = stmt;
    }
}
