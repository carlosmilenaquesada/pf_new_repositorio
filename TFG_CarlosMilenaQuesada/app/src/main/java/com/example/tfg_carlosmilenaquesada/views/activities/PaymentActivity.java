package com.example.tfg_carlosmilenaquesada.views.activities;

import static com.example.tfg_carlosmilenaquesada.views.activities.SaleActivity.CUSTOMER_TAX_ID;
import static com.example.tfg_carlosmilenaquesada.views.activities.SaleActivity.TICKET_AMOUNT;
import static com.example.tfg_carlosmilenaquesada.views.activities.SaleActivity.TICKET_LINES_LIST;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tfg_carlosmilenaquesada.R;
import com.example.tfg_carlosmilenaquesada.controllers.local_sqlite_manager.SqliteConnector;
import com.example.tfg_carlosmilenaquesada.models.ticket_line.TicketLine;

import java.util.ArrayList;
import java.util.Locale;


public class PaymentActivity extends AppCompatActivity {
    TextView tvTotal;

    EditText etCash;

    TextView tvChange;

    Button btCompleteCashPayment;
    Button btResetCashPaymentForm;
    Button btCalculateChange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_payment);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();
        ArrayList<TicketLine> ticketLinesList = (ArrayList<TicketLine>) intent.getSerializableExtra(TICKET_LINES_LIST);
        final Float ticketAmount = (Float) intent.getSerializableExtra(TICKET_AMOUNT);
        String customerTaxId = (String) intent.getSerializableExtra(CUSTOMER_TAX_ID);

        tvTotal = findViewById(R.id.tvTotal);
        etCash = findViewById(R.id.etCash);
        tvChange = findViewById(R.id.tvChange);
        btCalculateChange = findViewById(R.id.btCalculateChange);
        btResetCashPaymentForm = findViewById(R.id.btResetCashPaymentForm);
        btCompleteCashPayment = findViewById(R.id.btCompleteCashPayment);


        resetCashPaymentForm(ticketAmount);


        btCalculateChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etCash.getText().toString().isEmpty()) {
                    etCash.setError("Debe introducir el importe entregado por el cliente");
                    return;
                }
                if (Float.parseFloat(etCash.getText().toString()) < Float.parseFloat(tvTotal.getText().toString())) {
                    etCash.setError("El importe entregado no puede ser menor que el importe total");
                    return;
                }

                float change = Float.parseFloat(etCash.getText().toString()) - Float.parseFloat(tvTotal.getText().toString());
                String truncateChange = String.format(Locale.US, "%.2f", change);
                tvChange.setText(truncateChange);
                btCalculateChange.setEnabled(false);
                btCompleteCashPayment.setEnabled(true);
                tvChange.setEnabled(false);
            }
        });
        btResetCashPaymentForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetCashPaymentForm(ticketAmount);
            }
        });

        btCompleteCashPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PaymentActivity.this, "Pago en efectivo realizado correctamente", Toast.LENGTH_LONG).show();


                //Inserto las líneas de ticket en la base de datos de SQLITE
                SqliteConnector.getInstance(PaymentActivity.this).insertManyElementsToSqlite(ticketLinesList, SqliteConnector.TABLE_TICKET_LINES);//modificar campos adecuados
                //Actualizo el ticket a su nuevo estado.
                ContentValues contentValues = new ContentValues();
                contentValues.put("customer_tax_id", customerTaxId);
                contentValues.put("ticket_status_id", "STA001");
                contentValues.put("payment_method_id", "PAY002");
                contentValues.put("payment_method_name", "cash");

                SqliteConnector.getInstance(PaymentActivity.this).getReadableDatabase().update(
                        SqliteConnector.TABLE_TICKETS,
                        contentValues,
                        "ticket_id = ?",
                        new String[]{ticketLinesList.get(0).getTicket_id()}
                );

                startActivity(new Intent(PaymentActivity.this, MainMenuActivity.class));
            }
        });


    }

    private void resetCashPaymentForm(Float ticketAmount) {
        String truncateAmount = String.format(Locale.US, "%.2f", ticketAmount);
        tvTotal.setText(truncateAmount);
        etCash.setText("");
        tvChange.setText("");
        tvChange.setEnabled(true);
        btCalculateChange.setEnabled(true);
        btCompleteCashPayment.setEnabled(false);
    }
}