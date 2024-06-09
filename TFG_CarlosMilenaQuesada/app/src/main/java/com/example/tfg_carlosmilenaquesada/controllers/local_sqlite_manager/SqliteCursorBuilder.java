package com.example.tfg_carlosmilenaquesada.controllers.local_sqlite_manager;

import static com.example.tfg_carlosmilenaquesada.controllers.local_sqlite_manager.SqliteConnector.TABLE_ARTICLES;
import static com.example.tfg_carlosmilenaquesada.controllers.local_sqlite_manager.SqliteConnector.TABLE_CAPITAL_OPERATIONS;
import static com.example.tfg_carlosmilenaquesada.controllers.local_sqlite_manager.SqliteConnector.TABLE_TICKETS;
import static com.example.tfg_carlosmilenaquesada.controllers.local_sqlite_manager.SqliteConnector.TABLE_TICKETS_LINES;


import android.content.Context;
import android.database.Cursor;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.tfg_carlosmilenaquesada.models.desk.ArticlesFamilyRatio;
import com.example.tfg_carlosmilenaquesada.models.desk.BaseAndVat;
import com.example.tfg_carlosmilenaquesada.models.desk.FirstAndLast;
import com.example.tfg_carlosmilenaquesada.models.desk.PaymentMethodRatio;
import com.example.tfg_carlosmilenaquesada.models.desk.VatRatio;

import java.time.LocalDate;
import java.util.ArrayList;

@RequiresApi(api = Build.VERSION_CODES.O)
public class SqliteCursorBuilder {
    Context context;
    SqliteConnector sqliteConnector;

    public SqliteCursorBuilder(Context context) {
        this.context = context;
        this.sqliteConnector = SqliteConnector.getInstance(context);
    }

    public FirstAndLast<String> getFirstAndLastTicketId(boolean onlyToday) {
        FirstAndLast<String> firstAndLastTicketId = new FirstAndLast<>();
        String query = "SELECT MIN(ticket_id) AS 'ticket_id_from', MAX(ticket_id) as 'ticket_id_to' FROM " + TABLE_TICKETS;
        if (onlyToday) {
            query += " WHERE substr(sale_date, 1, 10) = '" + LocalDate.now().toString() + "'";
        }
        Cursor cursor = this.sqliteConnector.getReadableDatabase().rawQuery(query, null);
        if (cursor.moveToNext()) {
            firstAndLastTicketId.setFirst(cursor.getString(0));
            firstAndLastTicketId.setLast(cursor.getString(1));
        }
        cursor.close();
        return firstAndLastTicketId;
    }

    public FirstAndLast<String> getFirstAndLastTicketDate(boolean onlyToday) {
        FirstAndLast<String> firstAndLastTicketDate = new FirstAndLast<>();
        String query = "SELECT MIN(sale_date) AS 'sale_date_from', MAX(sale_date) as 'sale_date_to' FROM " + TABLE_TICKETS;
        if (onlyToday) {
            query += " WHERE substr(sale_date, 1, 10) = '" + LocalDate.now().toString() + "'";
        }
        Cursor cursor = this.sqliteConnector.getReadableDatabase().rawQuery(query, null);
        if (cursor.moveToNext()) {
            firstAndLastTicketDate.setFirst(cursor.getString(0));
            firstAndLastTicketDate.setLast(cursor.getString(1));
        }
        cursor.close();
        return firstAndLastTicketDate;
    }

    public BaseAndVat getBaseAndVatFromTotal(boolean onlyToday) {
        BaseAndVat baseAndVatFromTotal = new BaseAndVat();
        String query = "SELECT " +
                "SUM(TL.article_quantity * TL.applicated_sale_base_price) as 'total_sales_base', " +
                "SUM((TL.article_quantity * TL.applicated_sale_base_price) * TL.vat_fraction) as 'total_vat' " +
                "FROM " + TABLE_TICKETS_LINES + " TL";

        if (onlyToday) {
            query += " JOIN " + TABLE_TICKETS + " T ON TL.ticket_id = T.ticket_id AND substr(T.sale_date, 1, 10) = '" + LocalDate.now().toString() + "'";
        }
        Cursor cursor = this.sqliteConnector.getReadableDatabase().rawQuery(query, null);
        if (cursor.moveToNext()) {
            baseAndVatFromTotal.setBase(cursor.getFloat(0));
            baseAndVatFromTotal.setVat(cursor.getFloat(1));
        }
        cursor.close();
        return baseAndVatFromTotal;
    }


