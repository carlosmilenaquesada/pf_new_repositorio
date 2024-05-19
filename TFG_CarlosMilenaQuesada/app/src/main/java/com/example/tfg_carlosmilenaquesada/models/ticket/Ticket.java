package com.example.tfg_carlosmilenaquesada.models.ticket;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class Ticket implements Serializable {
    //la id del ticket (en mysql también) será: tiempo en milisegundos del momento de su creación.
    private String ticket_id;
    private String sale_date;
    private String customer_tax_id;
    private String ticket_status_id;
    private String payment_method_id;
    private String payment_method_name;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Ticket() {
        long currentTimeInMillis = System.currentTimeMillis();
        this.ticket_id = String.valueOf(currentTimeInMillis);
        this.sale_date = LocalDateTime.ofInstant(Instant.ofEpochMilli(currentTimeInMillis), ZoneId.systemDefault()).toString();
        this.ticket_status_id = "processing";
        this.payment_method_id = "PAYMENT001";
        this.payment_method_name = "undefined";
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Ticket(String customer_tax_id, String ticket_status_id, String payment_method_id, String payment_method_name) {
        long currentTimeInMillis = System.currentTimeMillis();
        this.ticket_id = String.valueOf(currentTimeInMillis);
        this.sale_date = LocalDateTime.ofInstant(Instant.ofEpochMilli(currentTimeInMillis), ZoneId.systemDefault()).toString();
        this.customer_tax_id = customer_tax_id;
        this.ticket_status_id = ticket_status_id;
        this.payment_method_id = payment_method_id;
        this.payment_method_name = payment_method_name;
    }

    public Ticket(String ticket_id, String sale_date, String customer_tax_id, String ticket_status_id, String payment_method_id, String payment_method_name) {
        this.ticket_id = ticket_id;
        this.sale_date = sale_date;
        this.customer_tax_id = customer_tax_id;
        this.ticket_status_id = ticket_status_id;
        this.payment_method_id = payment_method_id;
        this.payment_method_name = payment_method_name;
    }

    public String getTicket_id() {
        return ticket_id;
    }


    public String getSale_date() {
        return sale_date;
    }


    public String getCustomer_tax_id() {
        return customer_tax_id;
    }

    public void setCustomer_tax_id(String customer_tax_id) {
        this.customer_tax_id = customer_tax_id;
    }

    public String getTicket_status_id() {
        return ticket_status_id;
    }

    public void setTicket_status_id(String ticket_status_id) {
        this.ticket_status_id = ticket_status_id;
    }

    public String getPayment_method_id() {
        return payment_method_id;
    }

    public void setPayment_method_id(String payment_method_id) {
        this.payment_method_id = payment_method_id;
    }

    public String getPayment_method_name() {
        return payment_method_name;
    }

    public void setPayment_method_name(String payment_method_name) {
        this.payment_method_name = payment_method_name;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "ticket_id='" + ticket_id + '\'' +
                ", sale_date='" + sale_date + '\'' +
                ", customer_tax_id='" + customer_tax_id + '\'' +
                ", ticket_status_id='" + ticket_status_id + '\'' +
                ", payment_method_id='" + payment_method_id + '\'' +
                ", payment_method_name='" + payment_method_name + '\'' +
                '}';
    }
}
