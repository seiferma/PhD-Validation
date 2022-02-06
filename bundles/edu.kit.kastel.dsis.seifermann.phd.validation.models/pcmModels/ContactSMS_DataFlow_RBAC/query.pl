inputPin(N, PIN),
flowTree(N, PIN, S),
findall(R, nodeCharacteristic(N,'OwnedRoles (1r767ezfo4zv021jb63vb3itu)',R), L_AR),
findall(R, characteristic(N,PIN,'AllowedRoles (2dt9gxmtre4jmnlrv8hksv0zh)',R,S), L_PR),
sort(L_AR, ROLES), sort(L_PR, REQ), intersection(ROLES, REQ, [])
