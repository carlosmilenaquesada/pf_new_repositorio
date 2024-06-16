package com.example.tfg_carlosmilenaquesada.views.activities.tickets;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tfg_carlosmilenaquesada.R;
import com.example.tfg_carlosmilenaquesada.controllers.local_sqlite_manager.SqliteConnector;
import com.example.tfg_carlosmilenaquesada.models.ticket.Ticket;
import com.example.tfg_carlosmilenaquesada.models.ticket.TicketAdapter;
import com.example.tfg_carlosmilenaquesada.models.ticket_line.TicketLine;
import com.example.tfg_carlosmilenaquesada.models.ticket_line.TicketLineAdapter;
import com.example.tfg_carlosmilenaquesada.views.activities.SaleActivity;
import com.example.tfg_carlosmilenaquesada.views.activities.SalesManagementMenuActivity;

public class ReservedTicketsActivity extends AppCompatActivity implements TicketDetailInterface, TicketRescueInterface {
    public static final String RESTORED_TICKET = "com.example.tfg_carlosmilenaquesada.views.activities.tickets.reservedticketsactivity.restored_ticket";
    RecyclerView rvTicketsInReserve;
    RecyclerView rvTicketDetailLines;
    Button btRestoreTicket;

    Ticket rescuedTicket;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_reserved_tickets);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        rvTicketsInReserve = findViewById(R.id.rvTicketsInReserve);
        rvTicketDetailLines = findViewById(R.id.rvTicketDetailLines);
        btRestoreTicket = findViewById(R.id.btRestoreTicket);
        rvTicketsInReserve.setLayoutManager(new LinearLayoutManager(this));
        rvTicketsInReserve.setAdapter(new TicketAdapter(this));
        rvTicketDetailLines.setLayoutManager(new LinearLayoutManager(this));
        rvTicketDetailLines.setAdapter(new TicketLineAdapter());

        new ItemTouchHelper(((TicketAdapter) rvTicketsInReserve.getAdapter()).getSimpleCallback()).attachToRecyclerView(rvTicketsInReserve);
        Cursor cursor = SqliteConnector.getInstance(this).getReadableDatabase().rawQuery(
                "SELECT * FROM " + SqliteConnector.TABLE_TICKETS + " WHERE ticket_status_id = ?",
                new String[]{"reserved"}
        );
        while (cursor.moveToNext()) {
            ((TicketAdapter) rvTicketsInReserve.getAdapter()).addTicket(
                    new Ticket(
                            cursor.getString(cursor.getColumnIndexOrThrow("ticket_id")),
                            cursor.getString(cursor.getColumnIndexOrThrow("sale_date")),
                            cursor.getString(cursor.getColumnIndexOrThrow("customer_tax_id")),
                            cursor.getString(cursor.getColumnIndexOrThrow("ticket_status_id")),
                            cursor.getString(cursor.getColumnIndexOrThrow("payment_method_id")),
                            cursor.getString(cursor.getColumnIndexOrThrow("payment_method_name"))
                    ),
                    rvTicketsInReserve.getAdapter().getItemCount()
            );
        }

        btRestoreTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rescuedTicket == null) {
                    return;
                }
                Intent intent = new Intent(ReservedTicketsActivity.this, SaleActivity.class);
                intent.putExtra(RESTORED_TICKET, rescuedTicket);
                startActivity(intent);
            }
        });

    }

    @Override
    public void showTicketDetails(String ticketId) {
        ((TicketLineAdapter) rvTicketDetailLines.getAdapter()).notifyItemRangeRemoved(0, ((TicketLineAdapter) rvTicketDetailLines.getAdapter()).getItemCount());
        ((TicketLineAdapter) rvTicketDetailLines.getAdapter()).getTicketLinesList().clear();
        String query = "SELECT * FROM " + SqliteConnector.TABLE_TICKET_LINES + " WHERE ticket_id = ?";
        Cursor cursor = SqliteConnector.getInstance(ReservedTicketsActivity.this).getReadableDatabase().rawQuery(query, new String[]{ticketId});
        while (cursor.moveToNext()) {
            ((TicketLineAdapter) rvTicketDetailLines.getAdapter()).addTicketLine(
                    new TicketLine(
                            cursor.getString(cursor.getColumnIndexOrThrow("ticket_line_id")),
                            cursor.getString(cursor.getColumnIndexOrThrow("ticket_id")),
                            cursor.getString(cursor.getColumnIndexOrThrow("article_id")),
                            cursor.getString(cursor.getColumnIndexOrThrow("article_name")),
                            cursor.getString(cursor.getColumnIndexOrThrow("vat_id")),
                            cursor.getFloat(cursor.getColumnIndexOrThrow("vat_fraction")),
                            cursor.getFloat(cursor.getColumnIndexOrThrow("article_quantity")),
                            cursor.getFloat(cursor.getColumnIndexOrThrow("applicated_sale_base_price")),
                            cursor.getInt(cursor.getColumnIndexOrThrow("sold_during_offer")) != 0
                    ),
                    rvTicketDetailLines.getAdapter().getItemCount()
            );
        }
    }

    @Override
    public void wipeTicketDetails() {
        ((TicketLineAdapter) rvTicketDetailLines.getAdapter()).notifyItemRangeRemoved(0, ((TicketLineAdapter) rvTicketDetailLines.getAdapter()).getItemCount());
        ((TicketLineAdapter) rvTicketDetailLines.getAdapter()).getTicketLinesList().clear();
    }

    @Override
    public void rescueTicket(Ticket ticket) {
        this.rescuedTicket = ticket;
    }

}