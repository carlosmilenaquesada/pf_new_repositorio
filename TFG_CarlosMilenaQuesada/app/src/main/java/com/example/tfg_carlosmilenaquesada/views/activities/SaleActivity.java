package com.example.tfg_carlosmilenaquesada.views.activities;

import static com.example.tfg_carlosmilenaquesada.controllers.local_sqlite_manager.SqliteConnector.TABLE_ARTICLES;
import static com.example.tfg_carlosmilenaquesada.controllers.local_sqlite_manager.SqliteConnector.TABLE_BARCODES;
import static com.example.tfg_carlosmilenaquesada.controllers.local_sqlite_manager.SqliteConnector.TABLE_CUSTOMERS_TAXABLES;
import static com.example.tfg_carlosmilenaquesada.controllers.local_sqlite_manager.SqliteConnector.TABLE_CUSTOMERS_TYPES;
import static com.example.tfg_carlosmilenaquesada.controllers.local_sqlite_manager.SqliteConnector.TABLE_VATS;
import static com.example.tfg_carlosmilenaquesada.views.activities.tickets.ReservedTicketsActivity.RESTORED_TICKET;


import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tfg_carlosmilenaquesada.R;
import com.example.tfg_carlosmilenaquesada.controllers.local_sqlite_manager.SqliteConnector;
import com.example.tfg_carlosmilenaquesada.controllers.remote_database_getters.JsonHttpGetter;
import com.example.tfg_carlosmilenaquesada.controllers.remote_database_getters.JsonHttpGetterInstances;
import com.example.tfg_carlosmilenaquesada.models.ticket.Ticket;
import com.example.tfg_carlosmilenaquesada.models.ticket_line.TicketLine;
import com.example.tfg_carlosmilenaquesada.models.ticket_line.TicketLineAdapter;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;

public class SaleActivity extends AppCompatActivity {
    public static final String TICKET_AMOUNT = "com.example.tfg_carlosmilenaquesada.views.activities.saleactivity.ticket_amount";
    public static final String TICKET_LINES_LIST = "com.example.tfg_carlosmilenaquesada.views.activities.saleactivity.ticket_lines_list";
    public static final String CUSTOMER_TAX_ID = "com.example.tfg_carlosmilenaquesada.views.activities.saleactivity.customer_tax_id";
    TextView tvTicketIdInSale;
    Spinner spCustomersTypes;
    AutoCompleteTextView actvCustomerId;
    EditText etndArticleQuantity;
    EditText etArticleCode;
    Button btOpenScanner;
    Button btPutArticle;
    RecyclerView rvArticlesOnTicket;
    TextView tvTicketTotalAmount;
    Button btPayTicket;
    Button btReserveTicket;
    Button btBackFromSaleActivity;
    JsonHttpGetter jsonHttpGetterCustomers;
    private Ticket ticket;
    ArrayList<String> customersTaxIds;

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
        rvArticlesOnTicket.setLayoutManager(new LinearLayoutManager(this));
        rvArticlesOnTicket.setAdapter(new TicketLineAdapter());

