package com.corylab.citatum.presentation.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.corylab.citatum.R;
import com.corylab.citatum.data.model.Tag;

import java.util.ArrayList;
import java.util.List;

public class SelectTagAdapter extends ListAdapter<Tag, SelectTagAdapter.SelectTagViewHolder> {

    private final List<Tag> chosenList = new ArrayList<>();

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

    public SelectTagAdapter() {
        super(DIFF_CALLBACK);
    }

    public final static class SelectTagViewHolder extends RecyclerView.ViewHolder {

        CheckBox checkBox;

        public SelectTagViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.sti_chb);
        }
    }

    @NonNull
    @Override
    public SelectTagViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View tagsItems = LayoutInflater.from(parent.getContext()).inflate(R.layout.select_tag_item, parent, false);
        return new SelectTagViewHolder(tagsItems);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectTagViewHolder holder, int position) {
        Tag tag = getItem(position);
        holder.checkBox.setText(tag.getName());
        holder.checkBox.setOnCheckedChangeListener((compoundButton, b) -> {
            Tag temp = getItem(holder.getAdapterPosition());
            if (b)
                chosenList.add(temp);
            else
                chosenList.remove(temp);
        });
    }

    public List<Tag> getChosenList() {
        return chosenList;
    }

    public List<Tag> getUnchosenList() {
        List<Tag> temp = new ArrayList<>(getCurrentList());
        temp.removeAll(chosenList);
        return temp;
    }
}