    public ArrayList<ArticlesFamilyRatio> getArticlesFamilyRatios(boolean onlyToday) {
        ArrayList<ArticlesFamilyRatio> articlesFamilyRatios = new ArrayList<>();
        float totalSoldBase = 0.0f;
        Cursor cursorTotal = this.sqliteConnector.getReadableDatabase().rawQuery("SELECT SUM(article_quantity * applicated_sale_base_price) AS 'total_sold_base' FROM " + TABLE_TICKETS_LINES, null);
        if (cursorTotal.moveToNext()) {
            totalSoldBase = cursorTotal.getFloat(cursorTotal.getColumnIndexOrThrow("total_sold_base"));
        }
        cursorTotal.close();

        String query = "SELECT " +
                "TL.family_name, " +
                "SUM(TL.article_quantity) AS 'units_sold_by_family', " +
                "SUM(TL.article_quantity * TL.applicated_sale_base_price) AS 'total_sold_base_by_family' " +
                "FROM " + TABLE_TICKETS_LINES + " TL ";
        if (onlyToday) {
            query += "JOIN " + TABLE_TICKETS + " T ON T.ticket_id = TL.ticket_id AND substr(T.sale_date, 1, 10) = '" + LocalDate.now().toString() + "' ";
        }
        query += "GROUP BY TL.family_name";
        Cursor cursorFamily = this.sqliteConnector.getReadableDatabase().rawQuery(query, null);

        while (cursorFamily.moveToNext()) {
            float ratio = (cursorFamily.getFloat(cursorFamily.getColumnIndexOrThrow("total_sold_base_by_family")) * 100) / totalSoldBase;
            articlesFamilyRatios.add(
                    new ArticlesFamilyRatio(
                            cursorFamily.getString(cursorFamily.getColumnIndexOrThrow("family_name")),
                            cursorFamily.getFloat(cursorFamily.getColumnIndexOrThrow("units_sold_by_family")),
                            cursorFamily.getFloat(cursorFamily.getColumnIndexOrThrow("total_sold_base_by_family")),
                            ratio
                    )
            );
        }
        cursorFamily.close();
        return articlesFamilyRatios;
    }

    public ArrayList<VatRatio> getVatsRatios(boolean onlyToday) {
        ArrayList<VatRatio> vatRatios = new ArrayList<>();
        String query = "SELECT " +
                "TL.vat_description, " +
                "TL.vat_fraction, " +
                "SUM(TL.article_quantity * TL.applicated_sale_base_price) AS 'total_sale_base_amount_from_vat', " +
                "SUM((TL.article_quantity * TL.applicated_sale_base_price) * TL.vat_fraction) 'total_vat_amount_from_vat' " +
                "FROM " + TABLE_TICKETS_LINES + " TL ";
        if (onlyToday) {
            query += "JOIN " + TABLE_TICKETS + " T ON T.ticket_id = TL.ticket_id AND substr(T.sale_date, 1, 10) = '" + LocalDate.now().toString() + "' ";
        }
        query += "GROUP BY TL.vat_description";
        Cursor cursor = this.sqliteConnector.getReadableDatabase().rawQuery(query, null);
        while (cursor.moveToNext()) {
            float totalAmount = cursor.getFloat(cursor.getColumnIndexOrThrow("total_sale_base_amount_from_vat")) + cursor.getFloat(cursor.getColumnIndexOrThrow("total_vat_amount_from_vat"));
            vatRatios.add(
                    new VatRatio(
                            cursor.getString(cursor.getColumnIndexOrThrow("vat_description")),
                            cursor.getFloat(cursor.getColumnIndexOrThrow("vat_fraction")) * 100,
                            cursor.getFloat(cursor.getColumnIndexOrThrow("total_sale_base_amount_from_vat")),
                            cursor.getFloat(cursor.getColumnIndexOrThrow("total_vat_amount_from_vat")),
                            totalAmount
                    )
            );
        }
        cursor.close();
        return vatRatios;
    }

