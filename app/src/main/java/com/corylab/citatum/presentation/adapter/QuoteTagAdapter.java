package com.corylab.citatum.presentation.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.corylab.citatum.R;
import com.corylab.citatum.data.model.Tag;

public class QuoteTagAdapter extends ListAdapter<Tag, QuoteTagAdapter.QuoteTagViewHolder> {

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

    public QuoteTagAdapter() {
        super(DIFF_CALLBACK);
    }

    public final static class QuoteTagViewHolder extends RecyclerView.ViewHolder {

        TextView tagName;

        public QuoteTagViewHolder(@NonNull View itemView) {
            super(itemView);
            tagName = itemView.findViewById(R.id.qti_tag_text);
        }
    }

    @NonNull
    @Override
    public QuoteTagViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View tagsItems = LayoutInflater.from(parent.getContext()).inflate(R.layout.quote_tag_item, parent, false);
        return new QuoteTagViewHolder(tagsItems);
    }

    @Override
    public void onBindViewHolder(@NonNull QuoteTagViewHolder holder, int position) {
        Tag tag = getItem(position);
        holder.tagName.setText(tag.getName());
    }
}