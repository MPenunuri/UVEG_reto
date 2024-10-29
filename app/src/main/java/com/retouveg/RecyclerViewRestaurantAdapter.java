package com.retouveg;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.annotation.SuppressLint;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.TextView;
import java.util.ArrayList;


public class RecyclerViewRestaurantAdapter extends RecyclerView.Adapter<RecyclerViewRestaurantAdapter.ViewHolder> {
    private static ArrayList<Restaurant> filteredRestaurantArrayList;
    RecyclerViewRestaurantAdapter(ArrayList<Restaurant> restaurantArrayList){
        filteredRestaurantArrayList = new ArrayList<>(restaurantArrayList);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_view_restaurant, parent, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new ViewHolder(view, mListener);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Restaurant restaurant = filteredRestaurantArrayList.get(position);
            holder.textView.setText(restaurant.name);
    }
    @Override
    public int getItemCount() {
        return filteredRestaurantArrayList.size();
    }
    public interface OnItemClickListener {
        void onItemClick(Restaurant restaurant);
    }
    private OnItemClickListener mListener;
    public void setOnItemClickListener(OnItemClickListener listener) { mListener = listener; }

    private static OnItemLongClickListener longClickListener;
    public interface OnItemLongClickListener {
        void onItemLongClick(View view, Restaurant restaurant);
    }
    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        longClickListener = listener;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        private final OnItemClickListener mListener;
        public ViewHolder(View itemView, OnItemClickListener listener) {
            super(itemView);
            textView = itemView.findViewById(R.id.restaurantName);
            this.mListener = listener;

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && mListener != null) {
                    mListener.onItemClick(filteredRestaurantArrayList.get(getAdapterPosition()));
                }
            });

            itemView.setOnLongClickListener(view -> {
                if (longClickListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        longClickListener.onItemLongClick(view, filteredRestaurantArrayList.get(getAdapterPosition()));
                        return true;
                    }
                }
                return false;
            });
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    public void filterList(ArrayList<Restaurant> filteredList) {
        filteredRestaurantArrayList.clear();
        filteredRestaurantArrayList.addAll(filteredList);
        notifyDataSetChanged();
    }
}