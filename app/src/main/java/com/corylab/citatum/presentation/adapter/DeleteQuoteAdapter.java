package com.corylab.citatum.presentation.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.corylab.citatum.R;
import com.corylab.citatum.data.model.Quote;
import com.corylab.citatum.presentation.viewmodel.QuoteTagJoinViewModel;

import java.text.DateFormat;

public class DeleteQuoteAdapter extends ListAdapter<Quote, DeleteQuoteAdapter.DeleteQuoteViewHolder> {

    private final Fragment fragment;
    private final QuoteTagJoinViewModel quoteTagJoinViewModel;

    private static final DiffUtil.ItemCallback<Quote> DIFF_CALLBACK = new DiffUtil.ItemCallback<Quote>() {
        @Override
        public boolean areItemsTheSame(@NonNull Quote oldItem, @NonNull Quote newItem) {
            return oldItem.getUid() == newItem.getUid();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Quote oldItem, @NonNull Quote newItem) {
            return oldItem.equals(newItem);
        }
    };

    public DeleteQuoteAdapter(Fragment fragment) {
        super(DIFF_CALLBACK);
        this.fragment = fragment;
        this.quoteTagJoinViewModel = new ViewModelProvider(fragment).get(QuoteTagJoinViewModel.class);
    }

    public final static class DeleteQuoteViewHolder extends RecyclerView.ViewHolder {

        TextView quoteText, authorTitleText, dataText;
        RecyclerView tagsRv;

        public DeleteQuoteViewHolder(@NonNull View itemView) {
            super(itemView);
            quoteText = itemView.findViewById(R.id.qi_quote_text);
            authorTitleText = itemView.findViewById(R.id.qi_author_title_text);
            dataText = itemView.findViewById(R.id.qi_data_text);
            tagsRv = itemView.findViewById(R.id.qi_rv);
        }
    }

    @NonNull
    @Override
    public DeleteQuoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View quotesItems = LayoutInflater.from(parent.getContext()).inflate(R.layout.quote_item, parent, false);
        return new DeleteQuoteViewHolder(quotesItems);
    }

    @Override
    public void onBindViewHolder(@NonNull DeleteQuoteViewHolder holder, int position) {
        Quote quote = getItem(position);
        holder.quoteText.setText(quote.getText());
        String authorTitleText = "© " + quote.getAuthor() + ", " + quote.getTitle();
        holder.authorTitleText.setText(authorTitleText);
        holder.dataText.setText(DateFormat.getDateInstance(DateFormat.SHORT).format(quote.getDate()));

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(fragment.getContext(), RecyclerView.HORIZONTAL, false);
        holder.tagsRv.setLayoutManager(layoutManager);
        QuoteTagAdapter adapter = new QuoteTagAdapter();
        holder.tagsRv.setAdapter(adapter);
        quoteTagJoinViewModel.getTagsForQuote(quote.getUid()).observe(fragment.getViewLifecycleOwner(), adapter::submitList);
    }

    public Quote getQuoteAtPosition(int position) {
        return getItem(position);
    }
}
