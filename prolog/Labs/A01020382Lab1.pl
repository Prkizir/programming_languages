/*Sergio Isaac Mercado Silvano.
* A01020382.
* Prolog: Lab 1.
*/

/*
*Compatible
* Returns true two people share a hobby
* Returns false otherwise
* Name Name : True|Fail
*/

/*KD*/

hobby(juan,kaggle).
hobby(luis,hack).
hobby(elena,tennis).
hobby(midori,videogame).
hobby(simon,sail).
hobby(simon,kaggle).
hobby(laura,hack).
hobby(hans,videogame).

compatible(X,Y):- /*Receives Two People*/
  hobby(X,Z),   /* Z must be the same */
  hobby(Y,Z).   /* to return true */

/*All Roads
* Returns true if you can get from a city
* to another. Fails otherwise.
* All roads can lead to rome.
* Name Name : True|Fail
*/

/*KD*/

road(pisae,roma).
road(ariminum,roma).
road(ancona,roma).
road(castrum_truentinum,roma).
road(genua,roma).
road(capua,roma).

road(genua,placentia).
road(placentia,ariminum).
road(ariminum,ancona).
road(ancona,castrum_truentinum).
road(brundisium,capua).
road(lilibeum,messana).
road(messana,capua).
road(syracusae,catina).
road(catina,rhegium).
road(rhegium,messana).
road(genua,pisae).


can_get_to(Org,Dest):- /*Evaluates if origin --> destination [base casse]*/
  road(Org,Dest),!.

can_get_to(Org,Dest):- /*Recursively  follows the roads to other cities*/
  road(Org,X),         /*until hitting base case */
  can_get_to(X,Dest).

/*
* All Roads: size
* Counts number of cities inbetween
* Name Name : Int
*/

size(X,Y,Z):- /*Evaluates if origin --> destination [base case]*/
  road(X,Y),  /*Then Z = 0 as there are no cities inbetween*/
  Z is 0.

size(X,Y,Z):- /*Recursive counter similar to can_get_to*/
  road(X,Tmp), /*Counts number of cities inbetween origin*/
  size(Tmp,Y,Z1), /*and destination*/
  Z is Z1 + 1.


/*
* Min Value
* Returns the minimum value of three given numbers
* Real Real Real : Real
*/

min(A,B,C,Z):- /*Evaluates all non-repetitive options*/
  A < B, A < C, Z is A;
  B < A, B < C, Z is B;
  C < A, C < B, Z is C;

  A = B, A < C, Z is A;
  A = B, A > C, Z is C;

  A = C, A < B, Z is A;
  A = C, A > B, Z is A;

  C = B, A < C, Z is A;
  C = B, A > C, Z is A;

  A = B, A = C, Z is A.

/*
* GCD
* Returns the greatest common divisor of two numbers
* with the Euclidian method to find GDC.
* Int Int : Int
*/

gcd(A,B,Z):- /*Base case: GDC of 0 and A is B*/
  A = 0,     /*           GDC of A and 0 is A*/
  Z is B;
  B = 0,
  Z is A.

gcd(A,B,Z):-
  A > B,        /*If A greater than B*/
  X is A mod B, /*Remainder of A/B*/
  gcd(B,X,Z).

gcd(A,B,Z):-
  B > A,        /*If B greater than A*/
  X is B mod A, /*Remainder of B/A*/
  gcd(A,X,Z).
