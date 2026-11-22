package com.invoicing.DB;

import com.invoicing.Utils.Logs.LogsManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.LinkedHashMap;
import java.util.Map;

public class DBInvoiceManager extends DBManager {

    // بترجع آخر Row للـ Client كـ Map (ColumnName -> Value)
    public static Map<String, Object> getInvoiceData(String clientName) {
        String query = "SELECT TOP 1 * FROM [Invoicing].[momkn].Invoices " +
                "WHERE ClientName = ? ORDER BY CreatedAt DESC";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, clientName);

            try (ResultSet rs = stmt.executeQuery()) {
                Map<String, Object> row = new LinkedHashMap<>();

                if (rs.next()) {
                    ResultSetMetaData meta = rs.getMetaData();
                    for (int i = 1; i <= meta.getColumnCount(); i++) {
                        row.put(meta.getColumnName(i), rs.getObject(i));
                    }
                    LogsManager.debug("Invoice found for client: " + clientName);
                } else {
                    LogsManager.warn("No invoice found for client: " + clientName);
                }
                return row;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // بترجع PaymentLink بس (أو null لو مش موجود لسه)
    public static String getPaymentLink(String clientName) {
        Object link = getInvoiceData(clientName).get("PaymentLink");
        return (link == null || link.toString().isBlank()) ? null : link.toString();
    }
}