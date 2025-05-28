import re
import string
import nltk
from nltk.corpus import stopwords
from nltk.tokenize import word_tokenize
import logging
from typing import Dict, List, Tuple

# Download required NLTK data
nltk.download('punkt', quiet=True)
nltk.download('stopwords', quiet=True)
nltk.download('averaged_perceptron_tagger', quiet=True)

logger = logging.getLogger(__name__)

class EducationalContentPreprocessor:
    def __init__(self):
        self.stopwords = set(stopwords.words('english'))
        # Remove some stopwords that might be important in educational context
        self.stopwords -= {'how', 'why', 'what', 'when', 'where', 'which', 'explain', 'define'}
        
        # Educational content markers
        self.educational_markers = {
            'concept_words': {'understand', 'learn', 'explain', 'analyze', 'define', 'example',
                            'concept', 'theory', 'practice', 'study', 'method', 'solve'},
            'technical_indicators': {'algorithm', 'function', 'system', 'process', 'technique',
                                   'framework', 'methodology', 'implementation'},
            'quality_markers': {'detailed', 'comprehensive', 'systematic', 'fundamental',
                              'essential', 'important', 'key', 'critical'}
        }

    def clean_text(self, text: str) -> Dict[str, any]:
        """
        Preprocess text and extract educational content metrics.
        """
        if not text:
            return {'cleaned_text': '', 'metrics': {}}

        # Basic cleaning
        text = text.lower()
        # Remove timestamps [00:00] format
        text = re.sub(r'\[\d+:\d+\]', '', text)
        # Remove special characters but keep sentence structure
        text = re.sub(r'[^\w\s.?!]', '', text)

        # Tokenize
        tokens = word_tokenize(text)
        
        # Remove stopwords but keep educational markers
        tokens = [t for t in tokens if t not in self.stopwords or 
                 any(t in marker_set for marker_set in self.educational_markers.values())]

        # Calculate educational content metrics
        metrics = self._calculate_educational_metrics(tokens, text)
        
        cleaned_text = ' '.join(tokens)
        
        return {
            'cleaned_text': cleaned_text,
            'metrics': metrics
        }

    def _calculate_educational_metrics(self, tokens: List[str], original_text: str) -> Dict[str, float]:
        """
        Calculate metrics that help identify educational content.
        """
        # Get sentence count using basic punctuation
        sentences = re.split(r'[.!?]+', original_text)
        sentences = [s.strip() for s in sentences if s.strip()]
        
        # Count educational markers
        marker_counts = {
            category: sum(1 for token in tokens if token in marker_set)
            for category, marker_set in self.educational_markers.items()
        }
        
        # Calculate average words per sentence (complexity metric)
        avg_words_per_sentence = len(tokens) / len(sentences) if sentences else 0
        
        # Calculate question density (engagement metric)
        question_count = sum(1 for s in sentences if '?' in s)
        question_density = question_count / len(sentences) if sentences else 0
        
        return {
            'marker_counts': marker_counts,
            'avg_words_per_sentence': avg_words_per_sentence,
            'question_density': question_density,
            'total_sentences': len(sentences),
            'vocabulary_size': len(set(tokens))
        }

if __name__ == '__main__':
    # Example usage
    preprocessor = EducationalContentPreprocessor()
    sample_text = """
    Let me explain how this algorithm works. First, we need to understand
    the basic concepts. What is the fundamental theory behind it? This is
    an important question that we'll analyze in detail. Let's look at a
    practical example to demonstrate the key principles.
    """
    
    result = preprocessor.clean_text(sample_text)
    print("Cleaned text:", result['cleaned_text'][:100], "...")
    print("\nEducational metrics:", result['metrics'])