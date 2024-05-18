package com.example.tfg_carlosmilenaquesada.views.activities.point_of_sale;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tfg_carlosmilenaquesada.R;

public class PointOfSaleClosingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_point_of_sale_closing);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

     /*
        CIERRE TOTAL------------------
        -- TICKET DESDE / TICKET HASTA - OK
        -- FECHA DESDE / FECHA HASTA - OK
        -- VENTA TOTAL SIN IVA - OK
        -- VENTA TOTAL CON IVA - OK
        -- CAPITAL EFECTIVO DECLARADO -- este viene del conteo de caja pre-cierre (retirada final) que se ejecuta al hacer el cierre (se incluirá en la tabla TABLE_CAPITAL_OPERATIONS con la capital_operation_type "retirada final")
        -- DIFERENCIA DECLARACIÓN - OK

        -- TOTAL VENTA EN TARJETA - OK
            --SIN IVA / CON IVA

        -- VENTA EN EFECTIVO - OK
            --SIN IVA / CON IVA


        -- PORCENTAJE E IMPORTE DE VENTA POR FAMILIAS (SIN IVA) - OK


        -- PORCENTAJE E IMPORTE DE VENTAS POR IVA - OK
            -- CON IVA INCLUIDO
            -- SIN IVA INCLUIDO

        */
}