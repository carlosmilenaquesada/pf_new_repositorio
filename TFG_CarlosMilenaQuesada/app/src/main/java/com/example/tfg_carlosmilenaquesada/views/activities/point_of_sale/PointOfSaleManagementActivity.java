package com.example.tfg_carlosmilenaquesada.views.activities.point_of_sale;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tfg_carlosmilenaquesada.R;
import com.example.tfg_carlosmilenaquesada.controllers.local_sqlite_manager.SqliteCursorBuilder;
import com.example.tfg_carlosmilenaquesada.models.desk.ArticlesFamilyRatio;
import com.example.tfg_carlosmilenaquesada.views.activities.MainMenuActivity;

import java.util.ArrayList;

public class PointOfSaleManagementActivity extends AppCompatActivity {
    Button btPointOfSaleAudit;
    Button btPointOfSaleClosing;
    Button btBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_point_of_sale_management);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btPointOfSaleAudit = findViewById(R.id.btPointOfSaleAudit);
        btPointOfSaleClosing = findViewById(R.id.btPointOfSaleClosing);
        btBack = findViewById(R.id.btBack);

        btPointOfSaleAudit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PointOfSaleManagementActivity.this, PointOfSaleAuditActivity.class));
            }
        });

        btPointOfSaleClosing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PointOfSaleManagementActivity.this, PointOfSaleClosingActivity.class));
            }
        });

        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PointOfSaleManagementActivity.this, MainMenuActivity.class));
            }
        });

    }
}