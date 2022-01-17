inputPin(P, PIN),
flowTree(P, PIN, S),
findall(R, nodeCharacteristic(P, 'Roles (_g8Baw0NEEeq3NrD2DjPidQ)', R), L_ROLES),
findall(R, characteristic(P, PIN, 'AccessRights (_fCiJk0NEEeq3NrD2DjPidQ)', R, S), L_REQ),
sort(L_ROLES, ROLES), sort(L_REQ, REQ),
intersection(REQ, ROLES, []).