readViolation(A, STORE, S) :-
	CT_IDENTITY = 'Identity (7anajbj4mf6r6oaopy735u789)',
	CT_READ = 'ReadPermission (g6imob81l2v19zogeqg8lt5)',
	store(STORE),
	(actor(A);actorProcess(A,_)),
	nodeCharacteristic(A, CT_IDENTITY, C_IDENTITY),
	\+ nodeCharacteristic(STORE, CT_READ, C_IDENTITY),
	inputPin(A, PIN),
	flowTree(A, PIN, S),
	traversedNode(S, STORE).
	
writeViolation(A, STORE, S) :-
	CT_IDENTITY = 'Identity (7anajbj4mf6r6oaopy735u789)',
	CT_WRITE = 'WritePermission (8ijtb67tb58rejsoxsn7d4iwm)',
	store(STORE),
	(actor(A);actorProcess(A,_)),
	inputPin(STORE, PIN),
	nodeCharacteristic(A, CT_IDENTITY, C_IDENTITY),
	\+ nodeCharacteristic(STORE, CT_WRITE, C_IDENTITY),
	flowTree(STORE, PIN, S),
	traversedNode(S, A).
