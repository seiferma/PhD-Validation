matchSubject('Clerk', N) :-
  nodeCharacteristic(N, 'EmployeeRole (_nNduk-JAEeqO9NqdRSqKUA)', 'Clerk (_c_En8OJAEeqO9NqdRSqKUA)').

matchSubject('Manager', N) :-
  nodeCharacteristic(N, 'EmployeeRole (_nNduk-JAEeqO9NqdRSqKUA)', 'Manager (_dvk30OJAEeqO9NqdRSqKUA)').

matchObject('Regular', N, PIN, S) :-
  exactCharacteristicValues(N, PIN, 'CustomerStatus (_lmMOw-JAEeqO9NqdRSqKUA)', ['Regular (_gYqZ8OJAEeqO9NqdRSqKUA)'], S).

matchObject('all', _, _, _).

read(N, PIN, S) :-
  matchSubject('Manager', N),
  matchObject('all', N, PIN, S).

read(N, PIN, S) :-
  matchSubject('Clerk', N),
  matchObject('Regular', N, PIN, S),
  nodeCharacteristic(N, 'EmployeeLocation (_j_v1Y-JAEeqO9NqdRSqKUA)', L),
  exactCharacteristicValues(N, PIN, 'CustomerLocation (_h6k4o-JAEeqO9NqdRSqKUA)', [L], S).