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
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.corylab.citatum.R;
import com.corylab.citatum.databinding.FragmentBottomNavigationBinding;
import com.corylab.citatum.presentation.activity.MainActivity;

public class BottomNavigationFragment extends Fragment {

    private FragmentBottomNavigationBinding binding;
    private MainActivity activity;

    public BottomNavigationFragment() {
        super(R.layout.fragment_bottom_navigation);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        activity = (MainActivity) context;
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentBottomNavigationBinding.inflate(inflater, container, false);
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
        View navigationContainerView = activity.findViewById(R.id.nav_container_view);
        binding.bnButton.setOnClickListener(view -> {
            NavController navController = Navigation.findNavController(navigationContainerView);
            int currentDestination = navController.getCurrentDestination().getId();
            int destination = R.id.quoteCreateFragment;
            if (currentDestination != destination) {
                navController.navigate(destination);
            }
        });

        Animation animation = AnimationUtils.loadAnimation(activity, R.anim.image_scale);

        DrawerLayout drawer = activity.findViewById(R.id.ma_drawer);
        binding.bnMenuIcon.setOnClickListener(view -> {
            view.startAnimation(animation);
            drawer.openDrawer(GravityCompat.START);
        });

        binding.bnSearchIcon.setOnClickListener(view -> {
            view.startAnimation(animation);
            NavController navController = Navigation.findNavController(navigationContainerView);
            int currentDestination = navController.getCurrentDestination().getId();
            int destination = R.id.searchFragment;
            if (currentDestination != destination) {
                navController.navigate(R.id.searchFragment);
            }
        });
    }
}