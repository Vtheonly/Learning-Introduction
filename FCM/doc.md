// ANDROID APP SIDE (Kotlin)

// 1. Add these dependencies to your app-level build.gradle
/*
dependencies {
    implementation platform('com.google.firebase:firebase-bom:32.5.0')
    implementation 'com.google.firebase:firebase-messaging-ktx'
    implementation 'com.google.firebase:firebase-firestore-ktx'
}
*/

// 2. Create a FirebaseMessagingService
package com.yourapp.fcm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.yourapp.MainActivity
import com.yourapp.R

class MyFirebaseMessagingService : FirebaseMessagingService() {
    
    override fun onCreate() {
        super.onCreate()
        // Set up Firestore listener to detect changes
        setupFirestoreListener()
    }
    
    private fun setupFirestoreListener() {
        val db = FirebaseFirestore.getInstance()
        
        // Listen for new documents in the notifications collection
        db.collection("notifications")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .limit(1)
            .addSnapshotListener { snapshots, e ->
                if (e != null) {
                    return@addSnapshotListener
                }
                
                snapshots?.documentChanges?.forEach { change ->
                    // We only care about added documents
                    if (change.type == DocumentChange.Type.ADDED) {
                        val notification = change.document.data
                        val title = notification["title"] as? String ?: "New Notification"
                        val body = notification["body"] as? String ?: "Check your app for updates"
                        
                        // Show the notification
                        showNotification(title, body)
                    }
                }
            }
    }

    // This handles notifications sent directly via FCM
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        
        // Get notification data
        val title = remoteMessage.notification?.title ?: "New Notification"
        val body = remoteMessage.notification?.body ?: "Check your app for updates"
        
        // Show notification
        showNotification(title, body)
    }

    private fun showNotification(title: String, body: String) {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationId = System.currentTimeMillis().toInt()
        
        // Create notification channel for Android O and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Website Changes",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }
        
        // Create intent for when notification is tapped
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_IMMUTABLE
        )
        
        // Build the notification
        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(body)
            .setSmallIcon(R.drawable.ic_notification)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
        
        // Show the notification
        notificationManager.notify(notificationId, notificationBuilder.build())
    }
    
    // Handle token refresh
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        // Send this token to your server if needed
    }
    
    companion object {
        private const val CHANNEL_ID = "website_changes_channel"
    }
}

// 3. Register the service in AndroidManifest.xml
/*
<service
    android:name=".fcm.MyFirebaseMessagingService"
    android:exported="false">
    <intent-filter>
        <action android:name="com.google.firebase.MESSAGING_EVENT" />
    </intent-filter>
</service>
*/