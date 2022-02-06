inputPin(N, PIN),
flowTree(N, PIN, S),
findall(R, nodeCharacteristic(N,'OwnedRoles (3xilgwu067lckfkyjvb18rgcv)',R), L_AR),
findall(R, characteristic(N,PIN,'AllowedRoles (98u7rbtn091qlptxgyi6rka07)',R,S), L_PR),
sort(L_AR, ROLES), sort(L_PR, REQ), intersection(ROLES, REQ, [])
