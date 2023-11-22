package com.example.sm_monitor_new_version.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.sm_monitor_new_version.R;
import com.example.sm_monitor_new_version.databinding.FragmentHomeBinding;

import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private HomeViewModel homeViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.autoCompleteText;

        // Get the HomeViewModel
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        // Initialize AutoCompleteTextView
        AutoCompleteTextView autoCompleteTextView = root.findViewById(R.id.auto_complete_text);

        // Observe changes in dropdown items
        homeViewModel.getDropdownItems().observe(getViewLifecycleOwner(), items -> {
            ArrayAdapter<String> adapter_items = new ArrayAdapter<>(requireContext(), R.layout.list_item, items);
            autoCompleteTextView.setAdapter(adapter_items);
        });

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                // Handle item selection as needed
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
