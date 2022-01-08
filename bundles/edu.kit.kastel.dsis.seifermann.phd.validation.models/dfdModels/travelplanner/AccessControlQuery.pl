inputPin(P, PIN),
flowTree(P, PIN, S),
setof(R, nodeCharacteristic(P, 'Roles (_JvuuQ9vqEeqNdo_V4bA-xw)', R), ROLES),
setof(R, characteristic(P, PIN, 'AccessPermissions (_k9jB49vTEeqNdo_V4bA-xw)', R, S), REQ),
intersection(REQ, ROLES, []).