(inputPin(N, PIN0),
inputPin(N, PIN1),
PIN0 \== PIN1,
sub_string(N,_,_,_,'ecrypt'),
findall(X, characteristic(N, PIN0, 'PrivateKeyOf (2x4fu74z0tdlcivgfhkdfm1zj)', X, S0), L_PROV),
findall(X, characteristic(N, PIN1, 'DecryptableBy (2zo8o7slh7omyz7l89iszwshl)', X, S1), L_REQ),
sort(L_PROV, PROV), sort(L_REQ, REQ),
REQ \== [],
intersection(PROV, REQ, []));(
inputPin(N, PIN),
nodeCharacteristic(N, 'Clearance (8btq8qpr58i2irxnwriw8tkww)', V_CLEAR),
characteristic(N, PIN, 'Classification (h420ukilcnp1ifokv6fs5nr0)', V_CLASS, S),
\+ connected(V_CLASS, V_CLEAR)).
