CT_LEVEL = 'DataClassification (8qyivjgiiatej6lp8rkrcq45e)',
CT_CLEARANCE = 'NodeClearance (8z9yc8ulyja0vj2a9jc6fp8o5)',
nodeCharacteristic(P, CT_CLEARANCE, V_CLEAR),
characteristicTypeValue(CT_CLEARANCE, V_CLEAR, N_CLEAR),
inputPin(P, PIN),
characteristic(P, PIN, CT_LEVEL, V_LEVEL, S),
characteristicTypeValue(CT_LEVEL, V_LEVEL, N_LEVEL),
N_LEVEL > N_CLEAR.