    public String getUserPrivileges(String userId, String password) {
        String[] selectionArgs = {userId, password};
        Cursor cursor = this.sqliteConnector.getReadableDatabase().rawQuery("SELECT * FROM " + sqliteConnector.TABLE_USERS + " where user_id = ? AND password = ?", selectionArgs);
        String privileges = cursor.moveToNext() ? cursor.getString(2) : null;
        cursor.close();
        return privileges;
    }

    public float getTotalCashAmount(boolean onlyToday) {
        float totalCashAmount = 0f;
        String query = "SELECT SUM(amount) AS 'total_cash_amount' FROM " + TABLE_CAPITAL_OPERATIONS;
        if (onlyToday) {
            query += " WHERE substr(operation_date, 1, 10) = '" + LocalDate.now().toString() + "'";
        }
        Cursor cursor = this.sqliteConnector.getReadableDatabase().rawQuery(query, null);
        if (cursor.moveToNext()) {
            totalCashAmount = cursor.getFloat(0);
        }
        cursor.close();
        return totalCashAmount;
    }

    public ArrayList<PaymentMethodRatio> getPaymentMethodsRatios(boolean onlyToday) {
        ArrayList<PaymentMethodRatio> paymentMethodRatios = new ArrayList<>();
        String query = "SELECT " +
                "T.payment_method_name, " +
                "SUM(TL.applicated_sale_base_price * TL.article_quantity) AS 'sale_base_amount', " +
                "SUM((TL.applicated_sale_base_price * TL.article_quantity) * (TL.vat_fraction)) AS 'vat_amount' " +
                "FROM " + TABLE_TICKETS_LINES + " TL JOIN " + TABLE_TICKETS + " T ON T.ticket_id = TL.ticket_id ";
        if (onlyToday) {
            query += " AND substr(T.sale_date, 1, 10) = '" + LocalDate.now().toString() + "' ";
        }
        query += "GROUP BY T.payment_method_name";
        Cursor cursor = this.sqliteConnector.getReadableDatabase().rawQuery(query, null);
        while (cursor.moveToNext()) {
            float totalSale = cursor.getFloat(cursor.getColumnIndexOrThrow("sale_base_amount")) +
                    cursor.getFloat(cursor.getColumnIndexOrThrow("vat_amount"));
            paymentMethodRatios.add(
                    new PaymentMethodRatio(
                            cursor.getString(cursor.getColumnIndexOrThrow("payment_method_name")),
                            cursor.getFloat(cursor.getColumnIndexOrThrow("sale_base_amount")),
                            cursor.getFloat(cursor.getColumnIndexOrThrow("vat_amount")),
                            totalSale
                    )
            );
        }
        return paymentMethodRatios;
    }

    public float getFinalCashWithdrawal() {
        float finalCashWithdrawal = 0f;
        String query = "SELECT amount as 'final_cash_withdrawal' FROM " + TABLE_CAPITAL_OPERATIONS + " WHERE capital_operation_type = 'retirada final'";
        Cursor cursor = this.sqliteConnector.getReadableDatabase().rawQuery(query, null);
        if (cursor.moveToNext()) {
            finalCashWithdrawal = cursor.getFloat(0);
        }
        cursor.close();
        return finalCashWithdrawal;
    }


}
    /*
        CIERRE TOTAL------------------
        -- TICKET DESDE / TICKET HASTA - OK
        -- FECHA DESDE / FECHA HASTA - OK
        -- VENTA TOTAL SIN IVA - OK
        -- VENTA TOTAL CON IVA - OK
        -- CAPITAL EFECTIVO DECLARADO - OK viene de la pantalla anterior, del recuento de capital
        -- DIFERENCIA DECLARACIÓN - OK

        -- TOTAL VENTA EN TARJETA - OK
            --SIN IVA / CON IVA

        -- VENTA EN EFECTIVO - OK
            --SIN IVA / CON IVA


        -- PORCENTAJE E IMPORTE DE VENTA POR FAMILIAS (SIN IVA) - OK


        -- PORCENTAJE E IMPORTE DE VENTAS POR IVA - OK
            -- CON IVA INCLUIDO
            -- SIN IVA INCLUIDO

        */





