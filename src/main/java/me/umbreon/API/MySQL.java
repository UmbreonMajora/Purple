package me.umbreon.API;

import java.sql.*;


public class MySQL {
    private static String HOST = "";
    private static String DATABASE = "";
    private static String USER = "";
    private static String PASSWORD = "";
    public static Connection con;

    public static void connect() {
        try {
            con = DriverManager.getConnection("jdbc:mysql://" + HOST + ":3306/" + DATABASE + "?autoReconnect=true", USER, PASSWORD);
            System.out.println("[MySQL] Die Verbindung zum MySQL Server wurde hergestellt.");
        } catch (SQLException e) {
            System.out.println("[MySQL] Die Verbindung zum MySQL Server ist fehlgeschlagen.\n" + e.getMessage());
            e.printStackTrace();
        }
    }
    public static void close( ) {
        try {
            if(con != null) {
                con.close();
                System.out.println("[MySQL] Die Verbindung zum MySQL Server wurde beendet.");
            }
        } catch (SQLException e) {
            System.out.println("[MySQL] Das beenden der Verbindung von MySQL ist fehlgeschagen.\n" + e.getMessage());
        }
    }
    public static void update(String qry) {
        try {
            Statement st = con.createStatement();
            st.executeUpdate(qry);
            st.close();
        } catch (SQLException e) {
            connect();

        }

    }
    public ResultSet query(String qry) {
        ResultSet rs = null;
        try {
            Statement st = con.createStatement();
            rs = st.executeQuery(qry);
        } catch (SQLException e) {
            connect();
            System.err.println(e);
        }
        return rs;
    }
    public static void setLogChannel(String GuildID){
        try {
            PreparedStatement st = MySQL.con.prepareStatement("SELECT LogChannel WHERE GuildID = ?");
            st.setString(1, GuildID);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public static String checkGuild(String GuildID){
        try {
            PreparedStatement st = MySQL.con.prepareStatement("SELECT GuildID FROM Guilds WHERE GuildID = ?");
            st.setString(1, GuildID);
        } catch (SQLException e) {
            e.printStackTrace();
        } return checkGuild(GuildID);
    }
    public static void setGuild(String GuildID, String language){
        try {
            PreparedStatement st = MySQL.con.prepareStatement("INSERT INTO Guilds (GuildID, Language) VALUES (?, ?)");
            st.setString(1, GuildID);
            st.setString(2, language);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void disableCommand(String GuildID, String Command){
        try {
            PreparedStatement st = MySQL.con.prepareStatement("INSERT INTO Guilds WHERE GuildID = ? (?) VALUES (0)");
            st.setString(1, GuildID);
            st.setString(2, Command);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public static void enableCommand(String GuildID, String Command){
        try {
            PreparedStatement st = MySQL.con.prepareStatement("INSERT INTO Guilds WHERE GuildID = ? (?) VALUES (1)");
            st.setString(1, GuildID);
            st.setString(2, Command);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static String checkCommand(String GuildID, String Command){
        try {
            PreparedStatement st = MySQL.con.prepareStatement("SELECT ? FROM Guilds WHERE GuildID = ?");
            st.setString(1, Command);
            st.setString(2, GuildID);
        } catch (SQLException e) {
            e.printStackTrace();
        } return checkCommand(GuildID, Command);
    }
}
