package com.example.tfg_carlosmilenaquesada.controllers.remote_database_getters;

import android.content.Context;

import com.example.tfg_carlosmilenaquesada.controllers.local_sqlite_manager.SqliteConnector;

public class JsonHttpGetterInstances {
    private static JsonHttpGetter jsonHttpGetterUsers;
    private static JsonHttpGetter jsonHttpGetterArticles;
    private static JsonHttpGetter jsonHttpGetterArticlesCategories;
    private static JsonHttpGetter jsonHttpGetterArticlesFamilies;
    private static JsonHttpGetter jsonHttpGetterPaymentMethods;


    private static JsonHttpGetter jsonHttpGetterBarcodes;
    private static JsonHttpGetter jsonHttpGetterCustomers;
    private static JsonHttpGetter jsonHttpGetterCustomersTypes;
    private static JsonHttpGetter jsonHttpGetterVats;

    public static void createInstanceJsonHttpGetterUsers(Context context) {
        if (jsonHttpGetterUsers == null) {
            jsonHttpGetterUsers = new JsonHttpGetter(context, SqliteConnector.TABLE_USERS);
            jsonHttpGetterUsers.getJsonFromHttp();
            try {
                jsonHttpGetterUsers.getLatch().await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void createInstanceJsonHttpGetterArticles(Context context) {
        if (jsonHttpGetterArticles == null) {
            jsonHttpGetterArticles = new JsonHttpGetter(context, SqliteConnector.TABLE_ARTICLES);
            jsonHttpGetterArticles.getJsonFromHttp();
            try {
                jsonHttpGetterArticles.getLatch().await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public static void createInstanceJsonHttpGetterArticlesFamilies(Context context) {
        if (jsonHttpGetterArticlesFamilies == null) {
            jsonHttpGetterArticlesFamilies = new JsonHttpGetter(context, SqliteConnector.TABLE_ARTICLE_FAMILIES);
            jsonHttpGetterArticlesFamilies.getJsonFromHttp();
            try {
                jsonHttpGetterArticlesFamilies.getLatch().await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }


    public static void createInstanceJsonHttpGetterBarcodes(Context context) {
        if (jsonHttpGetterBarcodes == null) {
            jsonHttpGetterBarcodes = new JsonHttpGetter(context, SqliteConnector.TABLE_BARCODES);
            jsonHttpGetterBarcodes.getJsonFromHttp();
            try {
                jsonHttpGetterBarcodes.getLatch().await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void createInstanceJsonHttpGetterCustomers(Context context) {
        if (jsonHttpGetterCustomers == null) {
            jsonHttpGetterCustomers = new JsonHttpGetter(context, SqliteConnector.TABLE_CUSTOMERS_TAXABLES);
            jsonHttpGetterCustomers.getJsonFromHttp();
            try {
                jsonHttpGetterCustomers.getLatch().await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public static void createInstanceJsonHttpGetterPaymentMethods(Context context) {
        if (jsonHttpGetterPaymentMethods == null) {
            jsonHttpGetterPaymentMethods = new JsonHttpGetter(context, SqliteConnector.TABLE_PAYMENT_METHODS);
            jsonHttpGetterPaymentMethods.getJsonFromHttp();
            try {
                jsonHttpGetterPaymentMethods.getLatch().await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }


    public static void createInstanceJsonHttpGetterVats(Context context) {
        if (jsonHttpGetterVats == null) {
            jsonHttpGetterVats = new JsonHttpGetter(context, SqliteConnector.TABLE_VATS);
            jsonHttpGetterVats.getJsonFromHttp();
            try {
                jsonHttpGetterVats.getLatch().await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
