package com.example.tfg_carlosmilenaquesada.models.desk;

public class BaseAndVat {
    float base;
    float vat;

    public BaseAndVat() {
    }

    public BaseAndVat(float base, float vat) {
        this.base = base;
        this.vat = vat;
    }

    public float getBase() {
        return base;
    }

    public void setBase(float base) {
        this.base = base;
    }

    public float getVat() {
        return vat;
    }

    public void setVat(float vat) {
        this.vat = vat;
    }
}
