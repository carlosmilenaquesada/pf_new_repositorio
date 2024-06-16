package com.example.tfg_carlosmilenaquesada.views.activities;

import static com.example.tfg_carlosmilenaquesada.controllers.local_sqlite_manager.SqliteConnector.TABLE_ARTICLES;
import static com.example.tfg_carlosmilenaquesada.controllers.local_sqlite_manager.SqliteConnector.TABLE_BARCODES;
import static com.example.tfg_carlosmilenaquesada.controllers.local_sqlite_manager.SqliteConnector.TABLE_VATS;
import static com.example.tfg_carlosmilenaquesada.views.activities.tickets.ReservedTicketsActivity.RESTORED_TICKET;


import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tfg_carlosmilenaquesada.R;
import com.example.tfg_carlosmilenaquesada.controllers.local_sqlite_manager.SqliteConnector;
import com.example.tfg_carlosmilenaquesada.controllers.tools.Tools;
import com.example.tfg_carlosmilenaquesada.models.ticket.Ticket;
import com.example.tfg_carlosmilenaquesada.models.ticket_line.TicketLine;
import com.example.tfg_carlosmilenaquesada.models.ticket_line.TicketLineAdapter;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;

public class SaleActivity extends AppCompatActivity {
    public static final String TICKET_AMOUNT = "com.example.tfg_carlosmilenaquesada.views.activities.saleactivity.ticket_amount";
    public static final String TICKET_LINES_LIST = "com.example.tfg_carlosmilenaquesada.views.activities.saleactivity.ticket_lines_list";
    public static final String CUSTOMER_TAX_ID = "com.example.tfg_carlosmilenaquesada.views.activities.saleactivity.customer_tax_id";
    TextView tvTicketIdInSale;

    Switch swGenerateInvoice;
    AutoCompleteTextView actvCustomerId;


