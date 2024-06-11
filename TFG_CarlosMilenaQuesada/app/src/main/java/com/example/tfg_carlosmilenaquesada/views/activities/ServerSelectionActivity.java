package com.example.tfg_carlosmilenaquesada.views.activities;

import static com.example.tfg_carlosmilenaquesada.controllers.tools.Tools.SHARED_PREFS;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tfg_carlosmilenaquesada.R;
import com.example.tfg_carlosmilenaquesada.views.loaders.LoginLoaderActivity;

public class ServerSelectionActivity extends AppCompatActivity {

    SharedPreferences sharedpreferences;

    public static final String SERVER_IP_ADDRESS = "com.example.tfg_carlosmilenaquesada.views.activities.ServerSelectionActivity.serverIpAddres";
    EditText etServerDirection;
    Button btConfirmServer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_server_selection);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        etServerDirection = findViewById(R.id.etServerDirection);
        btConfirmServer = findViewById(R.id.btConfirmServer);

        btConfirmServer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String serverIpAddres = etServerDirection.getText().toString();
                if(serverIpAddres.isEmpty()){
                    Toast.makeText(
                            ServerSelectionActivity.this, "Debe proporcionar la dirección IP del servidor", Toast.LENGTH_LONG
                    ).show();
                    return;
                }

                if(!serverIpAddres.matches("^((25[0-5]|(2[0-4]|1\\d|[1-9]|)\\d)\\.?\\b){4}$")){
                    Toast.makeText(
                            ServerSelectionActivity.this, "La dirección proporcionada no es una IP con formato válido", Toast.LENGTH_LONG
                    ).show();
                    return;
                }
                Toast.makeText(ServerSelectionActivity.this, "Conectando...", Toast.LENGTH_SHORT).show();
                sharedpreferences.edit().putString(SERVER_IP_ADDRESS, serverIpAddres).apply();

                startActivity(new Intent(ServerSelectionActivity.this, LoginLoaderActivity.class));

            }
        });
    }



}