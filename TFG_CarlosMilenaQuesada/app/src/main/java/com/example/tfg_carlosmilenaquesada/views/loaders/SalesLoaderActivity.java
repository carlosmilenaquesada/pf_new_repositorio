package com.example.tfg_carlosmilenaquesada.views.loaders;

import static com.example.tfg_carlosmilenaquesada.controllers.remote_database_getters.JsonHttpGetter.IS_CONNECTED;
import static com.example.tfg_carlosmilenaquesada.controllers.tools.Tools.SHARED_PREFS;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tfg_carlosmilenaquesada.R;
import com.example.tfg_carlosmilenaquesada.controllers.remote_database_getters.JsonHttpGetterInstances;
import com.example.tfg_carlosmilenaquesada.views.activities.MainMenuActivity;
import com.example.tfg_carlosmilenaquesada.views.activities.SaleActivity;

public class SalesLoaderActivity extends AppCompatActivity {

    ProgressBar pbSalesLoader;

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
                if (getSharedPreferences(SHARED_PREFS, MODE_PRIVATE).getBoolean(IS_CONNECTED, false)) {
                    Thread threadArticles = new Thread(() -> {
                        JsonHttpGetterInstances.createInstanceJsonHttpGetterArticles(SalesLoaderActivity.this);
                    });
                    threadArticles.start();

                    Thread threadArticlesCategories = new Thread(() -> {
                        JsonHttpGetterInstances.createInstanceJsonHttpGetterArticlesCategories(SalesLoaderActivity.this);
                    });
                    threadArticlesCategories.start();

                    Thread threadArticlesFamilies = new Thread(() -> {
                        JsonHttpGetterInstances.createInstanceJsonHttpGetterArticlesFamilies(SalesLoaderActivity.this);
                    });
                    threadArticlesFamilies.start();


                    Thread threadPaymentMethod = new Thread(() -> {
                        JsonHttpGetterInstances.createInstanceJsonHttpGetterPaymentMethods(SalesLoaderActivity.this);
                    });
                    threadPaymentMethod.start();


                    Thread threadBarcodes = new Thread(() -> {
                        JsonHttpGetterInstances.createInstanceJsonHttpGetterBarcodes(SalesLoaderActivity.this);
                    });
                    threadBarcodes.start();

                    Thread threadCustomersTypes = new Thread(() -> {
                        JsonHttpGetterInstances.createInstanceJsonHttpGetterCustomersTypes(SalesLoaderActivity.this);
                    });
                    threadCustomersTypes.start();

                    Thread threadVats = new Thread(() -> {
                        JsonHttpGetterInstances.createInstanceJsonHttpGetterVats(SalesLoaderActivity.this);
                    });
                    threadVats.start();

                    try {
                        threadArticles.join();
                        threadArticlesCategories.join();
                        threadArticlesFamilies.join();
                        threadPaymentMethod.join();
                        threadBarcodes.join();
                        threadCustomersTypes.join();
                        threadVats.join();

                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                startActivity(new Intent(SalesLoaderActivity.this, SaleActivity.class));
            }
        }).start();


    }
    protected void onRestart() {
        System.out.println("2");
        super.onRestart();
        startActivity(new Intent(SalesLoaderActivity.this, MainMenuActivity.class));
    }

    @Override
    protected void onStop() {
        System.out.println("3");
        super.onStop();
        pbSalesLoader.setVisibility(View.GONE);
    }
}