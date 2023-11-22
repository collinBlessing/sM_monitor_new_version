package com.example.sm_monitor_new_version.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<List<String>> dropdownItems;

    public HomeViewModel() {
        dropdownItems = new MutableLiveData<>();
        // Initialize with some default items
        List<String> months = new ArrayList<>();
        months.add("Oct 23");
        months.add("Nov 23");
        // Set the default items to the MutableLiveData
        dropdownItems.setValue(months);
    }

    public LiveData<List<String>> getDropdownItems() {
        return dropdownItems;
    }
}
