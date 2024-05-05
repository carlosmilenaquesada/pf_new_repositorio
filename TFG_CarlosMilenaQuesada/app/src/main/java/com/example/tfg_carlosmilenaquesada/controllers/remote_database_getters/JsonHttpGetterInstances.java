package com.example.tfg_carlosmilenaquesada.controllers.remote_database_getters;

import android.content.Context;

import com.example.tfg_carlosmilenaquesada.controllers.local_sqlite_manager.SqliteConnector;

public class JsonHttpGetterInstances {
    private static JsonHttpGetter jsonHttpGetterUsers;
    private static JsonHttpGetter jsonHttpGetterArticles;
    private static JsonHttpGetter jsonHttpGetterBarcodes;
    private static JsonHttpGetter jsonHttpGetterCustomersTypes;
    private static JsonHttpGetter jsonHttpGetterCustomers;

    public static JsonHttpGetter getInstanceJsonHttpGetterUsers(Context context) {
        if (jsonHttpGetterUsers == null) {
            jsonHttpGetterUsers = new JsonHttpGetter(context, SqliteConnector.TABLE_USERS);
            jsonHttpGetterUsers.getJsonFromHttp();
            try {
                jsonHttpGetterUsers.getLatch().await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return jsonHttpGetterUsers;
    }

    public static JsonHttpGetter getInstanceJsonHttpGetterArticles(Context context) {
        if (jsonHttpGetterArticles == null) {
            jsonHttpGetterArticles = new JsonHttpGetter(context, SqliteConnector.TABLE_ARTICLES);
            jsonHttpGetterArticles.getJsonFromHttp();
            try {
                jsonHttpGetterArticles.getLatch().await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return jsonHttpGetterArticles;
    }

    public static JsonHttpGetter getInstanceJsonHttpGetterBarcodes(Context context) {
        if (jsonHttpGetterBarcodes == null) {
            jsonHttpGetterBarcodes = new JsonHttpGetter(context, SqliteConnector.TABLE_BARCODES);
            jsonHttpGetterBarcodes.getJsonFromHttp();
            try {
                jsonHttpGetterBarcodes.getLatch().await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return jsonHttpGetterBarcodes;
    }

    public static JsonHttpGetter getInstanceJsonHttpGetterCustomersTypes(Context context) {
        if (jsonHttpGetterCustomersTypes == null) {
            jsonHttpGetterCustomersTypes = new JsonHttpGetter(context, SqliteConnector.TABLE_CUSTOMERS_TYPES);
            jsonHttpGetterCustomersTypes.getJsonFromHttp();
            try {
                jsonHttpGetterCustomersTypes.getLatch().await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return jsonHttpGetterCustomersTypes;
    }

    public static JsonHttpGetter getInstanceJsonHttpGetterCustomers(Context context) {
        if (jsonHttpGetterCustomers == null) {
            jsonHttpGetterCustomers = new JsonHttpGetter(context, SqliteConnector.TABLE_TAXABLE_CUSTOMERS);
            jsonHttpGetterCustomers.getJsonFromHttp();
            try {
                jsonHttpGetterCustomers.getLatch().await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return jsonHttpGetterCustomers;
    }


}
