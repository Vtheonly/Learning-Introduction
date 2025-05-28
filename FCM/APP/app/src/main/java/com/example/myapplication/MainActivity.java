package com.example.myapplication;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;

import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final String CHANNEL_ID = "notifications_channel"; // Consistent Channel ID
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ListenerRegistration notificationListenerRegistration;
    private ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    Log.d(TAG, "Notification permission granted");
                } else {
                    Log.w(TAG, "Notification permission denied");
                    // Optionally, show a dialog explaining why notification permission is needed.
                }
            });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main); // Make sure you have activity_main.xml layout
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Request notification permission for Android 13+
        requestNotificationPermission();

        // Create notification channel
        createNotificationChannel();

        // Start Firestore listener
        startNotificationListener();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (notificationListenerRegistration == null) {
            startNotificationListener(); // Start listener if not already started (e.g., after app restart)
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopNotificationListener();
    }

    private void requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) !=
                    PackageManager.PERMISSION_GRANTED) {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
            } else {
                Log.d(TAG, "Notification permission already granted");
            }
        } else {
            Log.d(TAG, "Notification permission not needed on this Android version");
        }
    }


    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Firestore Notifications", // User-visible name of channel
                    NotificationManager.IMPORTANCE_HIGH // Importance level - adjust as needed
            );
            channel.setDescription("Notifications triggered by Firestore changes"); // Description for channel
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
            Log.d(TAG, "Notification channel created");
        }
    }

    private void startNotificationListener() {
        Log.d(TAG, "Starting Firestore listener");
        if (notificationListenerRegistration != null) {
            return; // Listener already running
        }

        notificationListenerRegistration = db.collection("notifications")
                .orderBy("timestamp", Query.Direction.DESCENDING) // Optional: Order by timestamp
                // .limit(1) // REMOVED limit(1) to see all new notifications
                .addSnapshotListener((snapshots, e) -> {
                    if (e != null) {
                        Log.e(TAG, "Listen failed:", e);
                        return;
                    }

                    Log.d(TAG, "Snapshot listener triggered");

                    if (snapshots != null && !snapshots.isEmpty()) {
                        Log.d(TAG, "Documents in snapshot: " + snapshots.size());

                        for (DocumentChange change : snapshots.getDocumentChanges()) {
                            Log.d(TAG, "Document change type: " + change.getType());

                            if (change.getType() == DocumentChange.Type.ADDED) {
                                Map<String, Object> notificationData = change.getDocument().getData();
                                Log.d(TAG, "Document data: " + notificationData.toString());

                                String title = notificationData.containsKey("title") ?
                                        (String) notificationData.get("title") : "New Notification";
                                String body = notificationData.containsKey("message") ? // Use "message" to match your pushToDB
                                        (String) notificationData.get("message") : "Check Firestore for updates";

                                Log.d(TAG, "Showing notification - Title: " + title + ", Body: " + body);
                                showNotification(title, body);
                            }
                        }
                    } else {
                        Log.d(TAG, "No documents in snapshot");
                    }
                });
        Log.d(TAG, "Firestore listener started");
    }

    private void stopNotificationListener() {
        if (notificationListenerRegistration != null) {
            notificationListenerRegistration.remove();
            notificationListenerRegistration = null;
            Log.d(TAG, "Firestore listener stopped");
        } else {
            Log.d(TAG, "Firestore listener was not running.");
        }
    }

    private void showNotification(String title, String body) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        int notificationId = (int) System.currentTimeMillis();

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this, 0, intent,
                PendingIntent.FLAG_IMMUTABLE
        );

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION); // Add default sound

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH) // High priority for heads-up display (adjust as needed)
                .setSound(defaultSoundUri); // Add default notification sound


        notificationManager.notify(notificationId, notificationBuilder.build());
        Log.d(TAG, "Notification displayed with ID: " + notificationId);
    }
}