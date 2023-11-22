package com.example.sm_monitor_new_version.ui.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.sm_monitor_new_version.R;
import com.example.sm_monitor_new_version.databinding.FragmentGalleryBinding;

public class GalleryFragment extends Fragment {

    private FragmentGalleryBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        // WhatsApp
        LinearLayout whatsappLayout = root.findViewById(R.id.whatsappLayout);
        whatsappLayout.setOnClickListener(v -> openSocialMediaApp("com.whatsapp"));

        // Instagram
        LinearLayout instagramLayout = root.findViewById(R.id.instagramLayout);
        instagramLayout.setOnClickListener(v -> openSocialMediaApp("com.instagram.android"));

        // Facebook
        LinearLayout facebookLayout = root.findViewById(R.id.facebookLayout);
        facebookLayout.setOnClickListener(v -> openSocialMediaApp("com.facebook.katana"));

        return root;
    }

    private void openSocialMediaApp(String packageName) {
        Intent intent = requireActivity().getPackageManager().getLaunchIntentForPackage(packageName);
        if (intent != null) {
            startActivity(intent);
        } else {
            showAppNotInstalledMessage();
        }
    }

    private void showAppNotInstalledMessage() {
        Toast.makeText(requireContext(), "App not installed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
