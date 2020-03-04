package de.famoushistory.EconomySystem.config;

import java.sql.*;

public class DatabaseManager {

    String url = "88.99.35.207";
    String user = "keys";
    String password = "muE^7*QgBa%x";
    Connection myCon;

    {
        try {
            myCon = DriverManager.getConnection(url, user, password);
            Statement myStmt = myCon.createStatement();
            String sql = "select * from mydb.Contacts";
            ResultSet rs = myStmt.executeQuery(sql);

            while(rs.next()) {
                System.out.println(rs.getString("name"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
