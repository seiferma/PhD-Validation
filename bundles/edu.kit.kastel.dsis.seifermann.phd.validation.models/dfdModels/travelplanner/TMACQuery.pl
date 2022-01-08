inputPin(P, PIN), flowTree(P, PIN, S),
((
	setof(R, nodeCharacteristic(P, 'Roles (_JvuuQ9vqEeqNdo_V4bA-xw)', R), ROLES),
	setof(R, characteristic(P, PIN, 'AccessPermissions (_k9jB49vTEeqNdo_V4bA-xw)', R, S), REQ),
	intersection(REQ, ROLES, [])
) ; (
	nodeCharacteristic(P, 'Criticality (_nzAkk27TEey6fcb9AA1YiQ)', C),
	characteristicTypeValue('Criticality (_nzAkk27TEey6fcb9AA1YiQ)', C, NC),
	characteristic(P, PIN, 'Validation (_so-N827TEey6fcb9AA1YiQ)', V, S),
	characteristicTypeValue('Validation (_so-N827TEey6fcb9AA1YiQ)', V, NV),
	NV > NC
)).