# Use official Python slim image
FROM python:3.10-slim

# Set working directory
WORKDIR /app

# Copy requirements and install dependencies
COPY requirements.txt .
RUN pip install --no-cache-dir --upgrade pip && \
    pip install --no-cache-dir -r requirements.txt

# Download NLTK data (stopwords, punkt, averaged_perceptron_tagger)
RUN python -m nltk.downloader stopwords punkt averaged_perceptron_tagger

# Copy the rest of the application
COPY . .

# Ensure models and data directories exist
RUN mkdir -p models data

# (Optional) Set environment variables for model/data paths
ENV MODEL_DIR=/app/models
ENV DATA_DIR=/app/data

# Default entrypoint (can override with docker run)
ENTRYPOINT ["python", "src/main.py"]
