package com.example.tfg_carlosmilenaquesada.views.activities;

import static com.example.tfg_carlosmilenaquesada.views.activities.CapitalManagementActivity.AMOUNT_SIGN;
import static com.example.tfg_carlosmilenaquesada.views.activities.CapitalManagementActivity.CAPITAL_OPERATION_TYPE;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tfg_carlosmilenaquesada.R;
import com.example.tfg_carlosmilenaquesada.controllers.local_sqlite_manager.SqliteConnector;
import com.example.tfg_carlosmilenaquesada.controllers.tools.Tools;
import com.example.tfg_carlosmilenaquesada.models.desk.CapitalOperation;


public class CapitalOperationActivity extends AppCompatActivity {

    TextView tvOperationType;
    EditText etndOperationAmount;
    EditText etDescription;
    Button btApply;

    RecyclerView rvCapitalOperations;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_capital_operation);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();
        String capitalOperationType = (String) intent.getSerializableExtra(CAPITAL_OPERATION_TYPE);
        String amountSign = (String) intent.getSerializableExtra(AMOUNT_SIGN);
        if (capitalOperationType == null || amountSign == null) {
            finish();
        }


        tvOperationType = findViewById(R.id.tvOperationType);
        etndOperationAmount = findViewById(R.id.etndOperationAmount);
        etDescription = findViewById(R.id.etDescription);
        btApply = findViewById(R.id.btApply);

        tvOperationType.setText(capitalOperationType);
        etndOperationAmount.setTextColor(amountSign.equals("-") ? Color.RED : Color.BLUE);





        btApply.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                boolean hasError = false;
                if (etndOperationAmount.getText().toString().isEmpty()) {
                    etndOperationAmount.setError("Debe introducir un importe válido");
                    hasError = true;
                }
                if (etDescription.getText().toString().isEmpty()) {
                    etDescription.setError("Debe introducir una descripción");
                    hasError = true;
                }
                if (hasError) {
                    return;
                }

                CapitalOperation capitalOperation = new CapitalOperation(
                        capitalOperationType, Float.parseFloat(amountSign + etndOperationAmount.getText().toString()), etDescription.getText().toString()
                );

                ContentValues newContentValues = Tools.getContentValuesFromCapitalOperation(capitalOperation);

                try {
                    if(SqliteConnector.getInstance(CapitalOperationActivity.this).getReadableDatabase().insertOrThrow(SqliteConnector.TABLE_CAPITAL_OPERATIONS, null, newContentValues)==-1){
                        Toast.makeText(CapitalOperationActivity.this, "Fallo al realizar la operación", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    etndOperationAmount.setText("");
                    etDescription.setText("");

                    Toast.makeText(CapitalOperationActivity.this, "Operación de " + capitalOperationType + " realizada correctamente", Toast.LENGTH_SHORT).show();
                } catch (Exception E) {
                    Toast.makeText(CapitalOperationActivity.this, E.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });


    }
}