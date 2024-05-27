package com.example.tfg_carlosmilenaquesada.controllers.remote_database_getters;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.tfg_carlosmilenaquesada.controllers.local_sqlite_manager.SqliteConnector;

import org.json.JSONArray;

import java.util.concurrent.CountDownLatch;

public class JsonHttpGetter{
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
        String url = SqliteConnector.NODE_SYNC + table;
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        SqliteConnector.getInstance(context).insertFromJsonArrayToSqliteTable(response, table);
                        //setDone(true);

                        latch.countDown();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("errro");
                //setDone(true);
                latch.countDown();

            }
        });

        queue.add(jsonArrayRequest);



    }
}

