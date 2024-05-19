package com.example.tfg_carlosmilenaquesada.models.desk;

public class PaymentMethodRatio {
    String payment_method_name;
    float sale_base_amount;
    float vat_amount;
    float total_amount;

    public PaymentMethodRatio() {
    }

    public PaymentMethodRatio(String payment_method_name, float sale_base_amount, float vat_amount, float total_amount) {
        this.payment_method_name = payment_method_name;
        this.sale_base_amount = sale_base_amount;
        this.vat_amount = vat_amount;
        this.total_amount = total_amount;
    }

    public String getPayment_method_name() {
        return payment_method_name;
    }

    public void setPayment_method_name(String payment_method_name) {
        this.payment_method_name = payment_method_name;
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
