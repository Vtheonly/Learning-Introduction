import argparse
import logging
import sys
import os

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

from src.fetch_subs import TranscriptFetcher
from src.clean_text import EducationalContentPreprocessor
from src.classify_content import classify_text

logging.basicConfig(level=logging.INFO, format='%(asctime)s - %(levelname)s - %(message)s')

def run_analysis(video_id: str):
    logging.info(f"Analyzing video: {video_id}")
    fetcher = TranscriptFetcher()
    transcript_result = fetcher.get_transcript(video_id)
    if not transcript_result or not transcript_result.get('text'):
        print(f"Could not fetch transcript for video {video_id}.")
        sys.exit(1)
    transcript_text = transcript_result['text']
    preprocessor = EducationalContentPreprocessor()
    clean_result = preprocessor.clean_text(transcript_text)
    cleaned_text = clean_result['cleaned_text']
    metrics = clean_result['metrics']
    if not cleaned_text:
        print(f"Transcript for video {video_id} is empty after cleaning.")
        sys.exit(1)
    label, confidence = classify_text(cleaned_text)
    print(f"\n--- Analysis Results ---")
    print(f"Video ID: {video_id}")
    print(f"Transcript Length: {len(transcript_text.split())} words")
    print(f"Cleaned Length: {len(cleaned_text.split())} tokens")
    print(f"Educational Metrics: {metrics}")
    if label:
        conf_str = f"({confidence*100:.1f}% confidence)" if confidence is not None else "(confidence N/A)"
        print(f"Classified As: {label} {conf_str}")
        if label == 'educational':
            print("This video is likely WORTH WATCHING for educational purposes.")
        else:
            print("This video is likely NOT worth watching for educational purposes.")
    else:
        print("Classification failed.")
    print("------------------------\n")

if __name__ == "__main__":
    parser = argparse.ArgumentParser(description="Analyze YouTube video for educational value.")
    parser.add_argument("--video_id", required=True, help="YouTube video ID to analyze.")
    args = parser.parse_args()
    run_analysis(args.video_id)
