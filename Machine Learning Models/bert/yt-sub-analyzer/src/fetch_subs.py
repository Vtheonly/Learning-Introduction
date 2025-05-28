from youtube_transcript_api import YouTubeTranscriptApi, TranscriptsDisabled, NoTranscriptFound
import logging
from typing import Dict, List, Optional

logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)

class TranscriptFetcher:
    def __init__(self):
        self.supported_languages = ['en']  # Focus on English content first
        
    def get_transcript(self, video_id: str) -> Optional[Dict[str, str]]:
        """
        Fetches and processes video transcript with educational context awareness.
        Returns a dict with 'text' and 'metadata' if successful, None otherwise.
        """
        try:
            transcript_list = YouTubeTranscriptApi.list_transcripts(video_id)
            
            # Prioritize manual transcripts as they're typically more accurate
            try:
                transcript = transcript_list.find_manually_created_transcript(self.supported_languages)
                transcript_type = "manual"
            except NoTranscriptFound:
                try:
                    transcript = transcript_list.find_generated_transcript(self.supported_languages)
                    transcript_type = "auto-generated"
                except NoTranscriptFound:
                    logger.warning(f"No supported transcript found for video {video_id}")
                    return None
                
            # Fetch the actual transcript
            entries = transcript.fetch()
            
            # Extract the text and relevant metadata
            full_text = " ".join(entry['text'] for entry in entries)
            
            # Calculate metrics useful for educational content assessment
            word_count = len(full_text.split())
            avg_segment_length = word_count / len(entries) if entries else 0
            
            metadata = {
                'transcript_type': transcript_type,
                'word_count': word_count,
                'avg_segment_length': avg_segment_length,
                'language': transcript.language,
                'duration': sum(entry['duration'] for entry in entries if 'duration' in entry)
            }
            
            return {
                'text': full_text,
                'metadata': metadata
            }
            
        except TranscriptsDisabled:
            logger.error(f"Transcripts are disabled for video {video_id}")
            return None
        except Exception as e:
            logger.error(f"Error fetching transcript for {video_id}: {str(e)}")
            return None

if __name__ == '__main__':
    # Example usage
    fetcher = TranscriptFetcher()
    result = fetcher.get_transcript("dQw4w9WgXcQ")  # Example video ID
    if result:
        print(f"Transcript length: {len(result['text'])} characters")
        print(f"Metadata: {result['metadata']}")
    else:
        print("Failed to fetch transcript")