    EditText etndArticleQuantity;
    EditText etArticleCode;
    Button btOpenScanner;
    Button btPutArticle;
    RecyclerView rvArticlesOnTicket;
    TextView tvTicketTotalAmount;
    Button btPayTicket;
    Button btReserveTicket;
    private Ticket ticket;
    ArrayList<String> customersTaxIds;
    int indexOfCurrentTicketLine;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sale);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Intent intent = getIntent();
        rvArticlesOnTicket = findViewById(R.id.rvArticlesOnTicket);
        actvCustomerId = findViewById(R.id.actvCustomerId);
        swGenerateInvoice = findViewById(R.id.swGenerateInvoice);

        if(!swGenerateInvoice.isChecked()){
            actvCustomerId.setVisibility(View.INVISIBLE);
        }


        swGenerateInvoice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    actvCustomerId.setVisibility(View.VISIBLE);
                } else {
                    actvCustomerId.setVisibility(View.INVISIBLE);
                }
            }
        });
        rvArticlesOnTicket.setLayoutManager(new LinearLayoutManager(this));
        rvArticlesOnTicket.setAdapter(new TicketLineAdapter());

        if ((ticket = (Ticket) intent.getSerializableExtra(RESTORED_TICKET)) == null) {
            ticket = new Ticket();
        } else {
            ContentValues contentValues = new ContentValues();
            contentValues.put("ticket_status_id", "processing");
            contentValues.put("payment_method_id", "PAYMENT001");
            contentValues.put("payment_method_name", "undefined");
            SqliteConnector.getInstance(SaleActivity.this).getReadableDatabase().update(
                    SqliteConnector.TABLE_TICKETS,
                    contentValues,
                    "ticket_id = ?",
                    new String[]{ticket.getTicket_id()}
            );
            String selectTicketLinesQuery = "SELECT * FROM " + SqliteConnector.TABLE_TICKET_LINES + " WHERE ticket_id = ?";
            Cursor cursor = SqliteConnector.getInstance(SaleActivity.this).getReadableDatabase().rawQuery(selectTicketLinesQuery, new String[]{ticket.getTicket_id()});
            while (cursor.moveToNext()) {
                ((TicketLineAdapter) rvArticlesOnTicket.getAdapter()).addTicketLine(
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
                        rvArticlesOnTicket.getAdapter().getItemCount()
                );
            }

        }


        SqliteConnector.getInstance(this).insertOneElementToSqlite(ticket, SqliteConnector.TABLE_TICKETS);
        tvTicketIdInSale = findViewById(R.id.tvTicketIdInSale);
        tvTicketIdInSale.setText(ticket.getTicket_id());
        customersTaxIds = new ArrayList<>();
        tvTicketTotalAmount = findViewById(R.id.tvTicketTotalAmount);
        actvCustomerId = findViewById(R.id.actvCustomerId);
        btOpenScanner = findViewById(R.id.btOpenScanner);
        etndArticleQuantity = findViewById(R.id.etndArticleQuantity);
        etArticleCode = findViewById(R.id.etArticleCode);
        btPutArticle = findViewById(R.id.btPutArticle);


        //Se usa para poder borrar líneas de ticket arrastrándolas a la izquierda
        new ItemTouchHelper(((TicketLineAdapter) rvArticlesOnTicket.getAdapter()).getSimpleCallback()).attachToRecyclerView(rvArticlesOnTicket);
        btPayTicket = findViewById(R.id.btPayTicket);
        btReserveTicket = findViewById(R.id.btReserveTicket);
        if (tvTicketTotalAmount.getText().toString().isEmpty()) {
            tvTicketTotalAmount.setText("0.00");
        }




        IntentIntegrator intentIntegrator = new IntentIntegrator(SaleActivity.this);
        intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.PRODUCT_CODE_TYPES);
        intentIntegrator.setPrompt("Lector - CDP");
        intentIntegrator.setCameraId(0);
        intentIntegrator.setBeepEnabled(true);
        intentIntegrator.setBarcodeImageEnabled(true);
        intentIntegrator.setOrientationLocked(false);
        btOpenScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentIntegrator.initiateScan();
            }
        });

        btPutArticle.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                boolean hasErrors = false;
                if (etndArticleQuantity.getText().toString().isEmpty()) {
                    etndArticleQuantity.setError("Debe introducir una cantidad de artículo");
                    hasErrors = true;
                }
                if (etArticleCode.getText().toString().isEmpty()) {
                    etArticleCode.setError("Debe introducir un código de artículo");
                    hasErrors = true;
                }

                if (hasErrors) {
                    return;
                }


                String barcode = String.valueOf(etArticleCode.getText());


                String query = "SELECT A.*, V.vat_fraction " +
                        "FROM " + TABLE_ARTICLES + " A " +
                        "JOIN " + TABLE_BARCODES + " B ON A.article_id = B.article_id AND B.barcode = '" + barcode + "' " +
                        "JOIN " + TABLE_VATS + " V ON A.vat_id = V.vat_id";
                System.out.println(query);
                Cursor cursor = SqliteConnector.getInstance(SaleActivity.this).getReadableDatabase().rawQuery(query, null);


                if (cursor.moveToNext()) {

                    TicketLine ticketLine = new TicketLine(
                            ticket.getTicket_id() + "LIN" + (rvArticlesOnTicket.getAdapter().getItemCount() + 1),
                            ticket.getTicket_id(),
                            cursor.getString(cursor.getColumnIndexOrThrow("article_id")),
                            cursor.getString(cursor.getColumnIndexOrThrow("article_name")),
                            cursor.getString(cursor.getColumnIndexOrThrow("vat_id")),
                            cursor.getFloat(cursor.getColumnIndexOrThrow("vat_fraction")),
                            cursor.getFloat(cursor.getColumnIndexOrThrow("article_quantity"))
                    );
                    ticketLine.setSold_during_offer(
                            !cursor.isNull(cursor.getColumnIndexOrThrow("offer_start_date")) && !cursor.isNull(cursor.getColumnIndexOrThrow("offer_end_date")) && Tools.isArticleInOffer(
                                    Tools.stringToLocalDateTime(cursor.getString(cursor.getColumnIndexOrThrow("offer_start_date"))),
                                    Tools.stringToLocalDateTime(cursor.getString(cursor.getColumnIndexOrThrow("offer_end_date")))
                            )
                    );


                    if (ticketLine.isSold_during_offer()) {
                        ticketLine.setApplicated_sale_base_price(cursor.getFloat(cursor.getColumnIndexOrThrow("offer_unit_sale_base_price")));
                    } else {
                        ticketLine.setApplicated_sale_base_price(cursor.getFloat(cursor.getColumnIndexOrThrow("unit_sale_base_price")));
                    }


                    if ((indexOfCurrentTicketLine = ((TicketLineAdapter) rvArticlesOnTicket.getAdapter()).getTicketLinesList().indexOf(ticketLine)) != -1) {
                        float oldQuantity = ((TicketLineAdapter) rvArticlesOnTicket.getAdapter()).getTicketLinesList().get(indexOfCurrentTicketLine).getArticle_quantity();
                        ticketLine.setArticle_quantity(ticketLine.getArticle_quantity() + oldQuantity);
                        ((TicketLineAdapter) rvArticlesOnTicket.getAdapter()).replaceTicketLine(indexOfCurrentTicketLine, ticketLine);
                    } else {
                        indexOfCurrentTicketLine = rvArticlesOnTicket.getAdapter().getItemCount();
                        ((TicketLineAdapter) rvArticlesOnTicket.getAdapter()).addTicketLine(ticketLine, indexOfCurrentTicketLine);
                    }

                    float totalLineAmount = (ticketLine.getApplicated_sale_base_price() * (1 + ticketLine.getVat_fraction())) * ticketLine.getArticle_quantity();
                    float totalAmount = Float.parseFloat(String.valueOf(tvTicketTotalAmount.getText())) + totalLineAmount;
                    tvTicketTotalAmount.setText(String.valueOf(totalAmount));
                } else {
                    Toast.makeText(SaleActivity.this, "El código proporcionado no pertenece a ningún artículo", Toast.LENGTH_LONG).show();
                }

            }
        });


        btPayTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rvArticlesOnTicket.getAdapter().getItemCount() == 0) {
                    Toast.makeText(SaleActivity.this, "No hay artículos para pagar", Toast.LENGTH_LONG).show();
                    return;
                }
                Intent intent = new Intent(SaleActivity.this, PaymentActivity.class);
                intent.putExtra(TICKET_AMOUNT, Float.parseFloat(tvTicketTotalAmount.getText().toString()));
                ArrayList<TicketLine> ticketLines = ((TicketLineAdapter) rvArticlesOnTicket.getAdapter()).getTicketLinesList();
                intent.putExtra(TICKET_LINES_LIST, ticketLines);
                intent.putExtra(CUSTOMER_TAX_ID, actvCustomerId.isEnabled() && customersTaxIds.contains(actvCustomerId.getText().toString()) ? actvCustomerId.getText().toString() : null);
                startActivity(intent);
            }
        });
        btReserveTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((TicketLineAdapter) rvArticlesOnTicket.getAdapter()).getItemCount() == 0) {
                    return;
                }
                //insertar las líneas de ticket
                SqliteConnector.getInstance(SaleActivity.this).insertManyElementsToSqlite(((TicketLineAdapter) rvArticlesOnTicket.getAdapter()).getTicketLinesList(), SqliteConnector.TABLE_TICKET_LINES);
                //Actualizo el ticket a su nuevo estado.
                ContentValues contentValues = new ContentValues();
                contentValues.put("customer_tax_id", actvCustomerId.isEnabled() && customersTaxIds.contains(actvCustomerId.getText().toString()) ? actvCustomerId.getText().toString() : null);
                contentValues.put("payment_method_id", "undefined");
                contentValues.put("ticket_status_id", "reserved");
                SqliteConnector.getInstance(SaleActivity.this).getReadableDatabase().update(
                        SqliteConnector.TABLE_TICKETS,
                        contentValues,
                        "ticket_id = ?",
                        new String[]{ticket.getTicket_id()}
                );

                startActivity(new Intent(SaleActivity.this, MainMenuActivity.class));
            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (intentResult != null) {
            if (intentResult.getContents() != null) {
                etArticleCode.setText(intentResult.getContents());
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

}