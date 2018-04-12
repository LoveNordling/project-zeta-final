# Description conventions


## Exporterade funktioner

Dokumentationen ska börja med /** och sluta med */. Först skrivs en kort sammanfattning. Efter det ska den extra information som kodaren tycker borde skrivas ner skrivas ner.

Sedan kommer tags delen, det ska vara en blankrad mellan tags delen och beskrivnings delen. Obligatoriska tags är param ifall det finns några parametrar, return så länge det inte är en void och throws/execption för varje exception som hanteras. Använd också görna see tagen med sunt förnuft.

## Hjälpfunktioner

På hjälpfunktioner finns det inga speciella krav för dokumentationen. Ifall hjälpfunktion är komplicerad eller inte särksilt tydlig så borde dock den dokumenteras även om det inte är ett krav. Dokumentationen behöver inte följa någon standard.

## Anonyma inre funktioner

Skriva dokumentationen för anonyma inre funktioner som ett stycke i den extärna funktionen om det tycks behövas

## Allmänt

-Använd <p> mellan olika stycken.
-Ifall @author tagen används skriv namnen i alfabetisk ordning
-baserat på förnamnen.

### Tag ordning:

@author 
@version
@param
@return 
@exception/@throws
@see
@since
@serial
@deprecated
