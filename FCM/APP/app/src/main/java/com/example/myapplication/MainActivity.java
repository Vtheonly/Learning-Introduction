package com.example.myapplication;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final String CHANNEL_ID = "website_changes_channel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Create notification channel
        createNotificationChannel();

        // Set up Firestore listener
        setupFirestoreListener();
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Website Changes",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription("Notifications for website changes");
            // Register the channel with the system
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
            Log.d(TAG, "Notification channel created");
        }
    }

    private void setupFirestoreListener() {
        Log.d(TAG, "Setting up Firestore listener");
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Listen for new documents in the notifications collection
        db.collection("notifications")
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .limit(1)
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

                            // We only care about added documents
                            if (change.getType() == DocumentChange.Type.ADDED) {
                                Map<String, Object> notification = change.getDocument().getData();
                                Log.d(TAG, "Document data: " + notification.toString());

                                String title = notification.containsKey("title") ?
                                        (String) notification.get("title") : "New Notification";
                                String body = notification.containsKey("body") ?
                                        (String) notification.get("body") : "Check your app for updates";

                                Log.d(TAG, "Showing notification - Title: " + title + ", Body: " + body);

                                // Show the notification
                                showNotification(title, body);
                            }
                        }
                    } else {
                        Log.d(TAG, "No documents in snapshot");
                    }
                });

        Log.d(TAG, "Firestore listener setup complete");
    }

    private void showNotification(String title, String body) {
        Log.d(TAG, "Creating notification with Title: " + title + ", Body: " + body);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        int notificationId = (int) System.currentTimeMillis();

        // Create intent for when notification is tapped
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this, 0, intent,
                PendingIntent.FLAG_IMMUTABLE
        );

        // Build the notification
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        // Show the notification
        notificationManager.notify(notificationId, notificationBuilder.build());
        Log.d(TAG, "Notification displayed with ID: " + notificationId);
    }
}