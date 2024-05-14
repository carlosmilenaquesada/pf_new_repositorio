package com.example.tfg_carlosmilenaquesada.models.ticket_line;

import java.io.Serializable;

public class TicketLine implements Serializable {
    String ticket_line_id;
    String ticked_id;
    String article_id;
    String article_name;
    String article_category_id;
    String vat_id;
    float vat_fraction;

    float article_quantity;

    String offer_start_date;
    String offer_end_date;
    String is_in_offer;
    float sale_base_price;

    public TicketLine(String ticket_line_id, String ticked_id, String article_id, String article_name, String article_category_id, String vat_id, float vat_fraction, float article_quantity) {
        this.ticket_line_id = ticket_line_id;
        this.ticked_id = ticked_id;
        this.article_id = article_id;
        this.article_name = article_name;
        this.article_category_id = article_category_id;
        this.vat_id = vat_id;
        this.vat_fraction = vat_fraction;
        this.article_quantity = article_quantity;
    }

    public String getTicket_line_id() {
        return ticket_line_id;
    }

    public void setTicket_line_id(String ticket_line_id) {
        this.ticket_line_id = ticket_line_id;
    }

    public String getTicked_id() {
        return ticked_id;
    }

    public void setTicked_id(String ticked_id) {
        this.ticked_id = ticked_id;
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

    public String getVat_id() {
        return vat_id;
    }

    public void setVat_id(String vat_id) {
        this.vat_id = vat_id;
    }

    public float getVat_fraction() {
        return vat_fraction;
    }

    public void setVat_fraction(float vat_fraction) {
        this.vat_fraction = vat_fraction;
    }

    public float getArticle_quantity() {
        return article_quantity;
    }

    public void setArticle_quantity(float article_quantity) {
        this.article_quantity = article_quantity;
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

    public String getIs_in_offer() {
        return is_in_offer;
    }

    public void setIs_in_offer(String is_in_offer) {
        this.is_in_offer = is_in_offer;
    }

    public float getSale_base_price() {
        return sale_base_price;
    }

    public void setSale_base_price(float sale_base_price) {
        this.sale_base_price = sale_base_price;
    }
}
