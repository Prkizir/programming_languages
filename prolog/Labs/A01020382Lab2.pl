/*Sergio Isaac Mercado Silvano.
* A01020382.
* Prolog: Lab 2.
*/

/*Predef. Helper Functions*/

on(Item, [Item|_]).    % On: search given item in a given list
                       % Returns true if item is a member
on(Item,[_|Rest]):-    % returns false otherwise
  on(Item,Rest).       % Item List : True|False

append([],List,List).              % Append: Appens a list to another given list
                                   % Returns an appended list
append([H|T], List2, [H|Result]):- %
  append(T,List2,Result).          % List List : List


/*Unused helper functions*/
% Could be useful for something

get_first(_,0,[]).

get_first([H|T],X,[H|Rest]):-
  X1 is X - 1,
  get_first(T,X1,Rest).

get_last([],_,[]).

get_last(List,0,List).

get_last([_|T],X,Result):-
  X = 0;
  X1 is X - 1,
  get_last(T,X1,Result).

/*
* Any Positive
* Returns true if any item in the list is positive
* List : True|False
*/

any_positive([X|_]):-
  X > 0.

any_positive([_|Next]):-
  any_positive(Next).

/*
* Substitute
* Substitutes an item in a list for another item.
* Returns a list with the items replaced
* Item Item List : List
*/

substitute(_,_,[],[]).

substitute(X,Y,[X|T],[Y|Result]):-
  substitute(X,Y,T,Result).

substitute(X,Y,[H|T],[H|Result]):-
  substitute(X,Y,T,Result).

/*
* Eliminate duplicates
* Returns a list with just one unique element
* from a given list
* List : List
*/

eliminate_duplicates([],[]).

eliminate_duplicates([H|T],[H|Result]):-
  eliminate_duplicates(T,Result),
  not(on(H,Result));
  eliminate_duplicates(T,[_|Result]),
  not(on(H,Result)).

/*
* Intersect
* Returns a list with all the elements found in both lists
* List List : List
*/

intersect([],_,[]).

intersect([H|T],List,[H|Result]):-
  on(H,List),
  intersect(T,List,Result).

intersect([_|T],List,Result):-
  intersect(T,List,Result).

/*
* Invert
* Returns an inverted list from a given list
* List : List
*/

invert([],[]).

invert([H|T],Result):-
  append(NewList,[H],Result),
  invert(T,NewList).

/*
* Less than
* Returns a list with all the values less than
* a given value
* Int List : List
*/

less_than(_,[],[]).

less_than(X,[H|T],[H|Result]):-
  X > H,
  less_than(X,T,Result).

less_than(X,[_|T],Result):-
  less_than(X,T,Result).

/*
* More Than or Equal to
* Returns a list with all the values more than or
* equal to a given value
* Int List : List
*/

more_than(_,[],[]).

more_than(X,[H|T],[H|Result]):-
  H >= X,
  more_than(X,T,Result).

more_than(X,[_|T],Result):-
  more_than(X,T,Result).

/*
* Rotate
* Shifts a list to the right or to the left
* depending on a given number of shifts to either side
* List +-Int : List
*/

rotate(List,0,List).

rotate([H|T],X,Result):-
  X > 0,
  X1 is X - 1,
  append(T,[H],Tmp),
  rotate(Tmp,X1,Result).

rotate(T,X,Result):-
  X < 0,
  X1 is -X,
  rotate(Result,X1,T).

/*
* All Roads on Steroids
* Given a set of bidirectional roads
* return a list of the SHORTEST path from
* a given origin to a given destination
* Origin Destiny : List
*/

/*
* KB
* Set of bidirectional roads
*/

road(roma,capua).
road(capua,roma).
road(roma,pisae).
road(pisae,roma).
road(roma,genua).
road(genua,roma).
road(roma,ariminum).
road(ariminum,roma).
road(roma,ancoa).
road(ancoa,roma).
road(roma,castrum_truentinum).
road(castrum_truentinum,roma).

road(capua,brundisium).
road(brundisium,capua).
road(capua,messana).
road(messana,capua).

road(messana,rhegium).
road(rhegium,messana).
road(messana,lilibeum).
road(lilibeum,messana).

road(rhegium,catina).
road(catina,rhegium).

road(catina,syracusae).
road(syracusae,catina).

road(genua,pisae).
road(pisae,genua).
road(genua,placentia).
road(placentia,genua).

road(ariminum,placentia).
road(placentia,ariminum).
road(ariminum,ancoa).
road(ancoa,ariminum).

road(castrum_truentinum,ancoa).
road(ancoa,castrum_truentinum).

/*Actual recursive path-building section*/

path(Origin,Destiny,[Origin,Destiny]):-
  road(Origin,Destiny).

path(Origin,Destiny,[Origin|Result]):-
  road(Origin,X),
  path(X,Destiny,Result).
