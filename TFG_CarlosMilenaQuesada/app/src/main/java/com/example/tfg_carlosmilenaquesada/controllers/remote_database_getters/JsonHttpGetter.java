package com.example.tfg_carlosmilenaquesada.controllers.remote_database_getters;

import static com.example.tfg_carlosmilenaquesada.controllers.tools.Tools.SHARED_PREFS;
import static com.example.tfg_carlosmilenaquesada.views.activities.ServerSelectionActivity.SERVER_IP_ADDRESS;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.tfg_carlosmilenaquesada.controllers.local_sqlite_manager.SqliteConnector;

import org.json.JSONArray;

import java.util.concurrent.CountDownLatch;


public class JsonHttpGetter {
    public static final String IS_CONNECTED = "com.example.tfg_carlosmilenaquesada.controllers.remote_database_getters.JsonHttpGetter.IS_CONNECTED";
    private final CountDownLatch latch = new CountDownLatch(1);
    Context context;
    String table;

    public JsonHttpGetter(Context context, String table) {
        this.context = context;
        this.table = table;
    }


    public CountDownLatch getLatch() {
        return latch;
    }

    public void getJsonFromHttp() {
        String url = "http://" + context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE).getString(SERVER_IP_ADDRESS, null) + ":3000/sync/" + table;

        RequestQueue queue = Volley.newRequestQueue(context);
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        SqliteConnector.getInstance(context).insertFromJsonArrayToSqliteTable(response, table);
                        if (table.equals("users")) {
                            editor.putBoolean(IS_CONNECTED, true).apply();
                        }
                        latch.countDown();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (table.equals("users")) {
                    Toast.makeText(context, "Servidor no encontrado. Se usará la información previamente cargada", Toast.LENGTH_SHORT).show();
                }
                latch.countDown();
            }
        });

        queue.add(jsonArrayRequest);


    }
}

