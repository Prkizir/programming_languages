/*Sergio Isaac Mercado Silvano.
* A01020382.
* Prolog: Lab 3.
*/

quicksort([],[]).

quicksort(X,X).

quicksort(List,Sorted):-
  pivot(List,Pivot),

/*Get Array Length*/
list_length([],0).

list_length([_|T],X):-
  list_length(T,X1),
  X is X1 + 1.

/*Integer division*/
divide(X,Y,Z):-
  Z is X // Y.

/*Get the mid pivot*/

pivot(List,X):-
  list_length(List,Length),
  divide(Length,2,Mid),
  pv(List,Mid,X).

pv([H|_],0,H).

pv([_|T],Mid,Result):-
  M1 is Mid - 1,
  pv(T,M1,Result).
