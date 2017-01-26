package com.ks.fanat94.kschool.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ks.fanat94.kschool.R;

import java.util.ArrayList;

/**
 * Created by Zeeshan Ahmed on 1/15/2016.
 */
public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.UserViewHolder> {
    ArrayList<ScheduleItem> mDataSet;
    UserViewHolder holder;
    Context context;
    ScheduleItem current;

    public ScheduleAdapter(Context context,ArrayList<ScheduleItem> mDataSet) {
        this.mDataSet = mDataSet;
        this.context=context;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_card_schedule, parent, false);
        holder=new UserViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final UserViewHolder holder, int position) {
        current=mDataSet.get(position);
        holder.name_les.setText(current.getName_Les());
        holder.time_les.setText(current.getTime_Les());
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder{
        CardView cardViewschedule;
        TextView name_les, time_les;

        UserViewHolder(View itemView) {
            super(itemView);
            cardViewschedule = (CardView) itemView.findViewById(R.id.card_view_schedule);
            name_les = (TextView) itemView.findViewById(R.id.name_les);
            time_les = (TextView) itemView.findViewById(R.id.time_les);
        }
    }
}
