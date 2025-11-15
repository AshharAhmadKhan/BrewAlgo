# src/schemas/plan_schema.py
from pydantic import BaseModel, Field
from typing import List, Optional, Dict

class Risk(BaseModel):
    intrinsic: float = 0.0
    external: float = 0.0
    historical: float = 0.0
    total: float = 0.0
    factors: List[str] = []

class Step(BaseModel):
    id: str
    text: str
    dependencies: List[str] = Field(default_factory=list)
    estimated_time_hours: Optional[float] = None
    resources: List[str] = Field(default_factory=list)
    risk: Risk = Risk()

class Graph(BaseModel):
    nodes: List[str] = Field(default_factory=list)
    edges: List[List[str]] = Field(default_factory=list)

class Plan(BaseModel):
    goal: str
    steps: List[Step]
    graph: Graph = Graph()
    metadata: Dict[str, str] = Field(default_factory=dict)