        if ((ticket = (Ticket) intent.getSerializableExtra(RESTORED_TICKET)) == null) {
            ticket = new Ticket(null, "processing", "undefined");
        } else {
            ContentValues contentValues = new ContentValues();
            contentValues.put("ticket_status_id", "processing");
            SqliteConnector.getInstance(SaleActivity.this).getReadableDatabase().update(
                    SqliteConnector.TABLE_TICKETS,
                    contentValues,
                    "ticket_id = ?",
                    new String[]{ticket.getTicket_id()}
            );
            String selectTicketLinesQuery = "SELECT * FROM " + SqliteConnector.TABLE_TICKETS_LINES + " WHERE ticket_id = ?";
            Cursor cursor = SqliteConnector.getInstance(SaleActivity.this).getReadableDatabase().rawQuery(selectTicketLinesQuery, new String[]{ticket.getTicket_id()});
            while (cursor.moveToNext()) {
                ((TicketLineAdapter) rvArticlesOnTicket.getAdapter()).addTicketLine(
                        new TicketLine(
                                cursor.getString(cursor.getColumnIndexOrThrow("ticket_line_id")),
                                cursor.getString(cursor.getColumnIndexOrThrow("ticket_id")),
                                cursor.getString(cursor.getColumnIndexOrThrow("article_id")),
                                cursor.getString(cursor.getColumnIndexOrThrow("article_name")),
                                cursor.getFloat(cursor.getColumnIndexOrThrow("article_quantity")),
                                cursor.getFloat(cursor.getColumnIndexOrThrow("unit_sale_base_price")),
                                cursor.getString(cursor.getColumnIndexOrThrow("is_in_offer")),
                                cursor.getFloat(cursor.getColumnIndexOrThrow("vat_fraction"))
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
        spCustomersTypes = findViewById(R.id.spCustomersTypes);
        actvCustomerId = findViewById(R.id.actvCustomerId);
        btOpenScanner = findViewById(R.id.btOpenScanner);
        etndArticleQuantity = findViewById(R.id.etndArticleQuantity);
        etArticleCode = findViewById(R.id.etArticleCode);
        btPutArticle = findViewById(R.id.btPutArticle);


        //Se usa para poder borrar líneas de ticket arrastrándolas a la izquierda
        new ItemTouchHelper(((TicketLineAdapter) rvArticlesOnTicket.getAdapter()).getSimpleCallback()).attachToRecyclerView(rvArticlesOnTicket);
        btPayTicket = findViewById(R.id.btPayTicket);
        btReserveTicket = findViewById(R.id.btReserveTicket);
        btBackFromSaleActivity = findViewById(R.id.btBackFromSaleActivity);
        if (tvTicketTotalAmount.getText().toString().isEmpty()) {
            tvTicketTotalAmount.setText("0.00");
        }


        Cursor cursorCustomersTypes = SqliteConnector.getInstance(getApplication()).getReadableDatabase().rawQuery("SELECT description as _id FROM " + TABLE_CUSTOMERS_TYPES, null);
        String[] fromColumns = {"_id"};
        int[] toViews = {android.R.id.text1};
        CursorAdapter cursorAdapterCustomersTypes = new SimpleCursorAdapter(SaleActivity.this, android.R.layout.simple_spinner_item, cursorCustomersTypes, fromColumns, toViews, 0);
        spCustomersTypes.setAdapter(cursorAdapterCustomersTypes);
        spCustomersTypes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!((Cursor) spCustomersTypes.getAdapter().getItem(position)).getString(0).equals("Cliente fiscal")) {
                    actvCustomerId.setVisibility(View.GONE);
                    actvCustomerId.setEnabled(false);
                } else {
                    new Thread() {
                        @Override
                        public void run() {
                            jsonHttpGetterCustomers = JsonHttpGetterInstances.getInstanceJsonHttpGetterCustomers(SaleActivity.this);
                            Cursor cursorCustomers = SqliteConnector.getInstance(getApplication()).getReadableDatabase().rawQuery("SELECT customer_tax_id FROM " + TABLE_CUSTOMERS_TAXABLES, null);

                            while (cursorCustomers.moveToNext()) {
                                customersTaxIds.add(cursorCustomers.getString(0));
                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(SaleActivity.this, android.R.layout.simple_dropdown_item_1line, customersTaxIds);
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    actvCustomerId.setAdapter(adapter);
                                    actvCustomerId.setVisibility(View.VISIBLE);
                                    actvCustomerId.setEnabled(true);
                                }
                            });
                        }
                    }.start();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        IntentIntegrator intentIntegrator = new IntentIntegrator(SaleActivity.this);
        //Define el tipo de código de de barras que se pretenden scanear.
        //En este caso, voy a elegir códigos de barras PRODUCT_CODE_TYPES(los que
        // normalmente
        // usan los productos comerciales UPC_A, UPC_E, EAN_8, EAN_13, RSS_14)
        intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.PRODUCT_CODE_TYPES);
        //Promp en la pantalla de lector
        intentIntegrator.setPrompt("Lector - CDP");
        //Cámara que va a usarse (delantera, trasera, etc)
        //0 es trasera
        intentIntegrator.setCameraId(0);
        //beep de sonido al escanear
        intentIntegrator.setBeepEnabled(true);
        //
        intentIntegrator.setBarcodeImageEnabled(true);
        //bloquea/desbloquea la orientación del teléfono (he tenido que agregar lo
        // siguiente al manifest:)
				/*  <activity
					android:name="com.journeyapps.barcodescanner.CaptureActivity"
					android:screenOrientation="fullSensor"
					tools:replace="screenOrientation" />* */
        intentIntegrator.setOrientationLocked(false);
        btOpenScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //inicializa el scaneo
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

TENGO QUE RELLENAR LA TABLA DE VATS
                String barcode = String.valueOf(etArticleCode.getText());


                String query = "SELECT A.article_id, A.article_name, A.sale_base_price, V.vat_fraction, A.offer_start_date, A.offer_end_date, A.offer_sale_base_price" +
                        " FROM " + TABLE_ARTICLES + " A JOIN " + TABLE_BARCODES + " B ON A.article_id = B.article_id" +
                        " AND B.barcode = '" + barcode + "' JOIN " + TABLE_VATS + " V ON V.vat_id = A.vat_id";


                System.out.println(query);
                Cursor cursor = SqliteConnector.getInstance(SaleActivity.this).getReadableDatabase().rawQuery(query, null);


                if (cursor.moveToNext()) {
                    TicketLine ticketLine = new TicketLine();

                    ticketLine.setTicket_line_id(ticket.getTicket_id() + "LIN" + (rvArticlesOnTicket.getAdapter().getItemCount() + 1));
                    ticketLine.setTicket_id(ticket.getTicket_id());
                    ticketLine.setArticle_id(cursor.getString(cursor.getColumnIndexOrThrow("article_id")));
                    ticketLine.setArticle_name(cursor.getString(cursor.getColumnIndexOrThrow("article_name")));
                    ticketLine.setArticle_quantity(Float.parseFloat(String.valueOf(etndArticleQuantity.getText())));
                    ticketLine.setUnit_sale_base_price(cursor.getFloat(cursor.getColumnIndexOrThrow("sale_base_price")));


                    String isInOffer = "no";
                    String offerStartDate = cursor.getString(cursor.getColumnIndexOrThrow("offer_start_date"));
                    String offerEndDate = cursor.getString(cursor.getColumnIndexOrThrow("offer_end_date"));
                    if (offerStartDate != null && offerEndDate != null) {
                        LocalDateTime startDateTime = LocalDateTime.ofInstant(Instant.parse(offerStartDate), ZoneId.systemDefault());
                        LocalDateTime endDateTime = LocalDateTime.ofInstant(Instant.parse(offerEndDate), ZoneId.systemDefault());
                        LocalDateTime currentDateTime = LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault());
                        if ((currentDateTime.isEqual(startDateTime) || currentDateTime.isAfter(startDateTime))
                                && (currentDateTime.isEqual(endDateTime) || currentDateTime.isBefore(endDateTime))) {
                            ticketLine.setUnit_sale_base_price(cursor.getFloat(cursor.getColumnIndexOrThrow("offer_sale_base_price")));
                            isInOffer = "sí";
                        }
                    }
                    ticketLine.setIs_in_offer(isInOffer);
                    ticketLine.setVat_fraction(cursor.getFloat(cursor.getColumnIndexOrThrow("vat_fraction")));


                    ((TicketLineAdapter) rvArticlesOnTicket.getAdapter()).addTicketLine(ticketLine, rvArticlesOnTicket.getAdapter().getItemCount());
                    float totalLineAmount = (ticketLine.getUnit_sale_base_price() * (1 + ticketLine.getVat_fraction())) * ticketLine.getArticle_quantity();
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
                SqliteConnector.getInstance(SaleActivity.this).insertManyElementsToSqlite(((TicketLineAdapter) rvArticlesOnTicket.getAdapter()).getTicketLinesList(), SqliteConnector.TABLE_TICKETS_LINES);
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
                finish();
                startActivity(getIntent());
            }
        });

        btBackFromSaleActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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