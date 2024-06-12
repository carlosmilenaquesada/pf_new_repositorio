package com.example.tfg_carlosmilenaquesada.views.activities.point_of_sale;

import static com.example.tfg_carlosmilenaquesada.controllers.local_sqlite_manager.SqliteConnector.TABLE_TICKETS_ADD_QUERY;
import static com.example.tfg_carlosmilenaquesada.controllers.local_sqlite_manager.SqliteConnector.TABLE_TICKETS_LINES_ADD_QUERY;
import static com.example.tfg_carlosmilenaquesada.views.activities.point_of_sale.MoneyCountingActivity.MONEY_COUNTING;
import static com.example.tfg_carlosmilenaquesada.views.activities.point_of_sale.PointOfSaleManagementActivity.IS_TODAY_AUDIT;
import static com.example.tfg_carlosmilenaquesada.views.activities.tickets.ReservedTicketsActivity.RESTORED_TICKET;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tfg_carlosmilenaquesada.R;
import com.example.tfg_carlosmilenaquesada.controllers.local_sqlite_manager.SqliteConnector;
import com.example.tfg_carlosmilenaquesada.controllers.local_sqlite_manager.SqliteCursorBuilder;
import com.example.tfg_carlosmilenaquesada.controllers.remote_database_setters.JsonHttpSetter;
import com.example.tfg_carlosmilenaquesada.models.desk.ArticlesFamilyRatio;
import com.example.tfg_carlosmilenaquesada.models.desk.BaseAndVat;
import com.example.tfg_carlosmilenaquesada.models.desk.FirstAndLast;
import com.example.tfg_carlosmilenaquesada.models.desk.PaymentMethodRatio;
import com.example.tfg_carlosmilenaquesada.models.desk.VatRatio;
import com.example.tfg_carlosmilenaquesada.models.ticket.Ticket;

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

    Button btConfirmClosePointOfSale;

    private Boolean isTodayAudit;

    private Float moneyCounting;

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

        Intent intent = getIntent();
        if ((isTodayAudit = (Boolean) intent.getSerializableExtra(IS_TODAY_AUDIT)) == null) {
            isTodayAudit = false;

        }
        if ((moneyCounting = (Float) intent.getSerializableExtra(MONEY_COUNTING)) == null) {
            moneyCounting = 0f;
        }

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

        btConfirmClosePointOfSale = findViewById(R.id.btConfirmClosePointOfSale);


        SqliteCursorBuilder sqliteCursorBuilder = new SqliteCursorBuilder(this);

        FirstAndLast<String> firstAndLastTicketId = sqliteCursorBuilder.getFirstAndLastTicketId(isTodayAudit);

        tvCountedTicketFrom.setText(firstAndLastTicketId.getFirst());
        tvCountedTicketTo.setText(firstAndLastTicketId.getLast());

        FirstAndLast<String> firstAndLastTicketDate = sqliteCursorBuilder.getFirstAndLastTicketDate(isTodayAudit);

        tvCountedDateFrom.setText(firstAndLastTicketDate.getFirst());
        tvCountedDateTo.setText(firstAndLastTicketDate.getLast());

        BaseAndVat baseAndVat = sqliteCursorBuilder.getBaseAndVatFromTotal(isTodayAudit);
        tvTotalSaleBase.setText(String.valueOf(baseAndVat.getBase()));
        tvTotalVat.setText(String.valueOf(baseAndVat.getVat()));
        tvTotalSaleAmount.setText(String.valueOf(baseAndVat.getBase() + baseAndVat.getVat()));


        float operations = sqliteCursorBuilder.getTotalCashAmount(isTodayAudit);
        tvCashInDesk.setText(String.valueOf(moneyCounting));
        tvOperations.setText(String.valueOf(operations));
        tvCapitalDiffence.setText(String.valueOf(moneyCounting - operations));

        ArrayList<PaymentMethodRatio> paymentMethodsRatios = sqliteCursorBuilder.getPaymentMethodsRatios(isTodayAudit);
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

        ArrayList<ArticlesFamilyRatio> articlesFamilyRatios = sqliteCursorBuilder.getArticlesFamilyRatios(isTodayAudit);
        for (int i = 0; i < articlesFamilyRatios.size(); i++) {
            TableRow tableRow = new TableRow(this);
            tableRow.setBackgroundColor(i % 2 == 0 ? Color.WHITE : Color.rgb(230, 230, 230));
            TextView textViewColOne = new TextView(this);
            TextView textViewColTwo = new TextView(this);
            TextView textViewColThree = new TextView(this);
            TextView textViewColFour = new TextView(this);

            textViewColOne.setText(articlesFamilyRatios.get(i).getFamily_name());
            textViewColTwo.setText(String.valueOf(articlesFamilyRatios.get(i).getFamily_unit_quantity()));
            textViewColThree.setText(String.valueOf(articlesFamilyRatios.get(i).getFamily_sales_base()));
            textViewColFour.setText(String.valueOf(articlesFamilyRatios.get(i).getFamily_ratio()));

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

            tlSalesRatioByFamily.addView(tableRow);
        }

        ArrayList<VatRatio> vatRatios = sqliteCursorBuilder.getVatsRatios(isTodayAudit);
        for (int i = 0; i < vatRatios.size(); i++) {
            TableRow tableRow = new TableRow(this);
            tableRow.setBackgroundColor(i % 2 == 0 ? Color.WHITE : Color.rgb(230, 230, 230));
            TextView textViewColOne = new TextView(this);
            TextView textViewColTwo = new TextView(this);
            TextView textViewColThree = new TextView(this);
            TextView textViewColFour = new TextView(this);
            TextView textViewColFive = new TextView(this);

            textViewColOne.setText(vatRatios.get(i).getVat_description());
            textViewColTwo.setText(String.valueOf(vatRatios.get(i).getVat_percent()));
            textViewColThree.setText(String.valueOf(vatRatios.get(i).getSale_base_amount()));
            textViewColFour.setText(String.valueOf(vatRatios.get(i).getVat_amount()));
            textViewColFive.setText(String.valueOf(vatRatios.get(i).getTotal_amount()));

            tableRow.addView(textViewColOne);
            ((LinearLayout.LayoutParams) textViewColOne.getLayoutParams()).weight = 1;
            textViewColOne.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1));
            textViewColOne.setEllipsize(TextUtils.TruncateAt.END);
            textViewColOne.setSingleLine(true);
            textViewColOne.setTooltipText(textViewColOne.getText());


            tableRow.addView(textViewColTwo);
            ((LinearLayout.LayoutParams) textViewColTwo.getLayoutParams()).weight = 1;
            textViewColTwo.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
            tableRow.addView(textViewColThree);
            ((LinearLayout.LayoutParams) textViewColThree.getLayoutParams()).weight = 1;
            textViewColThree.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
            tableRow.addView(textViewColFour);
            ((LinearLayout.LayoutParams) textViewColFour.getLayoutParams()).weight = 1;
            textViewColFour.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
            tableRow.addView(textViewColFive);
            ((LinearLayout.LayoutParams) textViewColFive.getLayoutParams()).weight = 1;
            textViewColFive.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);

            tlSalesByVat.addView(tableRow);
        }
        if (isTodayAudit) {
            btConfirmClosePointOfSale.setVisibility(View.GONE);
        } else {
            btConfirmClosePointOfSale.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    JsonHttpSetter jsonHttpSetterTicketsLines = new JsonHttpSetter(PointOfSaleClosingActivity.this, SqliteConnector.TABLE_TICKETS_LINES, TABLE_TICKETS_LINES_ADD_QUERY);
                    jsonHttpSetterTicketsLines.setHttpFromJson();

                    JsonHttpSetter jsonHttpSetterTickets = new JsonHttpSetter(PointOfSaleClosingActivity.this, SqliteConnector.TABLE_TICKETS, TABLE_TICKETS_ADD_QUERY);
                    jsonHttpSetterTickets.setHttpFromJson();
                }
            });
        }


    }

}