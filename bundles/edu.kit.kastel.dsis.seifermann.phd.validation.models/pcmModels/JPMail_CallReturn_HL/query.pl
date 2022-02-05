CT_LEVEL = 'DataClassification (43gy63vsr6nsm9fu29say4mir)',
CT_CLEARANCE = 'NodeClearance (c3wlkfowuq99ilvz3abhddu3l)',
nodeCharacteristic(P, CT_CLEARANCE, V_CLEAR),
characteristicTypeValue(CT_CLEARANCE, V_CLEAR, N_CLEAR),
inputPin(P, PIN),
characteristic(P, PIN, CT_LEVEL, V_LEVEL, S),
characteristicTypeValue(CT_LEVEL, V_LEVEL, N_LEVEL),
N_LEVEL > N_CLEAR.
