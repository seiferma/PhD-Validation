(actor(N);actorProcess(N, _)),
inputPin(N, PIN),
flowTree(N, PIN, S),
findall(X, characteristic(N, PIN, 'Compartment (_IgunozANEeyxUoEkMpyhIg)', X, S), L_COMP),
findall(X, nodeCharacteristic(N, 'Need to Know (_hedQgzANEeyxUoEkMpyhIg)', X), L_NTK),
sort(L_COMP, COMP), sort(L_NTK, NTK),
\+ subset(COMP, NTK).