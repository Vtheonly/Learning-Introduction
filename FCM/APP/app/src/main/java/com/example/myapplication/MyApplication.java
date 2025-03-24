package com.example.myapplication;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.Map;

public class MyApplication extends Application {
    private static final String TAG = "MyApplication";
    private static final String CHANNEL_ID = "website_changes_channel";

    @Override
    public void onCreate() {
        super.onCreate();

        // Create notification channel
        createNotificationChannel();

        // Set up Firestore listener
        setupFirestoreListener();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Website Changes",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription("Notifications for website changes");
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void setupFirestoreListener() {
        Log.d(TAG, "Setting up Firestore listener in Application class");
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("notifications")
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .limit(1)
                .addSnapshotListener((snapshots, e) -> {
                    if (e != null) {
                        Log.e(TAG, "Listen failed:", e);
                        return;
                    }

                    if (snapshots != null && !snapshots.isEmpty()) {
                        for (DocumentChange change : snapshots.getDocumentChanges()) {
                            if (change.getType() == DocumentChange.Type.ADDED) {
                                Map<String, Object> notification = change.getDocument().getData();

                                String title = notification.containsKey("title") ?
                                        (String) notification.get("title") : "New Notification";
                                String body = notification.containsKey("body") ?
                                        (String) notification.get("body") : "Check your app for updates";

                                showNotification(title, body);
                            }
                        }
                    }
                });
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

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        notificationManager.notify(notificationId, notificationBuilder.build());
    }
}