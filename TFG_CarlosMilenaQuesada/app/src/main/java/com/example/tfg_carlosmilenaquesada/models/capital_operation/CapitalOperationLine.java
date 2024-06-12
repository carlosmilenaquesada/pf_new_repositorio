package com.example.tfg_carlosmilenaquesada.models.capital_operation;

public class CapitalOperationLine {

    String description;
    float amount;

    public CapitalOperationLine(String description, float amount) {
        this.description = description;
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
}
