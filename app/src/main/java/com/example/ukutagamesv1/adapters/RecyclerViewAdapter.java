package com.example.ukutagamesv1.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.ukutagamesv1.activities.GameActivity;
import com.example.ukutagamesv1.activities.PlatformsActivity;
import com.example.ukutagamesv1.model.Game;
import com.example.ukutagamesv1.model.Platform;
import com.example.ukutagamesv1.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> implements Filterable {


    private Context mContext;
    private List<Game> mData;
    private List<Game> mDataFull;

    RequestOptions option;

    public RecyclerViewAdapter(Context mContext, List<Game> mData) {
        this.mContext = mContext;
        this.mData = mData;

        option = new RequestOptions().centerCrop().placeholder(R.drawable.loading_shape).error(R.drawable.loading_shape);

        mDataFull = new ArrayList<>(mData);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.game_row_item, parent,false);
        final MyViewHolder viewHolder =  new MyViewHolder(view);
        viewHolder.view_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(mContext,GameActivity.class);
                i.putExtra("game_id", mData.get(viewHolder.getAdapterPosition()).getId());
                i.putExtra("game_name", mData.get(viewHolder.getAdapterPosition()).getName());
                i.putExtra("game_genre", mData.get(viewHolder.getAdapterPosition()).getGenres());
                i.putExtra("game_release", mData.get(viewHolder.getAdapterPosition()).getRelease());
                i.putExtra("game_platforms", mData.get(viewHolder.getAdapterPosition()).getPlatforms());
                i.putExtra("game_rating", mData.get(viewHolder.getAdapterPosition()).getRating());
                i.putExtra("game_img", mData.get(viewHolder.getAdapterPosition()).getImage_url());

                mContext.startActivity(i);
            }
        });

        return viewHolder;
    }



    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.tv_name.setText(mData.get(position).getName());
        holder.tv_genre.setText(mData.get(position).getGenres());
        holder.tv_rating.setText(mData.get(position).getRating());

        Glide.with(mContext).load(mData.get(position).getImage_url()).apply(option).into(holder.img_thumbnail);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tv_name;
        TextView tv_genre;
        TextView tv_rating;
        ImageView img_thumbnail;
        LinearLayout view_container;


        public MyViewHolder(View itemView)
        {
            super(itemView);

            view_container = itemView.findViewById(R.id.container);
            tv_name = itemView.findViewById(R.id.game_name);
            tv_genre = itemView.findViewById(R.id.genre);
            tv_rating = itemView.findViewById(R.id.rating);
            img_thumbnail = itemView.findViewById(R.id.thumbnail);

        }
    }

    @Override
    public Filter getFilter() {
        return gFilter;
    }

    private Filter gFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Game> filteredList = new ArrayList<>();

            if(constraint == null || constraint.length() == 0)
            {
                filteredList.addAll(mDataFull);
            }
            else
            {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Game item : mDataFull){
                    if(item.getName().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mData.clear();
            mData.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}
