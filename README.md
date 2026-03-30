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
11. Navigáljunk a 9. lépésben kiválasztott mappának gyökérmappáján belül az `src` könyvtárba.
12. Adjuk ki az alábbi parancsokat:
    ```cmd
    dir /s /B *.java > sources.txt
    javac -g @sources.txt
    ```

## Futtatás

A sikeres fordítást követően a futtatható kód elindítása szintén a megadott parancssori környezetből történik. A futtatás lépései:

1. Győződjünk meg róla, hogy a terminál továbbra is az `src` mappában áll (ahol a fordítást az előző lépésben elvégeztük).
2. A program elindításához adjuk ki az alábbi parancsot:
    ```cmd
    java logger.SkeletonTracer
    ```
