CT_LEVEL = 'DataClassification (4vw2ul7p1z5kns0slxd1jppyl)',
CT_CLEARANCE = 'NodeClearance (6euglvi6q6fxn3qpx7bc5jtm9)',
nodeCharacteristic(P, CT_CLEARANCE, V_CLEAR),
characteristicTypeValue(CT_CLEARANCE, V_CLEAR, N_CLEAR),
inputPin(P, PIN),
characteristic(P, PIN, CT_LEVEL, V_LEVEL, S),
characteristicTypeValue(CT_LEVEL, V_LEVEL, N_LEVEL),
N_LEVEL > N_CLEAR.
