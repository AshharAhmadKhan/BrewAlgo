class BottleneckAgent:
    """
    Uses tools (Google Search) to identify external risks.
    """

    def __init__(self, google_search_tool):
        self.google_search_tool = google_search_tool

    def run(self, graph: dict) -> dict:
        # TODO: Search external risk factors
        return {
            "annotated_graph": {},
            "external_risks": [],
        }
