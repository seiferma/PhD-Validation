CT_LEVEL = 'DataClassification (98u7rbtn091qlptxgyi6rka07)',
CT_CLEARANCE = 'NodeClearance (3xilgwu067lckfkyjvb18rgcv)',
nodeCharacteristic(P, CT_CLEARANCE, V_CLEAR),
characteristicTypeValue(CT_CLEARANCE, V_CLEAR, N_CLEAR),
inputPin(P, PIN),
characteristic(P, PIN, CT_LEVEL, V_LEVEL, S),
characteristicTypeValue(CT_LEVEL, V_LEVEL, N_LEVEL),
N_LEVEL > N_CLEAR.
