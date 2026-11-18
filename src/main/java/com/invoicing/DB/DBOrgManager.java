package com.invoicing.DB;

import com.invoicing.Utils.Logs.LogsManager;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class DBOrgManager extends DBManager {
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


    public static void insertOrgInfo(String OrgName) throws SQLException {
        String query = """
                INSERT INTO [Invoicing].[platform].[Tenants]
                (
                    [Id],
                    [Slug],
                    [DisplayName],
                    [ContactEmail],
                    [TenantEmailDomain],
                    [CreatedAt],
                    [Status],
                    [CreatedBy],
                    [UpdatedAt],
                    [UpdatedBy],
                    [IsDeleted],
                    [DeletedAt],
                    [DeletedBy],
                    [IsSuperAdminTenant],
                    [ContactAddress],
                    [LandlineNumber],
                    [LogoUrl],
                    [MobileNumber],
                    [DateFormat],
                    [FirstDayOfWeek],
                    [CreatedAtUtc],
                    [DeletedAtUtc],
                    [UpdatedAtUtc]
                )
                VALUES
                (
                    NEWID(),      -- ✅ SQL بيولّده تلقائياً
                    ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,
                    ?, ?, ?, ?, ?, ?, ?, ?, ?
                )
                """;

        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, "automation");           // Slug
        stmt.setString(2, OrgName);               // DisplayName ✅ بتاخد الاسم الفعلي
        stmt.setString(3, "automation@tenant.com");
        stmt.setString(4, "tenant.com");
        stmt.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
        stmt.setString(6, "active");
        stmt.setString(7, "system");
        stmt.setNull(8, java.sql.Types.TIMESTAMP);
        stmt.setNull(9, java.sql.Types.VARCHAR);
        stmt.setInt(10, 0);
        stmt.setNull(11, java.sql.Types.TIMESTAMP);
        stmt.setNull(12, java.sql.Types.VARCHAR);
        stmt.setInt(13, 0);
        stmt.setString(14, "cairo");
        stmt.setNull(15, java.sql.Types.VARCHAR);
        stmt.setString(16, "/uploads/organization-logos/default-logo.png");
        stmt.setString(17, "01278021209");
        stmt.setInt(18, 1);
        stmt.setInt(19, 1);
        stmt.setTimestamp(20, new Timestamp(System.currentTimeMillis()));
        stmt.setNull(21, java.sql.Types.TIMESTAMP);
        stmt.setNull(22, java.sql.Types.TIMESTAMP);
        LogsManager.debug("Inserted OrgName: " + OrgName);

    }

}



