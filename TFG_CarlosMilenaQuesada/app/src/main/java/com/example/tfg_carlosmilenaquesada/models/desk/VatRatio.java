package com.example.tfg_carlosmilenaquesada.models.desk;

public class VatRatio {
    String vat_description;

    float vat_percent;
    float sale_base_amount;
    float vat_amount;
    float total_amount;

    public VatRatio() {
    }

    public VatRatio(String vat_description, float vat_percent, float sale_base_amount, float vat_amount, float total_amount) {
        this.vat_description = vat_description;
        this.vat_percent = vat_percent;
        this.sale_base_amount = sale_base_amount;
        this.vat_amount = vat_amount;
        this.total_amount = total_amount;
    }

    public String getVat_description() {
        return vat_description;
    }

    public void setVat_description(String vat_description) {
        this.vat_description = vat_description;
    }

    public float getVat_percent() {
        return vat_percent;
    }

    public void setVat_percent(float vat_percent) {
        this.vat_percent = vat_percent;
    }

    public float getSale_base_amount() {
        return sale_base_amount;
    }

    public void setSale_base_amount(float sale_base_amount) {
        this.sale_base_amount = sale_base_amount;
    }

    public float getVat_amount() {
        return vat_amount;
    }

    public void setVat_amount(float vat_amount) {
        this.vat_amount = vat_amount;
    }

    public float getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(float total_amount) {
        this.total_amount = total_amount;
    }
}
