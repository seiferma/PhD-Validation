CT_LEVEL = 'DataClassification (bbl5zatg6jxcode1h33ob34q4)',
CT_CLEARANCE = 'NodeClearance (a49etqoctb76x2lvcze5xv2ie)',
nodeCharacteristic(P, CT_CLEARANCE, V_CLEAR),
characteristicTypeValue(CT_CLEARANCE, V_CLEAR, N_CLEAR),
inputPin(P, PIN),
characteristic(P, PIN, CT_LEVEL, V_LEVEL, S),
characteristicTypeValue(CT_LEVEL, V_LEVEL, N_LEVEL),
N_LEVEL > N_CLEAR.
