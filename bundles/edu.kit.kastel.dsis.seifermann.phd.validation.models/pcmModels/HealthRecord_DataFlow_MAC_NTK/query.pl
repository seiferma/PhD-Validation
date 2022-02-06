(actor(N);actorProcess(N, _)),
inputPin(N, PIN),
flowTree(N, PIN, S),
findall(X, characteristic(N,PIN,'Compartments (cmilr0zuh6kio4lo83935b12p)',X,S), L_COMP),
findall(X, nodeCharacteristic(N,'NeedsToKnow (dlejmtjd31werhp70wrysga5b)',X), L_NTK),
sort(L_COMP, COMP), sort(L_NTK, NTK), \+ subset(COMP, NTK).
