package com.corylab.citatum.presentation.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.corylab.citatum.R;
import com.corylab.citatum.data.entity.QuoteTagJoin;
import com.corylab.citatum.data.model.Tag;
import com.corylab.citatum.databinding.FragmentTagSelectionBinding;
import com.corylab.citatum.presentation.activity.MainActivity;
import com.corylab.citatum.presentation.adapter.QuoteTagAdapter;
import com.corylab.citatum.presentation.adapter.SelectTagAdapter;
import com.corylab.citatum.presentation.viewmodel.QuoteTagJoinViewModel;
import com.corylab.citatum.presentation.viewmodel.TagViewModel;

import java.util.List;

public class TagSelectionFragment extends Fragment {

    private FragmentTagSelectionBinding binding;
    private MainActivity activity;
    private QuoteTagJoinViewModel quoteTagJoinViewModel;
    private TagViewModel tagViewModel;

    public TagSelectionFragment() {
        super(R.layout.fragment_tag_selection);
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
        quoteTagJoinViewModel = new ViewModelProvider(this).get(QuoteTagJoinViewModel.class);
        tagViewModel = new ViewModelProvider(this).get(TagViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentTagSelectionBinding.inflate(inflater, container, false);
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
        final int[] uid = {-1};
        Bundle trBundle = getArguments();
        uid[0] = trBundle.getInt("uid");
        Log.i("ID", String.valueOf(uid[0]));

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(activity, RecyclerView.VERTICAL, false);
        binding.stfChbRv.setLayoutManager(layoutManager);
        SelectTagAdapter tagAdapter = new SelectTagAdapter();
        binding.stfChbRv.setAdapter(tagAdapter);
        tagViewModel.getTags().observe(getViewLifecycleOwner(), tagAdapter::submitList);

        RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false);
        binding.stfCurrentTagsRv.setLayoutManager(layoutManager2);
        QuoteTagAdapter tagAdapter2 = new QuoteTagAdapter();
        binding.stfCurrentTagsRv.setAdapter(tagAdapter2);
        quoteTagJoinViewModel.getTagsForQuote(uid[0]).observe(getViewLifecycleOwner(), tagAdapter2::submitList);

        Animation animation = AnimationUtils.loadAnimation(activity, R.anim.image_scale);

        binding.stfConfirmIcon.setOnClickListener(view -> {
            view.startAnimation(animation);
            List<Tag> chosenList = tagAdapter.getChosenList();
            for (Tag t : chosenList)
                quoteTagJoinViewModel.insert(new QuoteTagJoin(uid[0], t.getUid()));
            List<Tag> unchosenList = tagAdapter.getUnchosenList();
            for (Tag t : unchosenList)
                quoteTagJoinViewModel.delete(new QuoteTagJoin(uid[0], t.getUid()));
            Navigation.findNavController(view).navigateUp();
        });

        binding.stfTagIcon.setOnClickListener(view -> {
            view.startAnimation(animation);
            Navigation.findNavController(view).navigate(R.id.action_tagSelectionFragment_to_tagCreateFragment);
        });
    }
}
