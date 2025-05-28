#!/bin/bash

# YouTube Subtitle Analyzer Setup Script
# This script sets up and runs the YouTube Subtitle Analyzer application

echo "==== Setting up YouTube Subtitle Analyzer ===="

# Ensure python3-venv is installed
if ! dpkg -l | grep -q python3-venv; then
    echo "Installing python3-venv..."
    sudo apt-get update && sudo apt-get install -y python3-venv python3-pip
fi

# Move to the app directory
cd yt-sub-analyzer || {
    echo "Error: yt-sub-analyzer directory not found."
    echo "Please ensure you're running this script from the correct location."
    exit 1
}

# Create and activate virtual environment
echo "Creating Python virtual environment..."
python3 -m venv venv
source venv/bin/activate

# Install Python dependencies in virtual environment
echo "Installing Python dependencies..."
pip install --upgrade pip
pip install -r requirements.txt

# Download required NLTK data
echo "Downloading NLTK data..."
python3 -c "import nltk; nltk.download('stopwords'); nltk.download('punkt'); nltk.download('averaged_perceptron_tagger')"

# Create required dummy models for first run
echo "Creating dummy models for first run..."
python3 create_dummy_models.py

# Set appropriate permissions
echo "Setting appropriate permissions..."
chmod +x src/main.py

# Check if Docker is installed and running
if command -v docker &> /dev/null; then
    # Check if Docker daemon is running
    if docker info &> /dev/null; then
        echo "Building Docker image..."
        docker build -t yt-sub-analyzer .
    else
        echo "Warning: Docker is installed but the daemon is not running."
        echo "Please start Docker service with: sudo systemctl start docker"
        echo "Then run this script again."
        DOCKER_AVAILABLE=false
    fi
else
    echo "Docker is not installed. Skipping Docker setup."
    DOCKER_AVAILABLE=false
fi

# Get video ID from user if not provided
if [ -z "$1" ]; then
    read -p "Enter a YouTube video ID to analyze: " VIDEO_ID
else
    VIDEO_ID="$1"
fi

# Run the application
echo "Running the analyzer with video ID: $VIDEO_ID"
echo "==== Local Execution ===="
python3 src/main.py --video_id "$VIDEO_ID"

# If Docker is available and running, run in container
if [ "$DOCKER_AVAILABLE" != "false" ] && docker info &> /dev/null; then
    echo ""
    echo "==== Docker Execution ===="
    # Pass environment variables to Docker container
    docker run --rm \
        -e PYTHONPATH=/app \
        -e MODEL_DIR=/app/models \
        -e DATA_DIR=/app/data \
        -e LOG_LEVEL=${LOG_LEVEL:-INFO} \
        yt-sub-analyzer --video_id "$VIDEO_ID"
fi

echo ""
echo "Setup and execution complete!"
echo "You can now analyze videos by running:"
echo "  python3 src/main.py --video_id <YOUTUBE_VIDEO_ID>"
echo "  or with Docker: docker run --rm yt-sub-analyzer --video_id <YOUTUBE_VIDEO_ID>"