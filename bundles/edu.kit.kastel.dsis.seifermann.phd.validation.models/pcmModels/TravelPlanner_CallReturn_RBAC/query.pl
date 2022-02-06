inputPin(N, PIN),
flowTree(N, PIN, S),
findall(R, nodeCharacteristic(N,'OwnedRoles (8cisi2ifn8s98tr6cxrcqnmar)',R), L_AR),
findall(R, characteristic(N,PIN,'AllowedRoles (9r0n0fku86y6a36qdy4gy7kgv)',R,S), L_PR),
sort(L_AR, ROLES), sort(L_PR, REQ), intersection(ROLES, REQ, [])
