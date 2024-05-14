package com.example.tfg_carlosmilenaquesada.models.desk;

public class ArticlesFamilyShare {
    String family_name;
    float family_unit_quantity;
    float family_sales_base;

    public ArticlesFamilyShare() {
    }

    public ArticlesFamilyShare(String family_name, float family_unit_quantity, float family_sales_base) {
        this.family_name = family_name;
        this.family_unit_quantity = family_unit_quantity;
        this.family_sales_base = family_sales_base;
    }

    public String getFamily_name() {
        return family_name;
    }

    public void setFamily_name(String family_name) {
        this.family_name = family_name;
    }

    public float getFamily_unit_quantity() {
        return family_unit_quantity;
    }

    public void setFamily_unit_quantity(float family_unit_quantity) {
        this.family_unit_quantity = family_unit_quantity;
    }

    public float getFamily_sales_base() {
        return family_sales_base;
    }

    public void setFamily_sales_base(float family_sales_base) {
        this.family_sales_base = family_sales_base;
    }
}