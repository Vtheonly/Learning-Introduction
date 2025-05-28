# 📘 README.md

## 🎯 Project: YouTube Subtitles Analyzer
A tool to classify YouTube video content by analyzing its subtitle transcripts using machine learning. Its primary purpose is to determine whether a video should be recommended or avoided — especially in an educational context.

---

## 🚀 Features
- Fetches subtitles from YouTube videos.
- Cleans and preprocesses subtitle text.
- Uses a pretrained classification model to determine the video's content type.
- Supports Dockerized deployment for portability and easy testing.

---

## 🤖 Model Used
The project currently uses a **TF-IDF vectorizer** combined with a **Scikit-learn classifier** (e.g., Logistic Regression, SVM, or Random Forest). You can train this model on a labeled dataset and store it as `genre_classifier.pkl` and `tfidf_vectorizer.pkl` in the `models/` directory.

If you want more accuracy and context understanding, you can later switch to:
- **DistilBERT or TinyBERT** via `transformers` library
- **FastText** for ultra-fast text classification
- **SBERT** (Sentence-BERT) + clustering or classification for semantic labeling

---

## 📦 Setup (Docker)
```bash
# Build image
docker build -t yt-sub-analyzer .

# Run analysis (replace VIDEO_ID)
docker run --rm yt-sub-analyzer python src/main.py --video_id VIDEO_ID
```

---

## 🧠 Educational Relevance Dataset Ideas
To determine whether content is **educational or time-wasting**, you can build or adapt a dataset with the following sources:

### 📚 Potential Datasets:
1. **YouTube-8M Dataset** (Google AI)
   - Preprocessed video-level labels from YouTube videos
   - Includes high-level categories (e.g., Education, Entertainment)
   - [https://research.google.com/youtube8m/](https://research.google.com/youtube8m/)

2. **OpenAI’s WebGPT Dataset** or **NarrativeQA**
   - Helps extract purposeful vs aimless dialogue or content

3. **Academic Torrents** / Kaggle Educational Datasets:
   - Look for labeled "education" videos vs meme/vlog/entertainment

4. **Custom Data Collection**:
   - Manually label ~500 videos as `educational`, `time-waster`, `neutral`
   - Use YouTube APIs to fetch subtitles + metadata + duration

---

## ✅ Labeling Strategy
You can define your labels like:
- **Educational**: tutorials, courses, explainers, etc.
- **Recommended**: light but intellectually stimulating (e.g. documentary clips)
- **Time-Wasting**: shorts/reels/vines with no value, endless reaction videos

Train a supervised classifier based on these categories.

---
