package com.invoicing.DB;

import com.invoicing.Utils.Logs.LogsManager;

import java.sql.PreparedStatement;

public class DBCreateInvoiceManager extends DBManager {
    public static void deleteInvoice(String Invoice) {
        try {
            String query = "Delete from [Invoicing].[momkn].[Invoices] where ClientName = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, Invoice);
            int rows = stmt.executeUpdate();
            LogsManager.debug("Deleted Invoice : " + Invoice + " | Rows affected: " + rows);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
