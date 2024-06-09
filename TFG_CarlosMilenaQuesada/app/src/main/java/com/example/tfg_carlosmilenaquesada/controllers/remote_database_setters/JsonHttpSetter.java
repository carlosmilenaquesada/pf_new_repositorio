package com.example.tfg_carlosmilenaquesada.controllers.remote_database_setters;

import static com.example.tfg_carlosmilenaquesada.controllers.tools.Tools.SHARED_PREFS;
import static com.example.tfg_carlosmilenaquesada.views.activities.ServerSelectionActivity.SERVER_IP_ADDRESS;

import android.content.Context;
import android.database.Cursor;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.tfg_carlosmilenaquesada.controllers.local_sqlite_manager.SqliteConnector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class JsonHttpSetter {

    Context context;
    String table;

    String query;

    public JsonHttpSetter(Context context, String table, String query) {
        this.context = context;
        this.table = table;
        this.query = query;
    }

    public void setHttpFromJson(){
        JSONArray jsonArray = new JSONArray();
        try {
            Cursor cursor = SqliteConnector.getInstance(context).getReadableDatabase().rawQuery(query, null);
            while (cursor.moveToNext()) {
                JSONObject jsonObject = new JSONObject();
                for (String columnName : cursor.getColumnNames()) {
                    switch (cursor.getType(cursor.getColumnIndexOrThrow(columnName))) {
                        case Cursor.FIELD_TYPE_STRING:
                            jsonObject.put(columnName, cursor.getString(cursor.getColumnIndexOrThrow(columnName)));
                            break;
                        case Cursor.FIELD_TYPE_BLOB:
                            jsonObject.put(columnName, cursor.getBlob(cursor.getColumnIndexOrThrow(columnName)));
                            break;
                        case Cursor.FIELD_TYPE_FLOAT:
                            jsonObject.put(columnName, cursor.getFloat(cursor.getColumnIndexOrThrow(columnName)));
                            break;
                        case Cursor.FIELD_TYPE_INTEGER:
                            jsonObject.put(columnName, cursor.getInt(cursor.getColumnIndexOrThrow(columnName)));
                            break;
                        case Cursor.FIELD_TYPE_NULL:
                            jsonObject.put(columnName, null);
                            break;
                        default:
                    }
                }
                jsonArray.put(jsonObject);
            }
            cursor.close();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        String url = "http://" + context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE).getString(SERVER_IP_ADDRESS, null) + ":3000/add/" + table;
        RequestQueue queue = Volley.newRequestQueue(context);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(table, jsonArray);
            System.out.println(jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println("response: " + response);
                        try {
                            SqliteConnector.getInstance(context).getReadableDatabase().execSQL("DELETE FROM " + table);
                            Toast.makeText(context, "Cierre completado con éxito", Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Toast.makeText(context, "Error inesperado al realizar el proceso de cierre", Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Error inesperado al realizar el proceso de cierre", Toast.LENGTH_LONG).show();
                        System.out.println(table + " " +error.networkResponse.statusCode);
                    }
                }
        );


        queue.add(jsonObjectRequest);

    }

}
