import os
import joblib
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.linear_model import LogisticRegression

# Dummy training data
texts = [
    "This is an educational video about math and science.",
    "Learn how to solve equations and understand physics.",
    "Funny cat videos and memes for entertainment.",
    "Watch this prank and laugh out loud!"
]
labels = ["educational", "educational", "not_educational", "not_educational"]

# Train vectorizer and classifier
vectorizer = TfidfVectorizer()
X = vectorizer.fit_transform(texts)
clf = LogisticRegression()
clf.fit(X, labels)

# Save models
dest_dir = os.environ.get("MODEL_DIR", "models")
os.makedirs(dest_dir, exist_ok=True)
joblib.dump(vectorizer, os.path.join(dest_dir, "tfidf_vectorizer.pkl"))
joblib.dump(clf, os.path.join(dest_dir, "genre_classifier.pkl"))

print(f"Dummy models saved to {dest_dir}")
