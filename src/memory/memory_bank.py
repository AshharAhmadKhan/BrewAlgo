class MemoryBank:
    """
    Stores common failure modes for various domains.
    """

    def __init__(self):
        self.memory = {
            "software": "Scope creep; conflicting requirements.",
            "marketing": "Budget exhaustion before optimization.",
            "renovation": "Permit delays; material shortages.",
        }

    def query(self, domain: str) -> str:
        return self.memory.get(domain.lower(), "No common failures found.")
