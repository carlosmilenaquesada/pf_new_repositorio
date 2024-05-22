package com.example.tfg_carlosmilenaquesada.views.activities.point_of_sale;

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

public class MoneyCountingActivity extends AppCompatActivity {

    public static final String MONEY_COUNTING = "com.example.tfg_carlosmilenaquesada.views.activities.point_of_sale.MoneyCountingActivity.MONEY_COUNTING";


    Button btPointOfSaleClosing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_money_counting);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btPointOfSaleClosing = findViewById(R.id.btPointOfSaleClosing);

        float declaredCash = 0f;

        findViewById(R.id.iv500e).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("clicado");
            }
        });
        btPointOfSaleClosing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MoneyCountingActivity.this, PointOfSaleClosingActivity.class);
                intent.putExtra(MONEY_COUNTING, declaredCash);
                startActivity(intent);
            }
        });
    }


}