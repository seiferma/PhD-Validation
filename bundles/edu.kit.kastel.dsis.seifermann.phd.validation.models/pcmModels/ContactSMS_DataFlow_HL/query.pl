CT_LEVEL = 'DataClassification (2dt9gxmtre4jmnlrv8hksv0zh)',
CT_CLEARANCE = 'NodeClearance (1r767ezfo4zv021jb63vb3itu)',
nodeCharacteristic(P, CT_CLEARANCE, V_CLEAR),
characteristicTypeValue(CT_CLEARANCE, V_CLEAR, N_CLEAR),
inputPin(P, PIN),
characteristic(P, PIN, CT_LEVEL, V_LEVEL, S),
characteristicTypeValue(CT_LEVEL, V_LEVEL, N_LEVEL),
N_LEVEL > N_CLEAR.
