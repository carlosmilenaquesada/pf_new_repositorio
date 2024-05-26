package com.example.tfg_carlosmilenaquesada.views.loaders;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tfg_carlosmilenaquesada.R;
import com.example.tfg_carlosmilenaquesada.controllers.local_sqlite_manager.SqliteConnector;
import com.example.tfg_carlosmilenaquesada.controllers.remote_database_getters.JsonHttpGetter;
import com.example.tfg_carlosmilenaquesada.controllers.remote_database_getters.JsonHttpGetterInstances;
import com.example.tfg_carlosmilenaquesada.views.activities.LoginActiviy;
import com.example.tfg_carlosmilenaquesada.views.activities.MainMenuActivity;
import com.example.tfg_carlosmilenaquesada.views.activities.customers.CustomersManagementMenuActivity;

public class CustomersLoaderActivity extends AppCompatActivity {
    ProgressBar pbCustomersLoader;
    JsonHttpGetter jsonHttpGetterCustomers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_customers_loader);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        pbCustomersLoader = findViewById(R.id.pbCustomersLoader);

        new Thread() {
            @Override
            public void run() {
                jsonHttpGetterCustomers = JsonHttpGetterInstances.getInstanceJsonHttpGetterCustomers(CustomersLoaderActivity.this);
                startActivity(new Intent(CustomersLoaderActivity.this, CustomersManagementMenuActivity.class));
            }
        }.start();
    }

}