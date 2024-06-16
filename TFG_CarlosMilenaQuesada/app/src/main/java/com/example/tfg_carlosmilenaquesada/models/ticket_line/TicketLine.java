package com.example.tfg_carlosmilenaquesada.models.ticket_line;

import java.io.Serializable;

public class TicketLine implements Serializable {
    private String ticket_line_id;
    private String ticket_id;
    private String article_id;
    private String article_name;
    private String vat_id;
    private float vat_fraction;
    private float article_quantity;
    private float applicated_sale_base_price;
    private boolean sold_during_offer;

    public TicketLine() {
    }

    public TicketLine(String ticket_line_id, String ticket_id, String article_id, String article_name, String vat_id, float vat_fraction, float article_quantity) {
        this.ticket_line_id = ticket_line_id;
        this.ticket_id = ticket_id;
        this.article_id = article_id;
        this.article_name = article_name;
        this.vat_id = vat_id;
        this.vat_fraction = vat_fraction;
        this.article_quantity = article_quantity;
    }

    public TicketLine(String ticket_line_id, String ticket_id, String article_id, String article_name,  String vat_id, float vat_fraction, float article_quantity, float applicated_sale_base_price, boolean sold_during_offer) {
        this.ticket_line_id = ticket_line_id;
        this.ticket_id = ticket_id;
        this.article_id = article_id;
        this.article_name = article_name;
        this.vat_id = vat_id;
        this.vat_fraction = vat_fraction;
        this.article_quantity = article_quantity;
        this.applicated_sale_base_price = applicated_sale_base_price;
        this.sold_during_offer = sold_during_offer;
    }

    public String getTicket_line_id() {
        return ticket_line_id;
    }

    public void setTicket_line_id(String ticket_line_id) {
        this.ticket_line_id = ticket_line_id;
    }

    public String getTicket_id() {
        return ticket_id;
    }

    public void setTicket_id(String ticket_id) {
        this.ticket_id = ticket_id;
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

    public float getApplicated_sale_base_price() {
        return applicated_sale_base_price;
    }

    public void setApplicated_sale_base_price(float applicated_sale_base_price) {
        this.applicated_sale_base_price = applicated_sale_base_price;
    }

    public boolean isSold_during_offer() {
        return sold_during_offer;
    }

    public void setSold_during_offer(boolean sold_during_offer) {
        this.sold_during_offer = sold_during_offer;
    }
}