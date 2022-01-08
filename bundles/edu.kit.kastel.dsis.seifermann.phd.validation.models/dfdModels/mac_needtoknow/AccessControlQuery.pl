(actor(N);actorProcess(N, _)),
inputPin(N, PIN),
flowTree(N, PIN, S),
setof(X, characteristic(N, PIN, 'Compartment (_IgunozANEeyxUoEkMpyhIg)', X, S), COMP),
setof(X, nodeCharacteristic(N, 'Need to Know (_hedQgzANEeyxUoEkMpyhIg)', X), NTK),
\+ subset(COMP, NTK).