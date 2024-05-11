package com.example.tfg_carlosmilenaquesada.views.loaders;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tfg_carlosmilenaquesada.R;
import com.example.tfg_carlosmilenaquesada.controllers.local_sqlite_manager.SqliteConnector;
import com.example.tfg_carlosmilenaquesada.controllers.remote_database_getters.JsonHttpGetter;
import com.example.tfg_carlosmilenaquesada.controllers.remote_database_getters.JsonHttpGetterInstances;
import com.example.tfg_carlosmilenaquesada.views.activities.MainMenuActivity;
import com.example.tfg_carlosmilenaquesada.views.activities.SaleActivity;

public class SalesLoaderActivity extends AppCompatActivity {

    ProgressBar pbSalesLoader;

    JsonHttpGetter jsonHttpGetterArticles;
    JsonHttpGetter jsonHttpGetterBarcodes;
    JsonHttpGetter jsonHttpGetterCustomersTypes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sales_loader);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        pbSalesLoader = findViewById(R.id.pbSalesLoader);
        new Thread() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void run() {
                jsonHttpGetterArticles = JsonHttpGetterInstances.getInstanceJsonHttpGetterArticles(SalesLoaderActivity.this);
                jsonHttpGetterBarcodes = JsonHttpGetterInstances.getInstanceJsonHttpGetterBarcodes(SalesLoaderActivity.this);
                jsonHttpGetterCustomersTypes = JsonHttpGetterInstances.getInstanceJsonHttpGetterCustomersTypes(SalesLoaderActivity.this);

                startActivity(new Intent(SalesLoaderActivity.this, SaleActivity.class));
            }
        }.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (jsonHttpGetterArticles != null && jsonHttpGetterBarcodes != null && jsonHttpGetterCustomersTypes != null) {
            startActivity(new Intent(SalesLoaderActivity.this, MainMenuActivity.class));
        }

    }
}