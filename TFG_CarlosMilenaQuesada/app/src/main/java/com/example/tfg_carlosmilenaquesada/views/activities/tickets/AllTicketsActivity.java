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
import com.example.tfg_carlosmilenaquesada.views.activities.SalesManagementMenuActivity;

public class AllTicketsActivity extends AppCompatActivity implements TicketDetailInterface {

    RecyclerView rvAllTickets;
    RecyclerView rvTicketDetailLines;
    Button btBackFromAllTicketsActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_all_tickets);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        rvAllTickets = findViewById(R.id.rvAllTickets);
        rvTicketDetailLines = findViewById(R.id.rvTicketDetailLines);
        btBackFromAllTicketsActivity = findViewById(R.id.btBackFromAllTicketsActivity);
        rvAllTickets.setLayoutManager(new LinearLayoutManager(this));
        rvAllTickets.setAdapter(new TicketAdapter(this));
        rvTicketDetailLines.setLayoutManager(new LinearLayoutManager(this));
        rvTicketDetailLines.setAdapter(new TicketLineAdapter());


        new ItemTouchHelper(((TicketAdapter) rvAllTickets.getAdapter()).getSimpleCallback()).attachToRecyclerView(rvAllTickets);
        Cursor cursor = SqliteConnector.getInstance(this).getReadableDatabase().rawQuery(
                "SELECT * FROM " + SqliteConnector.TABLE_TICKETS, null
        );
        while (cursor.moveToNext()) {
            ((TicketAdapter) rvAllTickets.getAdapter()).addTicket(
                    new Ticket(
                            cursor.getString(cursor.getColumnIndexOrThrow("ticket_id")),
                            cursor.getString(cursor.getColumnIndexOrThrow("sale_date")),
                            cursor.getString(cursor.getColumnIndexOrThrow("customer_tax_id")),
                            cursor.getString(cursor.getColumnIndexOrThrow("ticket_status_id")),
                            cursor.getString(cursor.getColumnIndexOrThrow("payment_method_id")),
                            cursor.getString(cursor.getColumnIndexOrThrow("payment_method_name"))
                    ),
                    rvAllTickets.getAdapter().getItemCount()
            );
        }
        btBackFromAllTicketsActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AllTicketsActivity.this, SalesManagementMenuActivity.class));
            }
        });

    }


    @Override
    public void showTicketDetails(String ticketId) {
        ((TicketLineAdapter) rvTicketDetailLines.getAdapter()).notifyItemRangeRemoved(0, ((TicketLineAdapter) rvTicketDetailLines.getAdapter()).getItemCount());
        ((TicketLineAdapter) rvTicketDetailLines.getAdapter()).getTicketLinesList().clear();
        String query = "SELECT * FROM " + SqliteConnector.TABLE_TICKETS_LINES + " WHERE ticket_id = ?";
        Cursor cursor = SqliteConnector.getInstance(AllTicketsActivity.this).getReadableDatabase().rawQuery(query, new String[]{ticketId});
        while (cursor.moveToNext()) {
            ((TicketLineAdapter) rvTicketDetailLines.getAdapter()).addTicketLine(
                    new TicketLine(
                            cursor.getString(cursor.getColumnIndexOrThrow("ticket_line_id")),
                            cursor.getString(cursor.getColumnIndexOrThrow("ticket_id")),
                            cursor.getString(cursor.getColumnIndexOrThrow("article_id")),
                            cursor.getString(cursor.getColumnIndexOrThrow("article_name")),
                            cursor.getString(cursor.getColumnIndexOrThrow("article_category_id")),
                            cursor.getString(cursor.getColumnIndexOrThrow("family_name")),
                            cursor.getString(cursor.getColumnIndexOrThrow("vat_id")),
                            cursor.getFloat(cursor.getColumnIndexOrThrow("vat_fraction")),
                            cursor.getString(cursor.getColumnIndexOrThrow("vat_description")),
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


}