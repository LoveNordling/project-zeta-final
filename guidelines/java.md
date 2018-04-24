# Java Guidelines

### Ta ner senaste versionen

Innan du börjar jobba så se till att du sitter på den senaste versionen genom att köra en git pull.

```git
$ git pull
```

## Innan du börjar jobba på något

Lägg upp ett issue med en tydlig titel som beskriver vad du ska göra/problemet. Detta gör det enklare att hålla koll på utvecklingsprocessen och buggar. Om det behövs kan du även förtydliga det du ska göra genom att skriva en kommentar.

### Branches

Alla måste jobba på en separat branch och inte direkt på master. Använd gärna beskrivande namn även på branchen.
För att skapa en ny branch använd kommandot:

```git
$ git checkout -b <branch_name>
```
För att växla mellan branches använd kommandot:

```git
$ git checkout <branch_name>
```

### Commits

Commita ofta så commiten inte blir för stor (det är drygt och kan leda till problem med utdaterad kod etc).
Gör så här:

```bash
$ git add modifieradFil1
$ git add modifieradFil2
$ git commit -m "tydlig beskrivning av vad som gjorts"
```

Använd detta kommando för att adda och commita alla modifierade filer:

```bash
$ git commit -am "beskrivning"
```

Använd sedan kommandot:

```bash
$ git push
```

för att pusha upp dina förändringar till din branch.

### Pull request

Gå sedan in på github och gör en pull request. Om din pull request stänger ett issue (ex du är helt klar med issue 10) så skriv  ``closes #10`` i beskrivningen (eller ditt commit message).

Lägg till två personer som ska reviewa din pull request. För att en förändring ska pushas till master måste dessa ha accepterat förändringarna.

Saker som gör att pull requests nekas är te.x: bugg som upptäckts, inte följt kodstandarden, dålig beskrivning av vad som gjorts.  

##### Nu är du klar! :thumbsup:
