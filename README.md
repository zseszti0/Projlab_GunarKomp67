## Fordítás

A program forráskódjának lefordításához a 2. Követelmények című dokumentumban rögzített Java környezet (JDK) és egy Bash-kompatibilis terminál szükséges. A fordítás lépései:

1.Indítsuk el a tárgy oldalán a 6. feladatban leírt virtuális gépet.
2.Nyissuk meg a https://github.com/zseszti0/Projlab_GunarKomp67 oldalt.
3.Kattintsunk a zöld “Code” gombra
4.Válasszunk ki a “Download ZIP”
5.Tömörítsük ki a letöltött fájlt egy gyökérmappába.
6.Nyissunk egy Command Prompt-ot
7.Navigáljunk az 5. lépésben létrehozott gyökérmappába.
8.Adjuk ki az alábbi parancsokat:

    ```cmd
    mkdir bin
    dir /s /b src\*.java > sources.txt & javac -d bin @sources.txt & del sources.txt
    ```
    
## Futtatás

A sikeres fordítást követően a futtatható kód elindítása szintén a megadott parancssori környezetből történik. A futtatás lépései:

1. A program elindításához adjuk ki az alábbi parancsot:
    ```cmd
    java -cp bin test.TestOracle
    ```
    A parancs, az összes tesztesetet lefuttatja, majd a gyökérmappába egy output.txt file-ba összegzi az eredményt. Illetve minden tesztmappába egy output.txc-ben látható a pontos futássi output és az asserttel való összehasonlítási eredmény. Az output.xml-ben pedig maga az elért játékállapot a futás végére.

2. Ha egy specifikus tesztet szeretnénk futtatni, a futtatási argumentumokhoz írjuk ba a teszt mappa elérési útját (a gyökérmappából)
    Például: 
    ```cmd
    java -cp bin test.TestOracle tests/buschaffeur/ap/bap
    ```
