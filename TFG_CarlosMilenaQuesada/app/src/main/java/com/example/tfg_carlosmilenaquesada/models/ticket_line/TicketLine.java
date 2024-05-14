package com.example.tfg_carlosmilenaquesada.models.ticket_line;

import java.io.Serializable;

public class TicketLine implements Serializable {
    String article_id;
    String article_name;
    String article_category_id;
    float sale_base_price;
    String vat_id;
    String offer_start_date;
    String offer_end_date;
    float offer_sale_base_price;

    public TicketLine() {
    }

    public TicketLine(String article_id, String article_name, String article_category_id, float sale_base_price, String vat_id, String offer_start_date, String offer_end_date, float offer_sale_base_price) {
        this.article_id = article_id;
        this.article_name = article_name;
        this.article_category_id = article_category_id;
        this.sale_base_price = sale_base_price;
        this.vat_id = vat_id;
        this.offer_start_date = offer_start_date;
        this.offer_end_date = offer_end_date;
        this.offer_sale_base_price = offer_sale_base_price;
    }

    public String getArticle_id() {
        return article_id;
    }

    public void setArticle_id(String article_id) {
        this.article_id = article_id;
    }

    public String getArticle_name() {
        return article_name;
    }

    public void setArticle_name(String article_name) {
        this.article_name = article_name;
    }

    public String getArticle_category_id() {
        return article_category_id;
    }

    public void setArticle_category_id(String article_category_id) {
        this.article_category_id = article_category_id;
    }

    public float getSale_base_price() {
        return sale_base_price;
    }

    public void setSale_base_price(float sale_base_price) {
        this.sale_base_price = sale_base_price;
    }

    public String getVat_id() {
        return vat_id;
    }

    public void setVat_id(String vat_id) {
        this.vat_id = vat_id;
    }

    public String getOffer_start_date() {
        return offer_start_date;
    }

    public void setOffer_start_date(String offer_start_date) {
        this.offer_start_date = offer_start_date;
    }

    public String getOffer_end_date() {
        return offer_end_date;
    }

    public void setOffer_end_date(String offer_end_date) {
        this.offer_end_date = offer_end_date;
    }

    public float getOffer_sale_base_price() {
        return offer_sale_base_price;
    }

    public void setOffer_sale_base_price(float offer_sale_base_price) {
        this.offer_sale_base_price = offer_sale_base_price;
    }
}
