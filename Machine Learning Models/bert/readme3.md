To get everything running and test your YouTube Subtitles Analyzer app, follow these steps:

---

### 1. **Install Python dependencies (if running locally)**
```bash
cd yt-sub-analyzer
pip install -r requirements.txt
```

---

### 2. **Download NLTK data (if running locally)**
This is usually handled in code, but you can do it manually for reliability:
```bash
python3 -c "import nltk; nltk.download('stopwords'); nltk.download('punkt'); nltk.download('averaged_perceptron_tagger')"
```

---

### 3. **Create dummy models (required for first run)**
```bash
python3 create_dummy_models.py
```
This will generate `models/tfidf_vectorizer.pkl` and `models/genre_classifier.pkl`.

---

### 4. **Test the app locally**
Replace `VIDEO_ID` with any real YouTube video ID (e.g., `dQw4w9WgXcQ`):
```bash
python3 src/main.py --video_id VIDEO_ID
```
You should see output with classification and confidence.

---

### 5. **(Optional) Build and run with Docker**
If you want to test in a containerized environment:
```bash
docker build -t yt-sub-analyzer .
docker run --rm yt-sub-analyzer --video_id VIDEO_ID
```

---

### 6. **Verify everything is working**
- You should see logs and a classification result in the terminal.
- If you see errors about missing models, NLTK data, or dependencies, repeat the relevant steps above.

---

**You are now ready to analyze YouTube videos for educational value!**