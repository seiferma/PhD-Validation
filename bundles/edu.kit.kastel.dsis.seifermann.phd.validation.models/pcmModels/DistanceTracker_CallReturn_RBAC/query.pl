inputPin(N, PIN),
flowTree(N, PIN, S),
findall(R, nodeCharacteristic(N,'OwnedRoles (6euglvi6q6fxn3qpx7bc5jtm9)',R), L_AR),
findall(R, characteristic(N,PIN,'AllowedRoles (4vw2ul7p1z5kns0slxd1jppyl)',R,S), L_PR),
sort(L_AR, ROLES), sort(L_PR, REQ), intersection(ROLES, REQ, [])
