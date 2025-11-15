class IngestionAgent:
    """
    Converts raw plan text into structured step data.
    """

    def run(self, raw_text: str) -> dict:
        # TODO: Implement LLM call + text cleaning
        return {
            "steps": [],
            "goal": "",
        }
