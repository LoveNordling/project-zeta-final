#Description conventions


##Exporterade funktioner

Dokumentationen ska b�rja med /** och sluta med */. F�rst skrivs en kort sammanfattning. Efter det ska den extra information som kodaren tycker borde skrivas ner skrivas ner.

Sedan kommer tags delen, det ska vara en blankrad mellan tags delen och beskrivnings delen. Obligatoriska tags �r param ifall det finns n�gra parametrar, return s� l�nge det inte �r en void och throws/execption f�r varje exception som hanteras. Anv�nd ocks� g�rna see tagen med sunt f�rnuft.

##Hj�lpfunktioner

P� hj�lpfunktioner finns det inga speciella krav f�r dokumentationen. Ifall hj�lpfunktion �r komplicerad eller inte s�rksilt tydlig s� borde dock den dokumenteras �ven om det inte �r ett krav. Dokumentationen beh�ver inte f�lja n�gon standard.

##Anonyma inre funktioner

Skriva dokumentationen f�r anonyma inre funktioner som ett stycke i den ext�rna funktionen om det tycks beh�vas

##Allm�nt

-Anv�nd <p> mellan olika stycken.
-Ifall @author tagen anv�nds skriv namnen i alfabetisk ordning
-baserat p� f�rnamnen.

###Tag ordning:

@author 
@version
@param
@return 
@exception/@throws
@see
@since
@serial
@deprecated
