edge('Any (_3FoxoHC2EeygXcujbis_9A)', 'Contact (_3e6NQHC2EeygXcujbis_9A)').
edge('Any (_3FoxoHC2EeygXcujbis_9A)', 'Route (_3wuWAHC2EeygXcujbis_9A)').
edge('Contact (_3e6NQHC2EeygXcujbis_9A)', 'PrivateTaxi (_4C1ZsHC2EeygXcujbis_9A)').
edge('Contact (_3e6NQHC2EeygXcujbis_9A)', 'Driver (_4VwVsHC2EeygXcujbis_9A)').
edge('Contact (_3e6NQHC2EeygXcujbis_9A)', 'Rider (_9JoUYHC2EeygXcujbis_9A)').
edge('Route (_3wuWAHC2EeygXcujbis_9A)', 'CalcDistance (_9dgSoHC2EeygXcujbis_9A)').
edge('Route (_3wuWAHC2EeygXcujbis_9A)', 'Driver (_4VwVsHC2EeygXcujbis_9A)').
edge('Route (_3wuWAHC2EeygXcujbis_9A)', 'Rider (_9JoUYHC2EeygXcujbis_9A)').
connected(X, X).
connected(SRC, DST) :-
	edge(SRC, X),
	connected(X, DST).