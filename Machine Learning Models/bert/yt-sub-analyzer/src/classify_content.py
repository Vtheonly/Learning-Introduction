import joblib
import logging
import os
from typing import Tuple, Any

MODEL_DIR = "models"
VECTORIZER_PATH = os.path.join(MODEL_DIR, "tfidf_vectorizer.pkl")
CLASSIFIER_PATH = os.path.join(MODEL_DIR, "genre_classifier.pkl")

vectorizer = None
model = None

try:
    vectorizer = joblib.load(VECTORIZER_PATH)
    logging.info(f"Vectorizer loaded from {VECTORIZER_PATH}")
except Exception as e:
    logging.error(f"Vectorizer not loaded: {e}")

try:
    model = joblib.load(CLASSIFIER_PATH)
    logging.info(f"Classifier loaded from {CLASSIFIER_PATH}")
except Exception as e:
    logging.error(f"Classifier not loaded: {e}")

def classify_text(text: str) -> Tuple[str | None, float | None]:
    if not vectorizer or not model:
        logging.error("Vectorizer or model not loaded. Cannot classify.")
        return None, None
    if not text:
        return "unknown", 0.0
    try:
        X = vectorizer.transform([text])
        pred = model.predict(X)[0]
        conf = None
        if hasattr(model, "predict_proba"):
            proba = model.predict_proba(X)[0]
            idx = list(model.classes_).index(pred)
            conf = proba[idx]
        return pred, conf
    except Exception as e:
        logging.error(f"Classification failed: {e}")
        return None, None
