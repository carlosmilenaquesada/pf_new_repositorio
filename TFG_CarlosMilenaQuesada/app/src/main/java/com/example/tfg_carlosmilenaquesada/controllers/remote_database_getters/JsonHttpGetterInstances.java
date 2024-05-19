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

    public static JsonHttpGetter getInstanceJsonHttpGetterArticlesCategories(Context context) {
        if (jsonHttpGetterArticlesCategories == null) {
            jsonHttpGetterArticlesCategories = new JsonHttpGetter(context, SqliteConnector.TABLE_ARTICLES_CATEGORIES);
            jsonHttpGetterArticlesCategories.getJsonFromHttp();
            try {
                jsonHttpGetterArticlesCategories.getLatch().await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return jsonHttpGetterArticlesCategories;
    }

    public static JsonHttpGetter getInstanceJsonHttpGetterArticlesFamilies(Context context) {
        if (jsonHttpGetterArticlesFamilies == null) {
            jsonHttpGetterArticlesFamilies = new JsonHttpGetter(context, SqliteConnector.TABLE_ARTICLES_FAMILIES);
            jsonHttpGetterArticlesFamilies.getJsonFromHttp();
            try {
                jsonHttpGetterArticlesFamilies.getLatch().await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return jsonHttpGetterArticlesFamilies;
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

    public static JsonHttpGetter getInstanceJsonHttpGetterCustomers(Context context) {
        if (jsonHttpGetterCustomers == null) {
            jsonHttpGetterCustomers = new JsonHttpGetter(context, SqliteConnector.TABLE_CUSTOMERS_TAXABLES);
            jsonHttpGetterCustomers.getJsonFromHttp();
            try {
                jsonHttpGetterCustomers.getLatch().await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return jsonHttpGetterCustomers;
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
    public static JsonHttpGetter getInstanceJsonHttpGetterPaymentMethods(Context context) {
        if (jsonHttpGetterPaymentMethods == null) {
            jsonHttpGetterPaymentMethods = new JsonHttpGetter(context, SqliteConnector.TABLE_PAYMENT_METHODS);
            jsonHttpGetterPaymentMethods.getJsonFromHttp();
            try {
                jsonHttpGetterPaymentMethods.getLatch().await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return jsonHttpGetterPaymentMethods;
    }



    public static JsonHttpGetter getInstanceJsonHttpGetterVats(Context context) {
        if (jsonHttpGetterVats == null) {
            jsonHttpGetterVats = new JsonHttpGetter(context, SqliteConnector.TABLE_VATS);
            jsonHttpGetterVats.getJsonFromHttp();
            try {
                jsonHttpGetterVats.getLatch().await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return jsonHttpGetterVats;
    }
}
