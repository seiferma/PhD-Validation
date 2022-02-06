CT_LEVEL = 'DataClassification (eqrbd7g77gynzxvv4svulp1qi)',
CT_CLEARANCE = 'NodeClearance (3smd63xqb0gan0o6hha6sx1qd)',
nodeCharacteristic(P, CT_CLEARANCE, V_CLEAR),
characteristicTypeValue(CT_CLEARANCE, V_CLEAR, N_CLEAR),
inputPin(P, PIN),
characteristic(P, PIN, CT_LEVEL, V_LEVEL, S),
characteristicTypeValue(CT_LEVEL, V_LEVEL, N_LEVEL),
N_LEVEL > N_CLEAR.
