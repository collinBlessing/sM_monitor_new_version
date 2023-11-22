package com.example.sm_monitor_new_version.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.sm_monitor_new_version.R;
import com.example.sm_monitor_new_version.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Get the HomeViewModel
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        // Initialize AutoCompleteTextView
        AutoCompleteTextView autoCompleteTextView = root.findViewById(R.id.auto_complete_text);

        // Observe changes in dropdown items
        homeViewModel.getDropdownItems().observe(getViewLifecycleOwner(), items -> {
            ArrayAdapter<String> adapter_items = new ArrayAdapter<>(requireContext(), R.layout.list_item, items);
            autoCompleteTextView.setAdapter(adapter_items);
        });

        autoCompleteTextView.setOnItemClickListener((adapterView, view, i, l) -> {
            // Handle item selection as needed
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
