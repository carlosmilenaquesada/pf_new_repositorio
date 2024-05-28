package com.example.tfg_carlosmilenaquesada.controllers.remote_database_setters;

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

public class JsonHttpSetter {

    Context context;
    String table;

    String query;

    public JsonHttpSetter(Context context, String table, String query) {
        this.context = context;
        this.table = table;
        this.query = query;
    }

    public void setHttpFromJson() {
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


        String url = SqliteConnector.NODE_ADD + table;
        RequestQueue queue = Volley.newRequestQueue(context);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(table, jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error);

                    }
                }
        );



        queue.add(jsonObjectRequest);
    }

}
