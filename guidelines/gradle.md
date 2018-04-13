# Using Gradle
Gradle är ett byggverktyg som underlättar kompilering och struktur av projektet. Om man använder en IDE som har stöd för Gradle kan det mesta göras automatiskt av ett Gradleplugin. T.ex om man kör sin mainfil i Intellij så komileras allt som behöver kompileras automatiskt och man behöver inte göra några operationer från terminalen.

Klicka [här](https://gradle.org/install/?_ga=2.238320325.491585748.1523532123-2042413676.1512398121) för instruktioner hur man installerar Gradle.

Klicka [här](https://guides.gradle.org/building-kotlin-jvm-libraries/) för instruktioner hur man genererar mappstrukturen för projektet (behöver bara göras en gång). Då genereras en liknande mappstruktur:

<pre>
my-kotlin-library
├── build.gradle.kts
└── src
    ├── main
    │   └── kotlin  
    │       └── org  
    │           └── example  
    │               └── MyLibrary.kt  
    └── test  
        └── kotlin  
            └── org  
                └── example  
                    └── MyLibraryTest.kt  
</pre>
Filen **gradle.build.kts** är central för projektet, där lägger man till dependencies för t.ex. testning och dokumentation. Man kan även definiera tasks i samma fil, dvs olika operationer som kan utföras. Ett exempel på en task skulle kunna vara att kopiera alla filer från en mapp till en annan. 

Gradle har många bra [guider](https://gradle.org/guides/) för att komma igång.