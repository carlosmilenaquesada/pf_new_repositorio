package com.example.tfg_carlosmilenaquesada.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tfg_carlosmilenaquesada.R;

public class CapitalManagementActivity extends AppCompatActivity {
    public static final String CAPITAL_OPERATION_TYPE = "com.example.tfg_carlosmilenaquesada.views.activities.CapitalManagementActivity.CAPITAL_OPERATION_TYPE";
    public static final String AMOUNT_SIGN = "com.example.tfg_carlosmilenaquesada.views.activities.CapitalManagementActivity.AMOUNT_SIGN";
    Button btCashIncome;
    Button btCashWithdrawal;
    Button btBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_capital_management);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btCashIncome = findViewById(R.id.btCashIncome);
        btCashWithdrawal = findViewById(R.id.btCashWithdrawal);


        btCashIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CapitalManagementActivity.this, CapitalOperationActivity.class);
                intent.putExtra(CAPITAL_OPERATION_TYPE, "ingreso");
                intent.putExtra(AMOUNT_SIGN, "+");
                startActivity(intent);
            }
        });
        btCashWithdrawal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CapitalManagementActivity.this, CapitalOperationActivity.class);
                intent.putExtra(CAPITAL_OPERATION_TYPE, "retirada");
                intent.putExtra(AMOUNT_SIGN, "-");
                startActivity(intent);
            }
        });
    }
}