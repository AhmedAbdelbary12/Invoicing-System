package com.invoicing.DB;

import com.invoicing.Utils.Logs.LogsManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import static com.invoicing.Utils.DataManagement.PropertyReader.getProperty;

public class DBManager {
    private static Connection connection;

    public static void connect() {
        try {
            connection = DriverManager.getConnection(
                    getProperty("db.url"),
                    getProperty("db.userName"),
                    getProperty("db.password")
            );
            LogsManager.info("DB Connected Successfully");
        } catch (Exception e) {
            LogsManager.error("DB Connection Failed: " + e.getMessage());
        }
        // SQL Server

    }

    public static void deleteRole(String roleName) {
        try {
            String query = "DELETE FROM [Invoicing].[adb].[AspNetRoles] WHERE Name = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, roleName);
            int rows = stmt.executeUpdate();
            LogsManager.debug("Deleted Role: " + roleName + " | Rows affected: " + rows);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteOrgInfo(String OrgName) {
        try {
            String query = "Delete From [Invoicing].[platform].[Tenants] where DisplayName = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, OrgName);
            int rows = stmt.executeUpdate();
            LogsManager.debug("Deleted OrgName: " + OrgName + " | Rows affected: " + rows);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                LogsManager.info("DB Connection Closed");
            }
        } catch (Exception e) {
            LogsManager.error("Failed to close DB: " + e.getMessage());
        }
    }
}
