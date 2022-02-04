CT_LEVEL = 'DataClassification (9r0n0fku86y6a36qdy4gy7kgv)',
CT_CLEARANCE = 'NodeClearance (8cisi2ifn8s98tr6cxrcqnmar)',
nodeCharacteristic(P, CT_CLEARANCE, V_CLEAR),
characteristicTypeValue(CT_CLEARANCE, V_CLEAR, N_CLEAR),
inputPin(P, PIN),
characteristic(P, PIN, CT_LEVEL, V_LEVEL, S),
characteristicTypeValue(CT_LEVEL, V_LEVEL, N_LEVEL),
N_LEVEL > N_CLEAR.
