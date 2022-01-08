inputPin(P, PIN),
flowTree(P, PIN, S),
setof(R, nodeCharacteristic(P, 'Roles (_g8Baw0NEEeq3NrD2DjPidQ)', R), ROLES),
setof(R, characteristic(P, PIN, 'AccessRights (_fCiJk0NEEeq3NrD2DjPidQ)', R, S), REQ),
intersection(REQ, ROLES, []).