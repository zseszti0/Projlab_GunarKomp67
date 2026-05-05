## Fordítás

A program forráskódjának lefordításához a 2. Követelmények című dokumentumban rögzített Java környezet (JDK) és egy Bash-kompatibilis terminál szükséges. A fordítás lépései:

1. Indítsuk el a tárgy oldalán a 6. feladatban leírt virtuális gépet!
2. Töltsük le a Circle Client-et (ha még nem lenne) és csatlakozzunk!
3. Az asztalon nyissuk meg az Eclipse-t!
4. **Window** -> **Show View** -> **Other…**
5. **Git** -> **Git Repositories**
6. Bal alsó sarokban kattintsunk a **“Clone a Git repository”**-ra
7. Az URL mezőbe a következő repository linkjét írjuk:
   `https://github.com/zseszti0/Projlab_GunarKomp67`
8. Kattintsunk a **“Next”** gombra
9. Válasszunk ki egy mappát (vagy maradhat az alapértelmezett)
10. Nyissunk egy Command Prompt-ot
11. Navigáljunk a 9. lépésben kiválasztott mappának gyökérmappájába.
12. Adjuk ki az alábbi parancsokat:
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
