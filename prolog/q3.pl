/*
* Sergio Isaac Mercado Silvano
* A01020382
* Quizz 3
*/

/*Helper Functions*/

on(Elem,[Elem|_]).

on(Elem,[_|Rest]):-
  on(Elem,Rest).

intersects([],_,[]).

intersects([H|T],List,[H|Result]):-
  on(H,List),
  intersects(T,List,Result).

intersects([_|T],List,Result):-
  intersects(T,List,Result).

/*KB*/

ytvid(ynmEePUXLRs,tame_inpala_the_less_I_know_the_better,217,
      uCSj_vy5ts6QMMzQzjPcxjEQ,[video]).

ytvid(hVHUjzZZGQ4,weezer_island_in_the_sun,210,
      uCVaX2SN5hi6LSzalg2V1y4g,[weezer,island,in,the,sun]).

ytvid(vjdkeIKDejdls,gorillaz_feels_good_inc,200,
      jckdeDkKDlleDLE_dlELDm894,[feels,good,inc,gorillaz,sky]).

ytvid(dkelDek3dlLaK,gorillaz_melancholly_hill,232,
      jckdeDkKDlleDLE_dlELDm894,[melancholly,hill,artists,gorillaz,walrus]).

ytvid(alewDL38dDLEJa,queens_of_the_stone_age_go_with_the_flow,179,
      kakeleDLAu3dajks_3dAjdeld,[qosa,go,with,the,flow,cars]).

getAllVidsIDs(L):-
  findall(ID,
    ytvid(ID,_,_,_,_),
    L).

sameChannelVids(CID,VidList):-
  getAllVidsIDs(L),
  smcv(CID,L,AQ VidList).

smcv(_,[],[]).

smcv(CID,[H|T],[H|Result]):-
   ytvid(H,Title,)

getAvailableVids(Title):-
  ytvid(Title,_,_,_,_).

getVideoID(Title,ID):-
  ytvid(ID,Title,_,_,_).

getVidTags(Title,TagList):-
  ytvid(_,Title,_,_,TagList).

getVideosByChannel(CID,VideoName):-
  ytvid(_,VideoName,_,CID,_).

commonVidTags(Title1,Title2,List):-
  getVidTags(Title1,L1),
  getVidTags(Title2,L2),
  intersects(L2,L1,List).
