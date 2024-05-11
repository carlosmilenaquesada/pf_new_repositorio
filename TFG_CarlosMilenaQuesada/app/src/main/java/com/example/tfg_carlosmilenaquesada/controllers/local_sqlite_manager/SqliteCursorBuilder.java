package com.example.tfg_carlosmilenaquesada.controllers.local_sqlite_manager;

import static com.example.tfg_carlosmilenaquesada.controllers.local_sqlite_manager.SqliteConnector.TABLE_TICKETS;
import static com.example.tfg_carlosmilenaquesada.controllers.local_sqlite_manager.SqliteCursorBuilder.RangeFrom.ALL_DAYS;
import static com.example.tfg_carlosmilenaquesada.controllers.local_sqlite_manager.SqliteCursorBuilder.RangeFrom.CURRENT_DAY;

import android.content.Context;
import android.database.Cursor;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class SqliteCursorBuilder {

    public enum RangeFrom {
        ALL_DAYS, CURRENT_DAY
    }

    Context context;
    SqliteConnector sqliteConnector;

    public SqliteCursorBuilder(Context context) {
        this.context = context;
        this.sqliteConnector = SqliteConnector.getInstance(context);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public Cursor getTicketsMinMaxRange(RangeFrom rangeFrom) {
        String query = "";
        switch (rangeFrom) {
            case ALL_DAYS:
                query = "SELECT MIN(ticket_id), MAX(ticket_id) FROM " + TABLE_TICKETS;
                break;
            case CURRENT_DAY:
                query = "SELECT MIN(ticket_id), MAX(ticket_id) FROM " + TABLE_TICKETS + " WHERE substr(sale_date, 1, 10) = " + LocalDate.now().toString();
                break;
        }
        return this.sqliteConnector.getReadableDatabase().rawQuery(query, null);
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public Cursor getTicketsMinMaxDate(RangeFrom rangeFrom) {
        String query = "";
        switch (rangeFrom) {
            case ALL_DAYS:
                query = "SELECT MIN(sale_date), MAX(sale_date) FROM " + TABLE_TICKETS;
                break;
            case CURRENT_DAY:
                query = "SELECT MIN(sale_date), MAX(sale_date) FROM " + TABLE_TICKETS + " WHERE substr(sale_date, 1, 10) = " + LocalDate.now().toString();
                break;
        }
        return this.sqliteConnector.getReadableDatabase().rawQuery(query, null);
    }
}
    /*
        CIERRE TOTAL------------------
        -- TICKET DESDE / TICKET HASTA - OK
        -- FECHA DESDE / FECHA HASTA - OK
        -- VENTA TOTAL SIN IVA - OK
        -- VENTA TOTAL CON IVA - OK
        -- CAPITAL EFECTIVO DECLARADO -- este viene del conteo de caja pre-cierre (retirada final) que se ejecuta al hacer el cierre (se incluirá en la tabla TABLE_CAPITAL_OPERATIONS con la capital_operation_type "retirada final")
        -- DIFERENCIA DECLARACIÓN - OK

        -- TOTAL VENTA EN TARJETA
            --SIN IVA / CON IVA

        -- VENTA EN EFECTIVO
            --SIN IVA / CON IVA


        -- PORCENTAJE E IMPORTE DE VENTA POR FAMILIAS (SIN IVA)


        -- PORCENTAJE E IMPORTE DE VENTAS POR IVA
            -- CON IVA INCLUIDO
            -- SIN IVA INCLUIDO

        */



/*db.execSQL("CREATE VIEW "
        + VIEW_CASH_CLOSING
        + " AS SELECT "
        + " (SELECT MIN(ticket_id) FROM " + TABLE_TICKETS + ") AS 'ticket_id_from', "
        + " (SELECT MAX(ticket_id) FROM " + TABLE_TICKETS + ") AS 'ticket_id_to', "
        + " (SELECT MIN(sale_date) FROM " + TABLE_TICKETS + ") AS 'sale_date_from', "
        + " (SELECT MAX(sale_date) FROM " + TABLE_TICKETS + ") AS 'sale_date_to', "
        + " (SELECT SUM(unit_sale_base_price) FROM " + TABLE_TICKETS_LINES + ") AS 'total_sales_base', "
        + " (SELECT SUM(unit_sale_base_price * (1 + vat_fraction)) FROM " + TABLE_TICKETS_LINES + ") AS 'total_sales_with_vat', "
        + " (SELECT SUM(amount) FROM " + TABLE_CAPITAL_OPERATIONS + ") AS 'total_cash_amount', "
        + " (SELECT amount FROM " + TABLE_CAPITAL_OPERATIONS + " WHERE capital_operation_type = 'retirada final') AS 'final_cash_withdrawal', "
        + " (SELECT SUM(TL.unit_sale_base_price) FROM " + TABLE_TICKETS_LINES + " TL JOIN " + TABLE_TICKETS + " T ON TL.ticket_id = T.ticket_id AND T.payment_method_id = 'bankcard') AS 'total_bankcard_sales_base', "
        + " (SELECT SUM(TL.unit_sale_base_price * (1 + TL.vat_fraction)) FROM " + TABLE_TICKETS_LINES + " TL JOIN " + TABLE_TICKETS + " T ON TL.ticket_id = T.ticket_id AND T.payment_method_id = 'bankcard') AS 'total_bankcard_sales_base', "
        + " (SELECT SUM(TL.unit_sale_base_price) FROM " + TABLE_TICKETS_LINES + " TL JOIN " + TABLE_TICKETS + " T ON TL.ticket_id = T.ticket_id AND T.payment_method_id = 'cash') AS 'total_cash_sales_base', "
        + " (SELECT SUM(TL.unit_sale_base_price * (1 + TL.vat_fraction)) FROM " + TABLE_TICKETS_LINES + " TL JOIN " + TABLE_TICKETS + " T ON TL.ticket_id = T.ticket_id AND T.payment_method_id = 'cash') AS 'total_cash_sales_base'"

        );*/

