package com.example.tfg_carlosmilenaquesada.models.ticket_line;

import java.io.Serializable;
import java.util.Objects;

public class TicketLine implements Serializable {
    String ticket_line_id;
    String ticket_id;
    String article_id;
    String article_name;
    String article_category_id;
    String family_name;
    String vat_id;
    float vat_fraction;
    String vat_description;
    float article_quantity;
    float applicated_sale_base_price;
    boolean sold_during_offer;

    public TicketLine() {
    }

    public TicketLine(String ticket_line_id, String ticket_id, String article_id, String article_name, String article_category_id, String family_name, String vat_id, float vat_fraction, String vat_description, float article_quantity, float applicated_sale_base_price, boolean sold_during_offer) {
        this.ticket_line_id = ticket_line_id;
        this.ticket_id = ticket_id;
        this.article_id = article_id;
        this.article_name = article_name;
        this.article_category_id = article_category_id;
        this.family_name = family_name;
        this.vat_id = vat_id;
        this.vat_fraction = vat_fraction;
        this.vat_description = vat_description;
        this.article_quantity = article_quantity;
        this.applicated_sale_base_price = applicated_sale_base_price;
        this.sold_during_offer = sold_during_offer;
    }

    public TicketLine(String ticket_line_id, String ticket_id, String article_id, String article_name, String article_category_id, String family_name, String vat_id, float vat_fraction, String vat_description, float article_quantity) {
        this.ticket_line_id = ticket_line_id;
        this.ticket_id = ticket_id;
        this.article_id = article_id;
        this.article_name = article_name;
        this.article_category_id = article_category_id;
        this.family_name = family_name;
        this.vat_id = vat_id;
        this.vat_fraction = vat_fraction;
        this.vat_description = vat_description;
        this.article_quantity = article_quantity;
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

    public String getArticle_category_id() {
        return article_category_id;
    }

    public void setArticle_category_id(String article_category_id) {
        this.article_category_id = article_category_id;
    }

    public String getFamily_name() {
        return family_name;
    }

    public void setFamily_name(String family_name) {
        this.family_name = family_name;
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

    public String getVat_description() {
        return vat_description;
    }

    public void setVat_description(String vat_description) {
        this.vat_description = vat_description;
    }

    public float getArticle_quantity() {
        return article_quantity;
    }

    public void setArticle_quantity(float article_quantity) {
        this.article_quantity = article_quantity;
    }

    public float getapplicated_sale_base_price() {
        return applicated_sale_base_price;
    }

    public void setapplicated_sale_base_price(float applicated_sale_base_price) {
        this.applicated_sale_base_price = applicated_sale_base_price;
    }

    public boolean issold_during_offer() {
        return sold_during_offer;
    }

    public void setsold_during_offer(boolean sold_during_offer) {
        this.sold_during_offer = sold_during_offer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TicketLine that = (TicketLine) o;
        return Objects.equals(ticket_id, that.ticket_id) && Objects.equals(article_id, that.article_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ticket_id, article_id);
    }
}