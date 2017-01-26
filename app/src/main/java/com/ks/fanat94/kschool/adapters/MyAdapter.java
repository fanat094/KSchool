package com.ks.fanat94.kschool.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ks.fanat94.kschool.FeedItem;
import com.ks.fanat94.kschool.NewsDetailActivity;
import com.ks.fanat94.kschool.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by rishabh on 26-02-2016.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    ArrayList<FeedItem>feedItems;
    Context context;
    FeedItem current;
    MyViewHolder holder;
    public MyAdapter(Context context,ArrayList<FeedItem>feedItems){
        this.feedItems=feedItems;
        this.context=context;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_card,parent,false);
        holder=new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        current=feedItems.get(position);
        holder.Title.setText(current.getTitle());
        holder.Description.setText(current.getDescription());
        SimpleDateFormat inputFormat = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss Z",
                Locale.UK);
        DateFormat outputFormat = DateFormat.getDateInstance(DateFormat.LONG);
        Date date = null;
        try {
            date = inputFormat.parse(current.getPubDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String outputData = outputFormat.format(date);
        holder.Date.setText(outputData);
       // Picasso.with(context).load(current.getThumbnailUrl()).into(holder.Thumbnail);
              /*  ,new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {
                //Try again online if cache failed
                Picasso.with(context)
                        .load(current.getThumbnailUrl())
                        .into(holder.Thumbnail, new Callback() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError() {
                                Log.v("Picasso","Could not fetch image");
                            }
                        });
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return feedItems.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView Title,Description,Date;
        CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setClickable(true);
            itemView.setOnClickListener(this);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
            Title= (TextView) itemView.findViewById(R.id.card_title);
            Description= (TextView) itemView.findViewById(R.id.card_text);
            Date= (TextView) itemView.findViewById(R.id.date_button);
        }

        @Override
        public void onClick(View view) {
            // Toast.makeText(view.getContext(), "Clicked Country Position = " + getPosition(), Toast.LENGTH_SHORT).show();
            Intent intentitem = new Intent(view.getContext(), NewsDetailActivity.class);
            intentitem.putExtra("ii", getPosition());
            intentitem.putParcelableArrayListExtra("content", (ArrayList<? extends Parcelable>) feedItems);
            view.getContext().startActivity(intentitem);
        }
    }
}