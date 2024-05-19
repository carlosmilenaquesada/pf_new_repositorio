package com.example.tfg_carlosmilenaquesada.views.activities.point_of_sale;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tfg_carlosmilenaquesada.R;
import com.example.tfg_carlosmilenaquesada.controllers.local_sqlite_manager.SqliteCursorBuilder;
import com.example.tfg_carlosmilenaquesada.models.desk.ArticlesFamilyRatio;
import com.example.tfg_carlosmilenaquesada.models.desk.BaseAndVat;
import com.example.tfg_carlosmilenaquesada.models.desk.FirstAndLast;
import com.example.tfg_carlosmilenaquesada.models.desk.PaymentMethodRatio;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class PointOfSaleClosingActivity extends AppCompatActivity {

    TextView tvCountedTicketFrom;
    TextView tvCountedTicketTo;
    TextView tvCountedDateFrom;
    TextView tvCountedDateTo;
    TextView tvTotalSaleBase;
    TextView tvTotalVat;
    TextView tvTotalSaleAmount;
    TextView tvCashInDesk;
    TextView tvOperations;
    TextView tvCapitalDiffence;
    TableLayout tlSalesByPaymentMethod;
    TableLayout tlSalesRatioByFamily;
    TableLayout tlSalesByVat;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_point_of_sale_closing);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        tvCountedTicketFrom = findViewById(R.id.tvCountedTicketFrom);
        tvCountedTicketTo = findViewById(R.id.tvCountedTicketTo);

        tvCountedDateFrom = findViewById(R.id.tvCountedDateFrom);
        tvCountedDateTo = findViewById(R.id.tvCountedDateTo);

        tvTotalSaleBase = findViewById(R.id.tvTotalSaleBase);
        tvTotalVat = findViewById(R.id.tvTotalVat);
        tvTotalSaleAmount = findViewById(R.id.tvTotalSaleAmount);

        tvCashInDesk = findViewById(R.id.tvCashInDesk);
        tvOperations = findViewById(R.id.tvOperations);
        tvCapitalDiffence = findViewById(R.id.tvCapitalDiffence);

        tlSalesByPaymentMethod = findViewById(R.id.tlSalesByPaymentMethod);
        tlSalesRatioByFamily = findViewById(R.id.tlSalesRatioByFamily);
        tlSalesByVat = findViewById(R.id.tlSalesByVat);

        SqliteCursorBuilder sqliteCursorBuilder = new SqliteCursorBuilder(this);

        FirstAndLast<String> firstAndLastTicketId = sqliteCursorBuilder.getFirstAndLastTicketId(false);

        tvCountedTicketFrom.setText(firstAndLastTicketId.getFirst());
        tvCountedTicketTo.setText(firstAndLastTicketId.getLast());

        FirstAndLast<String> firstAndLastTicketDate = sqliteCursorBuilder.getFirstAndLastTicketDate(false);

        tvCountedDateFrom.setText(firstAndLastTicketDate.getFirst());
        tvCountedDateTo.setText(firstAndLastTicketDate.getLast());

        BaseAndVat baseAndVat = sqliteCursorBuilder.getBaseAndVatFromTotal(false);
        tvTotalSaleBase.setText(String.valueOf(baseAndVat.getBase()));
        tvTotalVat.setText(String.valueOf(baseAndVat.getVat()));
        tvTotalSaleAmount.setText(String.valueOf(baseAndVat.getBase() + baseAndVat.getVat()));


        float cashInDesk = 0.0f;
        float operations = sqliteCursorBuilder.getTotalCashAmount(false);
        tvCashInDesk.setText(String.valueOf(cashInDesk));
        tvOperations.setText(String.valueOf(operations));
        tvCapitalDiffence.setText(String.valueOf(cashInDesk - operations));

        ArrayList<PaymentMethodRatio> paymentMethodsRatios = sqliteCursorBuilder.getPaymentMethodsRatios(false);
        for (int i = 0; i < paymentMethodsRatios.size(); i++) {
            TableRow tableRow = new TableRow(this);
            tableRow.setBackgroundColor(i % 2 == 0 ? Color.WHITE : Color.rgb(230, 230, 230));
            TextView textViewColOne = new TextView(this);
            TextView textViewColTwo = new TextView(this);
            TextView textViewColThree = new TextView(this);
            TextView textViewColFour = new TextView(this);
            textViewColOne.setText(paymentMethodsRatios.get(i).getPayment_method_name());
            textViewColTwo.setText(String.valueOf(paymentMethodsRatios.get(i).getSale_base_amount()));
            textViewColThree.setText(String.valueOf(paymentMethodsRatios.get(i).getVat_amount()));
            textViewColFour.setText(String.valueOf(paymentMethodsRatios.get(i).getTotal_amount()));
            tableRow.addView(textViewColOne);
            ((LinearLayout.LayoutParams) textViewColOne.getLayoutParams()).weight = 1;

            tableRow.addView(textViewColTwo);
            ((LinearLayout.LayoutParams) textViewColTwo.getLayoutParams()).weight = 1;
            textViewColTwo.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
            tableRow.addView(textViewColThree);
            ((LinearLayout.LayoutParams) textViewColThree.getLayoutParams()).weight = 1;
            textViewColThree.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
            tableRow.addView(textViewColFour);
            ((LinearLayout.LayoutParams) textViewColFour.getLayoutParams()).weight = 1;
            textViewColFour.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);

            tlSalesByPaymentMethod.addView(tableRow);
        }

        ArrayList<ArticlesFamilyRatio> articlesFamilyRatios = sqliteCursorBuilder.getArticlesFamilyRatios(false);
        for (int i = 0; i < articlesFamilyRatios.size(); i++) {
            TableRow tableRow = new TableRow(this);
            tableRow.setBackgroundColor(i % 2 == 0 ? Color.WHITE : Color.rgb(230, 230, 230));
            TextView textViewColOne = new TextView(this);
            TextView textViewColTwo = new TextView(this);
            TextView textViewColThree = new TextView(this);

            textViewColOne.setText(articlesFamilyRatios.get(i).getFamily_name());
            textViewColTwo.setText(String.valueOf(articlesFamilyRatios.get(i).getFamily_unit_quantity()));
            textViewColThree.setText(String.valueOf(articlesFamilyRatios.get(i).getFamily_sales_base()));
            asdfasfdasd
            tableRow.addView(textViewColOne);
            ((LinearLayout.LayoutParams) textViewColOne.getLayoutParams()).weight = 1;

            tableRow.addView(textViewColTwo);
            ((LinearLayout.LayoutParams) textViewColTwo.getLayoutParams()).weight = 1;
            textViewColTwo.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
            tableRow.addView(textViewColThree);
            ((LinearLayout.LayoutParams) textViewColThree.getLayoutParams()).weight = 1;
            textViewColThree.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
            tableRow.addView(textViewColFour);
            ((LinearLayout.LayoutParams) textViewColFour.getLayoutParams()).weight = 1;
            textViewColFour.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);

            tlSalesByPaymentMethod.addView(tableRow);
        }


    }

     /*
        CIERRE TOTAL------------------
        -- TICKET DESDE / TICKET HASTA - OK - OK
        -- FECHA DESDE / FECHA HASTA - OK - OK
        -- VENTA TOTAL SIN IVA - OK - OK
        -- VENTA TOTAL CON IVA - OK - OK
        -- CAPITAL EFECTIVO DECLARADO - OK, viene de la pantalla anterior, del recuento de capital
        -- DIFERENCIA DECLARACIÓN - OK - Ok

        -- TOTAL VENTA EN TARJETA - OK
            --SIN IVA / CON IVA

        -- VENTA EN EFECTIVO - OK
            --SIN IVA / CON IVA


        -- PORCENTAJE E IMPORTE DE VENTA POR FAMILIAS (SIN IVA) - OK


        -- PORCENTAJE E IMPORTE DE VENTAS POR IVA - OK
            -- CON IVA INCLUIDO
            -- SIN IVA INCLUIDO

        */
}