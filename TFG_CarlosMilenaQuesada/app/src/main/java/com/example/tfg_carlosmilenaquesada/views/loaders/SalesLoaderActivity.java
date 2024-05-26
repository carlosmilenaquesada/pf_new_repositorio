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
    JsonHttpGetter jsonHttpGetterArticlesCategories;
    JsonHttpGetter jsonHttpGetterArticlesFamilies;
    JsonHttpGetter jsonHttpGetterPaymentMethods;
    JsonHttpGetter jsonHttpGetterBarcodes;
    JsonHttpGetter jsonHttpGetterCustomersTypes;
    JsonHttpGetter jsonHttpGetterVats;


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

        new Thread(new Runnable() {
            @Override
            public void run() {
                Thread threadArticles = new Thread(() -> {
                    jsonHttpGetterArticles = JsonHttpGetterInstances.getInstanceJsonHttpGetterArticles(SalesLoaderActivity.this);
                });
                threadArticles.start();

                Thread threadArticlesCategories = new Thread(() -> {
                    jsonHttpGetterArticlesCategories = JsonHttpGetterInstances.getInstanceJsonHttpGetterArticlesCategories(SalesLoaderActivity.this);
                });
                threadArticlesCategories.start();

                Thread threadArticlesFamilies = new Thread(() -> {
                    jsonHttpGetterArticlesFamilies = JsonHttpGetterInstances.getInstanceJsonHttpGetterArticlesFamilies(SalesLoaderActivity.this);
                });
                threadArticlesFamilies.start();

                Thread threadBarcodes = new Thread(() -> {
                    jsonHttpGetterBarcodes = JsonHttpGetterInstances.getInstanceJsonHttpGetterBarcodes(SalesLoaderActivity.this);
                });
                threadBarcodes.start();

                Thread threadCustomersTypes = new Thread(() -> {
                    jsonHttpGetterCustomersTypes = JsonHttpGetterInstances.getInstanceJsonHttpGetterCustomersTypes(SalesLoaderActivity.this);
                });
                threadCustomersTypes.start();

                Thread threadVats = new Thread(() -> {
                    jsonHttpGetterVats = JsonHttpGetterInstances.getInstanceJsonHttpGetterVats(SalesLoaderActivity.this);
                });
                threadVats.start();

                try {
                    threadArticles.join();
                    threadArticlesCategories.join();
                    threadArticlesFamilies.join();
                    threadBarcodes.join();
                    threadCustomersTypes.join();
                    threadVats.join();

                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }


                startActivity(new Intent(SalesLoaderActivity.this, SaleActivity.class));
            }
        }).start();




    }

}