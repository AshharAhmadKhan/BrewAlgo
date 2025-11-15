def probabilistic_risk_model(step_description: str, external_factors: list) -> float:
    """
    Custom deterministic scoring function.
    """

    base_risk = 0.1

    if len(step_description.split()) > 20:
        base_risk += 0.15

    for factor in external_factors:
        if "delay" in factor.lower():
            base_risk += 0.2
        if "approval" in factor.lower():
            base_risk += 0.1

    return round(min(base_risk, 0.95), 2)
