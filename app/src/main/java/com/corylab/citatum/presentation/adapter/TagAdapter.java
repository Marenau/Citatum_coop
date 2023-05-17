package com.corylab.citatum.presentation.adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.corylab.citatum.R;
import com.corylab.citatum.data.model.Tag;

public class TagAdapter extends ListAdapter<Tag, TagAdapter.TagViewHolder> {

    int inflateId;

    private static final DiffUtil.ItemCallback<Tag> DIFF_CALLBACK = new DiffUtil.ItemCallback<Tag>() {
        @Override
        public boolean areItemsTheSame(@NonNull Tag oldItem, @NonNull Tag newItem) {
            return oldItem.getUid() == newItem.getUid();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Tag oldItem, @NonNull Tag newItem) {
            return oldItem.equals(newItem);
        }
    };

    public TagAdapter(int inflateId) {
        super(DIFF_CALLBACK);
        this.inflateId = inflateId;
    }

    public final static class TagViewHolder extends RecyclerView.ViewHolder {
        TextView tagName;

        public TagViewHolder(@NonNull View itemView, int inflateId) {
            super(itemView);

            if (inflateId == R.layout.tag_item)
                tagName = itemView.findViewById(R.id.ti_tag_name);
            else
                tagName = itemView.findViewById(R.id.bti_tag_name);
        }
    }

    @NonNull
    @Override
    public TagViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View tagsItems = LayoutInflater.from(parent.getContext()).inflate(inflateId, parent, false);
        return new TagViewHolder(tagsItems, inflateId);
    }

    @Override
    public void onBindViewHolder(@NonNull TagViewHolder holder, int position) {
        Tag tag = getItem(position);
        holder.tagName.setText(tag.getName());
        holder.itemView.setOnClickListener(view -> {
            Animation animation = AnimationUtils.loadAnimation(view.getContext(), R.anim.image_scale);
            view.startAnimation(animation);
            Bundle transfer = new Bundle();
            transfer.putInt("uid", tag.getUid());
            Navigation.findNavController(view).navigate(R.id.tagFragment, transfer);
        });
    }

    public Tag getTagAtPosition(int position) {
        return getItem(position);
    }
}
