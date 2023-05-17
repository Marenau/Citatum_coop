package com.corylab.citatum.presentation.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.corylab.citatum.R;
import com.corylab.citatum.databinding.FragmentHubBinding;
import com.corylab.citatum.presentation.activity.MainActivity;
import com.corylab.citatum.presentation.adapter.QuoteAdapter;
import com.corylab.citatum.presentation.adapter.TagAdapter;
import com.corylab.citatum.presentation.viewmodel.QuoteViewModel;
import com.corylab.citatum.presentation.viewmodel.SharedPreferencesViewModel;
import com.corylab.citatum.presentation.viewmodel.TagViewModel;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class HubFragment extends Fragment {

    private FragmentHubBinding binding;
    private MainActivity activity;
    private SharedPreferencesViewModel sharedPreferencesViewModel;
    private QuoteViewModel quoteViewModel;
    private TagViewModel tagViewModel;

    public HubFragment() {
        super(R.layout.fragment_hub);
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
        sharedPreferencesViewModel = new ViewModelProvider(this).get(SharedPreferencesViewModel.class);
        quoteViewModel = new ViewModelProvider(this).get(QuoteViewModel.class);
        tagViewModel = new ViewModelProvider(this).get(TagViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHubBinding.inflate(inflater, container, false);
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
        Calendar calendar = Calendar.getInstance();
        int timeOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        String welcomeText;
        if (timeOfDay >= 6 && timeOfDay < 12) {
            welcomeText = getString(R.string.hf_welcome_morning_text);
        } else if (timeOfDay >= 12 && timeOfDay < 18) {
            welcomeText = getString(R.string.hf_welcome_day_text);
        } else if (timeOfDay >= 18 && timeOfDay < 21) {
            welcomeText = getString(R.string.hf_welcome_evening_text);
        } else {
            welcomeText = getString(R.string.hf_welcome_night_text);
        }
        binding.hfWelcomeText.setText(welcomeText);

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("EEEE, d MMMM, yyyy");
        LocalDate currentDate = LocalDate.now();
        String formattedDate = dateFormatter.format(currentDate);
        formattedDate = formattedDate.substring(0, 1).toUpperCase() + formattedDate.substring(1);
        binding.hfDateText.setText(formattedDate);

        String dayQuoteText = sharedPreferencesViewModel.getString("quote_text", "default");
        String dayQuoteAuthor = sharedPreferencesViewModel.getString("quote_author", "default");
        binding.hfQuoteText.setText(dayQuoteText);
        binding.hfQuoteAuthor.setText(dayQuoteAuthor);

        RecyclerView.LayoutManager quoteLayoutManager = new LinearLayoutManager(activity, RecyclerView.VERTICAL, false);
        binding.hfQuotesRv.setLayoutManager(quoteLayoutManager);
        QuoteAdapter quoteAdapter = new QuoteAdapter(this);
        binding.hfQuotesRv.setAdapter(quoteAdapter);
        quoteViewModel.getThreeQuotes().observe(getViewLifecycleOwner(), quoteAdapter::submitList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false);
        binding.hfTagsRv.setLayoutManager(layoutManager);
        TagAdapter tagAdapter = new TagAdapter(R.layout.base_tag_item);
        binding.hfTagsRv.setAdapter(tagAdapter);
        tagViewModel.getTags().observe(getViewLifecycleOwner(), tagAdapter::submitList);
    }
}
