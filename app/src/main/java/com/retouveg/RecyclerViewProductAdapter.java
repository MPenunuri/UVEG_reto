package com.retouveg;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.annotation.SuppressLint;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.TextView;
import java.util.ArrayList;
import java.text.NumberFormat;
import java.util.Locale;

public class RecyclerViewProductAdapter extends RecyclerView.Adapter<RecyclerViewProductAdapter.ViewHolder> {
    public ArrayList<Food> filteredProductArrayList;
    RecyclerViewProductAdapter(ArrayList<Food> productArrayList){
        this.filteredProductArrayList = new ArrayList<>(productArrayList);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_view_product, parent, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new ViewHolder(view, mListener);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Food food = filteredProductArrayList.get(position);
        holder.productName.setText(food.name);
        Locale locale = new Locale("es", "MX");
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(locale);
        String formatPrice = currencyFormat.format(food.price);
        holder.productPrice.setText(formatPrice);

        holder.itemView.setOnClickListener(v -> {
            if (mListener != null) {
                mListener.onItemClick(position);
            }
        });
    }
    @Override
    public int getItemCount() {
        return filteredProductArrayList.size();
    }
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    private OnItemClickListener mListener;
    public void setOnItemClickListener(OnItemClickListener listener) { mListener = listener; }
    private static RecyclerViewProductAdapter.OnItemLongClickListener longClickListener;
    public interface OnItemLongClickListener {
        void onItemLongClick(View view, Food food);
    }

    public void setOnItemLongClickListener(RecyclerViewProductAdapter.OnItemLongClickListener listener) {
        longClickListener = listener;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView productName;
        public TextView productPrice;

        public ViewHolder(View itemView, OnItemClickListener listener) {
            super(itemView);
            productName = itemView.findViewById(R.id.productName);
            productPrice = itemView.findViewById(R.id.productPrice);

        }
    }
    @SuppressLint("NotifyDataSetChanged")
    public void filterList(ArrayList<Food> filteredList) {
        filteredProductArrayList.clear();
        filteredProductArrayList.addAll(filteredList);
        notifyDataSetChanged();
    }
}