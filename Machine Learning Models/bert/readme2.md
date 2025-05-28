Perfect, Marshal. Here's a **focused and technical README** just for the part of your app that analyzes subtitles to determine video type. It's written in a structured Markdown format, ready for Obsidian or GitHub.

---

# 📄 YouTube Subtitles Analyzer – README

## 🔍 Purpose

This module focuses solely on **analyzing the subtitle (caption) content of YouTube videos** to determine their genre, quality, and relevance for distraction-free consumption. It is the brain of your YouTube Focus App that detects low-value vs. high-value content based on **textual cues**.

---

## 📁 File Structure

```bash
yt-sub-analyzer/
├── src/
│   ├── main.py                # Entry point; orchestrates the analysis
│   ├── fetch_subs.py          # Retrieves subtitles using YouTube Data API / scraping
│   ├── clean_text.py          # Preprocesses and cleans subtitle text
│   ├── classify_content.py    # Applies ML/NLP models to classify the video type
│   └── utils.py               # Helpers for logging, formatting, etc.
├── models/
│   ├── genre_classifier.pkl   # Trained ML model for genre classification
│   └── tfidf_vectorizer.pkl   # Text vectorizer for converting text to features
├── data/
│   └── stopwords.txt          # Custom stopwords for cleaner classification
├── requirements.txt           # Python dependencies
└── README.md                  # This file
```

---

## 🧠 Pipeline Description

### 1. **Subtitle Extraction**
- **Source**: YouTube Captions (Auto or Manual)
- **Methods**:
  - Uses `youtube_transcript_api` or YouTube API (where available)
  - Fallback to scraping for unavailable captions

### 2. **Text Cleaning**
- Lowercasing, punctuation stripping, number removal
- Stopword filtering (NLTK + domain-specific terms)
- Optional: Lemmatization (via SpaCy)

### 3. **Feature Engineering**
- TF-IDF Vectorization
- Word Embeddings (Word2Vec, GloVe, or BERT for advanced analysis)

### 4. **Classification**
- **Model**: Pre-trained Logistic Regression or Transformer model
- **Labels (examples)**:
  - 🧠 `educational`
  - 🕹️ `entertainment`
  - 🧘 `mindless`
  - 🎭 `motivational`
  - 🎬 `shorts`
  - 🐒 `low-effort clickbait`
  - 📰 `news / commentary`
- Confidence score assigned per label

---

## 🚨 YouTube Policy Considerations

> ⚠️ *The following features might trigger YouTube's detection systems or violate terms of service:*

| Feature | Risk Level | Description |
|--------|------------|-------------|
| Fetching subtitles without API | 🔴 High | Scraping auto-captions may violate ToS |
| Emulating watch behavior | 🔴 High | Marking videos as "watched" without viewing may violate behavior policies |
| Auto-interaction with YouTube (e.g., dislike/remove/like) | 🟠 Medium | Automated interaction bots are against YouTube's terms |
| Using third-party APIs to emulate progress bar | 🟠 Medium | YouTube Player API doesn't officially allow modifying watch progress directly |
| Hosting analysis results publicly with video links | 🟡 Low | Might be flagged under privacy or content redistribution policies |

---

## 📌 Legal Gray Areas

YouTube's [Terms of Service](https://www.youtube.com/t/terms) prohibit:

- Automated scraping or data extraction unless using official APIs
- Circumventing or manipulating watch history to influence the algorithm
- Interacting with video elements (likes/dislikes/history) via automation

> ❗Use this module for **personal use**, **educational research**, or with **user consent** to avoid violating YouTube policies.

---

## 🧪 Testing

You can run this module independently:

```bash
# Fetch subs and classify
python src/main.py --video_id=<YouTubeVideoID>
```

Expected output:

```bash
Video ID: dQw4w9WgXcQ
Subtitle Length: 2381 tokens
Classified As: entertainment (87% confidence)
```

---

## ✅ Dependencies

```txt
youtube_transcript_api
scikit-learn
nltk
spacy
joblib
numpy
```

---

## 🧭 Future Improvements

- Add semantic similarity scoring with educational content
- Integrate live subtitle extraction while video is playing
- Allow custom label training by user

---

Let me know when you're ready for a legal/ethical breakdown that you can feed to a Google-based LLM or lawyer-style AI. We’ll dig deeper into scraping legality, fair use, user simulation, and possibly even machine ethics if you're planning long-term deployment.