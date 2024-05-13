package com.example.tfg_carlosmilenaquesada.controllers.local_sqlite_manager;

import static com.example.tfg_carlosmilenaquesada.controllers.local_sqlite_manager.SqliteConnector.TABLE_ARTICLES;
import static com.example.tfg_carlosmilenaquesada.controllers.local_sqlite_manager.SqliteConnector.TABLE_CAPITAL_OPERATIONS;
import static com.example.tfg_carlosmilenaquesada.controllers.local_sqlite_manager.SqliteConnector.TABLE_TICKETS;
import static com.example.tfg_carlosmilenaquesada.controllers.local_sqlite_manager.SqliteConnector.TABLE_TICKETS_LINES;


import android.content.Context;
import android.database.Cursor;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.tfg_carlosmilenaquesada.models.desk.ArticlesFamilyShare;
import com.example.tfg_carlosmilenaquesada.models.desk.BaseAndVat;
import com.example.tfg_carlosmilenaquesada.models.desk.FirstAndLast;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

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
            query += " WHERE substr(sale_date, 1, 10) = " + LocalDate.now().toString();
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
            query += " WHERE substr(sale_date, 1, 10) = " + LocalDate.now().toString();
        }
        Cursor cursor = this.sqliteConnector.getReadableDatabase().rawQuery(query, null);
        if (cursor.moveToNext()) {
            firstAndLastTicketDate.setFirst(cursor.getString(0));
            firstAndLastTicketDate.setLast(cursor.getString(1));
        }
        cursor.close();
        return firstAndLastTicketDate;
    }

    /**
     * @param onlyToday
     * @param specificPaymentMethod if null, this method will return total sales from all payment method
     * @return
     */
    public BaseAndVat getBaseAndVatFromTotal(boolean onlyToday, String specificPaymentMethod) {
        BaseAndVat baseAndVatFromTotal = new BaseAndVat();
        String query = "SELECT SUM(TL.unit_sale_base_price) as 'total_sales_base', SUM(TL.unit_sale_base_price * TL.vat_fraction)) as 'total_vat' FROM " + TABLE_TICKETS_LINES + " TL JOIN " + TABLE_TICKETS + " T ON TL.ticket_id = T.ticket_id";
        if (specificPaymentMethod != null) {
            query += " AND T.payment_method_id = '" + specificPaymentMethod + "'";
        }
        if (onlyToday) {
            query += " AND substr(T.sale_date, 1, 10) = " + LocalDate.now().toString();
        }
        Cursor cursor = this.sqliteConnector.getReadableDatabase().rawQuery(query, null);
        if (cursor.moveToNext()) {
            baseAndVatFromTotal.setBase(cursor.getFloat(0));
            baseAndVatFromTotal.setVat(cursor.getFloat(1));
        }
        cursor.close();
        return baseAndVatFromTotal;
    }

    public ArrayList<ArticlesFamilyShare> getArticlesFamilyShares(boolean onlyToday) {
        ArrayList<ArticlesFamilyShare> articlesFamilyShares = new ArrayList<>();
        String query = "SELECT A.family_name, SUM(TL.article_quantity) AS 'sold_unit_from_family', SUM(TL.article_quantity *  TL.unit_sale_base_price) AS 'total_sold_base_from_family' FROM " + TABLE_ARTICLES + " A JOIN " + TABLE_TICKETS_LINES + " TL ON A.article_id = TL.article_id GROUP BY A.family_name";
        if (onlyToday) {
            query += " JOIN " + TABLE_TICKETS + " T ON T.ticket_id = TL.ticket_id AND substr(T.sale_date, 1, 10) = " + LocalDate.now().toString();
        }
        Cursor cursor = this.sqliteConnector.getReadableDatabase().rawQuery(query, null);
        if (cursor.moveToNext()) {
            articlesFamilyShares.add(
                    new ArticlesFamilyShare(
                            cursor.getString(cursor.getColumnIndexOrThrow("family_name")),
                            cursor.getFloat(cursor.getColumnIndexOrThrow("sold_unit_from_family")),
                            cursor.getFloat(cursor.getColumnIndexOrThrow("total_sold_base_from_family"))
                    )
            );
        }
        return articlesFamilyShares;
    }




    public float getTotalCashAmount(boolean onlyToday) {
        float totalCashAmount = 0f;
        String query = "SELECT SUM(amount) AS 'total_cash_amount' FROM" + TABLE_CAPITAL_OPERATIONS;
        if (onlyToday) {
            query += " WHERE substr(operation_date, 1, 10) = " + LocalDate.now().toString();
        }
        Cursor cursor = this.sqliteConnector.getReadableDatabase().rawQuery(query, null);
        if (cursor.moveToNext()) {
            totalCashAmount = cursor.getFloat(0);
        }
        cursor.close();
        return totalCashAmount;
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


        );*/

