package com.invoicing.DB;

import com.invoicing.Utils.Logs.LogsManager;

import java.sql.PreparedStatement;

public class DBRoleManager extends DBManager {
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
}
