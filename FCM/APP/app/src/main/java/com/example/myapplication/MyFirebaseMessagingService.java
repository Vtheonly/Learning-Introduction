package com.example.myapplication;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "FCM_SERVICE";
    private static final String CHANNEL_ID = "website_changes_channel";

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d(TAG, "Message received from: " + remoteMessage.getFrom());

        // Get notification data
        String title = "New Notification";
        String body = "Check your app for updates";

        if (remoteMessage.getNotification() != null) {
            title = remoteMessage.getNotification().getTitle() != null ?
                    remoteMessage.getNotification().getTitle() : title;
            body = remoteMessage.getNotification().getBody() != null ?
                    remoteMessage.getNotification().getBody() : body;
            Log.d(TAG, "Message Notification - Title: " + title + ", Body: " + body);
        }

        // Show notification
        showNotification(title, body);
    }

    private void showNotification(String title, String body) {
        Log.d(TAG, "Creating notification with Title: " + title + ", Body: " + body);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        int notificationId = (int) System.currentTimeMillis();

        // Create notification channel for Android O and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Website Changes",
                    NotificationManager.IMPORTANCE_HIGH
            );
            notificationManager.createNotificationChannel(channel);
            Log.d(TAG, "Notification channel created");
        }

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

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        Log.d(TAG, "Refreshed token: " + token);

        // Save the token to Firestore
        saveTokenToFirestore(token);
    }

    private void saveTokenToFirestore(String token) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Associate the token with a device ID
        String deviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        Map<String, Object> tokenData = new HashMap<>();
        tokenData.put("token", token);
        tokenData.put("timestamp", new Date());
        tokenData.put("platform", "android");

        db.collection("device_tokens").document(deviceId)
                .set(tokenData)
                .addOnSuccessListener(aVoid -> Log.d(TAG, "Token saved to Firestore"))
                .addOnFailureListener(e -> Log.e(TAG, "Error saving token", e));
    }
}