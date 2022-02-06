inputPin(P, PIN), flowTree(P, PIN, S),
((
	findall(R, nodeCharacteristic(P, 'OwnedRoles (8cisi2ifn8s98tr6cxrcqnmar)', R), L_ROLES),
	findall(R, characteristic(P, PIN, 'AllowedRoles (9r0n0fku86y6a36qdy4gy7kgv)', R, S), L_REQ),
	sort(L_ROLES, ROLES), sort(L_REQ, REQ),
	intersection(REQ, ROLES, [])
) ; (
	CT_CRITICALITY='Criticality (11e9qsun4b4z2uw03ndq69uyo)',
	CT_VALIDATION='ValidationStatus (d6iot71mwmib3168csjm1lmn8)',
	nodeCharacteristic(P, CT_CRITICALITY, C),
	characteristicTypeValue(CT_CRITICALITY, C, NC),
	characteristic(P, PIN, CT_VALIDATION, V, S),
	characteristicTypeValue(CT_VALIDATION, V, NV),
	NV > NC
)).
