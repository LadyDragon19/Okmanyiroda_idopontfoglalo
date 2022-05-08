package com.example.okmanyiroda;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AppointmentItemAdapter extends RecyclerView.Adapter<AppointmentItemAdapter.ViewHolder> implements Filterable {
    private ArrayList<AppointmentItem> mAppointmentItemData;
    private ArrayList<AppointmentItem> mAppointmentItemDataAll;
    private Context mContext;
    private int lastPosition = -1;

    //TODO

    AppointmentItemAdapter(Context context, ArrayList<AppointmentItem> itemsData) {
        this.mAppointmentItemData = itemsData;

    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.list_items, parent, false));
    }


    @Override
    public void onBindViewHolder(AppointmentItemAdapter.ViewHolder holder, int position) {
        AppointmentItem currentItem = mAppointmentItemData.get(position);

        // Populate the textviews with data.
        holder.bindTo(currentItem);


        if(holder.getAdapterPosition() > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.slide_in_row);
            holder.itemView.startAnimation(animation);
            lastPosition = holder.getAdapterPosition();
        }
    }

    @Override
    public int getItemCount() {
        return mAppointmentItemData.size();
    }


    @Override
    public Filter getFilter() {
        return shopingFilter;
    }

    private Filter shopingFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<AppointmentItem> filteredList = new ArrayList<>();
            FilterResults results = new FilterResults();

            if(charSequence == null || charSequence.length() == 0) {
                results.count = mAppointmentItemDataAll.size();
                results.values = mAppointmentItemDataAll;
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();
                for(AppointmentItem item : mAppointmentItemDataAll) {
                    if(item.getIgazolvanyTipus().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }
                }

                results.count = filteredList.size();
                results.values = filteredList;
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            mAppointmentItemData = (ArrayList)filterResults.values;
            notifyDataSetChanged();
        }
    };

    class ViewHolder extends RecyclerView.ViewHolder {

        // Member Variables for the TextViews

        private TextView mEmail;
        private TextView mIgTipus;
        private TextView mIdopont;

        ViewHolder(View itemView) {
            super(itemView);

            // Initialize the views.
            mEmail = itemView.findViewById(R.id.emailTV);
            mIgTipus = itemView.findViewById(R.id.igazolvanyTipusTV);
            mIdopont = itemView.findViewById(R.id.idopontTV);


        }

        void bindTo(AppointmentItem currentItem){
            mEmail.setText(currentItem.getEmail());
            mIgTipus.setText(currentItem.getIgazolvanyTipus());
            mIdopont.setText(currentItem.getIdopont());
            itemView.findViewById(R.id.delete).setOnClickListener(view -> ((AppointmentListActivity)mContext).deleteItem(currentItem));

        }
    }
}

