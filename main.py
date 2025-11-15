from src.agents.ingestion_agent import IngestionAgent
from src.agents.dependency_agent import DependencyAgent
from src.agents.bottleneck_agent import BottleneckAgent
from src.agents.failure_forecaster import FailureForecaster
from src.tools.risk_model import probabilistic_risk_model
from src.tools.google_search_wrapper import GoogleSearchTool
from src.memory.memory_bank import MemoryBank

def run_pipeline(plan_text: str):
    ingestion = IngestionAgent()
    dep_agent = DependencyAgent()
    google_tool = GoogleSearchTool()
    bottleneck = BottleneckAgent(google_tool)
    memory = MemoryBank()
    forecaster = FailureForecaster(probabilistic_risk_model, memory)

    ingested = ingestion.run(plan_text)
    deps = dep_agent.run(ingested["steps"])
    risks = bottleneck.run(deps)
    final = forecaster.run(risks)

    return final

if __name__ == "__main__":
    sample_plan = "Plan to launch a mobile app."
    print(run_pipeline(sample_plan))
