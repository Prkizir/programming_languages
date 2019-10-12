/*List search*/
on(Item,[Item|_Rest]).

on(Item,[_DisregardHead|Tail]):-
  on(Item,Tail).

/*Member in List*/

member(Elem,[Elem|_Rest]).

member(Elem,[_NotElem|Next]):-
  member(Elem,Next).
