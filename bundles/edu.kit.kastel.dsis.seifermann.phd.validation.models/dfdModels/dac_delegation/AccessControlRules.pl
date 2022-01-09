dynamic(STORE, CT, V) :-
	inputPin(STORE, PIN),
	characteristic(STORE, PIN, CT, V, S),
	actor(A),
	flowTree(STORE, PIN, S),
	traversedNode(S, A),
	nodeCharacteristic(A, 'Identity (_o7_1k9VeEeqRbpVUMz5XAQ)', Y),
	owner(Y, STORE).

owner(V, STORE) :-
	nodeCharacteristic(STORE, 'Owner (_uEVoY9VeEeqRbpVUMz5XAQ)', V);
	dynamic(STORE, 'Add Owner (_JjVjMi6kEeyiOr-FRYaMOg)', V).
readAccess(V, STORE) :-
	nodeCharacteristic(STORE, 'Read Access (_rd9cA9VeEeqRbpVUMz5XAQ)', V);
	dynamic(STORE, 'Add Read Access (_LuTl8y6kEeyiOr-FRYaMOg)', V).
writeAccess(V, STORE) :-
	nodeCharacteristic(STORE, 'Write Access (_swJco9VeEeqRbpVUMz5XAQ)', V);
	dynamic(STORE, 'Add Write Access (_NTmv0y6kEeyiOr-FRYaMOg)', V).

readViolation(A, STORE, S) :-
	store(STORE),
	actor(A),
	inputPin(A, PIN),
	flowTree(A, PIN, S),
	traversedNode(S, STORE),
	nodeCharacteristic(A, 'Identity (_o7_1k9VeEeqRbpVUMz5XAQ)', Y),
	\+ readAccess(Y, STORE).

writeViolation(A, STORE, S) :-
	store(STORE),
	actor(A),
	inputPin(STORE, PIN),
	flowTree(STORE, PIN, S),
	traversedNode(S, A),
	nodeCharacteristic(A, 'Identity (_o7_1k9VeEeqRbpVUMz5XAQ)', Y),
	\+ writeAccess(Y, STORE).