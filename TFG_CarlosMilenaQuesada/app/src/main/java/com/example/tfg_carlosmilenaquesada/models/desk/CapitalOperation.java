package com.example.tfg_carlosmilenaquesada.models.desk;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class CapitalOperation {
    String capital_operation_id;
    String operation_date;
    String capital_operation_type;
    float amount;
    String description;

    public CapitalOperation() {
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public CapitalOperation(String capital_operation_type, float amount, String description ) {
        long currentTimeInMillis = System.currentTimeMillis();
        this.capital_operation_id = String.valueOf(currentTimeInMillis);
        this.operation_date = LocalDateTime.ofInstant(Instant.ofEpochMilli(currentTimeInMillis), ZoneId.systemDefault()).toString();

        this.capital_operation_type = capital_operation_type;
        this.amount = amount;
        this.description = description;
    }

    public String getCapital_operation_id() {
        return capital_operation_id;
    }

    public void setCapital_operation_id(String capital_operation_id) {
        this.capital_operation_id = capital_operation_id;
    }

    public String getOperation_date() {
        return operation_date;
    }

    public void setOperation_date(String operation_date) {
        this.operation_date = operation_date;
    }

    public String getCapital_operation_type() {
        return capital_operation_type;
    }

    public void setCapital_operation_type(String capital_operation_type) {
        this.capital_operation_type = capital_operation_type;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
