package com.corylab.citatum.presentation.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.corylab.citatum.R;
import com.corylab.citatum.data.model.Tag;
import com.corylab.citatum.databinding.FragmentTagCreateBinding;
import com.corylab.citatum.presentation.activity.MainActivity;
import com.corylab.citatum.presentation.viewmodel.TagViewModel;
import com.google.android.material.snackbar.Snackbar;

public class TagCreateFragment extends Fragment {

    private FragmentTagCreateBinding binding;
    private MainActivity activity;
    private TagViewModel tagViewModel;

    public TagCreateFragment() {
        super(R.layout.fragment_tag_create);
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
        binding = FragmentTagCreateBinding.inflate(inflater, container, false);
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
        if (trBundle != null) {
            if (trBundle.containsKey("uid")) {
                uid[0] = trBundle.getInt("uid");
                Tag tag = tagViewModel.getTagById(uid[0]);
                binding.tcfNameEt.setText(tag.getName());
            }
        } else {
            binding.tcfNameEt.requestFocus();
            InputMethodManager inputMethodManagerShow = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManagerShow.showSoftInput(binding.tcfNameEt, InputMethodManager.SHOW_IMPLICIT);
        }

        Animation animation = AnimationUtils.loadAnimation(activity, R.anim.image_scale);
        binding.tcfConfirmIcon.setOnClickListener(view -> {
            view.startAnimation(animation);
            if (uid[0] == -1) {
                tagViewModel.insert(new Tag(binding.tcfNameEt.getText().toString().isEmpty() ? getString(R.string.tc_autosubstitution_name_text) : binding.tcfNameEt.getText().toString()));
                Navigation.findNavController(view).navigateUp();
                createSnackbar(R.string.tc_tag_added);
            } else {
                tagViewModel.update(new Tag(uid[0], binding.tcfNameEt.getText().toString()));
                createSnackbar(R.string.tc_tag_update);
            }
        });
    }

    private void createSnackbar(int id) {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
        );
        params.gravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;
        params.topMargin = 15;
        Snackbar snackbar = Snackbar.make(getView(), "", Snackbar.LENGTH_SHORT);
        snackbar.getView().setLayoutParams(params);
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.show_snackbar, null);
        TextView message = customView.findViewById(R.id.ss_text);
        message.setText(getString(id));
        snackbar.getView().setBackgroundResource(R.drawable.empty_drawable);
        Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbar.getView();
        snackbarLayout.addView(customView, 0);
        snackbar.show();
    }
}
