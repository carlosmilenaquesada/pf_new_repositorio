package com.example.tfg_carlosmilenaquesada.models.capital_operation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tfg_carlosmilenaquesada.R;


import java.util.ArrayList;
import java.util.Locale;

public class CapitalOperationAdapter extends RecyclerView.Adapter<CapitalOperationAdapter.CapitalOperationItemViewHolder> {
    private ArrayList<CapitalOperationLine> capitalOperationsList;
    Context context;


    public CapitalOperationAdapter(Context context) {
        this.capitalOperationsList = new ArrayList<>();
        this.context = context;
    }


    @NonNull
    @Override
    public CapitalOperationAdapter.CapitalOperationItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_capital_operation, parent, false);
        return new CapitalOperationAdapter.CapitalOperationItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CapitalOperationItemViewHolder holder, int position) {
        CapitalOperationLine capitalOperation = capitalOperationsList.get(position);
        holder.tvCapitalOperationDescription.setText(capitalOperation.getDescription());

        float capitalAmount = capitalOperation.getAmount();
        String truncateAmount = String.format(Locale.US, "%.2f", capitalAmount);
        if(capitalAmount > 0){
            holder.etDepositAmount.setText(truncateAmount);
        }else{
            holder.etWithdrawalAmount.setText(truncateAmount);
        }

    }




    @Override
    public int getItemCount() {
        return capitalOperationsList.size();
    }



    public ArrayList<CapitalOperationLine> getCapitalOperationsList() {
        return this.capitalOperationsList;
    }

    public void removeCustomerItem(int position) {
        capitalOperationsList.remove(position);
        notifyItemRemoved(position);
    }

    public void addCapitalOperation(CapitalOperationLine capitalOperation, int position) {
        capitalOperationsList.add(capitalOperation);
        notifyItemInserted(position);
    }


    public static class CapitalOperationItemViewHolder extends RecyclerView.ViewHolder {
        public TextView tvCapitalOperationDescription;
        public EditText etDepositAmount;
        public EditText etWithdrawalAmount;


        public CapitalOperationItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCapitalOperationDescription = itemView.findViewById(R.id.tvCapitalOperationDescription);
            etDepositAmount = itemView.findViewById(R.id.etDepositAmount);
            etWithdrawalAmount = itemView.findViewById(R.id.etWithdrawalAmount);


        }
    }
}
