// WEBSITE SIDE (JavaScript)

// 1. Initialize Firebase on your website
import { initializeApp } from "firebase/app";
import { getFirestore, collection, addDoc, serverTimestamp } from "firebase/firestore";

// Your Firebase configuration



const firebaseConfig = {
    apiKey: "AIzaSyCncnMyVosvBQCBqeVz1KQP3trF0rKAR0k",
    authDomain: "test-f7dea.firebaseapp.com",
    projectId: "test-f7dea",
    storageBucket: "test-f7dea.firebasestorage.app",
    messagingSenderId: "1024972270938",
    appId: "1:1024972270938:web:06aac2c07d420419fc5763",
    measurementId: "G-LHFLCW8X9H"
};






// Initialize Firebase
const app = initializeApp(firebaseConfig);
const db = getFirestore(app);

// 2. Function to send notification when a change is made
async function sendNotification(title, body, data = {}) {
    try {
        // Add a new document to a "notifications" collection
        const docRef = await addDoc(collection(db, "notifications"), {
            title: title,
            body: body,
            data: data,
            timestamp: serverTimestamp(),
            // You can add user-specific tokens or topics here if needed
        });
        console.log("Notification document written with ID: ", docRef.id);
        return true;
    } catch (e) {
        console.error("Error sending notification: ", e);
        return false;
    }
}

// 3. Call this function when changes are made on your site
document.getElementById("changeButton").addEventListener("click", () => {
    // When the button is clicked, send a notification
    sendNotification(
        "Change Detected",
        "Someone made a change on the website",
        { url: window.location.href }
    );
});








