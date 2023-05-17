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
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.corylab.citatum.R;
import com.corylab.citatum.data.model.Tag;
import com.corylab.citatum.databinding.FragmentTagsBinding;
import com.corylab.citatum.presentation.activity.MainActivity;
import com.corylab.citatum.presentation.adapter.TagAdapter;
import com.corylab.citatum.presentation.viewmodel.TagViewModel;
import com.google.android.material.snackbar.Snackbar;

public class TagsFragment extends Fragment {

    private FragmentTagsBinding binding;
    private MainActivity activity;
    private TagViewModel tagViewModel;

    public TagsFragment() {
        super(R.layout.fragment_tags);
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
        tagViewModel = new ViewModelProvider(this).get(TagViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentTagsBinding.inflate(inflater, container, false);
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
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        binding.tgfRecyclerView.setLayoutManager(layoutManager);
        TagAdapter tagAdapter = new TagAdapter(R.layout.tag_item);
        binding.tgfRecyclerView.setAdapter(tagAdapter);
        tagViewModel.getTags().observe(getViewLifecycleOwner(), tagAdapter::submitList);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                int dragFlags = 0;
                int swipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                return makeMovementFlags(dragFlags, swipeFlags);
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Tag thisTag = tagAdapter.getTagAtPosition(position);
                if (direction == ItemTouchHelper.LEFT) {
                    tagViewModel.delete(thisTag);
                    createUndoSnackbar(getView(), thisTag);
                } else if (direction == ItemTouchHelper.RIGHT) {
                    Bundle transfer = new Bundle();
                    transfer.putInt("uid", thisTag.getUid());
                    Navigation.findNavController(getView()).navigate(R.id.action_tagsFragment_to_tagCreateFragment, transfer);
                }
            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                View itemView = viewHolder.itemView;
                float alpha = 1.0f - Math.abs(dX) / itemView.getWidth();
                Paint paint = new Paint();
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    int color;
                    Drawable drawableIcon;
                    RectF rect;
                    float cornerRadius = 20f;
                    if (dX > 0) {
                        color = ContextCompat.getColor(activity, R.color.light_blue);
                        drawableIcon = ContextCompat.getDrawable(activity, R.drawable.icon_pencil);
                        rect = new RectF(itemView.getLeft(), itemView.getTop(), dX + itemView.getRight() / 2, itemView.getBottom());
                    } else {
                        color = ContextCompat.getColor(activity, R.color.light_red);
                        drawableIcon = ContextCompat.getDrawable(activity, R.drawable.icon_basket);
                        rect = new RectF(itemView.getRight() - itemView.getRight() / 2 + dX, itemView.getTop(), itemView.getRight(), itemView.getBottom());
                    }
                    paint.setColor(color);
                    paint.setAlpha((int) (alpha * 255));
                    c.drawRoundRect(rect, cornerRadius, cornerRadius, paint);
                    Drawable.ConstantState constantState = drawableIcon.mutate().getConstantState();
                    Drawable drawableCopy = constantState.newDrawable();
                    drawableCopy.setTint(ContextCompat.getColor(activity, R.color.background_color));
                    int iconWidth = (int) (drawableCopy.getIntrinsicWidth() / 1.5);
                    int iconHeight = (int) (drawableCopy.getIntrinsicHeight() / 1.5);
                    int iconTop = itemView.getTop() + (itemView.getHeight() - iconHeight) / 2;
                    int iconMargin = (itemView.getHeight() - iconHeight) / 2;
                    int iconLeft = (dX > 0) ? itemView.getLeft() + iconMargin : itemView.getRight() - iconMargin - iconWidth;
                    int iconRight = (dX > 0) ? itemView.getLeft() + iconMargin + iconWidth : itemView.getRight() - iconMargin;
                    int iconBottom = iconTop + iconHeight;
                    drawableCopy.setBounds(iconLeft, iconTop, iconRight, iconBottom);
                    drawableCopy.draw(c);
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        });
        itemTouchHelper.attachToRecyclerView(binding.tgfRecyclerView);

        Animation animation = AnimationUtils.loadAnimation(activity, R.anim.image_scale);
        binding.tgfTagIcon.setOnClickListener(view -> {
            binding.tgfTagIcon.startAnimation(animation);
            Navigation.findNavController(view).navigate(R.id.action_tagsFragment_to_tagCreateFragment);
        });
    }

    private void createUndoSnackbar(View view, Tag tag) {
        Snackbar snackbar = Snackbar.make(view, "", Snackbar.LENGTH_SHORT);
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.show_undo_snackbar, null);
        TextView message = customView.findViewById(R.id.sus_text);
        String messageText = getString(R.string.sus_text) + " " + tag.getName() + " " + getString(R.string.sus_delete_text);
        message.setText(messageText);
        Button undoButton = customView.findViewById(R.id.sus_undo_text);
        Animation animation = AnimationUtils.loadAnimation(activity, R.anim.image_scale);
        undoButton.setOnClickListener(view1 -> {
            view1.startAnimation(animation);
            tagViewModel.insert(tag);
            undoButton.setOnClickListener(null);
        });
        snackbar.getView().setBackgroundResource(R.drawable.empty_drawable);
        Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbar.getView();
        snackbarLayout.addView(customView, 0);
        snackbar.show();
    }
}
