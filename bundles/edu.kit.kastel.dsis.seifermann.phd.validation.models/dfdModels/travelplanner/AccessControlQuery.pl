inputPin(P, PIN),
flowTree(P, PIN, S),
findall(R, nodeCharacteristic(P, 'Roles (_JvuuQ9vqEeqNdo_V4bA-xw)', R), L_ROLES),
findall(R, characteristic(P, PIN, 'AccessPermissions (_k9jB49vTEeqNdo_V4bA-xw)', R, S), L_REQ),
sort(L_ROLES, ROLES), sort(L_REQ, REQ),
intersection(REQ, ROLES, []).