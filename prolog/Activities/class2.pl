dad(billy,bob). /*true*/
dad(bob,sue).
dad(bob,alex).
dad(alex,john).
dad(john,pete).
dad(pete,jack).
mom(ana,erika).
mom(erika,sue).
mom(sue,sussy).
mom(ana,billy).

grandparent(X,Z):-
  dad(X,Y),
  dad(Y,Z);
  mom(X,Y),
  mom(Y,Z).

ancestor_of(Ancestor,Descendant):-
  mom(Ancestor,Descendant).

ancestor_of(Ancestor,Descendant):-
  dad(Ancestor,Person),
  ancestor_of(Person,Descendant);
  mom(Ancestor,Person),
  ancestor_of(Person,Descendant).

/*GAME OF THRONES NEXT HEIR*/

/*KB*/
/*dad(maekari,aemon).
dad(maekari,aegonv).
dad(aegonv,jaehaerysii).x
dad(aegonv,rhaelle).
dad(jaehaerysii,aerysii).
dad(aerysii,rhaegar).
dad(aerysii,viserys).
dad(erysii,daenerys).
dad(rhaegar,jonsnow).

mom(lyanna,jonsnow).*/
