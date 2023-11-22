package com.example.sm_monitor_new_version.ui.home;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.sm_monitor_new_version.R;
import com.example.sm_monitor_new_version.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    // Timer variables
    private CountDownTimer countDownTimer;
    private boolean isTimerRunning = false;
    private final long totalTimeInMillis = 5 * 60 * 1000; // 5 minutes
    private long timeLeftInMillis = totalTimeInMillis;

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

        // Set up the timer UI elements
        TextView connectTextView = binding.connectTextview;

        // Set up listeners
        connectTextView.setOnClickListener(view -> toggleTimer());

        // Start the timer initially
        startTimer();

        return root;
    }

    private void toggleTimer() {
        if (isTimerRunning) {
            pauseTimer();
        } else {
            startTimer();
        }
    }

    /** @noinspection deprecation*/
    @SuppressLint("SetTextI18n")
    private void startTimer() {
        isTimerRunning = true;

        // Initialize UI elements
        TextView connectTextView = binding.connectTextview;
        connectTextView.setText("Running");
        connectTextView.setTextColor(requireContext().getResources().getColor(android.R.color.holo_green_light));

        // Set the timer to 5 minutes
        startCountDownTimer();
    }

    /** @noinspection deprecation*/
    @SuppressLint("SetTextI18n")
    private void pauseTimer() {
        isTimerRunning = false;

        // Initialize UI elements
        TextView connectTextView = binding.connectTextview;
        connectTextView.setText("Not Running");
        connectTextView.setTextColor(requireContext().getResources().getColor(android.R.color.holo_red_light));

        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    private void startCountDownTimer() {
        countDownTimer = new CountDownTimer(300000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateUI();

                // Check if the time is halfway (adjust as needed)
                if (millisUntilFinished <= totalTimeInMillis / 2) {
                    sendNotification("Take a Break", "You've reached halfway! Take a break from social media.");
                }
            }

            @Override
            public void onFinish() {
                // Timer finished, handle UI updates and notifications
                timeLeftInMillis = 0;
                updateUI();
                // TODO: Send notification here if needed
                sendNotification("Time's Up", "Your timer has finished! Consider taking a break.");
            }
        };

        countDownTimer.start();
    }

    private void updateUI() {
        long hours = timeLeftInMillis / 3600000;
        long minutes = (timeLeftInMillis % 3600000) / 60000;
        long seconds = (timeLeftInMillis % 60000) / 1000;

        @SuppressLint("DefaultLocale") String timeLeftFormatted = String.format("%02d:%02d:%02d", hours, minutes, seconds);

        // Update UI elements
        TextView timerDisplay = binding.timerDisplay;
        ProgressBar remainingTimeProgressBar = binding.remainingTimeProgressBar;

        timerDisplay.setText(timeLeftFormatted);

        int progress = (int) ((1 - (double) timeLeftInMillis / totalTimeInMillis) * 100);
        remainingTimeProgressBar.setProgress(progress);
    }

    private void sendNotification(String title, String message) {
        // TODO: Implement notification channel setup if not done already
        // Create a notification channel for Android Oreo and higher
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationManager notificationManager = requireContext().getSystemService(NotificationManager.class);
            NotificationChannel channel = new NotificationChannel("channel_id", "Channel Name", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("Channel Description");
            channel.enableLights(true);
            channel.setLightColor(Color.GREEN);
            notificationManager.createNotificationChannel(channel);
        }

        // Build the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(requireContext(), "channel_id")
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        // Show the notification
        NotificationManager notificationManager = (NotificationManager) requireContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, builder.build());
    }

        @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;

        // Cancel the timer to prevent leaks
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}
