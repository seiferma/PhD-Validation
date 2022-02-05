edge('Any (1erjynqvrio6qqyjgffvlux5p)', 'Contact (bdpzv2rcxw72qibw74u7g8rec)').
edge('Any (1erjynqvrio6qqyjgffvlux5p)', 'Route (8urwgowd6m3y11eouf8hxg66i)').
edge('Contact (bdpzv2rcxw72qibw74u7g8rec)', 'PrivateTaxi (cdy5etszgw8izas1zq7gp3by2)').
edge('Contact (bdpzv2rcxw72qibw74u7g8rec)', 'Driver (8fw4b0qvhiqy3itz88opvp8qe)').
edge('Contact (bdpzv2rcxw72qibw74u7g8rec)', 'Rider (43qfkix94pdo6k95zgy0sz1il)').
edge('Route (8urwgowd6m3y11eouf8hxg66i)', 'CalcDistance (c96fbg142beii1s52tpwvmklg)').
edge('Route (8urwgowd6m3y11eouf8hxg66i)', 'Driver (8fw4b0qvhiqy3itz88opvp8qe)').
edge('Route (8urwgowd6m3y11eouf8hxg66i)', 'Rider (43qfkix94pdo6k95zgy0sz1il)').
connected(X, X).
connected(SRC, DST) :-
	edge(SRC, X),
	connected(X, DST).
