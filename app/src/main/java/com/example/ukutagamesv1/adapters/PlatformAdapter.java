package com.example.ukutagamesv1.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ukutagamesv1.R;
import com.example.ukutagamesv1.model.Platform;

import java.util.ArrayList;
import java.util.List;

public class PlatformAdapter extends ListAdapter<Platform,PlatformAdapter.PlatformHolder> {
    private OnItemClickListener listener;

    public PlatformAdapter() {
        super(DIFF_CALLBACKK);
    }

    private static final DiffUtil.ItemCallback<Platform> DIFF_CALLBACKK = new DiffUtil.ItemCallback<Platform>() {
        @Override
        public boolean areItemsTheSame(@NonNull Platform oldItem, @NonNull Platform newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Platform oldItem, @NonNull Platform newItem) {
            return oldItem.getName().equals(newItem.getName()) &&
                    oldItem.getDeveloper().equals(newItem.getDeveloper()) &&
                    oldItem.getLaunch().equals(newItem.getLaunch());
        }
    };

    @NonNull
    @Override
    public PlatformHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.platform_item, parent, false);
        return new PlatformHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PlatformHolder holder, int position) {
        Platform currentPlatform = getItem(position);
        holder.textViewName.setText(currentPlatform.getName());
        holder.textViewLaunch.setText(currentPlatform.getLaunch());
        holder.textViewDeveloper.setText(currentPlatform.getDeveloper());
    }

    public Platform getPlatformAt(int position) {
        return getItem(position);
    }

    class PlatformHolder extends RecyclerView.ViewHolder {
        // private TextView textViewId;
        private TextView textViewName;
        private TextView textViewLaunch;
        private TextView textViewDeveloper;

        public PlatformHolder(@NonNull View itemView) {
            super(itemView);
            //textViewId = itemView.findViewById(R.id.text_view_id);
            textViewName = itemView.findViewById(R.id.text_view_name);
            textViewLaunch = itemView.findViewById(R.id.text_view_launch);
            textViewDeveloper = itemView.findViewById(R.id.text_view_developer);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(listener != null && position != RecyclerView.NO_POSITION){
                        listener.onItemClick(getItem(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Platform platform);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
