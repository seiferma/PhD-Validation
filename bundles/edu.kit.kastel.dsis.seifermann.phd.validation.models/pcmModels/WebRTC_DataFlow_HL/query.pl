CT_LEVEL = 'DataClassification (79dxmfs054wrjqw4uakde62kx)',
CT_CLEARANCE = 'NodeClearance (5rsjcf77w4b8lzt37s59kq24t)',
nodeCharacteristic(P, CT_CLEARANCE, V_CLEAR),
characteristicTypeValue(CT_CLEARANCE, V_CLEAR, N_CLEAR),
inputPin(P, PIN),
characteristic(P, PIN, CT_LEVEL, V_LEVEL, S),
characteristicTypeValue(CT_LEVEL, V_LEVEL, N_LEVEL),
N_LEVEL > N_CLEAR.
