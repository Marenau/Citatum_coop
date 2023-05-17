package com.corylab.citatum.presentation.fragment;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.corylab.citatum.R;
import com.corylab.citatum.data.model.Quote;
import com.corylab.citatum.databinding.FragmentRepositoryBinding;
import com.corylab.citatum.presentation.activity.MainActivity;
import com.corylab.citatum.presentation.adapter.QuoteAdapter;
import com.corylab.citatum.presentation.snackbar.CustomSnackBar;
import com.corylab.citatum.presentation.viewmodel.QuoteViewModel;

public class RepositoryFragment extends Fragment {

    private FragmentRepositoryBinding binding;
    private MainActivity activity;
    private QuoteViewModel quoteViewModel;

    public RepositoryFragment() {
        super(R.layout.fragment_repository);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        activity = (MainActivity) context;
        super.onAttach(context);
    }

    @Nullable
    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        if (enter) {
            return AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_left);
        } else {
            return AnimationUtils.loadAnimation(getContext(), R.anim.slide_out_left);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        quoteViewModel = new ViewModelProvider(this).get(QuoteViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentRepositoryBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void init() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(activity, RecyclerView.VERTICAL, false);
        binding.rfRecyclerView.setLayoutManager(layoutManager);
        QuoteAdapter quoteAdapter = new QuoteAdapter(this);
        binding.rfRecyclerView.setAdapter(quoteAdapter);
        quoteViewModel.getAllActive().observe(getViewLifecycleOwner(), quoteAdapter::submitList);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                int dragFlags = 0;
                int swipeFlags = ItemTouchHelper.RIGHT;
                return makeMovementFlags(dragFlags, swipeFlags);
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Quote quote = new Quote(quoteAdapter.getQuoteAtPosition(position));
                if (direction == ItemTouchHelper.RIGHT) {
                    if (quote.getBookmarkFlag() == 1) {
                        quote.setBookmarkFlag(0);
                        CustomSnackBar.createSnackbar(getView(), activity, R.string.qc_quote_bookmark_disable);
                    } else {
                        quote.setBookmarkFlag(1);
                        CustomSnackBar.createSnackbar(getView(), activity, R.string.qc_quote_bookmark_enable);
                    }
                    quoteViewModel.update(quote);
                }
            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                View itemView = viewHolder.itemView;
                float alpha = 1.0f - Math.abs(dX) / itemView.getWidth();
                Paint paint = new Paint();
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    if (dX > 0) {
                        paint.setColor(ContextCompat.getColor(activity, R.color.light_blue));
                        paint.setAlpha((int) (alpha * 255));
                        float cornerRadius = 20f;
                        RectF rect = new RectF(itemView.getLeft(), itemView.getTop(), dX + itemView.getRight() / 2, itemView.getBottom());
                        c.drawRoundRect(rect, cornerRadius, cornerRadius, paint);

                        Drawable basketIcon = ContextCompat.getDrawable(activity, R.drawable.icon_bookmark);
                        Drawable.ConstantState constantState = basketIcon.mutate().getConstantState();
                        Drawable drawableCopy = constantState.newDrawable();
                        drawableCopy.setTint(ContextCompat.getColor(activity, R.color.background_color));
                        int iconWidth = drawableCopy.getIntrinsicWidth();
                        int iconHeight = drawableCopy.getIntrinsicHeight();
                        int iconTop = itemView.getTop() + (itemView.getHeight() - iconHeight) / 2;
                        int iconMargin = (itemView.getHeight() - iconHeight) / 5;
                        int iconLeft = itemView.getLeft() + iconMargin;
                        int iconRight = itemView.getLeft() + iconMargin + iconWidth;
                        int iconBottom = iconTop + iconHeight;
                        drawableCopy.setBounds(iconLeft, iconTop, iconRight, iconBottom);
                        drawableCopy.draw(c);
                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        });
        itemTouchHelper.attachToRecyclerView(binding.rfRecyclerView);
    }
}