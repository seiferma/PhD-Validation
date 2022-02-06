matchSubject('Clerk', N) :-
  nodeCharacteristic(N, 'Role (cqi9za531xrqo5371zvdbyg0b)', 'Clerk (a0dl70ijemtva4vdsr8g9kocf)').

matchSubject('Manager', N) :-
  nodeCharacteristic(N, 'Role (cqi9za531xrqo5371zvdbyg0b)', 'Manager (1sfop19wddibeawwr5ijlx8fu)').

matchObject('Regular', N, PIN, S) :-
  exactCharacteristicValues(N, PIN, 'Status (4npzcb1lhhsp225frm1jao2fp)', ['Regular (52zdq0nc6z3qajep8byudjxh3)'], S).

matchObject('all', _, _, _).

read(N, PIN, S) :-
  matchSubject('Manager', N),
  matchObject('all', N, PIN, S).

read(N, PIN, S) :-
  matchSubject('Clerk', N),
  matchObject('Regular', N, PIN, S),
  nodeCharacteristic(N, 'Location (1e9x5r15r88injjk3ysvvitjy)', L),
  exactCharacteristicValues(N, PIN, 'Origin (a0h4bkss6gm9jn9l61y4np1ix)', [L], S).
