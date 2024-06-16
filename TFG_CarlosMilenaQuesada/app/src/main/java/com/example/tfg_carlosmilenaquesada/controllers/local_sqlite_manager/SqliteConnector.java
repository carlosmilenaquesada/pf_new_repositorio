package com.example.tfg_carlosmilenaquesada.controllers.local_sqlite_manager;



import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class SqliteConnector extends SQLiteOpenHelper {

    //SQLite solo permite tipos INTEGER, REAL, TEXT, BLOB, NUMERIC y NULL,
    //sin necesidad de especificar ni longuitud ni precisión.
    private static SqliteConnector sqliteConnector;

    private static final int DATABASE_VERSION = 63;
    private static final String DATABASE_NAME = "tpv.db";
    public static final String TABLE_ARTICLES = "articles";
    public static final String TABLE_ARTICLE_FAMILIES = "article_families";
    public static final String TABLE_BARCODES = "barcodes";
    public static final String TABLE_CAPITAL_OPERATIONS = "capital_operations";
    public static final String TABLE_CUSTOMERS_TAXABLES = "customers_taxables";
    public static final String TABLE_PAYMENT_METHODS = "payment_methods";

    public static final String TABLE_TICKETS = "tickets";
    public static final String TABLE_TICKET_LINES = "ticket_lines";
    public static final String TABLE_USERS = "users";
    public static final String TABLE_VATS = "vats";

    public static final String TABLE_TICKETS_ADD_QUERY = "SELECT ticket_id, sale_date, customer_tax_id, ticket_status_id, payment_method_id FROM " + TABLE_TICKETS;
    public static final String TABLE_TICKETS_LINES_ADD_QUERY = "SELECT ticket_line_id, ticket_id, article_id, article_quantity, applicated_sale_base_price, vat_id, sold_during_offer FROM " + TABLE_TICKET_LINES;


    private SqliteConnector(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    public static SqliteConnector getInstance(Context context) {
        if (sqliteConnector == null) {
            sqliteConnector = new SqliteConnector(context);
        }
        return sqliteConnector;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //TABLAS
        db.execSQL("CREATE TABLE " + TABLE_ARTICLES + "(" +
                "article_id TEXT PRIMARY KEY NOT NULL, " +
                "article_name TEXT NOT NULL, " +
                "article_family_id TEXT NOT NULL, " +
                "unit_sale_base_price REAL NOT NULL," +
                "vat_id TEXT NOT NULL, " +
                "offer_start_date TEXT," +
                "offer_end_date TEXT," +
                "offer_unit_sale_base_price REAL" +
                ")");

        db.execSQL("CREATE TABLE " + TABLE_ARTICLE_FAMILIES + "(" +
                "article_family_id  TEXT NOT NULL, " +
                "family_name TEXT NOT NULL " +
                ")");

        db.execSQL("CREATE TABLE " + TABLE_BARCODES + "(" +
                "barcode TEXT PRIMARY KEY NOT NULL," +
                "article_id TEXT NOT NULL " +
                ")");

        db.execSQL("CREATE TABLE " + TABLE_CAPITAL_OPERATIONS + "(" +
                "capital_operation_id TEXT PRIMARY KEY NOT NULL," +
                "operation_date TEXT NOT NULL," +
                "capital_operation_type TEXT NOT NULL," +
                "amount REAL NOT NULL," +
                "description TEXT NOT NULL " +
                ")");

        db.execSQL("CREATE TABLE " + TABLE_CUSTOMERS_TAXABLES + "(" +
                "customer_tax_id TEXT PRIMARY KEY NOT NULL," +
                "legal_company_name TEXT NOT NULL, " +
                "name TEXT, " +
                "legal_company_address TEXT NOT NULL, " +
                "legal_country TEXT NOT NULL, " +
                "legal_location TEXT NOT NULL, " +
                "legal_zip_code TEXT NOT NULL " +
                ")");



        db.execSQL("CREATE TABLE " + TABLE_PAYMENT_METHODS + "(" +
                "payment_method_id TEXT PRIMARY KEY NOT NULL, " +
                "payment_method_name TEXT NOT NULL" +
                ")");


        db.execSQL("CREATE TABLE " + TABLE_TICKETS + "(" +
                "ticket_id TEXT PRIMARY KEY NOT NULL," +
                "sale_date TEXT NOT NULL, " +
                "customer_tax_id TEXT, " +
                "ticket_status_id TEXT NOT NULL, " +
                "payment_method_id TEXT NOT NULL, " +
                "payment_method_name TEXT NOT NULL" +
                ")");

        db.execSQL("CREATE TABLE " + TABLE_TICKET_LINES + "(" +
                "ticket_line_id TEXT PRIMARY KEY NOT NULL," +
                "ticket_id TEXT NOT NULL, " +
                "article_id TEXT NOT NULL, " +
                "article_family_id TEXT NOT NULL, " +
                "vat_id TEXT NOT NULL, " +
                "vat_fraction REAL NOT NULL, " +
                "article_quantity REAL NOT NULL, " +
                "applicated_sale_base_price REAL NOT NULL, " +
                "sold_during_offer INTEGER NOT NULL " +
                ")");


        db.execSQL("CREATE TABLE " + TABLE_USERS + "(" +
                "user_id TEXT PRIMARY KEY NOT NULL," +
                "password TEXT NOT NULL " +
                ")");

        db.execSQL("CREATE TABLE " + TABLE_VATS + "(" +
                "vat_id TEXT PRIMARY KEY NOT NULL, " +
                "vat_description NOT NULL, " +
                "vat_fraction REAL NOT NULL" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //TABLAS
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ARTICLES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BARCODES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CAPITAL_OPERATIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ARTICLE_FAMILIES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CUSTOMERS_TAXABLES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PAYMENT_METHODS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TICKETS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TICKET_LINES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VATS);

        onCreate(db);
    }

    public void insertManyElementsToSqlite(ArrayList elements, String tableName) {
        try {
            String listString = new Gson().toJson(elements);
            JSONArray jsonArray = new JSONArray(listString);
            insertFromJsonArrayToSqliteTable(jsonArray, tableName);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertOneElementToSqlite(Object element, String tableName) {
        try {
            insertFromJsonObjectToSqliteTable(new JSONObject(new Gson().toJson(element)), tableName);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }


    public void insertFromJsonArrayToSqliteTable(JSONArray elements, String table) {
        for (int i = 0; i < elements.length(); i++) {
            try {
                getReadableDatabase().replace(table, null, jsonObjectToContentValues(elements.getJSONObject(i)));
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void insertFromJsonObjectToSqliteTable(JSONObject element, String table) {
        getReadableDatabase().insert(table, null, jsonObjectToContentValues(element));
    }


    private ContentValues jsonObjectToContentValues(JSONObject elementJson) {
        ContentValues contentValues = new ContentValues();

        for (int j = 0; j < elementJson.names().length(); j++) {
            try {
                String key = (String) elementJson.names().get(j);

                Object value = elementJson.get(key);
                if (value instanceof String) {
                    contentValues.put(key, (String) value);
                    continue;
                }
                if (value instanceof Integer) {
                    contentValues.put(key, (Integer) value);
                    continue;
                }
                if (value instanceof Double) {
                    contentValues.put(key, (Double) value);
                    continue;
                }
                if (value instanceof Long) {
                    contentValues.put(key, (Long) value);
                    continue;
                }
                if (value instanceof Boolean) {
                    contentValues.put(key, (Boolean) value ? 1 : 0);
                    continue;
                }
                if (value == null) {
                    contentValues.putNull(key);
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
        return contentValues;
    }


}
