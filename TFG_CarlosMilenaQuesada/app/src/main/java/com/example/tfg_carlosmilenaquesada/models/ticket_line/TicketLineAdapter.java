package com.example.tfg_carlosmilenaquesada.models.ticket_line;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.ItemTouchHelper.SimpleCallback;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tfg_carlosmilenaquesada.R;
import com.example.tfg_carlosmilenaquesada.controllers.local_sqlite_manager.SqliteConnector;

import java.util.ArrayList;
import java.util.Locale;

public class TicketLineAdapter extends RecyclerView.Adapter<TicketLineAdapter.TicketLineItemViewHolder> {

    private ArrayList<TicketLine> ticketLineItemsList;


    private SimpleCallback simpleCallback;

    public TicketLineAdapter(Context context) {
        this.ticketLineItemsList = new ArrayList<>();
        simpleCallback = new SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getBindingAdapterPosition();
                removeTicketLineItem(position);
                ((TicketLinesInterface) context).recalculateTicketAmount();
            }
        };


    }


    @NonNull
    @Override
    public TicketLineItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_ticket_line, parent, false);
        return new TicketLineItemViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull TicketLineItemViewHolder holder, int position) {
        TicketLine ticketLine = ticketLineItemsList.get(position);
        holder.tvItemArticleQuantity.setText(String.valueOf(ticketLine.getArticle_quantity()));
        holder.tvItemArticleName.setText(ticketLine.getArticle_name());
        holder.tvItemLineIsInOffer.setText(ticketLine.isSold_during_offer() ? "Sí" : "No");
        String amount = String.format(Locale.getDefault(), "%.2f", ticketLine.getApplicated_sale_base_price() * (1 + ticketLine.getVat_fraction()) * ticketLine.getArticle_quantity());
        holder.tvItemLineTotalAmount.setText(amount);
        holder.itemView.setOnClickListener(v -> {
        });
    }


    @Override
    public int getItemCount() {
        return ticketLineItemsList.size();
    }

    public SimpleCallback getSimpleCallback() {
        return simpleCallback;
    }

    public ArrayList<TicketLine> getTicketLinesList() {
        return this.ticketLineItemsList;
    }

    public void removeTicketLineItem(int position) {
        ticketLineItemsList.remove(position);
        notifyItemRemoved(position);
    }

    public void addTicketLine(TicketLine ticketLine, int position) {
        ticketLineItemsList.add(ticketLine);
        notifyItemInserted(position);
    }

    public void replaceTicketLine(int oldTicketLinePosition, TicketLine newTicketLine) {
        ticketLineItemsList.set(oldTicketLinePosition, newTicketLine);
        notifyItemChanged(oldTicketLinePosition);
    }

    public float getTotalFromTicketLinesAmount() {
        float totalAmount = 0.0f;
        for (TicketLine ticketLine : ticketLineItemsList) {
            totalAmount += (ticketLine.getArticle_quantity() * (ticketLine.getApplicated_sale_base_price() * (1 + ticketLine.getVat_fraction())));
        }
        return totalAmount;

    }


    public static class TicketLineItemViewHolder extends RecyclerView.ViewHolder {
        public TextView tvItemArticleQuantity;
        public TextView tvItemArticleName;
        public TextView tvItemLineIsInOffer;
        public TextView tvItemLineTotalAmount;


        public TicketLineItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItemArticleQuantity = itemView.findViewById(R.id.tvItemArticleQuantity);
            tvItemArticleName = itemView.findViewById(R.id.tvItemArticleName);
            tvItemLineIsInOffer = itemView.findViewById(R.id.tvItemLineIsInOffer);
            tvItemLineTotalAmount = itemView.findViewById(R.id.tvItemLineTotalAmount);
        }
    }


}
