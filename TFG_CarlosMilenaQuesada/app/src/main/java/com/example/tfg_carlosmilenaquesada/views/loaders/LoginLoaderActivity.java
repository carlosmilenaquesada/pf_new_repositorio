package com.example.tfg_carlosmilenaquesada.views.loaders;

import static com.example.tfg_carlosmilenaquesada.controllers.remote_database_getters.JsonHttpGetter.IS_CONNECTED;
import static com.example.tfg_carlosmilenaquesada.controllers.tools.Tools.SHARED_PREFS;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tfg_carlosmilenaquesada.R;

import com.example.tfg_carlosmilenaquesada.controllers.remote_database_getters.JsonHttpGetterInstances;
import com.example.tfg_carlosmilenaquesada.views.activities.LoginActiviy;

public class LoginLoaderActivity extends AppCompatActivity {
    ProgressBar pbLoginLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_loader);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        pbLoginLoader = findViewById(R.id.pbLoginLoader);

        new Thread() {
            @Override
            public void run() {
                JsonHttpGetterInstances.createInstanceJsonHttpGetterUsers(LoginLoaderActivity.this);
                startActivity(new Intent(LoginLoaderActivity.this, LoginActiviy.class));

            }
        }.start();

    }


}